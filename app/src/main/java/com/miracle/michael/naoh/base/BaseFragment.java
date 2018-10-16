package com.miracle.michael.naoh.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.databinding.FragmentBaseBinding;

public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment implements View.OnClickListener {
    protected Context mContext;
    protected B binding;
    private FragmentBaseBinding mBaseBinding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mBaseBinding == null) {
            mBaseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, null, false);
            binding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), getLayout(), null, false);
            mBaseBinding.baseFragContainer.addView(binding.getRoot());

            initView();
            initListener();
        }
        return mBaseBinding.getRoot();
    }


    public abstract int getLayout();

    public abstract void initView();

    public abstract void initListener();
}
