package com.webintoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private RelativeLayout mSplashScreen;
    private FrameLayout mCustomViewContainer;
    private View mCustomView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!AppConfig.SHOW_STATUS_BAR) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                               WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        
        setContentView(R.layout.activity_main);

        mWebView = findViewById(R.id.webView);
        mSplashScreen = findViewById(R.id.splashScreen);
        mCustomViewContainer = findViewById(R.id.customViewContainer);

        initWebView();
        startAppFlow();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings ws = mWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setDatabaseEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Auto Inject the Navigation Bar Script when page finishes loading
                injectNavigation();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Handling External Intent like Telegram
                if (url.startsWith("https://t.me/") || url.startsWith("tg://")) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
                return false; 
            }
        });

        // Fullscreen Video Support
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                mCustomView = view;
                mCustomViewContainer.addView(mCustomView);
                mCustomViewContainer.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);
            }

            @Override
            public void onHideCustomView() {
                mCustomViewContainer.setVisibility(View.GONE);
                mCustomViewContainer.removeView(mCustomView);
                mCustomView = null;
                mWebView.setVisibility(View.VISIBLE);
            }
        });

        mWebView.loadUrl(AppConfig.URL_TO_LOAD);
    }

    private void injectNavigation() {
        // This script is injected into ANY page loaded in the WebView
        String script = "var script = document.createElement('script');" +
                "script.src = 'https://unpkg.com/lucide@latest';" +
                "document.head.appendChild(script);" +
                "script.onload = function() {" +
                "   (function() {" +
                "       if(document.getElementById('app-bottom-nav')) return;" +
                "       var style = document.createElement('style');" +
                "       style.innerHTML = 'body { padding-bottom: 90px !important; } #app-bottom-nav { position: fixed; bottom: 15px; left: 50%; transform: translateX(-50%); width: 94%; max-width: 500px; height: 65px; background: rgba(15,15,20,0.95); backdrop-filter: blur(15px); border-radius: 20px; border: 1px solid rgba(255,255,255,0.1); box-shadow: 0 10px 30px rgba(0,0,0,0.5); display: flex; justify-content: space-around; align-items: center; z-index: 9999999; } .nav-item { flex: 1; display: flex; flex-direction: column; align-items: center; color: #8e8e93; font-size: 10px; cursor: pointer; transition: 0.3s; } .nav-item.active { color: #00f2ff; } .nav-item svg { width: 22px; height: 22px; margin-bottom: 4px; }';" +
                "       document.head.appendChild(style);" +
                "       var nav = document.createElement('div');" +
                "       nav.id = 'app-bottom-nav';" +
                "       nav.innerHTML = \"<div class='nav-item active' onclick='window.scrollTo({top:0,behavior:\\\"smooth\\\"})'><i data-lucide='home'></i><span>Home</span></div>\" +" +
                "                       \"<div class='nav-item' onclick='location.reload()'><i data-lucide='rotate-cw'></i><span>Refresh</span></div>\" +" +
                "                       \"<div class='nav-item' onclick='window.location.href=\\\"https://t.me/yourchannel\\\"'><i data-lucide='send'></i><span>Telegram</span></div>\";" +
                "       document.body.appendChild(nav);" +
                "       lucide.createIcons();" +
                "   })();" +
                "};";
        mWebView.evaluateJavascript(script, null);
    }

    private void startAppFlow() {
        if (AppConfig.SHOW_SPLASH_SCREEN) {
            mSplashScreen.setVisibility(View.VISIBLE);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                mSplashScreen.setVisibility(View.GONE);
                mWebView.setVisibility(View.VISIBLE);
            }, AppConfig.SPLASH_DURATION);
        } else {
            mSplashScreen.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (mCustomView != null) {
            mWebView.getWebChromeClient().onHideCustomView();
        } else if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
