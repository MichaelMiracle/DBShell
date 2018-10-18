package com.miracle.michael.part2.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.network.PageLoadCallback;
import com.miracle.base.network.ZClient;
import com.miracle.databinding.FragmentCategoryDetailBinding;
import com.miracle.michael.part2.Service2;
import com.miracle.michael.part2.adapter.FootballListAdapter2;
import com.miracle.michael.part2.entity.FootballKey;

/**
 * Created by Administrator on 2018/3/5.
 */

public class CategoryDetailFragment extends BaseFragment<FragmentCategoryDetailBinding> {


    private FootballListAdapter2 mAdapter;
    private PageLoadCallback callBack;
    private FootballKey footballKey;
    private int reqKey = 1;


    @Override
    public int getLayout() {
        return R.layout.fragment_category_detail;
    }

    @Override
    public void initView() {
        binding.recyclerView.setAdapter(mAdapter = new FootballListAdapter2(mContext));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        binding.recyclerView.setHasFixedSize(true);
        binding.tvCategoryTitle.setText(footballKey == null ? "双色球" : footballKey.getName());

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
//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                startActivity(new Intent(mContext, SimpleWebActivity.class).putExtra("id", mAdapter.getItem(position).getId()));
//            }
//        });
    }


    @Override
    public void onResume() {
        super.onResume();
        callBack.onRefresh();
    }

    @Override
    public void onClick(View view) {

    }

    public void setReqKey(FootballKey footballKey) {
        this.reqKey = footballKey.getId();
        binding.tvCategoryTitle.setText(footballKey.getName());
        callBack.onRefresh();
    }

    public CategoryDetailFragment setReqKey2(FootballKey footballKey) {
        this.reqKey = footballKey.getId();
        this.footballKey = footballKey;
        return this;
    }
}
