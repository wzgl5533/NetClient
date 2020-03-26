package com.qlh.netclient.interceptor;

import com.qlh.netclient.BuildConfig;
import com.qlh.netclient.utils.Logs;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

/**
 * Created by QLH on 2018/4/20.
 * 日子打印
 */

public class LoggingInterceptor implements Interceptor {

    private static final String TAG = "OKHtt";
    public static Boolean isDebug = true;
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();

        long t1 = System.nanoTime();//请求发起的时间

        if (isDebug){
                Logs.e(TAG,String.format("请求URL：%s%n 参数：%s%n on %s%n 请求头：%s",
                        request.url(), request.body().toString(),chain.connection(), request.headers()));
        }

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();//收到响应的时间

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);

        if (isDebug){
                Logs.e(TAG,String.format("响应URL: %s %n响应数据：%s %n请求用时：%.1fms%n%s",
                        response.request().url(),
                        responseBody.string(),
                        (t2 - t1) / 1e6d,
                        response.headers()));
        }

        return response;
    }
}
