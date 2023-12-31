package com.foodapp.app.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.foodapp.app.R
import com.foodapp.app.api.SingleResponse
import com.foodapp.app.base.BaseActivity
import com.foodapp.app.model.ProfileModel
import com.foodapp.app.utils.Common.alertErrorOrValidationDialog
import com.foodapp.app.utils.Common.dismissLoadingProgress
import com.foodapp.app.utils.Common.getLog
import com.foodapp.app.utils.Common.isCheckNetwork
import com.foodapp.app.utils.Common.isProfileEdit
import com.foodapp.app.utils.Common.isProfileMainEdit
import com.foodapp.app.utils.Common.setImageUpload
import com.foodapp.app.utils.Common.setRequestBody
import com.foodapp.app.utils.Common.showLoadingProgress
import com.foodapp.app.api.*
import com.foodapp.app.utils.Common
import com.foodapp.app.utils.Common.getCurrentLanguage
import com.foodapp.app.utils.SharePreference.Companion.getStringSharedPrefs
import com.foodapp.app.utils.SharePreference.Companion.userId
import kotlinx.android.synthetic.main.activity_editprofile.*
import kotlinx.android.synthetic.main.dlg_externalstorage.view.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*

@Suppress("DEPRECATION")
class EditProfileActivity:BaseActivity() {
    private val SELECT_FILE = 201
    private val REQUEST_CAMERA = 202
    private var mSelectedFileImg: File? = null
    override fun setLayout(): Int {
        return R.layout.activity_editprofile
    }
    override fun InitView() {
        if (isCheckNetwork(this@EditProfileActivity)) {
            val hasmap = HashMap<String, String>()
            hasmap.put("user_id", getStringSharedPrefs(this@EditProfileActivity, userId)!!)
            callApiProfile(hasmap)
        } else {
            alertErrorOrValidationDialog(
                this@EditProfileActivity,
                resources.getString(R.string.no_internet)
            )
        }
    }

    fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.tvUpdate->{
                if (edUserName.text.toString().equals("")) {
                    Common.showErrorFullMsg(this@EditProfileActivity,resources.getString(R.string.validation_name))
                } else {
                    if (isCheckNetwork(this@EditProfileActivity)) {
                        mCallApiEditProfile()
                    } else {
                        alertErrorOrValidationDialog(this@EditProfileActivity, resources.getString(R.string.no_internet))
                    }
                }
            }
            R.id.ivGellary->{
                getExternalStoragePermission()
            }

        }
    }

    private fun callApiProfile(hasmap: HashMap<String, String>) {
        showLoadingProgress(this@EditProfileActivity)
        val call = ApiClient.getClient.getProfile(hasmap)
        call.enqueue(object : Callback<RestResponse<ProfileModel>> {
            override fun onResponse(call: Call<RestResponse<ProfileModel>>, response: Response<RestResponse<ProfileModel>>) {
                if (response.code() == 200) {
                    val restResponce: RestResponse<ProfileModel> = response.body()!!
                    if (restResponce.getStatus().equals("1")) {
                        dismissLoadingProgress()
                        val dataResponse: ProfileModel = restResponce.getData()!!
                        setProfileData(dataResponse)
                    } else if (restResponce.getData()!!.equals("0")) {
                        dismissLoadingProgress()
                        alertErrorOrValidationDialog(
                            this@EditProfileActivity,
                            restResponce.getMessage()
                        )
                    }
                }
            }

            override fun onFailure(call: Call<RestResponse<ProfileModel>>, t: Throwable) {
                dismissLoadingProgress()
                alertErrorOrValidationDialog(
                    this@EditProfileActivity,
                    resources.getString(R.string.error_msg)
                )
            }
        })
    }

    private fun setProfileData(dataResponse: ProfileModel) {
        edEmailAddress!!.setText(dataResponse.getEmail())
        edUserName!!.setText(dataResponse.getName())
        tvMobileNumber!!.text=dataResponse.getIsAdmin().toString()
        Glide.with(this@EditProfileActivity).load(dataResponse.getProfilePic()).placeholder(
            ResourcesCompat.getDrawable(resources,R.drawable.ic_placeholder,null)).into(ivProfileEditPic)
    }


    /*-------------Image Upload Code-------------*/
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data)
            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data!!)
            }
        }
    }

    fun getExternalStoragePermission() {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        imageSelectDialog(this@EditProfileActivity)
                    }
                    if (report.isAnyPermissionPermanentlyDenied) {
                        Common.settingDialog(this@EditProfileActivity)
                    }
                }
                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            })
            .onSameThread()
            .check()
    }

    @SuppressLint("InlinedApi")
    fun imageSelectDialog(act: Activity) {
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
            dialog.setCancelable(true)
            val m_inflater = LayoutInflater.from(act)
            val m_view = m_inflater.inflate(R.layout.dlg_externalstorage, null, false)

            val finalDialog: Dialog = dialog
            m_view.tvSetImageCamera.setOnClickListener {
                finalDialog.dismiss()
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(
                    intent,
                    REQUEST_CAMERA
                )
            }
            m_view.tvSetImageGallery.setOnClickListener {
                finalDialog.dismiss()
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_PICK
                startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE)
            }
            dialog.setContentView(m_view)
            if (!act.isFinishing) dialog.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun onSelectFromGalleryResult(data: Intent?) {
        var bm: Bitmap? = null
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(
                    applicationContext.contentResolver,
                    data.data
                )
                val bytes = ByteArrayOutputStream()
                bm!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
                mSelectedFileImg = File(
                    Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis().toString() + ".jpg"
                )
                val fo: FileOutputStream
                try {
                    mSelectedFileImg!!.createNewFile()
                    fo = FileOutputStream(mSelectedFileImg!!)
                    fo.write(bytes.toByteArray())
                    fo.close()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        ivProfileEditPic.setImageBitmap(bm);
    }

    private fun onCaptureImageResult(data: Intent) {
        val thumbnail = data.extras!!["data"] as Bitmap?
        val bytes = ByteArrayOutputStream()
        thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)

        mSelectedFileImg = File(
            Environment.getExternalStorageDirectory(),
            System.currentTimeMillis().toString() + ".jpeg"
        )
        val fo: FileOutputStream
        try {
            mSelectedFileImg!!.createNewFile()
            fo = FileOutputStream(mSelectedFileImg)
            fo.write(bytes.toByteArray())
            fo.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Glide.with(this@EditProfileActivity)
            .load(Uri.parse("file://" + mSelectedFileImg!!.getPath()))
            .into(ivProfileEditPic)
    }



    private fun mCallApiEditProfile() {
        showLoadingProgress(this@EditProfileActivity)
        val call: Call<SingleResponse>?
        getLog("File_Path",mSelectedFileImg!!.absolutePath)
        getLog("File_Path",mSelectedFileImg!!.isFile.toString())
        call = if(mSelectedFileImg!=null)
            ApiClient.getClient.setProfile(setRequestBody(getStringSharedPrefs(this@EditProfileActivity, userId)!!),
                setRequestBody(edUserName.text.toString()),setImageUpload("image",mSelectedFileImg!!)) else {
            val profile = ApiClient.getClient.setProfile(
                setRequestBody(getStringSharedPrefs(this@EditProfileActivity, userId)!!),
                setRequestBody(edUserName.text.toString()), null
            )
            profile
        }
        call.enqueue(object : Callback<SingleResponse> {
            override fun onResponse(call: Call<SingleResponse>, response: Response<SingleResponse>) {
                if(response.code()==200){
                    val editProfileResponce: SingleResponse = response.body()!!
                    if(editProfileResponce.getStatus().equals("1")){
                        dismissLoadingProgress()
                        isProfileEdit=true
                        isProfileMainEdit=true
                        successfulDialog(this@EditProfileActivity, editProfileResponce.getMessage())
                    }else if(editProfileResponce.getStatus().equals("0")){
                        dismissLoadingProgress()
                        alertErrorOrValidationDialog(this@EditProfileActivity,editProfileResponce.getMessage())
                    }
                }else{
                    val restResponse = response.errorBody()!!.string()
                    val jsonObject= JSONObject(restResponse)
                    dismissLoadingProgress()
                    alertErrorOrValidationDialog(
                        this@EditProfileActivity,
                        jsonObject.getString("message")
                    )
                }

            }

            override fun onFailure(call: Call<SingleResponse>, t: Throwable) {
                dismissLoadingProgress()
                alertErrorOrValidationDialog(
                    this@EditProfileActivity,
                    resources.getString(R.string.error_msg)
                )
            }
        })

    }


    fun successfulDialog(act: Activity, msg: String?) {
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
            val m_view = m_inflater.inflate(R.layout.dlg_validation, null, false)
            val textDesc: TextView = m_view.findViewById(R.id.tvMessage)
            textDesc.text = msg
            val tvOk: TextView = m_view.findViewById(R.id.tvOk)
            val finalDialog: Dialog = dialog
            tvOk.setOnClickListener {
                finalDialog.dismiss()
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
        this@EditProfileActivity.getCurrentLanguage(false)
    }
}