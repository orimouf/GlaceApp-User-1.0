package com.foodapp.app.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.app.R
import com.foodapp.app.adaptor.ItemAdapter
import com.foodapp.app.base.BaseActivity
import com.foodapp.app.model.*
import com.foodapp.app.utils.Common.getCurrentDateTime
import com.foodapp.app.utils.Common.getCurrentLanguage
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_cart.ivBack
import kotlinx.android.synthetic.main.activity_cart.ivHome
import kotlinx.android.synthetic.main.dlg_updateproduct.*
import java.lang.Integer.parseInt
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

@Suppress("UNSAFE_CALL_ON_PARTIALLY_DEFINED_RESOURCE")
class CartActivity : BaseActivity() {

    var clientName : String = ""
    var clientId : String = ""
    var orderId : String = ""
    var productListId : Int = 0
    var firstTime : Boolean = true
    var thisProductListId : Int = 0
    var indexItemRV : Int = 0
    var topViewRV : Int = 0
    var date : String = ""

    override fun setLayout(): Int {
        return R.layout.activity_cart
    }

    /**
     * Function is used to show the list of inserted data.
     */
    private fun setupListOfDataIntoRecyclerView(productListID: Int) {

        if (getItemsList().size > 0) {
            rvCartFood.visibility = View.VISIBLE
            tvNoDataFound.visibility = View.GONE



            // Set the LayoutManager that this RecyclerView will use.
            rvCartFood.layoutManager = LinearLayoutManager(this)
            // Adapter class is initialized and list is passed in the param.
            val itemAdapter = ItemAdapter(this, getItemsList(), getAllProductList(productListID))
            // adapter instance is set to the recyclerview to inflate the items.
            rvCartFood.adapter = itemAdapter

            scrollToPosition(indexItemRV, topViewRV)
        } else {
            rvCartFood.visibility = View.GONE
            tvNoDataFound.visibility = View.VISIBLE
        }
    }

    /**
     * Function is used to get the Items List from the database table.
     */
    private fun getItemsList(): ArrayList<ItemModel> {
        val databaseHandler = DatabaseHandler(this)

        return databaseHandler.viewItem()
    }

    /**
     * Function is used to get the All Product List from the database table.
     */
    private fun getAllProductList(id: Int): ArrayList<AllProductModel> {
        val databaseHandler = DatabaseHandler(this)

        return databaseHandler.viewAllProduct(id)
    }

    //    /**
//     * Function is used to get the Order Summary List from the database table.
//     */
//    private fun getOrderSummaryList(clientId: Int): ArrayList<OrderSummaryModel> {
//        //creating the instance of DatabaseHandler class
//        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
//        //calling the viewEmployee method of DatabaseHandler class to read the records
//        val orderList: ArrayList<OrderSummaryModel> = databaseHandler.viewOrderSummary(clientId)
//
//        return orderList
//    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun InitView() {
        this@CartActivity.getCurrentLanguage(false)
//        tvCheckout.visibility = View.GONE


        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        date = current.format(formatter)

        clientName = intent.getStringExtra("clientName").toString()
        clientId = intent.getStringExtra("clientId").toString()
        tvClientName.text = clientName

        if (firstTime) addOrderSummary(false)

//        setupListOfDataIntoRecyclerView(parseInt(clientId),0)

        ivBack.setOnClickListener {
            val databaseHandler = DatabaseHandler(this)
            databaseHandler.deleteOrderSummary(orderId.toInt())
            databaseHandler.deleteAllProduct(orderId.toInt())

            finish()
        }

