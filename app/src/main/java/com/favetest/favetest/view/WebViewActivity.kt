package com.favetest.favetest.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.favetest.favetest.R
import com.favetest.favetest.data.StaticData.Companion.webURL
import kotlinx.android.synthetic.main.web_view_activity.*

class WebViewActivity : AppCompatActivity() {


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_view_activity)
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url.toString())
                return true
            }
        }
        webView.loadUrl(webURL)
    }
}