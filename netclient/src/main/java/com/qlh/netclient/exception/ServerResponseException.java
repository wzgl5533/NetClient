package com.qlh.netclient.exception;

/**
 * 服务器返回的异常
 */
public class ServerResponseException extends RuntimeException {
    public ServerResponseException(int errorCode, String cause) {
        //参数：在相应的 MyObserver 的 onError 获取，如e.message或者e.cause.message分别获取
        //Logs.d(TAG,e.message)
        //Logs.d(TAG,e.cause?.message)
        super("错误码："+errorCode+";错误原因"+cause, new Throwable(cause));
    }
}
