package com.miracle.michael.part1.activity;

import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.Constant;
import com.miracle.databinding.ActivityTextBinding;

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
