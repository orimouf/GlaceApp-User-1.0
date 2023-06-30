package com.foodapp.app.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.app.R
import com.foodapp.app.api.Communicator
import com.foodapp.app.model.RegionModel
import kotlinx.android.synthetic.main.row_region.view.*

@Suppress("UNSAFE_CALL_ON_PARTIALLY_DEFINED_RESOURCE")
class RegionAdaptor(val context: Context, private val regions: ArrayList<RegionModel>, private val listener: Communicator, private val selectPosition: Int) :
    RecyclerView.Adapter<RegionAdaptor.ViewHolder>() {

    /**
     * Inflates the item views which is designed in the XML layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_region,
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

        val region = regions[position]

        if (position == selectPosition) {
            holder.llRegionC?.background = ResourcesCompat.getDrawable(context.resources,R.drawable.bg_strock_orange5,null)
        } else {
            holder.llRegionC?.background = null
        }

        holder.tvRegionName.text = region.region_name

        holder.llRegionC?.setOnClickListener {
            listener.passData(position,region.region_name)
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return regions.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each item to
        val tvRegionName: TextView = view.tvRegionName
        val llRegionC: LinearLayout ?= view.llRegionC
    }
}