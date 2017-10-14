package com.xtech.gobike.commons.helper;


import android.os.Handler;
import android.os.Looper;

/**
 * UiThread handler,方便在执行修改界面之类需要在主线程中执行
 */
public final class UiThreadHandler {
    private static Object token = new Object();
    private static Handler uiHandler = new Handler(Looper.getMainLooper());

    public static Handler getUiHandler() {
        return uiHandler;
    }

    public static boolean post(Runnable paramRunnable) {
        if (uiHandler == null)
            return false;
        return uiHandler.post(paramRunnable);
    }

    public static boolean postDelayed(Runnable paramRunnable, long paramLong) {
        if (uiHandler == null)
            return false;
        return uiHandler.postDelayed(paramRunnable, paramLong);
    }

    public static boolean postOnceDelayed(Runnable paramRunnable, long paramLong) {
        if (uiHandler == null)
            return false;
        uiHandler.removeCallbacks(paramRunnable, token);
        return uiHandler.postDelayed(paramRunnable, paramLong);
    }

    public static void removeCallbacks(Runnable paramRunnable) {
        if (uiHandler == null)
            return;
        uiHandler.removeCallbacks(paramRunnable);
    }
}
