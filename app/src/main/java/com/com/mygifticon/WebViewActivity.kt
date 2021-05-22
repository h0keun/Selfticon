package com.com.mygifticon

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.com.mygifticon.databinding.ActivityWebviewBinding

class WebViewActivity  : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val resultContent = intent.getStringExtra("QRtoWeb").toString()

        if(resultContent.contains("http")){
            initViews(resultContent)
        }
        else{
            initViews("https://www.google.com/search?q=$resultContent")
        }
        bindViews()

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews(resultContent : String) {
        binding.webView.apply {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            settings.javaScriptEnabled = true
            loadUrl(resultContent)
        }
    }

    private fun bindViews() {
        binding.goHomeButton.setOnClickListener {
            binding.webView.loadUrl(DEFAULT_URL)
        }

        binding.addressBar.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val loadingUrl = v.text.toString()
                if (URLUtil.isNetworkUrl(loadingUrl)) {
                    binding.webView.loadUrl(loadingUrl)
                } else {
                    binding.webView.loadUrl("http://$loadingUrl")
                }
            }

            return@setOnEditorActionListener false
        }

        binding.goBackButton.setOnClickListener {
            binding.webView.goBack()
        }

        binding.goForwardButton.setOnClickListener {
            binding.webView.goForward()
        }

        binding.refreshLayout.setOnRefreshListener {
            binding.webView.reload()
        }
    }

    inner class WebViewClient : android.webkit.WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)

            binding.progressBar.show()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            binding.refreshLayout.isRefreshing = false
            binding.progressBar.hide()
            binding.goBackButton.isEnabled = binding.webView.canGoBack()
            binding.goForwardButton.isEnabled = binding.webView.canGoForward()
            binding.addressBar.setText(url)
        }
    }

    inner class WebChromeClient : android.webkit.WebChromeClient() {

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)

            binding.progressBar.progress = newProgress
        }
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
            val backIntent = Intent(this, MainActivity::class.java)
            backIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(backIntent)
            Toast.makeText(this, "웹 브라우저를 종료했습니다.", Toast.LENGTH_SHORT).show()

        }
    }

    companion object {
        private const val DEFAULT_URL = "https://www.google.com"
    }
}