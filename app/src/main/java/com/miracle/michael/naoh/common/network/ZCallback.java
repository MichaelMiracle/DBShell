package com.miracle.michael.naoh.common.network;

import android.app.Dialog;
import android.support.v4.widget.SwipeRefreshLayout;

import com.miracle.michael.naoh.common.util.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ZCallback<T> implements Callback<T> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private Dialog dialog;

    public ZCallback() {
    }

    public ZCallback(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    public ZCallback(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        T body = response.body();
        if (body instanceof ZResponse) {
            ZResponse zResponse = (ZResponse) body;
            if (zResponse.getCode() == 200) {
                onSuccess(body);
                onFinish();
            } else {
                onFailure(call, new Throwable(zResponse.getMessage()));
            }
        } else {
            onFailure(call, new Throwable("返回数据格式不正确！"));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        ToastUtil.toast(t.getMessage());
        onFinish();
    }

    public abstract void onSuccess(T data);

    public void onFinish() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}
