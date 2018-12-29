package com.qlh.app

import android.app.Application
import com.qlh.netclient.http.NetClient

/**
 * 作者：QLH on 2018-12-01
 * 描述：
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NetClient.init(this)
            .logToggle(true)
    }
}
