package com.qlh.netclient.interceptor;


import com.qlh.netclient.utils.Logs;
import com.qlh.netclient.utils.NetworkUtils;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by QLH on 2018/3/21.
 *
 * CacheInterceptor主要的作用是判断当前网络是否有效,
 * <p>
 * 如果有效,则创建一个请求,该请求能获取一个10秒内未过期的缓存;
 *<p>
 * 否则强制获取一个缓存(过期了30天也允许).
 *<p>
 *因此,当频繁请求的时候,OKHTTP使用10秒之内的缓存而不重复请求网络.
 * 当没网络的时候,请求会获取30天内的缓存,避免界面白屏.
 */

public class CacheInterceptor implements Interceptor {
    //  配置缓存的拦截器
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.isConnected()) {  //无网络,检查30天内的缓存,即使是过期的缓存
            Request newRequest = request.newBuilder()
                    .cacheControl(new CacheControl.Builder()
                            .onlyIfCached()
                            .maxStale(30, TimeUnit.DAYS)
                            .build())
                    .build();
            Logs.d("OkHttp----", "no network");
            return chain.proceed(newRequest);
        } else {  //有网络,检查10秒内的缓存
           Request newRequest = request.newBuilder()
                    .cacheControl(new CacheControl.Builder()
                            .maxAge(10, TimeUnit.SECONDS)
                            .build())
                    .build();
            return chain.proceed(newRequest);
        }
    }
}
