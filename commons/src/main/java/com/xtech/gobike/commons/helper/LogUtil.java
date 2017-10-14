package com.xtech.gobike.commons.helper;

import android.util.Log;

/**
 * 调试工具类
 */
public class LogUtil {
    //调试开关
    private static boolean debugFlag = true;

    public static void d(String tag, String msg) {
        if (debugFlag && msg != null)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (debugFlag && msg != null)
            Log.e(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (debugFlag && msg != null)
            Log.w(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (debugFlag && msg != null)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (debugFlag && msg != null)
            Log.v(tag, msg);
    }

    public static void e(String tag, String msg, Throwable e) {
        if (debugFlag && msg != null) {
            Log.e(tag, msg + "", e);
        }
    }
}

