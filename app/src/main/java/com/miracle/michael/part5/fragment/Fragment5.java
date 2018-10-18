package com.miracle.michael.part5.fragment;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.databinding.Fragment5Binding;

public class Fragment5 extends BaseFragment<Fragment5Binding> {
    @Override
    public int getLayout() {
        return R.layout.fragment5;
    }

    @Override
    public void initView() {
        initWebView();
        binding.webView.loadUrl("http://7.988lhkj.com/");
    }

    public void initWebView() {
        WebSettings webSettings =  binding.webView.getSettings();
        webSettings.setDomStorageEnabled(true);//主要是这句
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);

//        webSettings.setAppCacheEnabled(true);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.supportMultipleWindows();
//        webSettings.setAllowContentAccess(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setSavePassword(true);
//        webSettings.setSaveFormData(true);

        binding.webView.setWebChromeClient(new WebChromeClient());//这行最好不要丢掉
        //该方法解决的问题是打开浏览器不调用系统浏览器，直接用webview打开
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        binding.webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (binding.webView.canGoBack()) {
                        binding.webView.goBack();
                        return true;
                    }
                }
                return false;

            }
        });

    }


    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (binding.webView != null) {
            binding.webView.clearHistory();
            ((ViewGroup) binding.webView.getParent()).removeView(binding.webView);
            binding.webView.stopLoading();
            binding.webView.setWebChromeClient(null);
            binding.webView.setWebViewClient(null);
            binding.webView.destroy();
        }
    }
}
