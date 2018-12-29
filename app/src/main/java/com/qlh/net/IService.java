package com.qlh.net;

import io.reactivex.Observable;
import org.json.JSONObject;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 作者：QLH on 2018-12-01
 * 描述：
 */
public interface IService {

    //获取播放主地址
    @GET(QLHUrls.PLAYER_ROOT)
    Observable<String> getHttpRoot();

    //获取播放主地址
    @GET(QLHUrls.PLAYER_ROOT)
    Observable<BaseBean<String>> getHttpRoot1();

    @GET("wxarticle/list/{id}/{page}/json")//实现原理是通过正则表达式进行替换.
    Observable<Object> getList(@Path("id") String ids, @Path("page") String pages);
}
