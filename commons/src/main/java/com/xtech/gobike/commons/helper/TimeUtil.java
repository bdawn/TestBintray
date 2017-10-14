package com.xtech.gobike.commons.helper;

import android.text.format.DateFormat;

import java.util.Calendar;

public class TimeUtil {
    public static String getRemindingTime() {
        String delegate = "MM月dd日 aaa hh:mm";
        String date = (String) DateFormat.format(delegate, Calendar.getInstance().getTime());
        if (date.startsWith("0")){
            date = date.substring(1);
        }
        return date;
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

}