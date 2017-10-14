package com.xtech.gobike.commons.dao.helper;


import com.xtech.gobike.commons.helper.SpUtil;

import java.io.Serializable;

/**
 * 用户空间管理器
 */
public class UserInfoHelper implements Serializable {
    public static final String MOBILE = "0";
    public static final String QQ = "1";
    public static final String WECHAT = "2";
    public static final String WEIBO = "3";

    private static final String CURRENT_NAME_SPACE = "currentNameSpace";
    private static UserInfo userInfo;
    private static UserInfo dummyUserInfo = new UserInfo();

    static {
        dummyUserInfo.setNickName("dummy");
        dummyUserInfo.setUserId("dummy");
        if (userInfo == null && SpUtil.getObject(CURRENT_NAME_SPACE) != null) {
            userInfo = (UserInfo) SpUtil.getObject(CURRENT_NAME_SPACE);
        }
    }

    public static boolean isLogin() {
        return userInfo != null;
    }

    public static String getCurrentUserNameSpace() {
        return userInfo != null ? userInfo.getUserId() : dummyUserInfo.getUserId();
    }

    public static UserInfo getCurrentUserInfo() {
        return userInfo != null ? userInfo : dummyUserInfo;
    }


    public static String getShowUserName() {
        return userInfo == null ? "未登录" : userInfo.getNickName();
    }

    /**
     * 用户发送请求数据成功后,保存用户数据到SharePreference中
     *
     * @param userInfo
     * @return
     */
    public static void saveUserInfo(final UserInfo userInfo) {
        UserInfoHelper.userInfo = userInfo;
        SpUtil.saveObject(CURRENT_NAME_SPACE, userInfo);
    }

    public static void removeUserInfo() {
        userInfo = null;
        saveUserInfo(null);
    }

    public static boolean wechatUser() {
        return userInfo != null && WECHAT.equals(userInfo.getType());
    }

    public static boolean qqUser() {
        return userInfo != null && QQ.equals(userInfo.getType());
    }

    public static boolean weiboUser() {
        return userInfo != null && WEIBO.equals(userInfo.getType());
    }

    public static boolean mobileUser() {
        return userInfo != null && MOBILE.equals(userInfo.getType());
    }
}
