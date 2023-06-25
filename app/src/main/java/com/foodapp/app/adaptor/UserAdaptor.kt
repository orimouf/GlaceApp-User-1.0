package com.foodapp.app.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.app.R
import com.foodapp.app.activity.AddProductActivity
import com.foodapp.app.activity.CartActivity
import com.foodapp.app.activity.UsersActivity
import com.foodapp.app.model.CartItemModel
import com.foodapp.app.model.ItemModel
import com.foodapp.app.model.UserModel
import com.foodapp.app.utils.Common.alertErrorOrValidationDialog
import kotlinx.android.synthetic.main.dlg_updateproduct.*
import kotlinx.android.synthetic.main.row_cart.view.*
import kotlinx.android.synthetic.main.row_user.view.*
import java.lang.Integer.parseInt

class UserAdaptor(val context: Context, val users: ArrayList<UserModel>) :
    RecyclerView.Adapter<UserAdaptor.ViewHolder>() {

    /**
     * Inflates the item views which is designed in the XML layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_user,
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

        val user = users.get(position)

        outputView(holder, user)

        holder.ivEditUser.setOnClickListener { view ->
            if (context is UsersActivity) {
                context.updateUserDialog(user)
            }
        }

        holder.ivDeleteCartUser.setOnClickListener { view ->
            if (context is UsersActivity) {
                context.deleteUserAlertDialog(user)
            }
        }

    }

    private fun outputView (holder: ViewHolder, user: UserModel) {

        var isAdmin = "User"
        if (user.isadmin !== 0) isAdmin = "Admin"

        holder.tvUsername.text = user.username
        holder.tvCamion.text = "CAMION: " + user.camion
        holder.tvIsAdmin.text = isAdmin
        holder.ivUser.setImageResource(R.mipmap.ic_launcher_foreground)
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return users.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each item to
        val tvUsername = view.tvUsername
        val ivUser = view.ivUser
        val tvIsAdmin = view.tvIsAdmin
        val tvCamion = view.tvCamion
        val ivEditUser = view.ivEditUser
        val ivDeleteCartUser = view.ivDeleteCartUser
    }
}