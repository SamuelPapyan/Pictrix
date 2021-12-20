package com.example.pictrix.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Images {
    @GET("/v1/search")
    public Call<SearchPhotos> searchImage(@Query("query")String param);
    static Images create(){
        return RetrofitSetup.initRetrofit().create(Images.class);
    }
}