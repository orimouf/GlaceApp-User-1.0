package com.foodapp.app.activity


import android.os.Handler
import android.os.Looper
import com.foodapp.app.R
import com.foodapp.app.base.BaseActivity
import com.foodapp.app.utils.Common.getCurrentLanguage
import com.foodapp.app.utils.SharePreference.Companion.getBooleanSharedPrefs
import com.foodapp.app.utils.SharePreference.Companion.isTutorial


class SplashActivity : BaseActivity(){
    override fun setLayout(): Int {
        return R.layout.activity_splash
    }

    override fun InitView() {
        this@SplashActivity.getCurrentLanguage(false)
        Handler(Looper.getMainLooper()).postDelayed({
            if(!getBooleanSharedPrefs(this@SplashActivity, isTutorial)){
                openActivity(TutorialActivity::class.java)
                finish()
            }else{
                openActivity(DashboardActivity::class.java)
                finish()
            }
        },3000)
    }

    override fun onResume() {
        super.onResume()
        this@SplashActivity.getCurrentLanguage(false)
    }
}