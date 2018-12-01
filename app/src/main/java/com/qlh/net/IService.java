package com.qlh.net;

import io.reactivex.Observable;
import retrofit2.http.GET;

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
}
