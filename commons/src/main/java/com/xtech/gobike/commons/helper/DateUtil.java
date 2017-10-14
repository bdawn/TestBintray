package com.xtech.gobike.commons.helper;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public final class DateUtil {

    /**
     * yyyy-MM-dd
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * yyyy.MM.dd
     */
    public static final String YYMMDD = "yyyy.MM.dd";
    /**
     * yyyy-MM
     */
    public static final String YYYY_MM = "yyyy-MM";
    /**
     * MM月MM日
     */
    public static final String MMDD = "MM月dd日";
    /**
     * yyyyMMdd
     */
    public static final String YYYYMMDD = "yyyyMMdd";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyyMMddHHmmss
     */
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YEAR_MONTH_DAY = "yyyy年MM月dd日";

    /**
     * MM/dd/yyyy HH:mm:ss.S
     */
    public static final String MM_DD_YYYY_HH_MM_SS_S = "MM/dd/yyyy HH:mm:ss.S";

    /**
     *yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     */
    public static final String MM_DD_YYYY_HH_MM_SS_SSS_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * YYYY
     */
    public static final String YYYY = "yyyy";
    /**
     * DD
     */
    public static final String MM = "MM";
    /**
     * DD
     */
    public static final String DD = "dd";
    /**
     * HH:mm
     */
    public static final String HH_MM = "HH:mm";
    /**
     * HH:mm
     */
    public static final String HH_MM_SS = "HH:mm:ss";
    /**
     * yy-MM-ddkk:mm:ssz
     */
    public static final String YY_MM_DDKK_MM_SSSZ = "yyyy-MM-dd HH:mm:ss.SSSZ";
    /**
     * 东八区时间
     */
    public static final String EAST_EIGHT_ZONE_TIME = "GMT+08:00";

    private static final String[] DAY_OF_WEEK = {"日", "一", "二", "三", "四", "五", "六"};

    /**
     * 获取服务器时间
     *
     * @return 服务器时间
     */
    public static Date getServerTime() {
        return new Date();
    }

    /**
     * 获取当天23:59:59时间
     *
     * @param date date
     * @return 当天23:59:59时间 date
     */
    public static Date getDateEnd(Date date) {
        Calendar c = getCalendar();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    /**
     * 获取当天0点
     *
     * @param date date
     * @return 当天0点 date
     */
    public static Date getDateBegin(Date date) {
        Calendar c = getCalendar();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 获取当天0点
     *
     * @return 当天0点 date
     */
    public static Date getDateBegin() {
        return getDateBegin(getServerTime());
    }


    /**
     * 获取当天23:59:59时间
     *
     * @return 当天23:59:59时间 date
     */
    public static Date getDateEnd() {
        return getDateEnd(getServerTime());
    }

    /**
     * 获取当天0点字符串
     *
     * @return 获取当天0点
     */
    public static String getDateBeginYYYY_MM_DD_HH_MM_SS(Date date) {
        return formatDate(getDateBegin(date), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当天23:59:59时间字符串
     *
     * @return 获取当天23:59:59时间
     */
    public static String getDateEndYYYY_MM_DD_HH_MM_SS(Date date) {
        return formatDate(getDateEnd(date), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当天0点字符串
     *
     * @return 获取当天0点
     */
    public static String getDateBeginYYYY_MM_DD_HH_MM_SS() {
        return formatDate(getDateBegin(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当天23:59:59时间字符串
     *
     * @return 获取当天23:59:59时间
     */
    public static String getDateEndYYYY_MM_DD_HH_MM_SS() {
        return formatDate(getDateEnd(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取服务器时间当天时间时分秒
     * YYYY_MM_DD_HH_MM_SS
     *
     * @return YYYY_MM_DD_HH_MM_SS 格式时间
     */
    public static String getDatedayYYYY_MM_DD_HH_MM_SS() {
        return formatDate(getServerTime(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取服务器时间当天时间
     * YYYY_MM_DD
     *
     * @return YYYY_MM_DD 格式时间
     */
    public static String getDatedayYYYY_MM_DD() {
        return formatDate(getServerTime(), YYYY_MM_DD);
    }

    /**
     * 获取服务器时间当天时间时分秒
     * YYYYMMDDHHMMSS
     *
     * @return 获取时间的当天时间时分秒
     */
    public static String getDateYYYYMMDDHHMMSS() {
        return formatDate(getServerTime(), YYYYMMDDHHMMSS);
    }

    /**
     * 获取服务器时间当天时间时分秒
     * YYYY_MM_DD_HH_MM_SS
     *
     * @return 获取时间的当天时间时分秒
     */
    public static String getDateYYYY_MM_DD_HH_MM_SS() {
        return formatDate(getServerTime(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 时间封装在照片名中
     * YYYYMMDDHHMMSS
     *
     * @return YYYYMMDDHHMMSS
     */
    public static String getStringTimeYYYYMMDDHHMMSS() {
        return formatDate(getServerTime(), YYYYMMDDHHMMSS);
    }

    /**
     * 获取当天时间
     * YYYY_MM_DD
     *
     * @return YYYY_MM_DD
     */
    public static String getTodayYYYY_MM_DD() {
        return formatDate(getServerTime(), YYYY_MM_DD);
    }

    /**
     * 获取下N个月的第一天的 00:00:00
     *
     * @param date     时间
     * @param addMonth －N上N个月  +n下N个月
     * @return N个月的第一天的 00:00:00
     */
    public static Date getNextMonthFirstDay(Date date, int addMonth) {
        Calendar c = getCalendar();
        c.setTime(date);

        c.add(Calendar.MONTH, addMonth);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        c.add(Calendar.DAY_OF_MONTH, 1 - dayOfMonth);

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 格式化 yyyy-MM-dd HH:mm:ss
     *
     * @param date 时间对象
     * @return
     */
    public static String formatDateYYYY_MM_DD_HH_MM_SS(Date date) {
        return formatDate(date, YYYY_MM_DD_HH_MM_SS);
    }


    public static String getDisplayString() {
        Calendar calendar = getCalendar();
        calendar.setTime(getServerTime());
        int weekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekIndex < 0 || weekIndex >= DAY_OF_WEEK.length) {
            weekIndex = 0;
        }
        return "星期" + DAY_OF_WEEK[weekIndex] + "\n"
                + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String getHHMM() {
        return formatDate(getServerTime(), HH_MM);
    }

    /**
     * formatDate 日期转成格式化
     *
     * @param  millisTime Unix Time
     * @param format 格式 如：YYYY-mm-DD
     * @return 格式化好的日期字符串
     * @see {@link #YYYY_MM_DD}
     */
    public static String formatDate(long millisTime, String format) {
        Date date = new Date();
        date.setTime(millisTime);
        return formatDate(date,format);
    }

    /**
     * formatDate 日期转成格式化
     *
     * @param date 日期
     * @param format 格式 如：YYYY-mm-DD
     * @return 格式化好的日期字符串
     * @see {@link #YYYY_MM_DD}
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        sdf.setTimeZone(TimeZone.getTimeZone(EAST_EIGHT_ZONE_TIME));
        try {
            return sdf.format(date);
        } catch (Exception e) {
            Log.e("DateUtil", "formatDate date:" + date + ",format:" + format + " encounter error", e);
        }
        return "";
    }

    /**
     * 根据格式获取系统时间
     *
     * @param format 时间格式
     * @return 格式化的系统时间
     */
    public static String getServerTime(String format) {
        return formatDate(getServerTime(), format);
    }

    /**
     * 将某种格式的字符串日期，转换成固定格式
     * @param date 日期字符串
     * @param formatBefore 转化前的格式
     * @param formatAfter 转化后的格式
     * @return 转化后的日期字符串
     */
    public static String formatStringDate(String date, String formatBefore, String formatAfter) {
        return formatDate(parseDate(date,formatBefore),formatAfter);
    }

    /**
     * 将字符串转换成固定格式的日期
     * @param str 日期字符串
     * @param format 要转换的格式
     * @return 转化后的Date
     * @see {@link #YYYY_MM_DD}
     */
    public static Date parseDate(String str, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        sdf.setTimeZone(TimeZone.getTimeZone(EAST_EIGHT_ZONE_TIME));
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            Log.e("DateUtil", "parseDate str:" + str + ",format:" + format + " encounter error", e);
        }
        return null;
    }

    /**
     * 把日期字符串转化成时间戳
     * @param str 日期参数
     * @param format 日期格式
     * @return 时间戳
     */
    public static Date parseDateOfLong(long str, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        sdf.setTimeZone(TimeZone.getTimeZone(EAST_EIGHT_ZONE_TIME));
        try {
            return new Date(str);
        } catch (Exception e) {
            Log.e("DateUtil", "parseDate str:" + str + ",format:" + format + " encounter error", e);
        }
        return null;
    }

    /**
     * 所填写的时间在开始和结束时间之内
     *
     * @param startDate
     * @param endDate
     * @param selectDate
     * @return
     */
    public static boolean isContainTime(Date startDate, Date endDate, Date selectDate) {

        if (startDate.after(endDate) || startDate.equals(endDate)) {
            return false;
        }
        if (selectDate.after(startDate) && selectDate.before(endDate)) {
            return true;
        }

        return false;
    }

    /**
     * 是否包含指定时间月，日
     * ［startDate，endDate）左闭右开
     *
     * @param startDate
     * @param endDate
     * @param month      {@link Calendar#NOVEMBER } etc. 指定时间月 0-11
     * @param dayOfMonth 日 1-31
     * @return
     */
    public static boolean isContainMonth(Date startDate, Date endDate, int month, int dayOfMonth) {

        if (startDate.after(endDate) || startDate.equals(endDate)) {
            return false;
        }

        Date localDate = endDate;
        while (!localDate.before(startDate)) {

            localDate = getNextMonthFirstDay(localDate, -1);

            Calendar c = Calendar.getInstance();
            c.setTime(localDate);

            if (c.get(Calendar.MONTH) == month) {
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Date compareDate = c.getTime();
                if (compareDate.after(startDate) || compareDate.equals(startDate)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 计算一个财年的开始日期12月1号
     *
     * @param fiscalYear
     * @return
     */
    public static Date getFiscalYearBegin(int fiscalYear) {
        Calendar c = getCalendar();
        c.set(Calendar.YEAR, fiscalYear);
        //注意：月份从0开始计算，Calendar.DECEMBER代表12月（December）
        c.set(Calendar.MONTH, Calendar.DECEMBER);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    /**
     * 用于替换Calendar.getInstance()使用服务器时间的Calendar实例
     *
     * @return 服务器时间的Calendar实例
     */
    public static Calendar getCalendar() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone(EAST_EIGHT_ZONE_TIME));
        c.setTime(getServerTime());
        return c;
    }

    /**
     * 判断两个日期是否相等 年月
     *
     * @param first  日期1
     * @param second 日期2
     * @return 两个日期年月是否相等
     */
    public static boolean isYearAndMonthEq(Date first, Date second) {
        if (first == null || second == null) {
            return false;
        }

        Calendar c = getCalendar();

        c.setTime(first);
        int firstYear = c.get(Calendar.YEAR);
        int firstMonth = c.get(Calendar.MONTH);

        c.setTime(second);
        int secondYear = c.get(Calendar.YEAR);
        int secondMonth = c.get(Calendar.MONTH);

        return (firstYear == secondYear) && (firstMonth == secondMonth);

    }

    /**
     * 获取月份 1-12
     *
     * @param date 时间
     * @return 获取月份 1-12
     */
    public static int getMonth(Date date) {
        if (date == null) {
            throw new IllegalArgumentException();
        }
        Calendar c = getCalendar();

        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date) {
        if (date == null) {
            throw new IllegalArgumentException();
        }
        Calendar c = getCalendar();

        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 201505
     *
     * @param date
     * @return
     */
    public static int getYearMonth(Date date) {

        Calendar c = getCalendar();
        c.setTime(date);
        String yearday = String.format("%1$02d%2$02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
        return Integer.valueOf(yearday);
    }

    public static Date getMonthFirstDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException();
        }
        Calendar c = getCalendar();

        c.setTime(date);
        c.set(Calendar.DATE, 1);

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        return c.getTime();
    }

    /**
     * 判断两个日期是否是同年且同一个月份
     *
     * @param date1 时间对象1
     * @param date2 时间对象2
     * @return true if is At Same month and same year,
     */
    public static boolean isAtSameMonthAndYear(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return (getDateMonth(date1) == getDateMonth(date2)
                && getDateYear(date1) == getDateYear(date2));
    }


    /**
     * 返回当前时间的年，比如2015/12/02 返回2015
     *
     * @param date 时间对象
     * @return -1 if date is null
     */
    public static int getDateYear(Date date) {
        if (date == null) {
            return -1;
        }
        Calendar c = getCalendar();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 返回当前时间的月份，比如2015/12/02 返回12
     *
     * @return -1 if date is null
     */
    public static int getDateMonth(Date date) {
        if (date == null) {
            return -1;
        }
        Calendar c = getCalendar();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /*获取3个月前的时间*/
    public static Date getThreeMonthBefore(Date date) {
        if (date == null) {
            throw new IllegalArgumentException();
        }
        Calendar c = getCalendar();
        c.setTime(date);
        c.add(Calendar.MONTH, -3);
        return c.getTime();
    }

    /*获取上个月前的时间*/
    public static Date getLastMonthBefore(Date date) {
        if (date == null) {
            throw new IllegalArgumentException();
        }
        Calendar c = getCalendar();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        return c.getTime();
    }

    /**
     * 获取指定时间前几分钟
     */
    public static Date getMinuteBefore(Date date, int minute) {
        if (date == null) {
            throw new IllegalArgumentException();
        }
        Calendar c = getCalendar();
        c.setTime(date);
        c.add(Calendar.MINUTE, -minute);
        return c.getTime();
    }

    /**
     * 指定当天时间
     */
    public static Date getDateHour(int hour, int minute) {
        Calendar c = getCalendar();
        c.setTime(getServerTime());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.HOUR, hour);
        c.add(Calendar.MINUTE,minute);
        return c.getTime();
    }

    /**
     * 返回当前时间属于这个月的哪一天。例如：2015-11-23 返回23
     *
     * @param date 时间对象
     * @return -1 if date is null, else return day of date.
     */
    public static int getDateDay(Date date) {
        if (date == null) {
            return -1;
        }
        Calendar c = getCalendar();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 时间的计算
     *
     * @param addMinutes 单位为分
     * @return
     */
    public static Date countTime(int addMinutes) {
        Date dt = new Date();
        Calendar rightNow = getCalendar();
        rightNow.setTime(dt);
        rightNow.add(Calendar.MINUTE, addMinutes);
        Date nowDate = rightNow.getTime();
        String time = formatDateYYYY_MM_DD_HH_MM_SS(nowDate);
        LogUtil.e("time", time);
        return nowDate;

    }

    /**
     * 当前服务器日期，现在为周几
     *
     * @return 周一到日分别对应到 1，2，3，4，5，6，7
     */
    public static Integer parseDateToWeekday() {
        String date = getDatedayYYYY_MM_DD();
        if (TextUtil.isEmpty(date)) {
            return null;
        }
        Calendar c = getCalendar();
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            c.setTime(format.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ? 7 : c.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 根据年月获取当月最大天数
     *
     * @param year  年
     * @param month 月
     * @return 最大天数
     */
    public static int getMaxDay(int year, int month) {
        Calendar cal = getCalendar();
        SimpleDateFormat oSdf = new SimpleDateFormat("yyyy-MM");
        try {
            cal.setTime(oSdf.parse(year + "-" + month));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当天0时0分0秒0毫秒的日期
     *
     * @return
     */
    public static Date getToday() {
        Calendar calendar = getCalendar();
        calendar.setTime(getDateBegin());
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定日期0时0分0秒0毫秒的日期
     *
     * @return
     */
    public static Date getDate(Date date) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 根据年，月，日 生成固定格式的日期
     * @param formatDes 日期格式，如"yyyyMMdd"
     * @param year 年
     * @param month 月，日期控件取到的月份，不必加1
     * @param day 天
     * @return 固定格式的日期
     * zyn
     */
    public static String makeFormatDate(String formatDes, int year, int month, int day){
        Calendar calendar= Calendar.getInstance();
        calendar.set(year,month,day);
        return formatDate(calendar.getTime(),formatDes);
    }

    /**
     * 将固定格式 的日期中的年月日分离成int 型的年月日数组
     * @param dateFormat 日期格式
     * @param date 日期
     * @return 年月日 数组，int[0] 年，int【1】月 int【2】日
     */
    public static int[] spiltDateToInts(String dateFormat, String date){
        Calendar calendar= Calendar.getInstance();
        int[] results=new int[3];
        try {
            calendar.setTime(new SimpleDateFormat(dateFormat).parse(date));
            results[0]=calendar.get(Calendar.YEAR);
            results[1]=calendar.get(Calendar.MONTH);
            results[2]=calendar.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * 将日期的时分秒毫秒都设置为最大  即：23:59:59:999
     */
    public static Date getMaxValueDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMaximum(Calendar.MILLISECOND));

        return calendar.getTime();
    }


    /**
     * 获取当月最大天数
     * @param year 年
     * @param month 月
     */
    public static int getDays(int year, int month)
    {
        int days;
        int FebDay = 28;
        if (isLeap(year))
            FebDay = 29;
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                days = FebDay;
                break;
            default:
                days = 0;
                break;
        }
        return days;
    }

    //判断闰年
    public static boolean isLeap(int year)
    {
        if (((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0))
            return true;
        else
            return false;
    }

}
