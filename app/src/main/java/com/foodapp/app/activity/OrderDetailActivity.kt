package com.foodapp.app.activity

import android.content.ContentValues
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.foodapp.app.R
import com.foodapp.app.adaptor.OrderSummuryAdaptor
import com.foodapp.app.base.BaseActivity
import com.foodapp.app.model.*
import kotlinx.android.synthetic.main.activity_orderdetail.*
import java.lang.Integer.parseInt
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

import android.os.Environment
import android.provider.MediaStore
import java.io.*


@Suppress("UNSAFE_CALL_ON_PARTIALLY_DEFINED_RESOURCE")
class OrderDetailActivity:BaseActivity() {

    var clientName : String = ""
    var clientId : String = ""
    var orderId : String = ""
    var productListId : String = ""
    var totalCredit : String = ""
    var PrintMsg : String = ""
    lateinit var orderList : ArrayList<OrderSummaryModel>

    override fun setLayout(): Int {
        return R.layout.activity_orderdetail
    }

    /**
     * Function is used to show the list of inserted data.
     */
    private fun setupListOfDataIntoRecyclerViewOrderSummary(clientID: Int, productListID: Int) {
        orderList = getOrderSummaryList(clientID, productListID)
        if (orderList.size > 0) {

            val pricesListString = getClientList(clientID).get(0).prices
            val delim = ":"
            val pricesList = pricesListString.split(delim).toTypedArray()

            rvOrderSummaryItem.visibility = View.VISIBLE
            // Set the LayoutManager that this RecyclerView will use.
            rvOrderSummaryItem.layoutManager = LinearLayoutManager(this)
            // Adapter class is initialized and list is passed in the param.
            val orderSummaryAdapter = OrderSummuryAdaptor(this, getAllProductList(productListID), pricesList, orderList[0].id)
            // adapter instance is set to the recyclerview to inflate the items.
            rvOrderSummaryItem.adapter = orderSummaryAdapter
        } else {
            rvOrderSummaryItem.visibility = View.GONE
        }
    }

//    fun fff(price: String){
//
//        val delim = "."
//        val priceList = price.split(delim).toTypedArray()
//        
//        val finalPriceInt = priceList[0].toInt() + 
//    }

    /**
     * Function is used to get the Items List from the database table.
     */
    private fun getOrderSummaryList(id: Int, productListId: Int): ArrayList<OrderSummaryModel> {
        val databaseHandler = DatabaseHandler(this)

        return databaseHandler.viewOrderSummary(id, productListId)
    }

    /**
     * Function is used to get the Items List from the database table.
     */
    private fun getAllProductList(productID: Int): ArrayList<AllProductModel> {
        val databaseHandler = DatabaseHandler(this)

        return databaseHandler.viewAllProduct(productID)
    }

