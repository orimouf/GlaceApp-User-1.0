package com.foodapp.app.fragment

import android.content.Intent
import android.view.View
import com.foodapp.app.BuildConfig
import com.foodapp.app.R
import com.foodapp.app.activity.*
import com.foodapp.app.base.BaseFragmnet
import com.foodapp.app.utils.Common.getCurrentLanguage
import com.foodapp.app.utils.SharePreference
import com.foodapp.app.utils.SharePreference.Companion.getBooleanSharedPrefs
import com.foodapp.app.utils.SharePreference.Companion.isLogin
import com.foodapp.app.utils.SharePreference.Companion.setStringSharedPrefs
import kotlinx.android.synthetic.main.fragment_home.ivMenu
import kotlinx.android.synthetic.main.fragment_setting.*


class SettingFragment: BaseFragmnet() {
    override fun setView(): Int {
        return R.layout.fragment_setting
    }
    override fun Init(view: View) {
        requireActivity().getCurrentLanguage(false)
        ivMenu.setOnClickListener {
            (activity as DashboardActivity?)!!.onDrawerToggle()
        }

        cvBtnEditProfile.setOnClickListener {
            if(getBooleanSharedPrefs(requireActivity(),isLogin)){
                openActivity(EditProfileActivity::class.java)
            }else {
                openActivity(LoginActivity::class.java)
                requireActivity().finish()
                requireActivity().finishAffinity()
            }
        }

        cvBtnPassword.setOnClickListener {
            if(getBooleanSharedPrefs(requireActivity(),isLogin)){
                openActivity(ChangePasswordActivity::class.java)
            }else {
                openActivity(LoginActivity::class.java)
                requireActivity().finish()
                requireActivity().finishAffinity()
            }
        }
        cvPolicy.setOnClickListener {
            openActivity(ProvacyPolicyActivity::class.java)
        }
        llArabic.setOnClickListener {
            setStringSharedPrefs(requireActivity(), SharePreference.SELECTED_LANGUAGE,requireActivity().resources.getString(R.string.language_hindi))
            requireActivity().getCurrentLanguage(true)
        }
        llEnglish.setOnClickListener {
            setStringSharedPrefs(requireActivity(), SharePreference.SELECTED_LANGUAGE,requireActivity().resources.getString(R.string.language_english))
            requireActivity().getCurrentLanguage(true)
        }
        cvShare.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Food App")
                var shareMessage = "\nLet me recommend you this application\n\n"
                shareMessage = "${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}".trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().getCurrentLanguage(false)
    }


}