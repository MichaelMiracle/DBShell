package com.miracle.base.network;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.App;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.NetStateUtils;
import com.miracle.base.util.ToastUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NaOH on 2018/8/3 15:11 (星期五).
 */
public abstract class PageLoadCallback<T> implements Callback<T>, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerViewAdapter mAdapter;
    private int page = 1;
    private int limit = 20;
    private boolean isLoadMore;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public PageLoadCallback(RecyclerViewAdapter adapter, RecyclerView recyclerView) {
        adapter.setOnLoadMoreListener(this, recyclerView);
        mAdapter = adapter;
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout = swipeRefreshLayout;
    }


    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        T body = response.body();
        if (body != null && body instanceof ZResponse) {
            ZResponse data = (ZResponse) body;
            switch (data.getCode()) {
                case 200:
                    specialHandle(data.getData());
                    page++;
                    if (isLoadMore) {
                        mAdapter.addData((List) data.getData());
                    } else {
                        mAdapter.setNewData((List) data.getData());
                    }
                    if (mAdapter.getData().size() == data.getTotal()) {
                        mAdapter.loadMoreEnd();
                    } else {
                        mAdapter.loadMoreComplete();
                    }
                    onFinish(call);
                    break;

                case 0:
                    mAdapter.loadMoreEnd();
                    onFinish(call);
                    break;

                default:
                    onFailure(call, new Throwable(response.message()));
                    break;
            }
        } else {
            onFailure(call, new Throwable(response.message()));
        }
    }

    public void specialHandle(Object data) {
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        mAdapter.loadMoreFail();
        onFinish(call);
    }

    public void onFinish(Call<T> call) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        call.cancel();
    }

    @Override
    public void onRefresh() {
        if (!NetStateUtils.isNetworkConnected(App.getApp())) {
            ToastUtil.toast(App.getApp(), CommonUtils.getString(R.string.no_net));
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }
        isLoadMore = false;
        page = 1;
        requestAction(page, limit);
    }

    @Override
    public void onLoadMoreRequested() {
        isLoadMore = true;
        requestAction(page, limit);
    }

    public abstract void requestAction(int page, int limit);
}
