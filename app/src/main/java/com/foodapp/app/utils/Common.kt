@file:Suppress("DEPRECATION")

package com.foodapp.app.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.app.R
import com.foodapp.app.activity.DashboardActivity
import com.foodapp.app.activity.LoginActivity
import com.foodapp.app.api.ApiClient
import com.foodapp.app.api.RestResponse
import com.foodapp.app.base.BaseAdaptor
import com.foodapp.app.model.AddonsModel
import com.foodapp.app.model.LocationModel
import com.foodapp.app.utils.SharePreference.Companion.SELECTED_LANGUAGE
import com.foodapp.app.utils.SharePreference.Companion.isCurrancy
import com.androidadvance.topsnackbar.TSnackbar
import com.foodapp.app.utils.SharePreference.Companion.getStringSharedPrefs
import com.foodapp.app.utils.SharePreference.Companion.setBooleanSharedPrefs
import com.foodapp.app.utils.SharePreference.Companion.setStringSharedPrefs
import kotlinx.android.synthetic.main.dlg_setting.view.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern

object Common {
    var isProfileEdit:Boolean=false
    var isProfileMainEdit:Boolean=false
    var isCartTrue:Boolean=false
    var isCartTrueOut:Boolean=false
    fun getToast(activity: Activity, strTxtToast: String) {
        Toast.makeText(activity, strTxtToast, Toast.LENGTH_SHORT).show()
    }

    fun getLog(strKey: String, strValue: String) {
        Log.e(">>>---  $strKey  ---<<<", strValue)
    }

    fun isValidEmail(strPattern: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(strPattern).matches();
    }

