package com.xtech.gobike.commons.config;

/**
 * URL常数类
 */
public class UrlConstants {
    //1111发送验证码
    public static final String SEND_CODE = "sms/sendMessage/send";
    //1112Verify验证码
    public static final String VERIFY_CODE = "auth/verifyCode";
    //1113 通过验证码登录
    public static final String LOGIN_BY_VERIFY_CODE = "accounts/login";
    //1114 退出登录
    public static final String LOGOUT = "accounts/logout";

    //上传跑步数据(目前只支持单条)
    public static final String UPLOAD_RECORD = "run/records/add";

    public static final String URL_WEIBO_SHOW = "https://api.weibo.com/2/users/show.json";

    public static final String URL_WECHAT_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String URL_WECHAT_USER_INFO = "https://api.weixin.qq.com/sns/userinfo";

}