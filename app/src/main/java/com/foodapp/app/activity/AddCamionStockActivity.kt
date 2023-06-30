package com.foodapp.app.activity


import android.app.AlertDialog
import android.content.Intent
import com.foodapp.app.R
import com.foodapp.app.base.BaseActivity
import com.foodapp.app.utils.Common.getCurrentLanguage
import android.os.Bundle
import android.widget.Toast
import com.foodapp.app.model.CamionStockModel
import kotlinx.android.synthetic.main.activity_addcamionstock.*
import kotlinx.android.synthetic.main.activity_addproduct.etName
import kotlinx.android.synthetic.main.activity_cart.ivBack
import kotlinx.android.synthetic.main.activity_cart.ivHome


class AddCamionStockActivity : BaseActivity() {

    override fun setLayout(): Int {
        return R.layout.activity_addcamionstock
    }

    override fun InitView() {
        this@AddCamionStockActivity.getCurrentLanguage(false)


        ivBack.setOnClickListener {
            finish()
        }

        ivHome.setOnClickListener {
            val intent=Intent(this@AddCamionStockActivity,DashboardActivity::class.java).putExtra("pos","1")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

//        tvCheckout.setOnClickListener {
//
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addcamionstock)
        //setSupportActionBar(toolbar)

        btnAddStock.setOnClickListener {
            addCamionStock()
        }

        //setupListOfDataIntoRecyclerView()
    }

    //Method for saving the employee records in database
    private fun addCamionStock() {
        val name = etName.text.toString()
        val qty = etQty.text.toString()
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        val qtyInt = qty.toInt()

        if (!name.isEmpty() && !qty.isEmpty()) {
            val status =
                databaseHandler.addStock(CamionStockModel(0,"", name, qtyInt, "0","0","0",0))
            if (status > -1) {
                Toast.makeText(applicationContext, "Stock saved", Toast.LENGTH_LONG).show()
                etName.text.clear()
                etQty.text.clear()

                //setupListOfDataIntoRecyclerView()
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Data field cannot be blank",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    /**
     * Method is used to show the Custom Dialog.
     */
//    fun updateRecordDialog(CamionStockModel: CamionStockModel) {
//        val updateDialog = Dialog(this, R.style.Theme_Dialog)
//        updateDialog.setCancelable(false)
//        /*Set the screen content from a layout resource.
//         The resource will be inflated, adding all top-level views to the screen.*/
//        updateDialog.setContentView(R.layout.dialog_update)
//
//        updateDialog.etUpdateName.setText(CamionStockModel.name)
//        updateDialog.etUpdatePrice.setText(CamionStockModel.price)
//        updateDialog.etUpdateCategory.setText(CamionStockModel.category)
//        updateDialog.etUpdateImage.setText(CamionStockModel.image)
//        updateDialog.etUpdateQtyParOne.setText(CamionStockModel.qty_par_one)
//        updateDialog.etUpdateQtyStock.setText(CamionStockModel.qty_stock)
//        updateDialog.etUpdateStatus.setText(CamionStockModel.status)
//
//        updateDialog.tvUpdate.setOnClickListener(View.OnClickListener {
//
//            val name = updateDialog.etUpdateName.text.toString()
//            val price = updateDialog.etUpdatePrice.text.toString()
//            val category = updateDialog.etUpdateCategory.text.toString()
//            val status = updateDialog.etUpdateStatus.text.toString()
//            val qty_par_one = updateDialog.etUpdateQtyParOne.text.toString()
//            val qty_stock = updateDialog.etUpdateQtyStock.text.toString()
//            val image = updateDialog.etUpdateImage.text.toString()
//            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
//
//            val statusInt = status.toInt()
//            val qty_par_oneInt = qty_par_one.toInt()
//            val qty_stockInt = qty_stock.toInt()
//
//            if (!name.isEmpty() && !price.isEmpty() && !category.isEmpty() && !status.isEmpty() && !qty_par_one.isEmpty() && !qty_stock.isEmpty() && !image.isEmpty()) {
//
//                val status =
//                    databaseHandler.updateItem(CamionStockModel(CamionStockModel.id, name, price, category, statusInt, qty_par_oneInt, qty_stockInt, image))
//                if (status > -1) {
//                    Toast.makeText(applicationContext, "Record Updated.", Toast.LENGTH_LONG).show()
//
//                    setupListOfDataIntoRecyclerView()
//
//                    updateDialog.dismiss() // Dialog will be dismissed
//                }
//            } else {
//                Toast.makeText(
//                    applicationContext,
//                    "Data field cannot be blank",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        })
//        updateDialog.tvCancel.setOnClickListener(View.OnClickListener {
//            updateDialog.dismiss()
//        })
//        //Start the dialog and display it on screen.
//        updateDialog.show()
//    }

    /**
     * Method is used to show the Alert Dialog.
     */
    fun deleteRecordAlertDialog(CamionStockModel: CamionStockModel) {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Delete Stock")
        //set message for alert dialog
        builder.setMessage("Are you sure you wants to delete ${CamionStockModel.name}.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, _ ->

            //creating the instance of DatabaseHandler class
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
            //calling the deleteEmployee method of DatabaseHandler class to delete record
            val status = databaseHandler.deleteStock(CamionStockModel(CamionStockModel.id,"", "", 0, "0","0","0",0))
            if (status > -1) {
                Toast.makeText(
                    applicationContext,
                    "Record deleted successfully.",
                    Toast.LENGTH_LONG
                ).show()

                //setupListOfDataIntoRecyclerView()
            }

            dialogInterface.dismiss() // Dialog will be dismissed
        }
        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, _ ->
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show()  // show the dialog to UI
    }










    override fun onBackPressed() {
        finish()
    }

    override fun onResume() {
        super.onResume()
        this@AddCamionStockActivity.getCurrentLanguage(false)
    }

}
