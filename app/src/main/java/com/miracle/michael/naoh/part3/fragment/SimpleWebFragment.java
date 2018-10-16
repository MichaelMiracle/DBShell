package com.miracle.michael.naoh.part3.fragment;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.base.BaseFragment;
import com.miracle.michael.naoh.common.network.ZClient;
import com.miracle.michael.naoh.common.network.ZResponse;
import com.miracle.michael.naoh.common.util.CommonUtils;
import com.miracle.michael.naoh.databinding.FragmentSimpleWebBinding;
import com.miracle.michael.naoh.part3.Service3;
import com.miracle.michael.naoh.part3.entity.FootballDataDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimpleWebFragment extends BaseFragment<FragmentSimpleWebBinding> {
    private String type;


    @Override
    public int getLayout() {
        return R.layout.fragment_simple_web;
    }

    @Override
    public void initView() {
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        binding.webView.setHorizontalScrollBarEnabled(false);//水平不显示
        binding.webView.setVerticalScrollBarEnabled(false); //垂直不显示
        WebSettings settings = binding.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
//        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

//        setContentView(webView);
//        binding.webView.loadUrl(mUrl);
        reqData(type);
    }

    private void reqData(String type) {
        ZClient.getService(Service3.class).getFootballDataDetail(type).enqueue(new Callback<ZResponse<FootballDataDetail>>() {
            @Override
            public void onResponse(Call<ZResponse<FootballDataDetail>> call, Response<ZResponse<FootballDataDetail>> response) {
                binding.webView.loadDataWithBaseURL(null, CommonUtils.getHtmlData(response.body().getData().getContent()), "text/html", "utf-8", null);
            }

            @Override
            public void onFailure(Call<ZResponse<FootballDataDetail>> call, Throwable t) {

            }
        });
    }

    public SimpleWebFragment setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

}
