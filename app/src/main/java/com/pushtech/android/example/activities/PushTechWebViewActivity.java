package com.pushtech.android.example.activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.pushtech.android.example.R;
import com.pushtech.sdk.DataCollectorManager;

import static com.pushtech.android.example.utils.Constants.INTENT_WEB_URL;

public class PushTechWebViewActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressIndicator;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String webUrl = getIntent().getExtras().getString(INTENT_WEB_URL);
        initViews();
        setupToolbar();
        configureWebView(webUrl);
        DataCollectorManager.getInstance(this).contentView(this.getClass().getName());


    }

    private void initViews() {
        webView = (WebView) findViewById(R.id.activity_web_view_web);
        progressIndicator = (ProgressBar) findViewById(R.id.activity_web_view_progress);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void configureWebView(String webUrl) {
        progressIndicator.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new CustomWebViewClient());
        webView.setWebChromeClient(new CustomWebChromeClient());
        webView.loadUrl(webUrl);
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }

    private void setupToolbar() {
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getString(R.string.activity_web_view_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressIndicator.setVisibility(View.GONE);
        }

    }

    private class CustomWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            toolbar.setTitle(title);
        }
    }
}