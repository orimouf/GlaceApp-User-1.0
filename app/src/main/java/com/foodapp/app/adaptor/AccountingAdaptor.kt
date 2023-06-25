package com.foodapp.app.adaptor

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.glaceapp.DatabaseHandler
import com.foodapp.app.R
import com.foodapp.app.activity.AccountingActivity
import com.foodapp.app.model.*
import kotlinx.android.synthetic.main.activity_accounting.*
import kotlinx.android.synthetic.main.row_accounting.view.*
import java.lang.Integer.parseInt

class AccountingAdaptor(val context: Context, private val orders: ArrayList<OrderSummaryModel>) :
    RecyclerView.Adapter<AccountingAdaptor.ViewHolder>() {

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

        val order = orders[position]

        outputView(holder, order)

        holder.tvAccountingCheck.setOnClickListener {
            if (context is AccountingActivity) {
                context.makeItCheck(order.id, "order")
            }
        }

        holder.tvAccountingDelete.setOnClickListener {
            if (context is AccountingActivity) {
                context.deleteOrder(order.id)
            }
        }

        holder.tvAccountingEdit.setOnClickListener {
            if (context is AccountingActivity) {
                context.editOrder(order)
            }
        }
    }

    private fun outputView (holder: ViewHolder, order: OrderSummaryModel) {

        var isCredit = "Non Payé"
        if (order.iscredit === 0) {
            isCredit = "Payé"
            holder.tvAccountingStatus.setTextColor(Color.rgb(20,255,20));
        } else {
            isCredit = "Non Payé"
            holder.tvAccountingStatus.setTextColor(Color.rgb(255,20,20));
        }

        val databaseHandler = DatabaseHandler(context)

        val region =  databaseHandler.viewRegionClient(order.client_id)

        holder.tvAccountingClientName.text = order.client_name
        holder.tvAccountingRegion.text = region
        holder.tvAccountingAmount.text = order.total_to_pay.toString()
        holder.tvAccountingDate.text = order.date
        holder.tvAccountingStatus.text = isCredit
        holder.tvAccountingVerssiValue.text = order.verssi
        holder.tvAccountingRestValue.text = order.rest

        val verssment = parseInt(order.verssi)
        dayAmount += verssment

        if (context is AccountingActivity) {
            context.tvTotalDayView.text = dayAmount.toString()
        }

    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return orders.size
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