package com.webintoapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();

        // রিয়েল অ্যাপ ভাইব এবং পারফরম্যান্স বুস্ট
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        
        // নতুন সিস্টেমে ক্যাশ কন্ট্রোল (পুরানো setAppCacheEnabled বাদ দেওয়া হয়েছে)
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // অ্যাপের ভেতরেই লিংক ওপেন হবে
        mWebView.setWebViewClient(new WebViewClient());

        // ==========================================
        // নিচের যেকোনো একটি অপশন ব্যবহার করুন:
        // ==========================================

        // Option 1: REMOTE RESOURCE (আপনার সাইটের লিঙ্ক)
        mWebView.loadUrl("https://google.com");

        // Option 2: LOCAL RESOURCE (আপনার assets ফোল্ডারের index.html)
        // mWebView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
