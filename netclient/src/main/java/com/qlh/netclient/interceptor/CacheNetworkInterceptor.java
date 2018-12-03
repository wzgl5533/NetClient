package com.qlh.netclient.interceptor;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * 作者：QLH on 2018-12-03
 * <p>
 * 而CacheNetworkInterceptor主要是在缓存没命中的情况下,
 * 请求网络后,修改返回头,加上Cache-Control,告知OKHTTP对该请求进行一个60秒的缓存.
 */
public class CacheNetworkInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException{
        //无缓存,进行缓存
        return chain.proceed(chain.request()).newBuilder()
                .removeHeader("Pragma")
                //对请求进行最大60秒的缓存
                .addHeader("Cache-Control", "max-age=60")
                .build();
    }
}
