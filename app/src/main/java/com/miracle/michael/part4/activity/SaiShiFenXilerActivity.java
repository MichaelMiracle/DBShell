package com.miracle.michael.part4.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.Constant;
import com.miracle.base.network.PageLoadCallback;
import com.miracle.base.network.ZClient;
import com.miracle.databinding.SwipeRecyclerBinding;
import com.miracle.michael.part1.Service1;
import com.miracle.michael.part1.activity.SimpleWebActivity;
import com.miracle.michael.part1.adapter.FootballListAdapter;

public class SaiShiFenXilerActivity extends BaseActivity<SwipeRecyclerBinding> {

    private FootballListAdapter mAdapter;
    private PageLoadCallback callBack;
    private String reqKey;

    @Override
    public int getLayout() {
        return R.layout.swipe_recycler;
    }

    @Override
    public void initView() {
        setTitle(getIntent().getStringExtra(Constant.TITLE));
        reqKey = getIntent().getStringExtra(Constant.REQKEY);
        binding.recyclerView.setAdapter(mAdapter = new FootballListAdapter(mContext));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        initCallback();
    }

    private void initCallback() {
        callBack = new PageLoadCallback(mAdapter, binding.recyclerView) {
            @Override
            public void requestAction(int page, int limit) {
                ZClient.getService(Service1.class).getFootballList("cp",reqKey, 1, 10).enqueue(callBack);
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
