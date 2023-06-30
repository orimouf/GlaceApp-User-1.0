package com.foodapp.app.activity

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.foodapp.app.R
import com.foodapp.app.base.BaseActivity
import com.foodapp.app.model.LoginModel
import com.foodapp.app.utils.Common
import com.foodapp.app.utils.Common.showLoadingProgress
import com.foodapp.app.api.*
import com.foodapp.app.utils.Common.getCurrentLanguage
import com.foodapp.app.utils.Common.getLog
import com.foodapp.app.utils.Common.showErrorFullMsg
import com.foodapp.app.utils.SharePreference.Companion.getBooleanSharedPrefs
import com.foodapp.app.utils.SharePreference.Companion.getStringSharedPrefs
import com.foodapp.app.utils.SharePreference.Companion.isLogin
import com.foodapp.app.utils.SharePreference.Companion.loginToken
import com.foodapp.app.utils.SharePreference.Companion.setBooleanSharedPrefs
import com.foodapp.app.utils.SharePreference.Companion.setStringSharedPrefs
import com.foodapp.app.utils.SharePreference.Companion.userEmail
import com.foodapp.app.utils.SharePreference.Companion.userId
import com.foodapp.app.utils.SharePreference.Companion.userName
//import com.foodapp.app.utils.TokenManager
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_login.edEmail
import kotlinx.android.synthetic.main.activity_login.edPassword
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class LoginActivity:BaseActivity() {
    lateinit var strToken: String
//    @Inject
//    lateinit var tokenManager: TokenManager

    override fun setLayout(): Int {
        return R.layout.activity_login
    }
    override fun InitView() {
        this@LoginActivity.getCurrentLanguage(false)
        FirebaseApp.initializeApp(this@LoginActivity)

        strToken=FirebaseInstanceId.getInstance().token.toString()
        getLog("Token== ",strToken)
    }

    fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvLogin->{
                if (edEmail.text.toString().equals("")) {
                    showErrorFullMsg(this@LoginActivity,resources.getString(R.string.validation_email))
                } else if (!Common.isValidEmail(edEmail.text.toString())) {
                    showErrorFullMsg(this@LoginActivity,resources.getString(R.string.validation_valid_email))
                } else if (edPassword.text.toString().equals("")) {
                    showErrorFullMsg(this@LoginActivity,resources.getString(R.string.validation_password))
                } else {
                    val hasmap = HashMap<String, String>()
                    hasmap["email"] = edEmail.text.toString()
                    hasmap["password"] = edPassword.text.toString()
                    hasmap["token"] = strToken
                    if (Common.isCheckNetwork(this@LoginActivity)) {
                        callApiLogin(hasmap)
                    } else {
                        Common.alertErrorOrValidationDialog(this@LoginActivity,resources.getString(R.string.no_internet))
                    }
                }
            }
            R.id.tvSignup->{
                openActivity(RegistrationActivity::class.java)
            }
            R.id.tvForgetPassword->{
                openActivity(ForgetPasswordActivity::class.java)
            }
            R.id.tvSkip->{
                openActivity(DashboardActivity::class.java)
                finish()
            }
        }
    }

    private fun callApiLogin(hashmap: HashMap<String, String>) {
        showLoadingProgress(this@LoginActivity)
        val call = ApiClient.getClient.getLogin(hashmap)
        call.enqueue(object : Callback<RestResponse<LoginModel>> {
            override fun onResponse(
                call: Call<RestResponse<LoginModel>>,
                response: Response<RestResponse<LoginModel>>
            ) {
                if(response.code()==200){
                    val loginResponce: RestResponse<LoginModel> = response.body()!!
//                    tokenManager.saveToken(loginResponce.getAccessToken())

                    Toast.makeText(this@LoginActivity, response.body()!!.getData().toString(), Toast.LENGTH_LONG).show()
                    Toast.makeText(this@LoginActivity, loginResponce.getStatus().toString(), Toast.LENGTH_SHORT).show()

                    if (loginResponce.getStatus().equals("1")) {
                        Common.dismissLoadingProgress()
                        val loginModel: LoginModel = loginResponce.getData()!!
                        setBooleanSharedPrefs(this@LoginActivity, isLogin,true)
                        setStringSharedPrefs(this@LoginActivity, loginToken, strToken)
                        setStringSharedPrefs(this@LoginActivity,userId, loginModel.getId()!!)
                        setStringSharedPrefs(this@LoginActivity,userName, loginModel.getName()!!)
                        setStringSharedPrefs(this@LoginActivity,userEmail, loginModel.getEmail()!!)
                        val intent = Intent(this@LoginActivity,DashboardActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                        finishAffinity()
                    }
                } else  {
                    val error=JSONObject(response.errorBody()!!.string())
                    val status=error.getInt("status")
                    if(status==2){
                        Common.dismissLoadingProgress()
                        startActivity(Intent(this@LoginActivity,OTPVerificatinActivity::class.java).putExtra("email", edEmail.text.toString()))
                    }else{
                        Common.dismissLoadingProgress()
                        Common.showErrorFullMsg(this@LoginActivity,error.getString("message"))
                    }

                }
            }

            override fun onFailure(call: Call<RestResponse<LoginModel>>, t: Throwable) {
                Common.dismissLoadingProgress()
                Common.alertErrorOrValidationDialog(
                    this@LoginActivity,
                    resources.getString(R.string.error_msg)
                )
            }
        })
    }

    override fun onResume() {
        super.onResume()
        this@LoginActivity.getCurrentLanguage(false)
    }

    override fun onBackPressed() {
        finish()
        finishAffinity()
    }

}