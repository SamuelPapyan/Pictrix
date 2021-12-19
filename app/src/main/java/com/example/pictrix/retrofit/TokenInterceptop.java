package com.example.pictrix.retrofit;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptop implements Interceptor {
    private final String HEADER_AUTH = "Authorization";

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.header(HEADER_AUTH,"Bearer 563492ad6f91700001000001fe57b13d8f7d4ef58c49a65f9c74d227");
        builder.header("Content-Type","application/json");
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }
}
