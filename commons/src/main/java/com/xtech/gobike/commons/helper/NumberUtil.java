package com.xtech.gobike.commons.helper;

public class NumberUtil {

    public static long String2Long(String str) {
        long res = 0L;
        try{
            res = Long.valueOf(str);
        }catch (Exception e) {
            //
        }
        return res;
    }

    /**
     * 取小数点后几位
     *
     * @param f     float
     * @param scale 小数位数
     * @return double
     */
    public static String getString(float f, int scale) {
        return String.format("%." + scale + "f", f);
    }

    /**
     * 取小数点后几位
     *
     * @param d     float
     * @param scale 小数位数
     * @return double
     */
    public static String getString(double d, int scale) {
        return String.format("%." + scale + "f", d);
    }

    /**
     * 计时显示
     *
     * @param passedSeconds 单位s
     * @return
     */
    public static String getTimeString(long passedSeconds) {
        int hh = (int) (passedSeconds / 3600);
        int mm = (int) ((passedSeconds % 3600) / 60);
        int ss = (int) (passedSeconds % 3600 % 60);
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(hh);
        stringBuffer.append(":");

        if (mm >= 0 && mm < 10) {
            stringBuffer.append("0");
        }
        stringBuffer.append(mm);
        stringBuffer.append(":");

        if (ss >= 0 && ss < 10) {
            stringBuffer.append("0");
        }
        stringBuffer.append(ss);

        return stringBuffer.toString();
    }

    /**
     * 计算距离
     *
     * @param speed   速度 km/h
     * @param seconds 时间 s
     * @return 距离 单位cm
     */
    public static int getDistance(float speed, int seconds) {
        return (int) (speed / 3.6f * seconds * 100);
    }

    /**
     * 计算卡路里
     * @param speed 速度 km/h
     * @param hour 时间 h
     * @param incline 坡度 1-16
     * @return 卡
     */
    public static double getCalories(double speed, double hour, double incline) {
        return 70.0d * speed * hour * (1 + incline / 100d);
    }
}