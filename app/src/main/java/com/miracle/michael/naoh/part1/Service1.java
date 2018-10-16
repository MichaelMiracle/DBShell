package com.miracle.michael.naoh.part1;

import com.miracle.michael.naoh.common.network.ZResponse;
import com.miracle.michael.naoh.part1.entity.Football;
import com.miracle.michael.naoh.part1.entity.Lottery;
import com.miracle.michael.naoh.part1.entity.NewsDetailBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service1 {


    @POST("index/index")
    Call<ZResponse<List<Football>>> getFootballList(@Query("bigtype") String bigtype, @Query("smalltype") String smalltype, @Query("page") int page, @Query("pageSize") int pageSize);

//    @GET("article_play")
//    Call<ZResponse<List<BannerBean>>> getBanner();

    @POST("articleRow")
    Call<ZResponse<NewsDetailBean>> getNewsDetail(@Query("id") int id);


    @POST("footballCollect")
    Call<ZResponse> likeOrDislike(@Query("createid") int createid);


    @POST("caipiao/index")
    Call<ZResponse<List<Lottery>>> getLotteries();

}
