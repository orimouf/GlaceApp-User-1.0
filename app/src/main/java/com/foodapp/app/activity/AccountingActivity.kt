package com.foodapp.app.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.foodapp.app.R
import com.foodapp.app.base.BaseActivity
import com.foodapp.app.utils.Common.getCurrentLanguage
import kotlin.collections.ArrayList
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.foodapp.app.adaptor.AccountingAdaptor
import com.foodapp.app.adaptor.PaymentAdaptor
import com.foodapp.app.model.*
import kotlinx.android.synthetic.main.activity_accounting.*
import kotlinx.android.synthetic.main.activity_cart.ivBack
import kotlinx.android.synthetic.main.activity_cart.ivHome
import kotlinx.android.synthetic.main.dlg_payment.*
import kotlinx.android.synthetic.main.dlg_updateorder.*
import kotlinx.android.synthetic.main.dlg_updateproduct.etUpdateStatus
import kotlinx.android.synthetic.main.dlg_updateproduct.tvCancelUpdate
import kotlinx.android.synthetic.main.dlg_updateproduct.tvUpdate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AccountingActivity : BaseActivity() {

    var choseDate: String = ""

    override fun setLayout(): Int {
        return R.layout.activity_accounting
    }

    /**
     * Function is used to show the list of inserted data.
     */
    private fun setupListOfOrderDataIntoRecyclerView(choseDate: String) {
        if (getOrderList(choseDate).size > 0) {
            rvAccounting.visibility = View.VISIBLE
            tvNoAccountingDataFound.visibility = View.GONE

            rvAccounting.layoutManager = LinearLayoutManager(this)
            val orderAdapter = AccountingAdaptor(this, getOrderList(choseDate))
            rvAccounting.adapter = orderAdapter
        } else {
            rvAccounting.visibility = View.GONE
            tvNoAccountingDataFound.visibility = View.VISIBLE
        }
    }

    private fun setupListOfPaymentDataIntoRecyclerView(choseDate: String) {
        if (getPaymentList(choseDate).size > 0) {
            rvPayment.visibility = View.VISIBLE
            tvNoPaymentDataFound.visibility = View.GONE

            rvPayment.layoutManager = LinearLayoutManager(this)
            val paymentAdapter = PaymentAdaptor(this, getPaymentList(choseDate))
            rvPayment.adapter = paymentAdapter
        } else {
            rvPayment.visibility = View.GONE
            tvNoPaymentDataFound.visibility = View.VISIBLE
        }
    }

    /**
     * Function is used to get the Items List from the database table.
     */
    private fun getOrderList(date: String): ArrayList<OrderSummaryModel> {
        val databaseHandler = DatabaseHandler(this)

        return databaseHandler.viewOrderSummary(
            1,
            1,
            false,
            getAll = true,
            date = date
        )
    }

    private fun getPaymentList(date: String): ArrayList<VerssementModel> {
        val databaseHandler = DatabaseHandler(this)

        return databaseHandler.viewVerssemnt(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun InitView() {
        this@AccountingActivity.getCurrentLanguage(false)

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        choseDate = current.format(formatter)



        tvDateView.text = choseDate

        setupListOfOrderDataIntoRecyclerView(choseDate)

        cvCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->

            val choseMonth = when(month) {
                0 -> "01"
                1 -> "02"
                2 -> "03"
                3 -> "04"
                4 -> "05"
                5 -> "06"
                6 -> "07"
                7 -> "08"
                8 -> "09"
                9 -> "10"
                10 -> "11"
                else -> "12"
            }
            val choseDay = when(dayOfMonth) {
                1 -> "01"
                2 -> "02"
                3 -> "03"
                4 -> "04"
                5 -> "05"
                6 -> "06"
                7 -> "07"
                8 -> "08"
                9 -> "09"
                else -> dayOfMonth.toString()
            }
            choseDate =  "$choseDay-${choseMonth}-$year"
            tvDateView.text = choseDate
            llCalendar.visibility = View.GONE
            setupListOfOrderDataIntoRecyclerView(choseDate)
        }

        tvDateView.setOnClickListener {
            if (llCalendar.visibility == 0) llCalendar.visibility = View.GONE
            else llCalendar.visibility = View.VISIBLE
        }

        tvSwitchOrder.setOnClickListener {
            llOrderList.visibility = View.VISIBLE
            llPaymentList.visibility = View.GONE
            tvSwitchPayment.setTextColor(Color.rgb(195,195,195))
            tvSwitchOrder.setTextColor(Color.rgb(254,115,76))
            setupListOfOrderDataIntoRecyclerView(choseDate)
        }

        tvSwitchPayment.setOnClickListener {
            llOrderList.visibility = View.GONE
            llPaymentList.visibility = View.VISIBLE
            tvSwitchOrder.setTextColor(Color.rgb(195,195,195))
            tvSwitchPayment.setTextColor(Color.rgb(254,115,76))
            setupListOfPaymentDataIntoRecyclerView(choseDate)
        }

        ivBack.setOnClickListener {
            val intent=Intent(this@AccountingActivity,DashboardActivity::class.java).putExtra("pos","1")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        ivHome.setOnClickListener {
            val intent=Intent(this@AccountingActivity,DashboardActivity::class.java).putExtra("pos","1")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    fun makeItCheck(id: Int, type: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Check Order")
        builder.setMessage("Are you sure you wants to check order id: ${id}.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface, _ ->

            if (type == "order") {
                val databaseHandler = DatabaseHandler(this)
                val status = databaseHandler.updateOrderSummary(OrderSummaryModel(id,"", "", 0, 0,
                    0, "","",0,"",1,"0","0","0",0), "is_check")
                if (status > -1) {
                    Toast.makeText(
                        applicationContext,
                        "Order checked successfully.",
                        Toast.LENGTH_LONG
                    ).show()

                    setupListOfOrderDataIntoRecyclerView(choseDate)
                }
            } else {
                val databaseHandler = DatabaseHandler(this)
                val status = databaseHandler.updateVerssement(VerssementModel(id,"", "", "", "",
                    "", "","",0,0,"0","0","0", "0"), "is_check")
                if (status > -1) {
                    Toast.makeText(
                        applicationContext,
                        "Payment checked successfully.",
                        Toast.LENGTH_LONG
                    ).show()

                    setupListOfOrderDataIntoRecyclerView(choseDate)
                }
            }

            dialogInterface.dismiss()
        }

        builder.setNegativeButton("No") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun deleteOrder(id: Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Order")
        builder.setMessage("Are you sure you wants to delete order id: ${id}.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface, _ ->

            val databaseHandler = DatabaseHandler(this)
            val status = databaseHandler.deleteOrderSummary(id)
            val status2 = databaseHandler.deleteAllProduct(id)
            if (status > -1 && status2 > -1) {
                Toast.makeText(
                    applicationContext,
                    "Order deleted successfully.",
                    Toast.LENGTH_LONG
                ).show()

                setupListOfOrderDataIntoRecyclerView(choseDate)
            }

            dialogInterface.dismiss()
        }

        builder.setNegativeButton("No") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun deletePayment(id: Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Payment")
        builder.setMessage("Are you sure you wants to delete Payment id: ${id}.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface, _ ->

            val databaseHandler = DatabaseHandler(this)
            val status = databaseHandler.deleteVerssement(
                VerssementModel(id,"", "", "", "", "",
                    "","",0,0,"0","0","0","0"))
            if (status > -1) {
                Toast.makeText(
                    applicationContext,
                    "Payment deleted successfully.",
                    Toast.LENGTH_LONG
                ).show()

                setupListOfOrderDataIntoRecyclerView(choseDate)
            }

            dialogInterface.dismiss()
        }

        builder.setNegativeButton("No") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun editPayment(pay: VerssementModel) {
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)

        updateDialog.setContentView(R.layout.dlg_payment)

        updateDialog.tvClientNamePayment.text = pay.client_name
        updateDialog.tvOldTotalSomme.text = pay.old_somme

        updateDialog.tvPayment.setOnClickListener(View.OnClickListener {

            val payment = updateDialog.etPaymentVerssi.text.toString()
            val rest = updateDialog.etPaymentRest.text.toString()

            val databaseHandler = DatabaseHandler(this)

            if (payment.isNotEmpty() && rest.isNotEmpty()) {

                val status =
                    databaseHandler.updateVerssement(VerssementModel(0,"", pay.id.toString(), pay.client_name, pay.region,
                        pay.old_somme, payment, rest, 0, 0, "0","0","0", pay.date))
                if (status > -1) {
                    Toast.makeText(applicationContext, "Payment updated", Toast.LENGTH_LONG).show()

                    updateDialog.dismiss()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Data field cannot be blank",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        updateDialog.tvCancelPayment.setOnClickListener(View.OnClickListener {
            updateDialog.dismiss()
        })
        //Start the dialog and display it on screen.
        updateDialog.show()
    }

    fun editOrder(order: OrderSummaryModel) {

        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        /*Set the screen content from a layout resource.
         The resource will be inflated, adding all top-level views to the screen.*/
        updateDialog.setContentView(R.layout.dlg_updateorder)

        updateDialog.tvClientNameUpdateOrder.text = order.client_name
        updateDialog.etUpdateTotalSomme.setText(order.total_to_pay.toString())
        updateDialog.etUpdateVerssi.setText(order.verssi)
        updateDialog.etUpdateRest.setText(order.rest)
        updateDialog.etUpdateStatus.setText(order.iscredit.toString())

        updateDialog.tvUpdate.setOnClickListener(View.OnClickListener {

            val total_to_pay = updateDialog.etUpdateTotalSomme.text.toString()
            val verssi = updateDialog.etUpdateVerssi.text.toString()
            val rest = updateDialog.etUpdateRest.text.toString()
            val _status = updateDialog.etUpdateStatus.text.toString()

            val databaseHandler = DatabaseHandler(this)

            val statusInt = _status.toInt()

            if (total_to_pay.isNotEmpty() && verssi.isNotEmpty() && _status.isNotEmpty() && rest.isNotEmpty()) {

                val status =
                    databaseHandler.updateOrderSummary(OrderSummaryModel(order.id,order.server_id, order.client_name, order.client_id,
                        order.product_list_id, total_to_pay.toInt(), verssi, rest, statusInt, order.date,
                        order.is_check, "0","0","0",0), "All")
                if (status > -1) {
                    Toast.makeText(applicationContext, "Order Updated.", Toast.LENGTH_LONG).show()

                    setupListOfOrderDataIntoRecyclerView(order.date)

                    updateDialog.dismiss()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Data field cannot be blank",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        updateDialog.tvCancelUpdate.setOnClickListener(View.OnClickListener {
            updateDialog.dismiss()
        })
        //Start the dialog and display it on screen.
        updateDialog.show()
    }

    override fun onBackPressed() {
        val intent=Intent(this@AccountingActivity,DashboardActivity::class.java).putExtra("pos","1")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        this@AccountingActivity.getCurrentLanguage(false)
    }
}
