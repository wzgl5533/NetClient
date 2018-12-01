package com.qlh.netclient.http;

import android.os.Environment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qlh.netclient.converter.MyGsonConverterFactory;
import com.qlh.netclient.interceptor.HttpHeaderInterceptor;
import com.qlh.netclient.interceptor.LoggingInterceptor;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * 作者：QLH on 2018-12-01
 * 描述：管理Retrofit
 */
class RetrofitUtils {

    //超时时间 10s
    private static final int DEFAULT_TIME_OUT = 10;
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private static final int DEFAULT_WRITE_TIME_OUT = 10;

    private static OkHttpClient.Builder getOkHttpClientBuilder() {


        File cacheFile = new File(Environment.getExternalStorageDirectory(), "httpCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);//100M

        return new OkHttpClient.Builder()
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new HttpHeaderInterceptor())
                .cache(cache);
    }

    /**
     * 设置默认的GsonConverterFactory，不过滤数据，T泛型传入BaseBean解析，需要自己处理数据异常等逻辑
     **/
    private static Retrofit.Builder getRetrofitBuilder(String baseUrl) {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        OkHttpClient okHttpClient = RetrofitUtils.getOkHttpClientBuilder().build();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl);
    }

    /**
     * @param baseUrl                  基础地址
     * @param isDefineConverterFactory 是否需要自定义转换器
     *                                 <p>
     *                                 true：过滤数据，T泛型传入BaseBean的data的model解析，需要自己设置 addConverterFactory(MyGsonConverterFactory.create(gson))
     *                                 <p>
     *                                 false:不过滤，T泛型传入BaseBean解析，需要自己处理数据异常等逻辑
     **/
    public static Retrofit.Builder getRetrofitBuilder(String baseUrl, Boolean isDefineConverterFactory) {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        OkHttpClient okHttpClient = RetrofitUtils.getOkHttpClientBuilder().build();
        return isDefineConverterFactory ?
                new Retrofit.Builder()
                        .client(okHttpClient)
                        .addConverterFactory(MyGsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .baseUrl(baseUrl)
                : getRetrofitBuilder(baseUrl);
    }
}
