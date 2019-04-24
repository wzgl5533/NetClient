package com.qlh.netclient.utils

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *作者：QLH on 2019-04-24
 *描述：
 */

/**
 * 默认切换线程
 */
fun <T> Observable<T>.switch() : Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}