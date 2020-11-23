package com.qlh.netclient.http

import android.os.Environment
import com.google.gson.GsonBuilder
import com.qlh.netclient.converter.MyGsonConverterFactory
import com.qlh.netclient.interceptor.CacheInterceptor
import com.qlh.netclient.interceptor.CacheNetworkInterceptor
import com.qlh.netclient.interceptor.LoggingInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * 作者：QLH on 2018-12-01
 * 描述：管理Retrofit
 */
internal object RetrofitUtils {

    //超时时间 10s
    private val DEFAULT_TIME_OUT = 10L
    private val DEFAULT_READ_TIME_OUT = 10L
    private val DEFAULT_WRITE_TIME_OUT = 10L

    private var baseUrl: String = ""
    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create()
    private var builder: OkHttpClient.Builder? = null

    private fun getOkHttpClientBuilder(): OkHttpClient.Builder {
        //需要权限
        //OkHttp的缓存只能用于Get请求。OkHttp的Cache类中，对于GET以外的请求，
        // 都返回null。源码中也做出了解释：不允许Get以外的请求，
        // 从技术上是允许post请求的。但是他的复杂度很高，实用性太低
        val cacheFile = File(Environment.getExternalStorageDirectory(), "httpCache")
        if (!cacheFile.exists()) {
            cacheFile.mkdirs()
        }
        val cache = Cache(cacheFile, (1024 * 1024 * 100).toLong())//100M

        return OkHttpClient.Builder()
            .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            //.addInterceptor(LoggingInterceptor())
            //加入拦截器,注意Network与非Network的区别,目前不知道怎么测试
            //.addInterceptor(new HttpHeaderInterceptor())
            .addInterceptor(CacheInterceptor())
            .addNetworkInterceptor(CacheNetworkInterceptor())
            .cache(cache)
    }

    /**
     * @param baseUrl                  基础地址
     * @param isDefineConverterFactory 是否需要自定义转换器
     *
     *
     * true：过滤数据，T泛型传入BaseBean的data的model解析，需要自己设置 addConverterFactory(MyGsonConverterFactory.create(gson))
     *
     *
     * false:不过滤，T泛型传入BaseBean解析，需要自己处理数据异常等逻辑
     */
    fun getRetrofitBuilder(
        baseUrl: String,
        isDefineConverterFactory: Boolean,
        map: HashMap<String, String>?
    ): Retrofit.Builder {
        setBaseUrl(baseUrl)
        val okHttpClient = builder?:getOkHttpClientBuilder()
        val interceptor = LoggingInterceptor()
        map?.forEach {
            interceptor.addHeader(it.key, it.value)
        }
        okHttpClient.addInterceptor(interceptor)
        return if (isDefineConverterFactory)
            Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(MyGsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
        else
            Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
    }

    /**
     * @param isDefineConverterFactory 是否需要自定义转换器
     *
     *
     * true：过滤数据，T泛型传入BaseBean的data的model解析，需要自己设置 addConverterFactory(MyGsonConverterFactory.create(gson))
     *
     *
     * false:不过滤，T泛型传入BaseBean解析，需要自己处理数据异常等逻辑
     */
    fun getRetrofitBuilder(
        isDefineConverterFactory: Boolean,
        map: HashMap<String, String>?
    ): Retrofit.Builder {
        return getRetrofitBuilder(baseUrl, isDefineConverterFactory, map)

    }


    /**设置BaseURL**/
    fun setBaseUrl(url: String) {
        baseUrl = url
    }

    fun setOkHttpBuilder(builder: OkHttpClient.Builder){
        this.builder = builder
    }

    /**
     * @return true:设置了 false:没有设置
     * **/
    fun isSetBaseUrl(): Boolean = !baseUrl.isNullOrEmpty()

}
