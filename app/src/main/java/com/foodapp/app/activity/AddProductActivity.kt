package com.foodapp.app.activity

import android.content.Intent
import com.foodapp.app.R
import com.foodapp.app.base.BaseActivity
import com.foodapp.app.utils.Common.getCurrentLanguage
import android.widget.Toast
import com.foodapp.app.model.ItemModel
import kotlinx.android.synthetic.main.activity_addproduct.*
import kotlinx.android.synthetic.main.activity_addproduct.etName
import kotlinx.android.synthetic.main.activity_cart.ivBack
import kotlinx.android.synthetic.main.activity_cart.ivHome

class AddProductActivity : BaseActivity() {

    override fun setLayout(): Int {
        return R.layout.activity_addproduct
    }

    override fun InitView() {
        this@AddProductActivity.getCurrentLanguage(false)


        ivBack.setOnClickListener {
            val intent=Intent(this@AddProductActivity,DashboardActivity::class.java).putExtra("pos","1")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        ivHome.setOnClickListener {
            val intent=Intent(this@AddProductActivity,DashboardActivity::class.java).putExtra("pos","1")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        btnAdd.setOnClickListener {
            addRecord()
        }

        btnAddInitialProductForAdmin.setOnClickListener {
            addInitialProducts()
        }
    }

    //Method for saving the employee records in database
    private fun addRecord() {
        val name = etName.text.toString()
        val price = etPrice.text.toString()
        val _status = etStatus.text.toString()
        val qty_par_one = etQtyParOne.text.toString()
        val image = etImage.text.toString()
        val databaseHandler = DatabaseHandler(this)

        val statusInt = _status.toInt()
        val qty_par_oneInt = qty_par_one.toInt()

        if (name.isNotEmpty() && price.isNotEmpty() && _status.isNotEmpty() && qty_par_one.isNotEmpty() && image.isNotEmpty()) {
            val status =
                databaseHandler.addItem(ItemModel(0,"", name, price, statusInt, qty_par_oneInt, image, "0", "0", "0", 0))
            if (status > -1) {
                Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()
                etName.text.clear()
                etPrice.text.clear()
                etStatus.text.clear()
                etQtyParOne.text.clear()
                etImage.text.clear()
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Data field cannot be blank",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    //Method for saving the employee records in database
    private fun addInitialProducts() {
        val name = arrayOf("mini","trio","pot","solo","gini","pot vally","big","cornito 45",
            "cornito 50","cornito gini","gofrito","g8","gold","skiper","scobido","mini scobido",
            "venezia","B.F 2L","B.F 1L","B.F 900ml","B.F 750ml","B.F 0,5L","buch","tarte","mosta","misso",
            "juliana","bac 5L","bac 6L")
        val price = arrayOf("28","28","28","30","42","33","40","50","50","70","35","50","50",
            "62","32","28","43","400","250","230","200","150","450","500","60","55","80","900","1100")
        val status = arrayOf(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1)
        val qty_par_one = arrayOf(60,50,60,60,45,60,60,45,50,45,30,50,50,50,40,50,40,1,1,1,1,1,1,1,1,1,1,1,1)
        val image = arrayOf("mini","trio","pot","solo","gini","pot vally","big","cornito 45",
            "cornito 50","cornito gini","gofrito","g8","gold","skiper","scobido","mini scobido",
            "venezia","B.F 2L","B.F 1L","B.F 900ml","B.F 750ml","B.F 0,5L","buch","tarte","mosta","misso",
            "juliana","bac 5L","bac 6L")
        val databaseHandler = DatabaseHandler(this)

        for (i in 0..28) {
            val statusDB =
                databaseHandler.addItem(ItemModel(0,"", name[i], price[i], status[i], qty_par_one[i], image[i], "0", "0", "0", 0))
            if (statusDB < 0) {
                Toast.makeText(applicationContext, "ERROR " , Toast.LENGTH_LONG).show()
            }
        }
        Toast.makeText(applicationContext, "Initial Product Done. " , Toast.LENGTH_LONG).show()

    }

    override fun onBackPressed() {
        val intent=Intent(this@AddProductActivity,DashboardActivity::class.java).putExtra("pos","1")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        this@AddProductActivity.getCurrentLanguage(false)
    }

}
