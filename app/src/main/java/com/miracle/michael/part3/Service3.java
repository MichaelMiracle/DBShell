package com.miracle.michael.part3;

import com.miracle.base.network.ZResponse;
import com.miracle.michael.part3.entity.ClubKey;
import com.miracle.michael.part3.entity.DataType;
import com.miracle.michael.part3.entity.FootballDataDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service3 {

    /**
     * 足球比分列表
     */

    @GET("football/data")
    Call<ZResponse<List<ClubKey>>> getClubs();

    @POST("football")
    Call<ZResponse<List<DataType>>> getType(@Query("type") String type);


    @POST("footballRows")
    Call<ZResponse<FootballDataDetail>> getFootballDataDetail(@Query("type") String type);
}
