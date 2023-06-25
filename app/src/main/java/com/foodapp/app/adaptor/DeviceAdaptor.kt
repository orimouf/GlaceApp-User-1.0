package com.foodapp.app.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.app.R
import com.foodapp.app.activity.CartActivity
import com.foodapp.app.activity.PrintActivity
import com.foodapp.app.model.DeviceModel
import kotlinx.android.synthetic.main.activity_print.*
import kotlinx.android.synthetic.main.activity_print.view.*
import kotlinx.android.synthetic.main.row_cart.view.*
import kotlinx.android.synthetic.main.row_device.view.*

class DeviceAdaptor(val context: Context, private val devices: ArrayList<DeviceModel>) :
    RecyclerView.Adapter<DeviceAdaptor.ViewHolder>() {

    /**
     * Inflates the item views which is designed in the XML layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_device,
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

        val device = devices[position]

        holder.tvDeviceName.text = device.name
        holder.tvDeviceDescription.text = device.description

        holder.itemView.rl_device.setOnClickListener {

            if (context is PrintActivity) {
                context.findBT(device.name)
                context.rvDeviceList.visibility = View.GONE
                context.tvMsgLabel.text = device.name + " Connected."
            }
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return devices.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each item to
        val tvDeviceName: TextView = view.tvDeviceName
        val tvDeviceDescription: TextView = view.tvDeviceDescription
        val rl_device = view.rl_device
    }
}