package com.foodapp.app.activity

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.foodapp.app.R
import com.foodapp.app.base.BaseActivity
import com.foodapp.app.model.RegistrationModel
import com.foodapp.app.utils.Common
import com.foodapp.app.api.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_registration.edEmail
import kotlinx.android.synthetic.main.activity_registration.edPassword
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity :BaseActivity() {
    override fun setLayout(): Int {
        return R.layout.activity_registration
    }

    override fun InitView() {
        Common.getCurrentLanguage(this@RegistrationActivity, false)
        tvTermsAndCondition.setOnClickListener {
            openActivity(TearmsAndConditionActivity::class.java)
        }
    }

    fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvLogin->{
                finish()
            }
            R.id.tvSignup->{
                if(edFullName.text.toString().equals("")){
                    Common.showErrorFullMsg(this@RegistrationActivity,resources.getString(R.string.validation_name))
                }else if(edEmail.text.toString().equals("")){
                    Common.showErrorFullMsg(this@RegistrationActivity,resources.getString(R.string.validation_email))
                }else if(!Common.isValidEmail(edEmail.text.toString())){
                    Common.showErrorFullMsg(this@RegistrationActivity,resources.getString(R.string.validation_valid_email))
                }else if(edPassword.text.toString().equals("")){
                    Common.showErrorFullMsg(this@RegistrationActivity,resources.getString(R.string.validation_password))
                }else if(edCPassword.text.toString().equals("")){
                    Common.showErrorFullMsg(this@RegistrationActivity,resources.getString(R.string.validation_cpassword))
                }else if(!edPassword.text.toString().equals(edCPassword.text.toString())){
                    Common.showErrorFullMsg(this@RegistrationActivity,resources.getString(R.string.validation_valid_cpassword))
                }else{
                    if(cbCheck.isChecked){
                        val hasmap= HashMap<String, String>()
                        hasmap["appId"] = edFullName.text.toString() + "0001"
                        hasmap["username"] = edFullName.text.toString()
                        hasmap["email"] = edEmail.text.toString()
                        hasmap["password"] = edPassword.text.toString()
                        if(Common.isCheckNetwork(this@RegistrationActivity)){
                            callApiRegistration(hasmap)
                        }else{
                            Common.alertErrorOrValidationDialog(this@RegistrationActivity,resources.getString(R.string.no_internet))
                        }
                    }else{
                        Common.showErrorFullMsg(this@RegistrationActivity,"Please check tearm and condition")
                    }

                }
            }
        }
    }

    private fun callApiRegistration(hasmap: HashMap<String, String>) {
        Common.showLoadingProgress(this@RegistrationActivity)
        val call = ApiClient.getClient.setRegistration(hasmap)
        call.enqueue(object : Callback<RestResponse<RegistrationModel>> {
            override fun onResponse(
                call: Call<RestResponse<RegistrationModel>>,
                response: Response<RestResponse<RegistrationModel>>
            ) {
                if (response.code() == 201) {
                    val registrationResponse: RestResponse<RegistrationModel> = response.body()!!
                    if(registrationResponse.getStatus().equals("1")) {
                        Common.dismissLoadingProgress()
                        successfulDialog(this@RegistrationActivity,registrationResponse.getMessage())
                    }else if (registrationResponse.getStatus().equals("0")) {
                        Common.dismissLoadingProgress()
                        Common.alertErrorOrValidationDialog(
                            this@RegistrationActivity,
                            registrationResponse.getMessage()
                        )
                    }
                } else {
                    val error=JSONObject(response.errorBody()!!.string())
                    Common.dismissLoadingProgress()
                    Common.showErrorFullMsg(this@RegistrationActivity,error.getString("message"))
                }
            }

            override fun onFailure(call: Call<RestResponse<RegistrationModel>>, t: Throwable) {
                Common.dismissLoadingProgress()
                Common.alertErrorOrValidationDialog(
                    this@RegistrationActivity,
                    resources.getString(R.string.error_msg)
                )
            }
        })
    }

    fun successfulDialog(act: Activity, msg: String?) {
        var dialog: Dialog? = null
        try {
            dialog?.dismiss()
            dialog = Dialog(act, R.style.AppCompatAlertDialogStyleBig)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            );
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            val m_inflater = LayoutInflater.from(act)
            val m_view = m_inflater.inflate(R.layout.dlg_validation, null, false)
            val textDesc: TextView = m_view.findViewById(R.id.tvMessage)
            textDesc.text = msg
            val tvOk: TextView = m_view.findViewById(R.id.tvOk)
            val finalDialog: Dialog = dialog
            tvOk.setOnClickListener {
                finalDialog.dismiss()
//              startActivity(Intent(this@RegistrationActivity,OTPVerificatinActivity::class.java).putExtra("email", edEmail.text.toString()))
                openActivity(LoginActivity::class.java)
                finish()
            }
            dialog.setContentView(m_view)
            dialog.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        Common.getCurrentLanguage(this@RegistrationActivity, false)
    }
}