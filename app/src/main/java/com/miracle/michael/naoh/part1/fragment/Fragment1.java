package com.miracle.michael.naoh.part1.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.base.BaseFragment;
import com.miracle.michael.naoh.common.network.PageLoadCallback;
import com.miracle.michael.naoh.common.network.ZClient;
import com.miracle.michael.naoh.common.util.ContextHolder;
import com.miracle.michael.naoh.databinding.BannerLayoutBinding;
import com.miracle.michael.naoh.databinding.Fragment1Binding;
import com.miracle.michael.naoh.part1.Service1;
import com.miracle.michael.naoh.part1.activity.SimpleWebActivity;
import com.miracle.michael.naoh.part1.adapter.FootballListAdapter;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class Fragment1 extends BaseFragment<Fragment1Binding> {

    private FootballListAdapter mAdapter;
    private PageLoadCallback callBack;

    private List<String> images;

    @Override
    public int getLayout() {
        return R.layout.fragment1;
    }

    @Override
    public void initView() {
        mAdapter = new FootballListAdapter(mContext);
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        addHeadView();

//        int resId = R.anim.layout_animation_fall_down;
//        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
//        binding.recyclerView.setLayoutAnimation(animation);
        initCallback();
    }

    private void addHeadView() {
        BannerLayoutBinding bannerBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.banner_layout, null, false);
        initBanner(bannerBinding);
        mAdapter.addHeaderView(bannerBinding.getRoot());
    }

    private void initBanner(BannerLayoutBinding bannerBinding) {
        images = new ArrayList<>();
        images.add("file:///android_asset/banner1.png");
        images.add("file:///android_asset/banner2.png");
        images.add("file:///android_asset/banner3.png");
        bannerBinding.banner.setImages(images).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(ContextHolder.getContext()).load(path).into(imageView);
            }
        }).start();

        bannerBinding.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
            }
        });
    }


    private void initCallback() {
        callBack = new PageLoadCallback(mAdapter, binding.recyclerView) {
            @Override
            public void requestAction(int page, int limit) {
                ZClient.getService(Service1.class).getFootballList("cp", "wycp", page, limit).enqueue(callBack);
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
