package com.example.jessi.realtimereddit_androidclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String url = getIntent().getExtras().getString("url");
        setTitle(url);

        WebView web = findViewById(R.id.webView);
        web.loadUrl(getIntent().getExtras().getString("url"));
        web.setWebViewClient(new WebViewClient());
    }
}
