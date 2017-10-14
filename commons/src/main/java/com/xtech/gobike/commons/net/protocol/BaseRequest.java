package com.xtech.gobike.commons.net.protocol;

import com.xtech.gobike.commons.net.TokenHelper;

/**
 * Volley请求基类,所有请求类需要继承这个类
 */
public class BaseRequest {

    /**
     * 唯一id
     */
    private String uuid;

    /**
     * 访问令牌
     */
    private String accessToken = TokenHelper.getInstance().getAccessToken();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
