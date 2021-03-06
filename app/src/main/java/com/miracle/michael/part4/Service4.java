package com.miracle.michael.part4;

import com.miracle.base.network.ZResponse;
import com.miracle.michael.part1.entity.Football;
import com.miracle.michael.part4.entity.QQWechat;
import com.miracle.michael.part4.entity.UserInfo;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Service4 {

    /**
     * 足球列表
     */
    @POST("my_collection")
    Call<ZResponse<List<Football>>> getMycollections(@Query("type") String type, @Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 个人信息
     */
    @POST("user_info")
    Call<ZResponse<UserInfo>> getUserInfo(@Query("userid") int id);

    /**
     * 修改个人头像
     */
    @Multipart
    @POST("login_edit_img")
    Call<ZResponse> uploadHeadImg(@Query("username") String username, @Part() MultipartBody.Part imgs);

    /**
     * 修改密码
     */
    @POST("login_edit")
    Call<ZResponse> modifyPassword(@Query("oldPassWord") String oldPassWord, @Query("newPassWord") String newPassWord);

    @GET("get_qq")
    Call<ZResponse<QQWechat>> getCustomerServiceAccount();
}
