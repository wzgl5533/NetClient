package com.qlh.netclient.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by QLH on 2018/3/21.
 *
 * 配置会请求头数据，比如token等数据
 */

public class HttpHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //  配置请求头
        String accessToken = "token";
        String tokenType = "tokenType";
        Request request = chain.request().newBuilder()
                .header("app_key", "appId")
                .header("Authorization", tokenType + " " + accessToken)
                .header("Content-Type", "application/json")
                .addHeader("Connection", "close")
                .addHeader("Accept-Encoding", "identity")
                .build();
        return chain.proceed(request);
    }
}
