package com.foodapp.app.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.foodapp.app.R
import com.foodapp.app.base.BaseActivity
import com.foodapp.app.utils.Common.getCurrentLanguage
import kotlin.collections.ArrayList
import android.widget.Toast
import com.foodapp.app.adaptor.UserAdaptor
import com.foodapp.app.model.UserModel
import com.foodapp.app.utils.Common.getCurrentDateTime
import kotlinx.android.synthetic.main.activity_cart.ivBack
import kotlinx.android.synthetic.main.activity_cart.ivHome
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.dlg_updateuser.*
import java.lang.Integer.parseInt

class UsersActivity : BaseActivity() {

    override fun setLayout(): Int {
        return R.layout.activity_users
    }

    /**
     * Function is used to show the list of inserted data.
     */
    private fun setupListOfUserDataIntoRecyclerView() {
        if (getUsersList().size > 0) {
            rvUsers.visibility = View.VISIBLE
            tvNoUserDataFound.visibility = View.GONE

            rvUsers.layoutManager = LinearLayoutManager(this)
            val userAdapter = UserAdaptor(this, getUsersList())
            rvUsers.adapter = userAdapter
        } else {
            rvUsers.visibility = View.GONE
            tvNoUserDataFound.visibility = View.VISIBLE
        }
    }

    /**
     * Function is used to get the Items List from the database table.
     */
    private fun getUsersList(): ArrayList<UserModel> {
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val userList: ArrayList<UserModel> = databaseHandler.viewUser()

        return userList
    }

    override fun InitView() {
        this@UsersActivity.getCurrentLanguage(false)

        setupListOfUserDataIntoRecyclerView()

        ivBack.setOnClickListener {
            val intent=Intent(this@UsersActivity,DashboardActivity::class.java).putExtra("pos","1")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        ivHome.setOnClickListener {
            val intent=Intent(this@UsersActivity,DashboardActivity::class.java).putExtra("pos","1")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Method is used to show the Custom Dialog.
     */
    fun updateUserDialog(userModel: UserModel) {

        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        /*Set the screen content from a layout resource.
         The resource will be inflated, adding all top-level views to the screen.*/
        updateDialog.setContentView(R.layout.dlg_updateuser)

        updateDialog.etUpdateUsername.setText(userModel.username)
        updateDialog.etUpdatePassword.setText(userModel.password)
        updateDialog.etUpdateCamion.setText(userModel.camion)
        updateDialog.etUpdateIsAdmin.setText(userModel.isadmin.toString())

        updateDialog.tvUpdateUser.setOnClickListener(View.OnClickListener {

            val email = updateDialog.etUpdateEmail.text.toString()
            val username = updateDialog.etUpdateUsername.text.toString()
            val password = updateDialog.etUpdatePassword.text.toString()
            val camion = updateDialog.etUpdateCamion.text.toString()
            val isadmin = updateDialog.etUpdateIsAdmin.text.toString()
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)

            if (!username.isEmpty() && !password.isEmpty() && !camion.isEmpty() && !isadmin.isEmpty()) {

                val status =
                    databaseHandler.updateUser(UserModel(userModel.id, userModel.server_id, username, email, password, userModel.profile_pic,
                        camion, parseInt(isadmin), userModel.createdAt, getCurrentDateTime(), userModel.__v, userModel.up_to_server))
                if (status > -1) {
                    Toast.makeText(applicationContext, "User Updated.", Toast.LENGTH_LONG).show()

                    setupListOfUserDataIntoRecyclerView()

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
        updateDialog.tvCancelUpdateUser.setOnClickListener(View.OnClickListener {
            updateDialog.dismiss()
        })
        //Start the dialog and display it on screen.
        updateDialog.show()
    }

    /**
     * Method is used to show the Alert Dialog.
     */
    fun deleteUserAlertDialog(userModel: UserModel) {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Delete User")
        //set message for alert dialog
        builder.setMessage("Are you sure you wants to delete ${userModel.username}.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->

            //creating the instance of DatabaseHandler class
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
            //calling the deleteEmployee method of DatabaseHandler class to delete record
            val status = databaseHandler.deleteUser(UserModel(userModel.id,"", "", "", "", "null",
                "0", 0, "0", "0", "0", 0))
            if (status > -1) {
                Toast.makeText(
                    applicationContext,
                    "User deleted successfully.",
                    Toast.LENGTH_LONG
                ).show()

                setupListOfUserDataIntoRecyclerView()
            }

            dialogInterface.dismiss() // Dialog will be dismissed
        }
        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show()  // show the dialog to UI
    }

    override fun onBackPressed() {
        val intent=Intent(this@UsersActivity,DashboardActivity::class.java).putExtra("pos","1")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        this@UsersActivity.getCurrentLanguage(false)
    }
}
