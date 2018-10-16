package com.miracle.michael.naoh.part4.activity;

import android.view.View;

import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.base.BaseActivity;
import com.miracle.michael.naoh.common.util.CommonUtils;
import com.miracle.michael.naoh.databinding.ActivityAboutUsBinding;

import java.text.MessageFormat;

/**
 * Created by Michael on 2018/10/12 21:44 (星期五)
 */
public class AboutUsActivity extends BaseActivity<ActivityAboutUsBinding> {
    @Override
    public int getLayout() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initView() {
        setTitle("关于我们");
        binding.tvAppName.setText(CommonUtils.getAppName(mContext));
        binding.tvVersion.setText(MessageFormat.format("版本号：{0}", CommonUtils.getVersionName(mContext)));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
