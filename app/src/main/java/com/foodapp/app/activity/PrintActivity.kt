package com.foodapp.app.activity

import android.app.Activity
import android.os.Bundle
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.app.R
import com.foodapp.app.adaptor.DeviceAdaptor
import com.foodapp.app.model.DeviceModel
import com.foodapp.app.utils.Common
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_print.*
import kotlinx.android.synthetic.main.activity_print.ivBack
import kotlinx.android.synthetic.main.activity_print.ivHome
import kotlinx.android.synthetic.main.activity_print.tvNoDataFound
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Exception
import java.nio.charset.Charset
import java.util.UUID;

class PrintActivity : Activity() {

    var PrintMsg : String = ""

    // will show the statuses like bluetooth open, close or data sent
    var myLabel: TextView? = null
    var myDeviceList: RecyclerView? = null

    // android built in classes for bluetooth operations
    var mBluetoothAdapter: BluetoothAdapter? = null
    var mmSocket: BluetoothSocket? = null
    var mmDevice: BluetoothDevice? = null

    // needed for communication to bluetooth device / network
    var mmOutputStream: OutputStream? = null
    var mmInputStream: InputStream? = null
    var workerThread: Thread? = null

    lateinit var readBuffer: ByteArray
    var readBufferPosition = 0

    @Volatile
    var stopWorker = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print)

        PrintMsg = intent.getStringExtra("PrintMsg").toString()

        try {
            // we are going to have three buttons for specific functions
            val openButton = findViewById<View>(R.id.tvOpen) as TextView
            val sendButton = findViewById<View>(R.id.tvSend) as TextView
            val closeButton = findViewById<View>(R.id.tvClose) as TextView

            // text label and input box
            myLabel = findViewById<View>(R.id.tvMsgLabel) as TextView
            myDeviceList = findViewById<View>(R.id.rvDeviceList) as RecyclerView

            // open bluetooth connection
            openButton.setOnClickListener {
                try {
                    findBT("")
                    openBT()
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            }

            // send data typed by the user to be printed
            sendButton.setOnClickListener {
                try {
                    sendData()
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            }

            // close bluetooth connection
            closeButton.setOnClickListener(View.OnClickListener {
                try {
                    closeBT()
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            })

            ivBack.setOnClickListener {
                val intent=Intent(this@PrintActivity,DashboardActivity::class.java).putExtra("pos","1")
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }

            ivHome.setOnClickListener {
                val intent=Intent(this@PrintActivity,DashboardActivity::class.java).putExtra("pos","1")
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }

            tvDoneExit.setOnClickListener {
                closeBT()
                val intent=Intent(this@PrintActivity,DashboardActivity::class.java).putExtra("pos","1")
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // this will find a bluetooth printer device
    fun findBT(deviceName: String = "") {
//        Common.showLoadingProgress(this@PrintActivity)
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            if (mBluetoothAdapter == null) {
                myLabel!!.text = "No bluetooth adapter available"
            }
            if (!mBluetoothAdapter?.isEnabled!!) {
                val enableBluetooth = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBluetooth, 0)
            }
            val pairedDevices = mBluetoothAdapter!!.bondedDevices

            if (pairedDevices.size > 0) {
                myLabel!!.text = "Bluetooth device found."

                if (deviceName != "") {
                    mmDevice = pairedDevices.find { it.name == deviceName }
                    rvDeviceList.isAnimating
                } else {
                    tvNoDataFound.visibility = View.GONE
                    val deviceArrayList: ArrayList<DeviceModel> = ArrayList<DeviceModel>()

                    for (device in pairedDevices) {
//                        // RPP300 is the name of the bluetooth printer device
//                        // we got this name from the list of paired devices
                        if (device.name == "XP-P3302B-37B7") {
                            val dm = DeviceModel(device.name,device.address)
                            deviceArrayList.add(dm)
                            mmDevice = device
                            break
                        }
                    }

//                    Common.dismissLoadingProgress()
                    rvDeviceList.visibility = View.VISIBLE
                    // Set the LayoutManager that this RecyclerView will use.
                    rvDeviceList.layoutManager = LinearLayoutManager(this)
                    // Adapter class is initialized and list is passed in the param.
                    val deviceAdapter = DeviceAdaptor(this, deviceArrayList)
                    // adapter instance is set to the recyclerview to inflate the items.
                    rvDeviceList.adapter = deviceAdapter
                }

            } else {
//                Common.dismissLoadingProgress()
                myLabel!!.text = "Bluetooth device not found."
                rvDeviceList.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // tries to open a connection to the bluetooth printer device
    @Throws(IOException::class)
    fun openBT() {
//        Common.showLoadingProgress(this@PrintActivity)
        try {

            // Standard SerialPortService ID
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
            mmSocket = mmDevice!!.createRfcommSocketToServiceRecord(uuid)
            mmSocket?.connect()
            mmOutputStream = mmSocket?.outputStream
            mmInputStream = mmSocket?.inputStream
            beginListenForData()
            myLabel!!.text = "Bluetooth Opened"
//            Common.dismissLoadingProgress()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // close the connection to bluetooth printer.
    @Throws(IOException::class)
    fun closeBT() {
        try {
            stopWorker = true
            mmOutputStream!!.close()
            mmInputStream!!.close()
            mmSocket!!.close()
            myLabel!!.text = "Bluetooth Closed"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*
     * after opening a connection to bluetooth printer device,
     * we have to listen and check if a data were sent to be printed.
     */
    fun beginListenForData() {
        try {
            val handler = Handler()

            // this is the ASCII code for a newline character
            val delimiter: Byte = 10
            stopWorker = false
            readBufferPosition = 0
            readBuffer = ByteArray(1024)
            workerThread = Thread {
                while (!Thread.currentThread().isInterrupted && !stopWorker) {
                    try {
                        val bytesAvailable = mmInputStream!!.available()
                        if (bytesAvailable > 0) {
                            val packetBytes = ByteArray(bytesAvailable)
                            mmInputStream!!.read(packetBytes)
                            for (i in 0 until bytesAvailable) {
                                val b = packetBytes[i]
                                if (b == delimiter) {
                                    val encodedBytes = ByteArray(readBufferPosition)
                                    System.arraycopy(
                                        readBuffer, 0,
                                        encodedBytes, 0,
                                        encodedBytes.size
                                    )

                                    // specify US-ASCII encoding
                                    val charset: Charset = Charsets.ISO_8859_1   //.US_ASCII
                                    val data = String(encodedBytes, charset)
                                    readBufferPosition = 0

                                    // tell the user data were sent to bluetooth printer device
                                    handler.post { myLabel!!.text = data }
                                } else {
                                    readBuffer[readBufferPosition++] = b
                                }
                            }
                        }
                    } catch (ex: IOException) {
                        stopWorker = true
                    }
                }
            }
            workerThread!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // this will send text data to be printed by the bluetooth printer
    @Throws(IOException::class)
    fun sendData() {
//            Common.showLoadingProgress(this@PrintActivity)
        try {
            // the text typed by the user
            var msg = PrintMsg
            mmOutputStream!!.write(msg.toByteArray())

//                Common.dismissLoadingProgress()
            // tell the user data were sent
            myLabel!!.text = "Data sent."
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        val intent=Intent(this@PrintActivity,DashboardActivity::class.java).putExtra("pos","1")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        Common.getCurrentLanguage(this@PrintActivity, false)
    }

}