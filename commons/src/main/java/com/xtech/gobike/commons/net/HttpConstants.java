package com.xtech.gobike.commons.net;

/**
 * 常数类
 */
public class HttpConstants {
    public static final String INTENT_EXTRA_KEY = "com.xtech.intent_extra_key";
    public static String defaultFlashPath = "";

    //正常,成功访问
    public static final int OK = 0;
    //服务器内部错误
    public static final int SERVER_ERROR = 1;
    //验证码发送频繁
    public static final int VERIFY_CODE_BUSY = 2;
    //请重新发送验证码
    public static final int VERIFY_CODE_RESENT = 3;
    //验证码已过期
    public static final int VERIFY_CODE_OUT_DATE = 4;
    //验证码错误
    public static final int VERIFY_CODE_ERROR = 5;
    //手机号或验证码错误
    public static final int PHONE_VERIFY_CODE_ERROR = 6;
}