    // get current date/time
    fun getCurrentDateTime (): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatter.format(time)
    }
    // check if date 1 after date 2
    @RequiresApi(Build.VERSION_CODES.O)
    fun betweenDate(firstDate: String, secondDate: String): Boolean {
        val firstDateFormat = "${firstDate[0]}${firstDate[1]}${firstDate[2]}${firstDate[3]}${firstDate[4]}${firstDate[5]}${firstDate[6]}" +
                "${firstDate[7]}${firstDate[8]}${firstDate[9]} ${firstDate[11]}${firstDate[12]}${firstDate[13]}" +
                "${firstDate[14]}${firstDate[15]}${firstDate[16]}${firstDate[17]}${firstDate[18]}"
        val secondDateFormat = "${firstDate[0]}${firstDate[1]}${firstDate[2]}${firstDate[3]}${firstDate[4]}${firstDate[5]}${firstDate[6]}" +
                "${firstDate[7]}${firstDate[8]}${firstDate[9]} ${firstDate[11]}${firstDate[12]}${firstDate[13]}" +
                "${firstDate[14]}${firstDate[15]}${firstDate[16]}${firstDate[17]}${firstDate[18]}"
//        val firstDateFormat = firstDate.split("+")[0]
//        val secondDateFormat = secondDate.split("\\.")[0]

//        val sourceSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
//        val requiredSdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//        val newFirstDateFormat = requiredSdf.format(sourceSdf.parse(firstDateFormat))
//        val newSecondDateFormat = requiredSdf.format(sourceSdf.parse(secondDateFormat))
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var convertedDate = Date()
        var convertedDate2 = Date()
        var response: Boolean = false
        try {
            convertedDate = dateFormat.parse(firstDateFormat)
            convertedDate2 = dateFormat.parse(secondDateFormat)
            response = convertedDate2.after(convertedDate)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return response
    }

    fun isCheckNetwork(context: Context): Boolean {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun openActivity(activity: Activity, destinationClass: Class<*>?) {
        activity.startActivity(Intent(activity, destinationClass))
        activity.overridePendingTransition(R.anim.fad_in, R.anim.fad_out)
    }

    open var dialog: Dialog? = null

    open fun dismissLoadingProgress() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
            dialog = null
        }
    }

    open fun showLoadingProgress(context: Activity) {
        if (dialog != null) {
            dialog!!.dismiss()
            dialog = null
        }
        dialog = Dialog(context)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setContentView(R.layout.dlg_progress)
        dialog!!.setCancelable(false)
        dialog!!.show()
    }

    fun alertErrorOrValidationDialog(act: Activity, msg: String?) {
        var dialog: Dialog? = null
        try {
            if (dialog != null) {
                dialog.dismiss()
                dialog = null
            }
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
            }
            dialog.setContentView(m_view)
            dialog.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun settingDialog(act: Activity) {
        var dialog: Dialog? = null
        try {
            if (dialog != null) {
                dialog.dismiss()
                dialog = null
            }
            dialog = Dialog(act, R.style.AppCompatAlertDialogStyleBig)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            );
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            val m_inflater = LayoutInflater.from(act)
            val m_view = m_inflater.inflate(R.layout.dlg_setting, null, false)

            val finalDialog: Dialog = dialog
            m_view.tvOkSetting.setOnClickListener {
                var i = Intent()
                i.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                i.addCategory(Intent.CATEGORY_DEFAULT)
                i.setData(android.net.Uri.parse("package:" + act.getPackageName()))
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                act.startActivity(i)
                dialog.dismiss()
                finalDialog.dismiss()
            }
            dialog.setContentView(m_view)
            if (!act.isFinishing) dialog.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun Activity.setLogout() {
        val getLanguage= getStringSharedPrefs(this,SELECTED_LANGUAGE)
        val isTutorialsActivity:Boolean=SharePreference.getBooleanSharedPrefs(this!!,SharePreference.isTutorial)
        val preference = SharePreference(this)
        preference.mLogout()
        setBooleanSharedPrefs(this,SharePreference.isTutorial,isTutorialsActivity)
        setStringSharedPrefs(this,SharePreference.SELECTED_LANGUAGE,getLanguage!!)
        val intent = Intent(this, LoginActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent);
        finish()
    }



    @SuppressLint("NewApi", "SimpleDateFormat")
    fun getDayAndMonth(strDate: String): String {
        val sd = SimpleDateFormat("dd-MM-yyyy")
        val sdout = SimpleDateFormat("dd-MMMM-yyyy")
        val sdday = SimpleDateFormat("EEEE")
        val date: Date = sd.parse(strDate)!!
        val getDay = sdday.format(date)
        val getDate = sdout.format(date)
        val stringArray = getDate.split("-").toTypedArray()
        val strDay = stringArray.get(0).plus("th")
        return getDay.plus(" ".plus(stringArray.get(1))).plus(" ".plus(strDay))
    }

    fun setImageUpload(strParameter:String,mSelectedFileImg: File): MultipartBody.Part{
        return  MultipartBody.Part.createFormData(strParameter, mSelectedFileImg.getName(), RequestBody.create("image/*".toMediaType(), mSelectedFileImg))
    }

    fun setRequestBody(bodyData:String):RequestBody{
        return bodyData.toRequestBody("text/plain".toMediaType())
    }


    fun getCurrancy(act:Activity):String{
        return getStringSharedPrefs(act,isCurrancy)!!
    }


    fun Activity.getCurrentLanguage(isChangeLanguage: Boolean) {
        if (getStringSharedPrefs(this,SELECTED_LANGUAGE) == null || getStringSharedPrefs(this,SELECTED_LANGUAGE).equals("",true)) {
            setStringSharedPrefs(this,SELECTED_LANGUAGE, resources.getString(R.string.language_english))
        }
        val locale = if (getStringSharedPrefs(this,SELECTED_LANGUAGE).equals(resources.getString(R.string.language_english),true)) {
            Locale("en-us")
        } else {
            Locale("ar")
        }
        //start
        val activityRes = resources
        val activityConf = activityRes.configuration
        val newLocale = locale
        if (getStringSharedPrefs(this,SELECTED_LANGUAGE).equals(resources.getString(R.string.language_english),true)) {
            activityConf.setLocale(Locale("en-us")) // API 17+ only.
        } else {
            activityConf.setLocale(Locale("ar"))
        }
        activityRes.updateConfiguration(activityConf, activityRes.displayMetrics)
        val applicationRes = applicationContext.resources
        val applicationConf = applicationRes.configuration
        applicationConf.setLocale(newLocale)
        applicationRes.updateConfiguration(applicationConf, applicationRes.displayMetrics)

        if (isChangeLanguage) {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }


    fun alertNotesDialog(act: Activity, msg: String?) {
        var dialog: Dialog? = null
        try {
            if (dialog != null) {
                dialog.dismiss()
            }
            dialog = Dialog(act, R.style.AppCompatAlertDialogStyleBig)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            );
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            val m_inflater = LayoutInflater.from(act)
            val m_view = m_inflater.inflate(R.layout.dlg_note, null, false)
            val textDesc: TextView = m_view.findViewById(R.id.tvNotes)
            textDesc.text = msg
            val tvOk: TextView = m_view.findViewById(R.id.tvOk)
            val finalDialog: Dialog = dialog
            tvOk.setOnClickListener {
                finalDialog.dismiss()
            }
            dialog.setContentView(m_view)
            dialog.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun openDialogSelectedAddons(activity: Activity,orderHistoryList: ArrayList<AddonsModel>) {
        val dialog: Dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        val lp = WindowManager.LayoutParams()
        lp.windowAnimations = R.style.DialogAnimation
        dialog.window!!.attributes = lp
        dialog.setContentView(R.layout.dlg_displayaddons)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val ivCancel = dialog.findViewById<ImageView>(R.id.ivCancel)
        val rvPromocode = dialog.findViewById<RecyclerView>(R.id.rvSelectedAddonsList)
        setSelectedAddonsAdaptor(orderHistoryList,activity,rvPromocode)
        ivCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun setSelectedAddonsAdaptor(orderHistoryList: ArrayList<AddonsModel>, activity: Activity, rvAddons: RecyclerView) {
        val orderHistoryAdapter = object : BaseAdaptor<AddonsModel>(activity, orderHistoryList) {
            @SuppressLint("SetTextI18n")
            override fun onBindData(
                holder: RecyclerView.ViewHolder?,
                `val`: AddonsModel,
                position: Int
            ) {
                val tvAddonsName: TextView = holder!!.itemView.findViewById(R.id.tvAddonsName)
                val tvAddonsPrice: TextView = holder.itemView.findViewById(R.id.tvAddonsPrice)
                val ivCancel: ImageView = holder.itemView.findViewById(R.id.ivCancel)
                ivCancel.visibility= View.GONE
                tvAddonsName.text = orderHistoryList.get(position).getName()
                if(String.format(Locale.US,"%.2f",orderHistoryList[position].getPrice()!!.toDouble())=="0.00"){
                    tvAddonsPrice.text = "Free"
                }else{
                    tvAddonsPrice.text = getStringSharedPrefs(activity,isCurrancy) +String.format(Locale.US,"%,.02f", orderHistoryList.get(position).getPrice()!!.toDouble())
                }

            }

            override fun setItemLayout(): Int {
                return R.layout.row_selectedaddons
            }

            override fun setNoDataView(): TextView? {
                return null
            }
        }
        rvAddons.adapter = orderHistoryAdapter
        rvAddons.layoutManager = LinearLayoutManager(activity)
        rvAddons.itemAnimator = DefaultItemAnimator()
        rvAddons.isNestedScrollingEnabled = true
    }

    fun callApiLocation(activity: Activity) {
        showLoadingProgress(activity)
        val call = ApiClient.getClient.getLocation()
        call.enqueue(object : Callback<RestResponse<LocationModel>> {
            override fun onResponse(
                call: Call<RestResponse<LocationModel>>,
                response: Response<RestResponse<LocationModel>>
            ) {
                if (response.code() == 200) {
                    dismissLoadingProgress()
                    val restResponce: RestResponse<LocationModel> = response.body()!!
                    if(restResponce.getStatus().equals("1")){
                        if(restResponce.getData()!!.getLang()!=null && restResponce.getData()!!.getLat()!=null){
                            val urlAddress = "http://maps.google.com/maps?q=" + restResponce.getData()!!.getLat() + "," + restResponce.getData()!!.getLang() + "(" + "FoodApp" + ")&iwloc=A&hl=es"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress))
                            activity.startActivity(intent)
                        }
                    }
                }else{
                    val error= JSONObject(response.errorBody()!!.string())
                    dismissLoadingProgress()
                    alertErrorOrValidationDialog(
                        activity,
                        error.getString("message")
                    )
                }
            }

            override fun onFailure(call: Call<RestResponse<LocationModel>>, t: Throwable) {
                dismissLoadingProgress()
                alertErrorOrValidationDialog(
                    activity,
                    activity.resources.getString(R.string.error_msg)
                )
            }

        })
    }

    fun showSuccessFullMsg(activity:Activity,message:String){
        val snackbar: TSnackbar = TSnackbar.make(activity.findViewById(android.R.id.content),message, TSnackbar.LENGTH_SHORT)
        snackbar.setActionTextColor(Color.WHITE)
        val snackbarView: View = snackbar.getView()
        snackbarView.setBackgroundColor(activity.resources.getColor(R.color.green))
        val textView =snackbarView.findViewById<View>(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        snackbar.show()
    }

    fun showErrorFullMsg(activity:Activity,message:String){
        val snackbar: TSnackbar = TSnackbar.make(activity.findViewById(android.R.id.content),message, TSnackbar.LENGTH_SHORT)
        snackbar.setActionTextColor(Color.WHITE)
        val snackbarView: View = snackbar.getView()
        snackbarView.setBackgroundColor(Color.RED)
        val textView =snackbarView.findViewById<View>(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        snackbar.show()
    }

    fun getDate(strDate:String):String{
        val curFormater = SimpleDateFormat("dd-MM-yyyy",Locale.US)
        val dateObj = curFormater.parse(strDate)
        val postFormater = SimpleDateFormat("dd MMM yyyy",Locale.US)
        return postFormater.format(dateObj)
    }

    fun closeKeyBoard(activity: Activity) {
        val view: View? = activity.currentFocus
        if (view != null) {
            try {
                val imm: InputMethodManager =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getPrice(price:Double,tvPrice:TextView,activity: Activity){
        tvPrice.text = getStringSharedPrefs(activity,isCurrancy) +String.format(Locale.US,"%,.2f",price)
    }

}