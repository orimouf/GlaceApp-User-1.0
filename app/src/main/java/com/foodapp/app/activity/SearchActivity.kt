package com.foodapp.app.activity

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.foodapp.app.R
import com.foodapp.app.base.BaseActivity
import com.foodapp.app.adaptor.ClientAdaptor
import com.foodapp.app.model.ClientModel
import com.foodapp.app.model.VerssementModel
import com.foodapp.app.utils.Common.getCurrentLanguage
import kotlinx.android.synthetic.main.activity_addproduct.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.ivBack
import kotlinx.android.synthetic.main.dlg_payment.*
import kotlinx.android.synthetic.main.dlg_updateorder.*
import kotlinx.android.synthetic.main.dlg_updateproduct.*
import kotlinx.android.synthetic.main.row_client.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class SearchActivity : BaseActivity() {

    override fun setLayout(): Int {
        return R.layout.activity_search
    }

    /**
     * Function is used to show the list of inserted data.
     */
    private fun setupListOfDataIntoRecyclerView(strSearch: String) {
        val clientList = getClientList(strSearch)
        if (clientList.size > 0) {
            rvSearchOrder.visibility = View.VISIBLE
            tvNoDataSearchFound.visibility = View.GONE

            rvSearchOrder.layoutManager = LinearLayoutManager(this)
            val clientAdapter = ClientAdaptor(this, clientList, true)
            rvSearchOrder.adapter = clientAdapter

        } else {
            rvSearchOrder.visibility = View.GONE
            tvNoDataSearchFound.visibility = View.VISIBLE
        }
    }

    /**
     * Function is used to get the Items List from the database table.
     */
    private fun getClientList(string: String): ArrayList<ClientModel> {
        val databaseHandler = DatabaseHandler(this)
        return databaseHandler.viewSearchClient(string)
    }

    override fun InitView() {
        this@SearchActivity.getCurrentLanguage(false)

        setupListOfDataIntoRecyclerView("")

        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

        ivBack.setOnClickListener {
            imm?.hideSoftInputFromWindow(etSearch.windowToken, 0)
            val intent =
                Intent(this@SearchActivity, DashboardActivity::class.java).putExtra("pos", "1")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        etSearch.requestFocus();
        etSearch.doAfterTextChanged{
            setupListOfDataIntoRecyclerView(etSearch.text.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setPayment(client: ClientModel) {
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        /*Set the screen content from a layout resource.
         The resource will be inflated, adding all top-level views to the screen.*/
        updateDialog.setContentView(R.layout.dlg_payment)

        updateDialog.tvClientNamePayment.text = client.client_name
        updateDialog.tvOldTotalSomme.text = client.old_credit

        updateDialog.tvPayment.setOnClickListener(View.OnClickListener {

            val payment = updateDialog.etPaymentVerssi.text.toString()
            val rest = updateDialog.etPaymentRest.text.toString()
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val date = current.format(formatter)

            val databaseHandler = DatabaseHandler(this)

            if (payment.isNotEmpty() && rest.isNotEmpty()) {

                val status =
                    databaseHandler.addVerssement(VerssementModel(0, "", "","", client.id.toString(), client.client_name, client.region,
                        client.old_credit, payment, rest, 0, 0, "0", "0", "0", date))
                if (status > -1) {
                    Toast.makeText(applicationContext, "Payment saved", Toast.LENGTH_LONG).show()

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

    override fun onBackPressed() {
        val intent = Intent(this@SearchActivity, DashboardActivity::class.java).putExtra("pos", "1")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        this@SearchActivity.getCurrentLanguage(false)
    }
}