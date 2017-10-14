package com.xtech.gobike.commons.helper;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.xtech.gobike.commons.R;
import com.xtech.gobike.commons.app.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class AppInfoUtil {

    public static List<AppInfo> getAppList(Context context) {
        PackageManager packageManager = context.getPackageManager();

        List<AppInfo> appList = new ArrayList<>();

        List<PackageInfo> pkgInfoList = packageManager.getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES);

        for (PackageInfo info : pkgInfoList) {
            String packageName = info.packageName;
            ApplicationInfo applicationInfo = info.applicationInfo;
            Drawable icon = applicationInfo.loadIcon(packageManager);
            String appName = applicationInfo.loadLabel(packageManager).toString();
            //long codesize = packageStats.codeSize;
            boolean isSystemApp = isSystemApp(applicationInfo);

            if (isVendorApp(packageName)) {
                LogUtil.i("AppInfoUtil", "packageName: " + packageName);
                AppInfo myAppInfo = new AppInfo(icon, appName, packageName, isSystemApp, 0);
                appList.add(myAppInfo);
            }
        }
        return appList;
    }

    public static List<AppInfo> getMediaAppList(Context context) {
        List<AppInfo> appList = getAppList(context);
        List<AppInfo> res = new ArrayList<>();
        String[] pkgNames = ResourcesHelper.getStringArray(R.array.media_app_list);
        for (AppInfo appInfo : appList) {
            for (String pkgName : pkgNames) {
                if (pkgName.equals(appInfo.getPackageName())) {
                    res.add(appInfo);
                }
            }
        }
        return res;
    }


    private static boolean isSystemApp(ApplicationInfo info) {
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param packageName
     * @return
     */
    private static boolean isVendorApp(String packageName) {
        if (packageName == null) {
            return false;
        }
        if (packageName.startsWith("com.android")) {
            return false;
        }
        if (packageName.startsWith("com.softwinner")) {
            return false;
        }
        if (packageName.contains("google")) {
            return false;
        }

        String[] blackList = {"android", "jp.co.omronsoft.openwnn", "com.xtech.gorun",
                "com.orangekit.popupwindow"};
        for (String black : blackList) {
            if (black.equals(packageName)) {
                return false;
            }
        }

        String[] whiteList = {
                "com.qiyi.video.pad",
                "com.sohu.tv",
                "com.xiami.pad",
                "com.kugou.playerHD",
                "com.baidu.music.pad",
                "com.tencent.mm",
                "com.tencent.minihd.qq",
                "com.sina.weibotab"
        };
        for (String white : whiteList) {
            if (white.equals(packageName)) {
                return true;
            }
        }
        return false;
    }
}