    /**
     * Function is used to get the All Product List from the database table.
     */
    private fun getClientList(id: Int): ArrayList<ClientModel> {
        val databaseHandler = DatabaseHandler(this)

        return databaseHandler.viewClient(id, true)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun InitView() {
        clientName = intent.getStringExtra("clientNameOrder").toString()
        clientId = intent.getStringExtra("clientIdOrder").toString()
        orderId = intent.getStringExtra("orderIdOrder").toString()
        productListId = intent.getStringExtra("productListIdOrder").toString()

        val infoClient = getClientList(parseInt(clientId))[0]
        val getRegionOfClient = infoClient.region
        val getOldCredit = infoClient.old_credit
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        val formatted = current.format(formatter)

        tvClientNameInOrderDetail.text = "CLIENT NAME:    $clientName   |    $getRegionOfClient"
        tvDateDelivery.text = "DATE:    $formatted"
        tvPar.text = "PAR:    AMIR / camion"
        tvOldCredit.text = getOldCredit

        setupListOfDataIntoRecyclerViewOrderSummary(parseInt(clientId), parseInt(productListId))

        ivBackOrderDetail.setOnClickListener {
            finish()
        }

        tvOrderPaye.setOnClickListener {
            tvOrderStatusPayment.text = "Payé"
            tvOrderStatusPayment.visibility = View.VISIBLE
            tvOrderPaye.visibility = View.GONE
            tvOrderNoPaye.visibility = View.GONE
            tvPrintFacture.visibility = View.VISIBLE

            val numberArticle = tvNumberItems.text
            val getProductList = tvProductList.text
            val totalPrice = tvOrderTotalPrice.text

            PrintMsg = " TELEPHONE:  07.81.28.95.66 / 05.59.670962 \n \n" +
                    " CLIENT ID:  $clientId \n" +
                    " CLIENT NAME:  $clientName | $getRegionOfClient  \n" +
                    " DATE:   $formatted  \n" +
                    " PAR:   AMIR   |   CAMION 01  \n \n" +
                    "=============================================== \n" +
                    " Products    | QTY | UNITY | U.PRICE |     PRICE \n" +
                    "$getProductList " +
                    "=============================================== \n" +
                    " $numberArticle \n" +
                    " TOTAL.                             $totalPrice,00 Da \n" +
                    "----------------------------------------------- \n" +
                    " STATUS.                                PAYE \n \n" +
                    "***********************************************\n" +
                    " TOTAL CREDIT.                   $getOldCredit,00 Da \n" +
                    "\n" + "\n" + "\n"

            val databaseHandler = DatabaseHandler(applicationContext)
            val status =
                databaseHandler.updateTotalToPayOrderSummary(parseInt(orderId), totalPrice.toString().toFloat(), totalPrice.toString().toInt(), 0, 0)
            if (status > -1) {
                Toast.makeText(applicationContext, "Payment Done", Toast.LENGTH_SHORT).show()
            }
        }

        tvOrderNoPaye.setOnClickListener {
            tvOrderStatusPayment.text = "Non Payé"
            tvOrderStatusPayment.visibility = View.VISIBLE
            llVersement.visibility = View.VISIBLE
            etVersement.visibility = View.VISIBLE
            tvExit.visibility = View.VISIBLE
            tvDoneVersement.visibility = View.VISIBLE
            tvOrderPaye.visibility = View.GONE
            tvOrderNoPaye.visibility = View.GONE
        }

        tvDoneVersement.setOnClickListener {
            val totalToPay = parseInt(tvOrderTotalPrice.text.toString())
            val versement = etVersement.text.toString()
            val oldCredit = parseInt(tvOldCredit.text.toString())
            val rest = totalToPay - parseInt(versement)
            totalCredit = (rest + oldCredit).toString()

            etVersement.visibility = View.GONE
            tvDoneVersement.visibility = View.GONE
            tvOrderVersement.visibility = View.VISIBLE
            tvDecoPrice3.visibility = View.VISIBLE
            flReste.visibility = View.VISIBLE
            tvPrintFacture.visibility = View.VISIBLE

            tvOrderVersement.text = versement
            tvOrderReste.text = rest.toString()
            tvOldCredit.text = totalCredit

            val numberArticle = tvNumberItems.text
            val getProductList = tvProductList.text
            val totalPrice = tvOrderTotalPrice.text

            PrintMsg = " TELEPHONE:  07.81.28.95.66 / 05.59.670962 \n \n" +
                    " CLIENT ID:  $clientId \n" +
                    " CLIENT NAME:  $clientName | $getRegionOfClient  \n" +
                    " DATE:   $formatted  \n" +
                    " PAR:   AMIR   |   CAMION 01  \n \n" +
                    "=============================================== \n" +
                    " Products    | QTY | UNITY | U.PRICE |     PRICE \n" +
                    "$getProductList " +
                    "=============================================== \n" +
                    " $numberArticle \n" +
                    " TOTAL.                             $totalPrice,00 Da \n" +
                    "----------------------------------------------- \n" +
                    " STATUS.                            NON PAYE \n \n" +
                    " VERSEMENT.                      $versement,00 Da \n" +
                    " RESTE.                          $rest,00 Da \n" +
                    "***********************************************\n" +
                    " TOTAL CREDIT.                   $totalCredit,00 Da \n" +
                    "\n" + "\n" + "\n"

            var isCredit = if (rest == 0) 0 else 1

            val databaseHandler = DatabaseHandler(applicationContext)
            val status =
                databaseHandler.updateTotalToPayOrderSummary(parseInt(orderId), totalToPay.toFloat(), parseInt(versement), rest, isCredit)
            val thisClient = getClientList(clientId.toInt())[0]
            val clientBon = thisClient.credit_bon + 1
            val statusClient =
                databaseHandler.updateClient(ClientModel( thisClient.id, thisClient.server_id, thisClient.client_name, thisClient.phone, thisClient.prices, thisClient.region,
                    totalCredit, thisClient.is_frigo, thisClient.is_promo, thisClient.is_credit, clientBon, thisClient.last_serve, "0", "0", "0", 0))
            if (status > -1 && statusClient > -1) {
                Toast.makeText(applicationContext, "Payment Done", Toast.LENGTH_SHORT).show()
            }
        }

        tvExit.setOnClickListener {
            tvOrderStatusPayment.text = ""
            tvOrderStatusPayment.visibility = View.GONE
            llVersement.visibility = View.GONE
            etVersement.visibility = View.GONE
            tvDoneVersement.visibility = View.GONE
            tvOrderPaye.visibility = View.VISIBLE
            tvOrderNoPaye.visibility = View.VISIBLE
        }

        tvPrintFacture.setOnClickListener {
            screenshoot(clientId)

            val intent = Intent(this@OrderDetailActivity, PrintActivity::class.java)
            intent.putExtra("PrintMsg", PrintMsg)

            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    private fun screenshoot(clientID: String) {
        val date = Date()
        val now: CharSequence = android.text.format.DateFormat.format("__yyyy-MM-dd_HH-mm-ss", date)
        val root = window.decorView
        val cw = ContextWrapper(this)
        val filename = "ID-" + clientID + now + ".jpg"

        root.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(root.drawingCache)
        root.isDrawingCacheEnabled = false
        try {
            if (bitmap != null) saveMediaToStorage(filename, bitmap)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // this method saves the image to gallery
    private fun saveMediaToStorage(fileName: String, bitmap: Bitmap) {
        // Output stream
        var fos: OutputStream? = null

        // For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // getting the contentResolver
            this.contentResolver?.also { resolver ->

                // Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    // putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Glace_2022/")
                }

                // Inserting the contentValues to
                // contentResolver and getting the Uri
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                // Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            // These for devices running on android < Q
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/Glace_2022/")
            val image = File(imagesDir, fileName)
            fos = FileOutputStream(image)
        }

        fos?.use {
            // Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this , "Captured View and saved to Gallery" , Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        finish()
    }

}