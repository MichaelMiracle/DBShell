package com.miracle.michael.naoh.part1.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.base.BaseActivity;
import com.miracle.michael.naoh.common.network.ZCallback;
import com.miracle.michael.naoh.common.network.ZClientFootBall;
import com.miracle.michael.naoh.common.network.ZResponse;
import com.miracle.michael.naoh.common.util.CommonUtils;
import com.miracle.michael.naoh.common.util.ToastUtil;
import com.miracle.michael.naoh.databinding.ActivitySimpleWebBinding;
import com.miracle.michael.naoh.part1.Service1;
import com.miracle.michael.naoh.part1.entity.NewsDetailBean;

public class SimpleWebActivity extends BaseActivity<ActivitySimpleWebBinding> {

    private int id;


    @Override
    public int getLayout() {
        return R.layout.activity_simple_web;
    }

    @Override
    public void initView() {
        hideTitle();
        binding.tvTitle.setText("详情");
        id = getIntent().getIntExtra("id", 0);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        reqData();
    }

    private void reqData() {
        ZClientFootBall.getService(Service1.class).getNewsDetail(id).enqueue(new ZCallback<ZResponse<NewsDetailBean>>(binding.swipeRefreshLayout) {
            @Override
            public void onSuccess(ZResponse<NewsDetailBean> data) {
                binding.cbRight.setChecked(data.getData().getCoin() == 1);
                binding.webView.loadDataWithBaseURL(null, CommonUtils.getHtmlData(data.getData().getContent()), "text/html", "utf-8", null);
            }
        });
    }


    @Override
    public void initListener() {
        binding.rlLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.cbRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZClientFootBall.getService(Service1.class).likeOrDislike(id).enqueue(likeCallback);
            }
        });
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reqData();
            }
        });
    }

    private ZCallback<ZResponse> likeCallback = new ZCallback<ZResponse>() {
        @Override
        public void onSuccess(ZResponse data) {
            ToastUtil.toast(data.getMessage());
        }
    };

    @Override
    public void onClick(View v) {

    }

}
