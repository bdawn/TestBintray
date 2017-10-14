package com.xtech.gobike.commons.app;

import android.graphics.drawable.Drawable;

public class AppInfo implements Comparable<AppInfo> {
    private Drawable icon;
    private String appName;
    private String packageName;
    private boolean isSystemApp;
    private long codesize;

    public AppInfo(Drawable icon, String appName, String packageName,
                   boolean isSystemApp, long codesize) {
        this.icon = icon;
        this.appName = appName;
        this.packageName = packageName;
        this.isSystemApp = isSystemApp;
        this.codesize = codesize;
    }

    public long getCodesize() {
        return codesize;
    }

    public void setCodesize(long codesize) {
        this.codesize = codesize;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isSystemApp() {
        return isSystemApp;
    }

    public void setSystemApp(boolean isSystemApp) {
        this.isSystemApp = isSystemApp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppInfo appInfo = (AppInfo) o;

        if (!appName.equals(appInfo.appName)) return false;
        return packageName.equals(appInfo.packageName);

    }

    @Override
    public int hashCode() {
        int result = appName.hashCode();
        result = 31 * result + packageName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AppInfo [appName=" + appName + ", packageName=" + packageName
                + ", isSystemApp=" + isSystemApp + ", codesize=" + codesize
                + "]";
    }

    @Override
    public int compareTo(AppInfo arg0) {
        return 0;
    }
}
