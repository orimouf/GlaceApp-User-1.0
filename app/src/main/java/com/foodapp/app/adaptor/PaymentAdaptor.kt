package com.foodapp.app.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.app.activity.DatabaseHandler
import com.foodapp.app.R
import com.foodapp.app.activity.AccountingActivity
import com.foodapp.app.model.*
import kotlinx.android.synthetic.main.activity_accounting.*
import kotlinx.android.synthetic.main.row_accounting.view.*
import java.lang.Integer.parseInt

class PaymentAdaptor(val context: Context, private val payments: ArrayList<VerssementModel>) :
    RecyclerView.Adapter<PaymentAdaptor.ViewHolder>() {

    var dayAmount = 0

    /**
     * Inflates the item views which is designed in the XML layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_accounting,
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
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val payment = payments[position]

        if (context is AccountingActivity) {
            dayAmount = context.tvTotalDayView.text.toString().toInt()
        }

        outputView(holder, payment)

        holder.tvAccountingCheck.setOnClickListener {
            if (context is AccountingActivity) {
                context.makeItCheck(payment.id, "payment")
            }
        }

        holder.tvAccountingDelete.setOnClickListener {
            if (context is AccountingActivity) {
                context.deletePayment(payment.id)
            }
        }

        holder.tvAccountingEdit.setOnClickListener {
            if (context is AccountingActivity) {
                context.editPayment(payment)
            }
        }
    }

    private fun outputView (holder: ViewHolder, payment: VerssementModel) {

        val databaseHandler = DatabaseHandler(context)

        val region =  databaseHandler.viewRegionClient(payment.client_id.toInt())

        holder.tvAccountingClientName.text = payment.client_name
        holder.tvAccountingRegion.text = region
        holder.tvAccountingAmount.text = payment.old_somme
        holder.tvAccountingDate.text = payment.date
        holder.tvAccountingStatus.text = "VERSEMENT"
        holder.tvAccountingVerssiValue.text = payment.verssi
        holder.tvAccountingRestValue.text = payment.rest

        val verssment = parseInt(payment.verssi)
        dayAmount += verssment

        if (context is AccountingActivity) {
            context.tvTotalDayView.text = dayAmount.toString()
        }

    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return payments.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each item to
        val tvAccountingClientName: TextView = view.tvAccountingClientName
        val tvAccountingRegion: TextView = view.tvAccountingRegion
        val tvAccountingAmount: TextView = view.tvAccountingAmount
        val tvAccountingDate: TextView = view.tvAccountingDate
        val tvAccountingStatus: TextView = view.tvAccountingStatus
        val tvAccountingCheck: TextView = view.tvAccountingCheck
        val tvAccountingDelete: TextView = view.tvAccountingDelete
        val tvAccountingVerssiValue: TextView = view.tvAccountingVerssiValue
        val tvAccountingRestValue: TextView = view.tvAccountingRestValue
        val tvAccountingEdit: TextView = view.tvAccountingEdit

    }
}