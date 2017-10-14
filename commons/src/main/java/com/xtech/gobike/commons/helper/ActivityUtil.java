package com.xtech.gobike.commons.helper;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.xtech.gobike.commons.R;
import com.xtech.gobike.commons.config.AppConstants;

/**
 * UI类
 */
public class ActivityUtil {
    private static final int RIGHT_TO_LEFT = 0;
    private static final int LEFT_TO_RIGHT = 1;
    private static long lastClickTime;

    /**
     * 防止连击
     *
     * @return
     */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 跳转页面
     *
     * @param currentAct  当前页
     * @param targetClass 目标页
     * @param param       参数
     */
    public static void startActivity(Activity currentAct, Class targetClass, String param) {
        Intent intent = new Intent(currentAct, targetClass);
        intent.putExtra(AppConstants.INTENT_EXTRA_KEY, param);
        currentAct.startActivity(intent);
        currentAct.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
    }

    /**
     * 跳转页面,不结束当前页
     *
     * @param currentAct  当前页
     * @param targetClass 目标页
     * @param params      参数
     */
    public static void startActivity(Activity currentAct, Class targetClass, String[] params) {
        Intent intent = new Intent(currentAct, targetClass);
        intent.putExtra(AppConstants.INTENT_EXTRA_KEY, params);
        currentAct.startActivity(intent);
        currentAct.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
    }

    /**
     * 跳转页面,结束当前页
     *
     * @param activity      当前页
     * @param activityClass 目标页
     * @param params        参数
     */
    public static void startActivityAndFinish(Activity activity, Class activityClass, String[] params) {
        Intent intent = new Intent(activity, activityClass);
        intent.putExtra(AppConstants.INTENT_EXTRA_KEY, params);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        activity.finish();
    }

    /**
     * 跳转页面,不结束当前页
     *
     * @param currentAct  当前页
     * @param targetClass 目标页
     */
    public static void startActivity(Activity currentAct, Class targetClass) {
        Intent intent = new Intent(currentAct, targetClass);
        currentAct.startActivity(intent);
        currentAct.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
    }


    /**
     * 跳转页面,结束当前页
     *
     * @param currentAct  当前页
     * @param targetClass 目标页
     */
    public static void startActivityAndFinish(Activity currentAct, Class targetClass,String params) {
        Intent intent = new Intent(currentAct, targetClass);
        currentAct.startActivity(intent);
        currentAct.overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
        currentAct.finish();
    }

    /**
     * 跳转页面,结束当前页
     *
     * @param currentAct  当前页
     * @param targetClass 目标页
     */
    public static void startActivityAndFinish(Activity currentAct, Class targetClass) {
        Intent intent = new Intent(currentAct, targetClass);
        currentAct.startActivity(intent);
        currentAct.overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
        currentAct.finish();
    }

    public static void startAppByPkgName(Context context, String pkgName, String params) {
        PackageManager pm = context.getPackageManager();
        Intent launchIntent = pm.getLaunchIntentForPackage(pkgName);
        launchIntent.putExtra(AppConstants.INTENT_EXTRA_KEY, params);
        //launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(launchIntent);
    }

    public static void startActivity(Activity activity, Class c, Intent intent) {
        intent.setClass(activity,c);
        activity.startActivity(intent);
    }

    public static boolean isServiceRunning(Context context,Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
