package com.foodapp.app.activity

import android.content.Intent
import com.foodapp.app.R
import com.foodapp.app.base.BaseActivity
import com.foodapp.app.utils.Common.getCurrentLanguage
import android.widget.Toast
import com.foodapp.app.model.UserModel
import kotlinx.android.synthetic.main.activity_adduser.*
import kotlinx.android.synthetic.main.activity_cart.ivBack
import kotlinx.android.synthetic.main.activity_cart.ivHome

class AddUserActivity : BaseActivity() {

    override fun setLayout(): Int {
        return R.layout.activity_adduser
    }

    private fun addUser() {
        val email = etEmail.text.toString()
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()
        val camion = etCamion.text.toString()
        val isadmin = 0
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        if (!email.isEmpty() && !username.isEmpty() && !password.isEmpty() && !camion.isEmpty()) {
            val status =
                databaseHandler.addUser(UserModel(0,"", username, email, password , "null",
                    camion, isadmin, "0", "0", "0",0))
            if (status > -1) {
                Toast.makeText(applicationContext, "User saved", Toast.LENGTH_LONG).show()
                etUsername.text.clear()
                etPassword.text.clear()
                etCamion.text.clear()
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Data field cannot be blank",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun InitView() {
        this@AddUserActivity.getCurrentLanguage(false)

        btnAddUser.setOnClickListener {
            addUser()
        }

        ivBack.setOnClickListener {
            val intent=Intent(this@AddUserActivity,DashboardActivity::class.java).putExtra("pos","1")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        ivHome.setOnClickListener {
            val intent=Intent(this@AddUserActivity,DashboardActivity::class.java).putExtra("pos","1")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        val intent=Intent(this@AddUserActivity,DashboardActivity::class.java).putExtra("pos","1")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        this@AddUserActivity.getCurrentLanguage(false)
    }
}
