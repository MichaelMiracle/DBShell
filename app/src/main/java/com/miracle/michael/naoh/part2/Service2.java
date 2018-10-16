package com.miracle.michael.naoh.part2;

import com.miracle.michael.naoh.common.network.ZResponse;
import com.miracle.michael.naoh.part2.entity.FootballKey;
import com.miracle.michael.naoh.part2.entity.LotteryDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service2 {

    /**
     * 足球列表
     */

    @POST("caipiao/index")
    Call<ZResponse<List<FootballKey>>> getSearchKeys();

    @POST("caipiao/detail")
    Call<ZResponse<List<LotteryDetail>>> getLotteryDetail(@Query("class_id") int class_id, @Query("page") int page, @Query("pageSize") int pageSize);
}