        ivHome.setOnClickListener {
            val databaseHandler = DatabaseHandler(this)
            databaseHandler.deleteOrderSummary(orderId.toInt())
            databaseHandler.deleteAllProduct(orderId.toInt())

            val intent=Intent(this@CartActivity,DashboardActivity::class.java).putExtra("pos","1")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        tvCheckout.setOnClickListener {

            val intent = Intent(this@CartActivity,OrderDetailActivity::class.java)
            intent.putExtra("clientNameOrder", clientName)
            intent.putExtra("clientIdOrder", clientId)
            intent.putExtra("orderIdOrder", orderId)
            intent.putExtra("productListIdOrder", thisProductListId.toString())

            startActivity(intent)
        }

        rvCartFood.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                indexItemRV = (rvCartFood.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()!!
                val v = (rvCartFood.layoutManager as? LinearLayoutManager)?.getChildAt(0)
                topViewRV = if (v == null) 0 else v.top - (rvCartFood.layoutManager as? LinearLayoutManager)?.paddingTop!!
            }
        })
    }

    private fun scrollToPosition(position: Int, offset: Int = 0) {
        rvCartFood.stopScroll()
        (rvCartFood.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(position, offset)
    }

    //Method for saving the order summary in database
    private fun addOrderSummary(updateOrder: Boolean) {

        firstTime = false

        var initialProductListId = 0
        val isCredit = 1

        val databaseHandler = DatabaseHandler(this)

        if (updateOrder) initialProductListId = productListId

        if (clientName.isNotEmpty() && clientId.isNotEmpty()) {
            val status =
                databaseHandler.addOrderSummary(
                    OrderSummaryModel(0,"", clientName, parseInt(clientId), initialProductListId,
                        0, "0", "0", isCredit, date,0, "0", "0", "0",0))
            if (status > -1) {
                val getOrderId = databaseHandler.viewLastOrderSummary()
                orderId = getOrderId.toString()
                addToAllProduct(getOrderId)
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Data is Empty",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    //Method for saving the employee records in database
    fun addToAllProduct(orderID: Int) {
        val databaseHandler = DatabaseHandler(this)
        val status =
            databaseHandler.addAllProduct(AllProductModel(0,"", orderID, 0, 60, 0, 50, 0,
                60, 0,60,0,45,0,60,0,45,
                0,50,0,45,0,30,0,
                60, 0,50,0,50,0,50,0,
                40,0,50,0,40,0,0,
                0,0,0,0,0,0,0,0,0,0,
                "0", "0", "0", 0))
        if (status > -1) {
            val getAllProductId = databaseHandler.viewLastAllProduct()
            updateOrderSummary(orderID, getAllProductId)

        }
    }

    /**
     * Method is used to show the Custom Dialog.
     */
    fun updateAllProduct(itemProduct: ItemModel, item: AllProductModel, a: Int, b: Int, productListID: Int) {
        var mini_q_u = item.mini_q_u; var mini_qty = item.mini_qty
        var trio_q_u = item.trio_q_u; var trio_qty = item.trio_qty
        var solo_q_u = item.solo_q_u; var solo_qty = item.solo_qty
        var pot_q_u = item.pot_q_u; var pot_qty = item.pot_qty
        var gini_q_u = item.gini_q_u; var gini_qty = item.gini_qty
        var big_q_u = item.big_q_u; var big_qty = item.big_qty
        var cornito_4_q_u = item.cornito_4_q_u; var cornito_4_qty = item.cornito_4_qty
        var cornito_5_q_u = item.cornito_5_q_u; var cornito_5_qty = item.cornito_5_qty
        var cornito_g_q_u = item.cornito_g_q_u; var cornito_g_qty = item.cornito_g_qty
        var gofrito_q_u = item.gofrito_q_u; var gofrito_qty = item.gofrito_qty
        var pot_v_q_u = item.pot_v_q_u; var pot_v_qty = item.pot_v_qty
        var g8_q_u = item.g8_q_u; var g8_qty = item.g8_qty
        var gold_q_u = item.gold_q_u; var gold_qty = item.gold_qty
        var skiper_q_u = item.skiper_q_u; var skiper_qty = item.skiper_qty
        var scobido_q_u = item.scobido_q_u; var scobido_qty = item.scobido_qty
        var mini_scobido_q_u = item.mini_scobido_q_u; var mini_scobido_qty = item.mini_scobido_qty
        var venezia_q_u = item.venezia_q_u; var venezia_qty = item.venezia_qty
        var bf_400_q_u = item.bf_400_q_u
        var bf_250_q_u = item.bf_250_q_u
        var bf_230_q_u = item.bf_230_q_u
        var bf_200_q_u = item.bf_200_q_u
        var bf_150_q_u = item.bf_150_q_u
        var buch_q_u = item.buch_q_u
        var tarte_q_u = item.tarte_q_u
        var mosta_q_u = item.mosta_q_u
        var misso_q_u = item.misso_q_u
        var juliana_q_u = item.juliana_q_u
        var bac_5_q_u = item.bac_5_q_u
        var bac_6_q_u = item.bac_6_q_u

        when (itemProduct.name) {
            "mini" -> { mini_q_u = a; mini_qty = b }
            "trio" -> { trio_q_u = a; trio_qty = b }
            "solo" -> { solo_q_u = a; solo_qty = b }
            "pot" -> { pot_q_u = a; pot_qty = b }
            "gini" -> { gini_q_u = a; gini_qty = b }
            "big" -> { big_q_u = a; big_qty = b }
            "cornito 45" -> { cornito_4_q_u = a; cornito_4_qty = b }
            "cornito 50" -> { cornito_5_q_u = a; cornito_5_qty = b }
            "cornito gini" -> { cornito_g_q_u = a; cornito_g_qty = b }
            "gofrito" -> { gofrito_q_u = a; gofrito_qty = b }
            "pot vally" -> { pot_v_q_u = a; pot_v_qty = b }
            "g8" -> { g8_q_u = a; g8_qty = b }
            "gold" -> { gold_q_u = a; gold_qty = b }
            "skiper" -> { skiper_q_u = a; skiper_qty = b }
            "scobido" -> { scobido_q_u = a; scobido_qty = b }
            "mini scobido" -> { mini_scobido_q_u = a; mini_scobido_qty = b }
            "venezia" -> { venezia_q_u = a; venezia_qty = b }
            "B.F 2L" -> bf_400_q_u = a
            "B.F 1L" -> bf_250_q_u = a
            "B.F 900ml" -> bf_230_q_u = a
            "B.F 750ml" -> bf_200_q_u = a
            "B.F 0,5L" -> bf_150_q_u = a
            "buch" -> buch_q_u = a
            "tarte" -> tarte_q_u = a
            "mosta" -> mosta_q_u = a
            "misso" -> misso_q_u = a
            "juliana" -> juliana_q_u = a
            "bac 5L" -> bac_5_q_u = a
            "bac 6L" -> bac_6_q_u = a

            else -> { // Note the block
                print("x is neither 1 nor 2")
            }
        }

        val databaseHandler = DatabaseHandler(this)
        val status =
            databaseHandler.updateAllProduct(AllProductModel(
                item.id, item.server_id, item.orderId, mini_qty, mini_q_u, trio_qty, trio_q_u,
                solo_qty, solo_q_u, pot_qty, pot_q_u, gini_qty,gini_q_u, big_qty, big_q_u,
                cornito_4_qty, cornito_4_q_u, cornito_5_qty, cornito_5_q_u, cornito_g_qty,
                cornito_g_q_u, gofrito_qty, gofrito_q_u, pot_v_qty, pot_v_q_u, g8_qty,
                g8_q_u, gold_qty, gold_q_u, skiper_qty, skiper_q_u, scobido_qty,
                scobido_q_u, mini_scobido_qty, mini_scobido_q_u, venezia_qty, venezia_q_u,
                bf_400_q_u, bf_250_q_u, bf_230_q_u, bf_200_q_u, bf_150_q_u, buch_q_u, tarte_q_u,
                mosta_q_u, misso_q_u, juliana_q_u, bac_5_q_u, bac_6_q_u, "0", getCurrentDateTime(), "0", 0))
        if (status > -1) {
            setupListOfDataIntoRecyclerView(productListID)
        }
    }

    /**
     * Method is used to show the Custom Dialog.
     */
    fun updateRecordDialog(itemModelClass: ItemModel) {

        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        /*Set the screen content from a layout resource.
         The resource will be inflated, adding all top-level views to the screen.*/
        updateDialog.setContentView(R.layout.dlg_updateproduct)

        updateDialog.etUpdateName.setText(itemModelClass.name)
        updateDialog.etUpdatePrice.setText(itemModelClass.price)
        updateDialog.etUpdateImage.setText(itemModelClass.image)
        updateDialog.etUpdateQtyParOne.setText(itemModelClass.qty_par_one.toString())
        updateDialog.etUpdateStatus.setText(itemModelClass.status.toString())

        updateDialog.tvUpdate.setOnClickListener(View.OnClickListener {

            val name = updateDialog.etUpdateName.text.toString()
            val price = updateDialog.etUpdatePrice.text.toString()
            val _status = updateDialog.etUpdateStatus.text.toString()
            val qty_par_one = updateDialog.etUpdateQtyParOne.text.toString()
            val image = updateDialog.etUpdateImage.text.toString()
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)

            val statusInt = _status.toInt()
            val qty_par_oneInt = qty_par_one.toInt()

            if (!name.isEmpty() && !price.isEmpty() && !_status.isEmpty() && !qty_par_one.isEmpty() && !image.isEmpty()) {

                val status =
                    databaseHandler.updateItem(ItemModel(itemModelClass.id, itemModelClass.server_id, name, price, statusInt,
                        qty_par_oneInt, image, "0", getCurrentDateTime(), "0", 0))
                if (status > -1) {
                    Toast.makeText(applicationContext, "Product Updated.", Toast.LENGTH_LONG).show()

                    //setupListOfDataIntoRecyclerView(parseInt(clientId))

                    updateDialog.dismiss() // Dialog will be dismissed
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

    /**
     * Method is used to show the Custom Dialog.
     */
    fun updateOrderSummary(orderID: Int, productListID: Int) {
        val databaseHandler = DatabaseHandler(this)

        if (productListID != 0) {

            val status =
                databaseHandler.updateOrderSummary(
                    OrderSummaryModel(
                        orderID, "","", 0,
                        productListID, 0, "", "",0,"",0, "0", getCurrentDateTime(), "0",0
                    ), "up_to_server"
                )
            if (status > -1) {
                thisProductListId = productListID

                setupListOfDataIntoRecyclerView(productListID)
            }
        }
    }

    /**
     * Method is used to show the Alert Dialog.
     */
    fun deleteRecordAlertDialog(itemModelClass: ItemModel, productListID: Int) {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Delete Record")
        //set message for alert dialog
        builder.setMessage("Are you sure you wants to delete ${itemModelClass.name}.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, _ ->

            //creating the instance of DatabaseHandler class
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
            //calling the deleteEmployee method of DatabaseHandler class to delete record
            val status = databaseHandler.deleteItem(ItemModel(itemModelClass.id,"", "", "", 0, 0,
                "", "0", "0", "0", 0))
            if (status > -1) {
                Toast.makeText(
                    applicationContext,
                    "Product deleted successfully.",
                    Toast.LENGTH_LONG
                ).show()

                setupListOfDataIntoRecyclerView(productListID)
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
        val databaseHandler = DatabaseHandler(this)
        databaseHandler.deleteOrderSummary(orderId.toInt())
        databaseHandler.deleteAllProduct(orderId.toInt())

        finish()
    }

    override fun onResume() {
        super.onResume()
        this@CartActivity.getCurrentLanguage(false)
    }

}
