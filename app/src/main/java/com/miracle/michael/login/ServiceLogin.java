package com.miracle.michael.login;

import com.miracle.base.network.ZResponse;
import com.miracle.michael.login.entity.User;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceLogin {

    /**
     * 登录
     */

    @POST("loginSet")
    Call<ZResponse<User>> login(@Query("username") String username, @Query("password") String password);

    /**
     * 注册
     */
    @POST("login")
    Call<ZResponse<User>> register(@Query("username") String username, @Query("password") String password, @Query("nickname") String nickname);

}
