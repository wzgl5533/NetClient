package com.qlh.netclient.http

import android.widget.Toast
import com.google.gson.JsonParseException
import com.qlh.netclient.constant.*
import com.qlh.netclient.exception.ServerResponseException
import com.qlh.netclient.utils.Logs
import com.qlh.netclient.utils.Utils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.json.JSONException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * 作者：QLH on 2018-12-01
 * 描述：自定义观察者
 *
 * T 解析的数据model
 */
open abstract class NetworkObserver<T> : Observer<T> {

    var t : T? = null

    companion object {

        private val TAG = "Retrofit---"
    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: T) {
        this.t = t
    }

    override fun onError(e: Throwable) {
        Logs.d(TAG, e.message)
        when (e) {
            is HttpException -> onException(ExceptionReason.BAD_NETWORK)//HTTP错误
            is ConnectException, is UnknownHostException -> //连接错误
                onException(ExceptionReason.CONNECT_ERROR)
            is InterruptedIOException -> //连接超时
                onException(ExceptionReason.CONNECT_TIMEOUT)
            is JsonParseException, is JSONException, is ParseException -> //解析错误
                onException(ExceptionReason.PARSE_ERROR)
            is ServerResponseException->
                onFail(e.message)
            else -> onException(ExceptionReason.UNKNOWN_ERROR)
        }

        onFinish()
    }

    override fun onComplete() {
        onSuccess(t)
    }

    /**
     * 请求成功
     *
     * @param t 服务器返回的数据
     */
   abstract fun onSuccess(t: T?)

    /**
     * 服务器返回数据，但响应码不为200
     *
     */
    private fun onFail(message:String?){
        toast(message)
    }

    private fun onFinish(){}

  private fun onException(reason: ExceptionReason){
        when(reason){
            ExceptionReason.CONNECT_ERROR ->toast(CONNECT_ERROR)

            ExceptionReason.CONNECT_TIMEOUT ->toast(CONNECT_TIMEOUT)

            ExceptionReason.BAD_NETWORK ->toast(BAD_NETWORK)

            ExceptionReason.PARSE_ERROR ->toast(PARSE_ERROR)

            ExceptionReason.UNKNOWN_ERROR ->toast(UNKNOWN_ERROR)
        }
    }

   enum class ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }

    //toast
    private fun toast(msg:String?){
        msg?:Toast.makeText(Utils.getContext(),msg,Toast.LENGTH_SHORT).show()
    }
}
