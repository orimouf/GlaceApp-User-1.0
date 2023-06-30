package com.foodapp.app.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.foodapp.app.activity.DashboardActivity
import com.foodapp.app.fragment.HomeFragment

class SharePreference {

    companion object {
        lateinit var mContext: Context
        lateinit var sharedPreferences: SharedPreferences
        val PREF_NAME: String = "GlaceApp"
        val PRIVATE_MODE: Int = 0
        lateinit var editor: SharedPreferences.Editor
        val isLogin:String="isLogin"
        val loginToken:String="loginToken"
        val userId:String="userid"
        val userMobile:String="usermobile"
        val userEmail:String="useremail"
        val userName:String="userName"
        val userProfile:String="userprofile"
        val isTutorial="tutorial"
        val isAdmin="false"
        val isFavourite="favourite"
        val isCurrancy="Currancy"
        val isMiniMum="MiniMum"
        val isMaximum="Maximum"
        val isMiniMumQty="isMiniMumQty"
        val isLinearLayoutManager="Grid"
        var SELECTED_LANGUAGE = "selected_language"

        fun setStringSharedPrefs(context: Context, key: String, value: String) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
            editor = sharedPreferences.edit()
            editor.apply { putString(key, value) }.apply()
        }

        fun setBooleanSharedPrefs(context: Context, key:String, value: Boolean) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
            editor = sharedPreferences.edit()
            editor.apply { putBoolean(key, value) }.apply()
        }

        fun setIntSharedPrefs(context: Context, key:String, value: Int) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
            editor = sharedPreferences.edit()
            editor.apply { putInt(key, value) }.apply()
        }

        fun getStringSharedPrefs(context: Context, key:String): String? {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
            return sharedPreferences.getString(key, "")
        }

        fun getBooleanSharedPrefs(context: Context, key:String): Boolean {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
            return sharedPreferences.getBoolean(key, false)
        }
        fun getIntSharedPrefs(context: Context, key:String): Int {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
            return sharedPreferences.getInt(key, 0)
        }
    }

    @SuppressLint("CommitPrefEdits")
    constructor(mContext1: Context) {
        mContext = mContext1
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor= sharedPreferences.edit()
    }
    fun mLogout(){
        editor.clear()
        editor.commit()
    }

}