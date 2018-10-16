package com.miracle.michael.naoh.part2.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.base.BaseFragment;
import com.miracle.michael.naoh.common.network.PageLoadCallback;
import com.miracle.michael.naoh.common.network.ZClient;
import com.miracle.michael.naoh.databinding.FragmentCategoryDetail2Binding;
import com.miracle.michael.naoh.part2.Service2;
import com.miracle.michael.naoh.part2.adapter.FootballListAdapter2;
import com.miracle.michael.naoh.part2.entity.FootballKey;

/**
 * Created by Administrator on 2018/3/5.
 */

public class CategoryDetailFragment2 extends BaseFragment<FragmentCategoryDetail2Binding> {


    private FootballListAdapter2 mAdapter;
    private PageLoadCallback callBack;
    private FootballKey footballKey;
    private int reqKey = 1;


    @Override
    public int getLayout() {
        return R.layout.fragment_category_detail2;
    }

    @Override
    public void initView() {
        binding.recyclerView.setAdapter(mAdapter = new FootballListAdapter2(mContext));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        binding.recyclerView.setHasFixedSize(true);
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        binding.recyclerView.setLayoutAnimation(animation);
        initCallback();
    }

    private void initCallback() {
        callBack = new PageLoadCallback(mAdapter, binding.recyclerView) {
            @Override
            public void requestAction(int page, int limit) {
                ZClient.getService(Service2.class).getLotteryDetail(reqKey, page, limit).enqueue(callBack);
            }
        };
        callBack.setSwipeRefreshLayout(binding.swipeRefreshLayout);
    }


    @Override
    public void initListener() {
    }


    @Override
    public void onResume() {
        super.onResume();
        callBack.onRefresh();
    }

    @Override
    public void onClick(View view) {

    }

    public CategoryDetailFragment2 setReqKey2(FootballKey footballKey) {
        this.reqKey = footballKey.getId();
        this.footballKey = footballKey;
        return this;
    }
}
