package com.miracle.michael.naoh.part1.activity;

import android.view.View;

import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.base.BaseActivity;
import com.miracle.michael.naoh.base.Constant;
import com.miracle.michael.naoh.databinding.ActivityTextBinding;

public class TextActivity extends BaseActivity<ActivityTextBinding> {
    @Override
    public int getLayout() {
        return R.layout.activity_text;
    }

    @Override
    public void initView() {
        setTitle("详情");
        binding.tv.setText(getIntent().getStringExtra(Constant.CONTENT));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
