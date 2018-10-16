package com.miracle.michael.naoh.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.miracle.michael.naoh.MainActivity;
import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.common.util.CommonUtils;
import com.miracle.michael.naoh.databinding.ActivityBaseBinding;
import com.yanzhenjie.sofia.Sofia;

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity implements View.OnClickListener {

    protected Context mContext;
    protected B binding;
    protected ActivityBaseBinding mBaseBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false);
        binding = DataBindingUtil.inflate(getLayoutInflater(), getLayout(), null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        binding.getRoot().setLayoutParams(params);
        mBaseBinding.baseContainer.addView(binding.getRoot());
        getWindow().setContentView(mBaseBinding.getRoot());

        //设置状态栏颜色
//        setStatusBarColor();
        initView();
        initListener();

    }

    public abstract int getLayout();

    public abstract void initView();

    public abstract void initListener();


    protected void hideTitle() {
        mBaseBinding.titleBar.setVisibility(View.GONE);
    }

    protected void showRight(boolean show) {
        mBaseBinding.titleBar.showRight(show);
    }

    protected void showLeft(boolean show) {
        mBaseBinding.titleBar.showLeft(show);
    }

    protected void setTitle(String title) {
        mBaseBinding.titleBar.setTitle(title);
    }

    protected void setTitle(String title, int textColor) {
        mBaseBinding.titleBar.setTitle(title, textColor);
    }

    protected void setTitle(String title, int textColor, int textSize) {
        mBaseBinding.titleBar.setTitle(title, textColor, textSize);
    }

    protected void setRight(String text) {
        mBaseBinding.titleBar.setRight(text);
    }

    protected void setRight(String text, int textColor) {
        mBaseBinding.titleBar.setRight(text, textColor);
    }

    protected void setRight(String text, int textColor, int textSize) {
        mBaseBinding.titleBar.setRight(text, textColor, textSize);
    }


    protected void setLeft(int textColor, int textSize) {
        mBaseBinding.titleBar.setLeft(textColor, textSize);
    }

    protected void setLeft(String text, int textColor, int textSize) {
        mBaseBinding.titleBar.setLeft(text, textColor, textSize);
    }

    protected void setLeftColor(int textColor) {
        mBaseBinding.titleBar.setLeftColor(textColor);
    }

    protected void setRightClickListener(View.OnClickListener listener) {
        mBaseBinding.titleBar.setRightClickListener(listener);
    }

    protected void setLeftClickListener(View.OnClickListener listener) {
        mBaseBinding.titleBar.setLeftClickListener(listener);
    }


    private void setStatusBarColor() {
        if (mContext instanceof MainActivity) {
            Sofia.with(this).invasionStatusBar().statusBarBackgroundAlpha(0);
        } else {
            Sofia.with(this).statusBarBackground(CommonUtils.getColor(R.color.white)).statusBarDarkFont();
        }
    }
}
