package com.xtech.gobike.commons.helper;

import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * test util
 */
public class TextUtil {
    /**
     * 获取id
     *
     * @param paramView view
     * @return view id
     */
    public static String getIdString(View paramView) {
        return String.valueOf(paramView.getId());
    }

    public static CharSequence getString(int id, int formatArgs) {
        return getString(id, getString(formatArgs));
    }

    public static String getString(int id) {
        return ResourcesHelper.getString(id);
    }

    public static String getString(int id, String formatArgs) {
        return ResourcesHelper.getString(id, formatArgs);
    }

    public static String[] getStringArray(int id) {
        return ResourcesHelper.getStringArray(id);
    }

    /**
     * 判空，如果为数字0 也为空，非数字类的判断会有问题
     *
     * @param paramString 字符串
     * @return 结果
     */
    public static boolean isCoordinateEmpty(String paramString) {
        if (paramString == null)
            return true;

        String str = paramString.trim();
        return str.length() == 0 || str.equals("null")
                || Double.parseDouble(str) == 0.0D;
    }

    /**
     * 判空“null” null
     *
     * @param paramString 参数
     * @return 是否空
     */
    public static boolean isEmpty(String paramString) {
        if (paramString == null)
            return true;

        String str = paramString.trim();
        return str.length() == 0 || str.equals("null");

    }

    /**
     * 字符串长度，null 返回0
     *
     * @param paramString 字符串
     * @return 长度
     */
    public static int length(String paramString) {
        if (paramString == null)
            return 0;
        return paramString.length();
    }

    /**
     * 转化异常返回 Long.MIN_VALUE
     *
     * @param paramString 字符串
     * @return long
     */
    public static long parseLong(String paramString) {
        if (isEmpty(paramString))

            return Long.MIN_VALUE;
        try {
            return Long.parseLong(paramString);
        } catch (ClassCastException localClassCastException) {
        }
        return Long.MIN_VALUE;
    }

    /**
     * 设置文本颜色
     *
     * @param paramTextView view
     * @param colorId       颜色id
     */
    public static void setTextColor(TextView paramTextView, int colorId) {
        paramTextView.setTextColor(ResourcesHelper.getColor(colorId));
    }

    /**
     * 设置文本颜色
     *
     * @param paramTextView TextView
     * @param colorId       颜色id
     */
    public static void setTextColorList(TextView paramTextView, int colorId) {
        paramTextView.setTextColor(ResourcesHelper.getColorStateList(colorId));
    }

    /**
     * trim
     *
     * @param paramString 文本
     * @return trim后文本
     */
    public static String trim(String paramString) {
        if (isEmpty(paramString))
            return null;

        return paramString.trim();
    }

    public static String trimInner(String paramString) {
        if (isEmpty(paramString))
            paramString = null;
        else {
            Matcher localMatcher = Pattern.compile("(\\s+)").matcher(
                    paramString);

            while (localMatcher.find())
                paramString = paramString.replaceFirst(localMatcher.group(1),
                        " ");
        }


        return paramString;
    }

    /**
     * 格式化float类型，保留2位小数
     *
     * @param paramFloat 待格式化小数
     * @return 格式化文本，保留2位小数
     */
    public static String valueOf(float paramFloat) {
        DecimalFormat localDecimalFormat = new DecimalFormat();
        localDecimalFormat.setMaximumFractionDigits(2);
        return localDecimalFormat.format(paramFloat);
    }

    /**
     * 去掉两端的空格（包括全角空格和半角空格）
     *
     * @param s 文本
     */
    public static String trimSpace(String s) {
        if (s == null) {
            return null;
        }
        s = s.trim();
        while (s.startsWith("　")) {//这里判断是不是全角空格
            s = s.substring(1, s.length()).trim();
        }
        while (s.endsWith("　")) {
            s = s.substring(0, s.length() - 1).trim();
        }

        return s;
    }


    public static boolean contains(String seq, String searchSeq) {
        if (seq == null || searchSeq == null) {
            return false;
        }
        return seq.indexOf(searchSeq) >= 0;
    }


}
