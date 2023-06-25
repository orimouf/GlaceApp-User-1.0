package com.foodapp.app.adaptor

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.glaceapp.DatabaseHandler
import com.foodapp.app.R
import com.foodapp.app.activity.*
import com.foodapp.app.model.ClientModel
import kotlinx.android.synthetic.main.dlg_updateclient.*
import kotlinx.android.synthetic.main.row_client.view.*
import java.lang.Integer.parseInt

class ClientAdaptor(val context: Context, private val clients: ArrayList<ClientModel>, private val isSearch: Boolean) :
    RecyclerView.Adapter<ClientAdaptor.ViewHolder>() {

    /**
     * Inflates the item views which is designed in the XML layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_client,
                parent,
                false
            )
        )
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val client = clients[position]

        if (isSearch) outputSearchView(holder, client)
        if (!isSearch) outputView(holder, client)

        holder.ivEditClient.setOnClickListener {
            updateClientDialog(this.context, client)
        }

        holder.ivDeleteCartClient.setOnClickListener {
            deleteClientAlertDialog(holder, this.context, client)
        }

        holder.ivAddOrder.setOnClickListener {
            context.startActivities(client)
        }

        holder.tvPaymentBtn.setOnClickListener{
            if (context is SearchActivity) {
                context.setPayment(client)
            }
        }

    }

    private fun outputView (holder: ViewHolder, client: ClientModel) {
        holder.cvClientBox.updateLayoutParams {
            height = 180
            width = LinearLayout.LayoutParams.WRAP_CONTENT
        }

        holder.tvClientName.text = client.client_name
        holder.tvClientPhone.text = client.phone
        holder.llMidelClientInfo.visibility = View.GONE
        holder.llBottomClientInfo.visibility = View.GONE
        holder.llLeftBottomClientInfo.visibility = View.GONE
        holder.llEditOrDelete.visibility = View.GONE
        holder.tvClientPhone.textSize = 12F
        holder.tvRegionTop.visibility = View.VISIBLE
        holder.tvRegionTop.text =client.region
    }

    @SuppressLint("SetTextI18n")
    private fun outputSearchView (holder: ViewHolder, client: ClientModel) {
        var isCredit = "No Credit"; var isPromo = "No Promo"; var isFrigo = "No Frigo"
        if (client.is_credit != 0) isCredit = "Crediter"
        if (client.is_promo != 0) isPromo = "Promo"
        if (client.is_frigo != 0) isFrigo = "Notre Frigo"

        holder.tvClientName.text = client.client_name
        holder.tvClientPhone.text = client.phone
        holder.tvRegion.text = "REGION: ${client.region}"
        holder.tvOldCredit.text = "Old Credit: ${client.old_credit}"
        holder.tvCreditBon.text = "BON: ${client.credit_bon}"
        holder.tvLastServe.text = "SERVE: ${client.last_serve}"
        holder.tvIsCredit.text = isCredit
        holder.tvIsPromo.text = isPromo
        holder.tvIsFrigo.text = isFrigo
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return clients.size
    }

    /**
     * Method is used to show the Custom Dialog.
     */
    private fun updateClientDialog(context: Context, client: ClientModel) {

        val updateDialog = Dialog(context, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        /*Set the screen content from a layout resource.
         The resource will be inflated, adding all top-level views to the screen.*/
        updateDialog.setContentView(R.layout.dlg_updateclient)

        Toast.makeText(context, client.client_name, Toast.LENGTH_SHORT).show()
        updateDialog.etClientNameDlg.setText(client.client_name)
        updateDialog.etUpdatePhoneDlg.setText(client.phone)
        updateDialog.etUpdateRegionDlg.setText(client.region)
        updateDialog.etUpdateIsCreditDlg.setText(client.is_credit.toString())
        updateDialog.etUpdateIsFrigoDlg.setText(client.is_frigo.toString())
        updateDialog.etUpdateIsPromoDlg.setText(client.is_promo.toString())
        updateDialog.etUpdateOldCreditDlg.setText(client.old_credit)
        updateDialog.etUpdateCreditBonDlg.setText(client.credit_bon.toString())
        updateDialog.etUpdatePricesDlg.setText(client.prices)

        updateDialog.tvUpdateClientDlg.setOnClickListener {

            val clientName = updateDialog.etClientNameDlg.text.toString()
            val phone = updateDialog.etUpdatePhoneDlg.text.toString()
            val region = updateDialog.etUpdateRegionDlg.text.toString()
            val isCredit = updateDialog.etUpdateIsCreditDlg.text.toString()
            val isFrigo = updateDialog.etUpdateIsFrigoDlg.text.toString()
            val isPromo = updateDialog.etUpdateIsPromoDlg.text.toString()
            val oldCredit = updateDialog.etUpdateOldCreditDlg.text.toString()
            val creditBon = updateDialog.etUpdateCreditBonDlg.text.toString()
            val prices = updateDialog.etUpdatePricesDlg.text.toString()
            val lastServe = client.last_serve
            val databaseHandler = DatabaseHandler(context)

            if (clientName.isNotEmpty() && phone.isNotEmpty() && region.isNotEmpty() && isCredit.isNotEmpty() && isFrigo.isNotEmpty()
                && isPromo.isNotEmpty() && oldCredit.isNotEmpty() && creditBon.isNotEmpty() && prices.isNotEmpty()
            ) {

                val status =
                    databaseHandler.updateClient(
                        ClientModel(
                            client.id,
                            client.server_id,
                            clientName,
                            phone,
                            prices,
                            region,
                            oldCredit,
                            parseInt(isFrigo),
                            parseInt(isPromo),
                            parseInt(isCredit),
                            parseInt(creditBon),
                            lastServe,
                            "0",
                            "0",
                            "0",
                            0
                        )
                    )
                if (status > -1) {
                    Toast.makeText(context, "Client Updated.", Toast.LENGTH_LONG).show()

                    updateDialog.dismiss() // Dialog will be dismissed
                }
            } else {
                Toast.makeText(
                    context,
                    "Data field cannot be blank",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        updateDialog.tvCancelUpdateClientDlg.setOnClickListener {
            updateDialog.dismiss()
        }
        //Start the dialog and display it on screen.
        updateDialog.show()
    }

    /**
     * Method is used to show the Alert Dialog.
     */
    private fun deleteClientAlertDialog(holder: ViewHolder, context: Context, client: ClientModel) {
        val builder = AlertDialog.Builder(context)
        //set title for alert dialog
        builder.setTitle("Delete Client")
        //set message for alert dialog
        builder.setMessage("Are you sure you wants to delete ${client.client_name}.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, _ ->

            //creating the instance of DatabaseHandler class
            val databaseHandler = DatabaseHandler(context)
            //calling the deleteEmployee method of DatabaseHandler class to delete record
            val status = databaseHandler.deleteClient(ClientModel(client.id, "", "", "", "", "", "",
                0,0,0,0,"", "0", "0", "0",0))
            if (status > -1) {
                Toast.makeText(
                    context,
                    "Product deleted successfully.",
                    Toast.LENGTH_LONG
                ).show()

                holder.cvClientBox.visibility = View.GONE
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

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each item to
        val tvClientName: TextView = view.tvClientName
        val tvClientPhone: TextView = view.tvClientPhone
        val tvIsPromo: TextView = view.tvIsPromo
        val tvIsFrigo: TextView = view.tvIsFrigo
        val tvIsCredit: TextView = view.tvIsCredit
        val tvCreditBon: TextView = view.tvCreditBon
        val tvOldCredit: TextView = view.tvOldCredit
        val tvRegion: TextView = view.tvRegion
        val tvLastServe: TextView = view.tvLastServe
        val ivEditClient: ImageView = view.ivEditClient
        val ivDeleteCartClient: ImageView = view.ivDeleteCartClient
        val ivAddOrder: ImageView = view.ivAddOrder
        val cvClientBox: CardView = view.cvClientBox
        val llMidelClientInfo: LinearLayout = view.llMidelClientInfo
        val llBottomClientInfo: LinearLayout = view.llBottomClientInfo
        val llLeftBottomClientInfo: LinearLayout = view.llLeftBottomClientInfo
        val llEditOrDelete: LinearLayout = view.llEditOrDelete
        val tvRegionTop: TextView = view.tvRegionTop
        val tvPaymentBtn: TextView = view.tvPaymentBtn

    }
}

private fun Context.startActivities(client: ClientModel) {
    val intent = Intent(this,CartActivity::class.java)
    intent.putExtra("clientName", client.client_name)
    intent.putExtra("clientId", client.id.toString())
    startActivity(intent)
}
