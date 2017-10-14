package com.xtech.gobike.commons.helper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

/**
 * ResourcesHelper
 */
public class ResourcesHelper {

    private static Context getAppContext() {
        return CommonContext.getContext();
    }

    public static Configuration getConfiguration() {
        return getAppContext().getResources()
                .getConfiguration();
    }

    public static float getDimension(int id) {
        return getAppContext().getResources()
                .getDimension(id);
    }

    public static int getDimensionPixelSize(int id) {
        return getAppContext().getResources()
                .getDimensionPixelSize(id);
    }

    public static DisplayMetrics getDisplayMetrics() {
        return getAppContext().getResources()
                .getDisplayMetrics();
    }

    @SuppressWarnings({"unchecked","deprecation"})
    @Nullable
    public static Drawable getDrawable(int id) {

        return getAppContext().getResources()
                .getDrawable(id);
    }

    public static int getInteger(int id) {
        return getAppContext().getResources()
                .getInteger(id);
    }

    public static String getString(int id) {
        return getAppContext().getResources()
                .getString(id);
    }

    public static String getString(int id, int formatArgsInt0, int formatArgsInt1) {
        Resources localResources = getAppContext()
                .getResources();
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = formatArgsInt0;
        arrayOfObject[1] = formatArgsInt1;
        return localResources.getString(id, arrayOfObject);
    }

    public static String getString(int id, Object ... formatArgs) {
        return getAppContext().getResources()
                .getString(id, formatArgs);
    }

    public static String[] getStringArray(int id) {
        return getAppContext().getResources()
                .getStringArray(id);
    }

    public static XmlResourceParser getXml(int id) {
        return getAppContext().getResources().getXml(id);
    }

    public static int getColor(int id) {
        return getAppContext().getResources()
                .getColor(id);
    }

    public static ColorStateList getColorStateList(int id) {
        return getAppContext().getResources()
                .getColorStateList(id);
    }
}
