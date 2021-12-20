package com.example.pictrix.retrofit;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSetup {
    private static final String BASE_URL = "https://api.pexels.com";

    public static Retrofit initRetrofit(){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL);
        builder.client(getOkHttpClient());
        builder.addConverterFactory(GsonConverterFactory.create());

        return builder.build();
    }
    private static OkHttpClient getOkHttpClient(){
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(new TokenInterceptop());
        return okHttpBuilder.build();
    }
}
