package com.miracle.base.view.zradiogroup;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ZRadioGroup extends LinearLayout implements ZRadioButton.OnCheckedChangeListener {
    private Context context;
    private ZRadioButton[] mChildren = new ZRadioButton[0];
    private Fragment[] mFragments;
    private FragmentManager manager;

    public ZRadioGroup(Context context) {
        this(context, null);
    }

    public ZRadioGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        List<ZRadioButton> childrenList = new ArrayList<>();
        for (int i = 0, count = getChildCount(); i < count; i++) {
            View view = getChildAt(i);
            if (view instanceof ZRadioButton) {
                childrenList.add((ZRadioButton) view);
            }
        }
        mChildren = childrenList.toArray(mChildren);
        for (int i = 0; i < mChildren.length; i++) {
            ZRadioButton zRadioButton = mChildren[i];
            zRadioButton.setOncheckedChangeListener(this);
            zRadioButton.setPosition(i);

        }
        for (ZRadioButton zRadioButton : mChildren) {
            zRadioButton.setOncheckedChangeListener(this);
        }
    }


    public void setupWithContainerAndFragments(int containerId, Fragment... fragments) {
        if (context instanceof AppCompatActivity) {
            manager = ((AppCompatActivity) context).getSupportFragmentManager();
        } else {
            throw new RuntimeException("For better performance,ZRadioGroup only support used in AppCompatActivity");
        }
        mFragments = fragments;

        FragmentTransaction tran = manager.beginTransaction();
        for (Fragment fragment : mFragments) {
            tran.add(containerId, fragment);
            tran.hide(fragment);
        }
        tran.commit();
        //代码添加的Fragment，需调用该方法
        onFinishInflate();
        if (mChildren.length > 0)
            mChildren[0].performClick();

    }


    public void setCheck(int index) {
        mChildren[index].performClick();
    }

    private ZRadioButton lastCheckedZRadioButton;

    @Override
    public void onCheckedChange(ZRadioButton zRadioButton, int position, boolean isChecked) {
        if (isChecked) {
            if (manager != null) {
                FragmentTransaction tran = manager.beginTransaction();
                for (int i = 0; i < mFragments.length; i++) {
                    if (i == position) {
                        tran.show(mFragments[i]);
                    } else {
                        tran.hide(mFragments[i]);
                    }
                }
                tran.commit();
            }
            if (lastCheckedZRadioButton != null) {
                lastCheckedZRadioButton.toggle();
            }
            lastCheckedZRadioButton = zRadioButton;
        }
    }

}
