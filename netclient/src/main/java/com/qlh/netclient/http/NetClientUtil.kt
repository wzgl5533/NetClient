package com.qlh.netclient.http

import android.content.Context
import com.qlh.netclient.interceptor.LoggingInterceptor
import com.qlh.netclient.utils.Logs
import com.qlh.netclient.utils.Utils
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 *作者：QLH on 2018-12-01
 *描述：管理Retrofit
 */
object NetClientUtil {

    /**
     *isDefineConverterFactory
     *
     * true：过滤数据，T泛型传入BaseBean的data的model解析，需要自己设置 addConverterFactory(MyGsonConverterFactory.create(gson))
     *
     * false:不过滤，T泛型传入BaseBean解析，需要自己处理数据异常等逻辑
     *
     * @param baseUrl                  基础地址
     * @param isDefineConverterFactory 是否需要自定义转换器
     **/
    fun getRetrofit(
        baseUrl: String,
        isDefineConverterFactory: Boolean = false,
        headerMap: HashMap<String, String>? = null
    ): Retrofit {

        return RetrofitUtils.getRetrofitBuilder(baseUrl, isDefineConverterFactory, headerMap).build()
    }

    /**application中初始化定义baseUrl**/
    fun getRetrofit(
        isDefineConverterFactory: Boolean = false,
        headerMap: HashMap<String, String>? = null
    ): Retrofit? {

        return if (RetrofitUtils.isSetBaseUrl()) {
            RetrofitUtils.getRetrofitBuilder(isDefineConverterFactory,headerMap).build()
        } else {
            Logs.e(NetClientUtil.javaClass.simpleName, "需要设置基础地址")
            null
        }
    }

    /**初始化**/
    @JvmStatic
    fun init(context: Context): NetClientUtil {
        Utils.init(context)
        return this
    }

    /**设置Log开关**/
    @JvmStatic
    fun logToggle(isDebug: Boolean): NetClientUtil {
        LoggingInterceptor.isDebug = isDebug
        return this
    }

    /**设置BaseUrl**/
    @JvmStatic
    fun setBaseUrl(baseUrl: String) {
        RetrofitUtils.setBaseUrl(baseUrl)
    }

    @JvmStatic
    fun setOkHttpBuilder(builder:OkHttpClient.Builder) = RetrofitUtils.setOkHttpBuilder(builder)
}