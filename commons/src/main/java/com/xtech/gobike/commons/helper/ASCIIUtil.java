package com.xtech.gobike.commons.helper;

/**
 * Created by GX on 2017/8/23.
 */

public class ASCIIUtil {
    public static String stringToAscii(String value) {
        if (value == null) return null;
        StringBuilder s = new StringBuilder();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                s.append((int) chars[i]).append(",");
            } else {
                s.append((int) chars[i]);
            }
        }
        return s.toString();
    }

    public static String asciiToString(String value) {
        if (value == null) return null;
        StringBuilder s = new StringBuilder();
        String[] chars = value.split(",");
        for (String aChar : chars) {
            s.append((char) Integer.parseInt(aChar));
        }
        return s.toString();
    }
}
