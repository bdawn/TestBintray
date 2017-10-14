package com.xtech.gobike.commons.dao.helper;

import java.io.Serializable;

/**
 * 用户表
 */
public class UserInfo implements Serializable {
    /**
     * 用户类型 {0,手机} {1,qq} {2,微信}
     */
    private String type;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户展示名
     */
    private String nickName;

    /**
     * 头像
     */
    private String headImgUrl;

    /**
     * 微信登录access_token
     */
    private String accessToken;

    /**
     * 微信登录refresh_token
     */
    private String refreshToken;

    /**
     * token过期时间-ms
     */
    private long expireTime;

    public UserInfo() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "type='" + type + '\'' +
                ", userId='" + userId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expireTime=" + expireTime +
                '}';
    }
}
