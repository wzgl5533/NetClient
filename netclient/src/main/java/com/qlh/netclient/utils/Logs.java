package com.qlh.netclient.utils;

import android.util.Log;
import com.qlh.netclient.interceptor.LoggingInterceptor;

/**
 * Created by QLH on 2017/8/29.
 * <p>
 * 只有在debug模式下才打印日志
 */

public class Logs {

    public static void v(String tag, String msg) {
        if (LoggingInterceptor.isDebug) {
            Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (LoggingInterceptor.isDebug) {
            Log.v(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (LoggingInterceptor.isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (LoggingInterceptor.isDebug) {
            Log.d(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (LoggingInterceptor.isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (LoggingInterceptor.isDebug) {
            Log.i(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (LoggingInterceptor.isDebug) {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (LoggingInterceptor.isDebug) {
            Log.w(tag, msg, tr);
        }
    }

    public static void w(String tag, Throwable tr) {
        if (LoggingInterceptor.isDebug) {
            Log.w(tag, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (LoggingInterceptor.isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (LoggingInterceptor.isDebug) {
            Log.e(tag, msg, tr);
        }
    }
}
