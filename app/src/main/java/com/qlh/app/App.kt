package com.qlh.app

import android.app.Application
import com.qlh.netclient.http.NetClientUtil

/**
 * 作者：QLH on 2018-12-01
 * 描述：
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NetClientUtil.init(this)
            .logToggle(true)
            .setBaseUrl("https://www.wanandroid.com/")
    }
}
