package com.miracle.michael.naoh.part4.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.base.AppConfig;
import com.miracle.michael.naoh.base.BaseActivity;
import com.miracle.michael.naoh.common.network.PageLoadCallback;
import com.miracle.michael.naoh.common.network.ZClientFootBall;
import com.miracle.michael.naoh.databinding.SwipeRecyclerBinding;
import com.miracle.michael.naoh.part1.activity.SimpleWebActivity;
import com.miracle.michael.naoh.part1.adapter.FootballListAdapter;
import com.miracle.michael.naoh.part4.Service4;

public class MyCollectionsActivity extends BaseActivity<SwipeRecyclerBinding> {

    private FootballListAdapter mAdapter;
    private PageLoadCallback callBack;

    @Override
    public int getLayout() {
        return R.layout.swipe_recycler;
    }

    @Override
    public void initView() {
        setTitle("我的收藏");
        binding.recyclerView.setAdapter(mAdapter = new FootballListAdapter(mContext));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        initCallback();
    }

    private void initCallback() {
        callBack = new PageLoadCallback(mAdapter, binding.recyclerView) {
            @Override
            public void requestAction(int page, int limit) {
                ZClientFootBall.getService(Service4.class).getMycollections(AppConfig.APP_TYPE, page, limit).enqueue(callBack);
            }
        };
        callBack.setSwipeRefreshLayout(binding.swipeRefreshLayout);
    }

    @Override
    public void onResume() {
        super.onResume();
        callBack.onRefresh();
    }


    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, SimpleWebActivity.class).putExtra("id", mAdapter.getItem(position).getId()));
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
