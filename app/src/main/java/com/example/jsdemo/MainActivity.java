package com.example.jsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private TextView logTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webview);

        // 启用javascript
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.loadUrl("http://10.5.0.105:8080/JsDemo");

        /** 为webview添加注册js 接口回调监听.
         *
         * 参数一: 接口对象
         * 参数二: 接口别名(别名让js代码使用)
         */

        mWebView.addJavascriptInterface(this, "android");
        logTextView = (TextView) findViewById(R.id.text);
        Button button = (Button) findViewById(R.id.button);


        /**
         *  调用js.
         *  WebView.loadUrl("javascript:js中定义的方法")
         */
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // 无参数调用  android端调用js 方法
                mWebView.loadUrl("javascript:actionFromNative()");
                // 传递参数调用
                mWebView.loadUrl("javascript:actionFromNativeWithParam(" + "'come from Native'" + ")");
            }
        });

        /**
         *  重写这个方法的话,加载页面就不会弹出浏览器打开了
         */
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    /**
     * js调用此方法
     */
    @android.webkit.JavascriptInterface
    public void actionFromJs() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "js调用了Native函数", Toast.LENGTH_SHORT).show();
                String text = logTextView.getText() + "\njs调用了Native函数";
                logTextView.setText(text);
            }
        });
    }

    /**
     * js调用此方法. 并且将参数传递过来
     *
     * @param str js  传递过来的方法
     */
    @android.webkit.JavascriptInterface
    public void actionFromJsWithParam(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "js调用了Native函数传递参数：" + str, Toast.LENGTH_SHORT).show();
                String text = logTextView.getText() + "\njs调用了Native函数传递参数：" + str;
                logTextView.setText(text);
            }
        });

    }
}
