package com.miracle.michael.naoh.part4.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.base.BaseActivity;
import com.miracle.michael.naoh.base.Constant;
import com.miracle.michael.naoh.common.util.ToastUtil;
import com.miracle.michael.naoh.databinding.ActivityCustomerServiceBinding;

public class CustomerServiceActivity extends BaseActivity<ActivityCustomerServiceBinding> {


    @Override
    public int getLayout() {
        return R.layout.activity_customer_service;
    }

    @Override
    public void initView() {
        setTitle("联系客服");
    }

    @Override
    public void initListener() {
        binding.ibQQ.setOnClickListener(this);
        binding.ibWechat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibQQ:
                try {
                    String qqUrl = String.format(Constant.qqUrl, "800859225");
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast("您未安装QQ,可去应用市场下载安装");
                }
                break;

            case R.id.ibWechat:
                try {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText("Summer_JH5");
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast("您未安装微信,可去应用市场下载安装");
                }
                break;
        }
    }
}
