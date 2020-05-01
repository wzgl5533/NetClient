package com.qlh.netclient.interceptor;

import com.qlh.netclient.utils.Logs;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by QLH on 2018/4/20.
 * 日子打印
 */

public class LoggingInterceptor implements Interceptor {

    private static final String TAG = "OKHttp----";
    public static Boolean isDebug = true;
    private Map<String, String> headerMap = new HashMap<>();
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();
        if (!headerMap.isEmpty()) {
            Set<String> stringSet = headerMap.keySet();
            Iterator<String> iterator = stringSet.iterator();
            String keyTemp;

            Request.Builder builder = request.newBuilder();
            while (iterator.hasNext()) {
                keyTemp = iterator.next();
                builder.addHeader(keyTemp, headerMap.get(keyTemp));
            }
            request = builder.build();
        }

        long t1 = System.nanoTime();//请求发起的时间

        if (isDebug){
                Logs.e(TAG,String.format("请求URL------%s on %s%n请求头------%s",
                        request.url(), chain.connection(), request.headers()));
        }

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();//收到响应的时间

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);

        if (isDebug){
                Logs.e(TAG,String.format("响应URL-------: %s %n响应数据------%s 请求用时--------%.1fms%n%s",
                        response.request().url(),
                        responseBody.string(),
                        (t2 - t1) / 1e6d,
                        response.headers()));
        }

        return response;
    }

    /**
     * 添加header
     * @param name
     * @param value
     */
    public void addHeader(String name, String value) {
        headerMap.put(name, value);
    }
}
