package com.foodapp.app.activity

import android.view.View
import android.webkit.WebViewClient
import com.foodapp.app.R
import com.foodapp.app.api.ApiClient
import com.foodapp.app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_privacy_policy.*

class ProvacyPolicyActivity:BaseActivity() {
    override fun setLayout(): Int = R.layout.activity_privacy_policy

    override fun InitView() {
        ivBack.setOnClickListener {
            finish()
        }
        webView.setWebViewClient(WebViewClient())
        webView.getSettings().setLoadsImagesAutomatically(true)
        webView.getSettings().setJavaScriptEnabled(true)
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
        webView.loadUrl(ApiClient.PrivicyPolicy)
    }
}