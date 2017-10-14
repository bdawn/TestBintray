package com.xtech.gobike.commons.net;

import com.xtech.gobike.commons.dao.helper.UserInfoHelper;
import com.xtech.gobike.commons.helper.LogUtil;
import com.xtech.gobike.commons.helper.SpUtil;
import com.xtech.gobike.commons.helper.TextUtil;

/**
 * token 帮助类
 */
public class TokenHelper {


    private static final String EMPTY_VALUE = "";
    /**
     * refreshToken key
     */
    private static final String REFRESH_TOKEN_KEY = "refreshToken";

    /**
     * accessToken key
     */
    private static final String ACCESS_TOKEN_KEY = "accessToken";
    private static TokenHelper instance;
    private String accessToken;
    private String refreshToken;

    public static TokenHelper getInstance() {
        if (instance == null) {
            instance = new TokenHelper();
        }
        return instance;
    }

    public String getRefreshToken() {
        if (!TextUtil.isEmpty(refreshToken)) {
            return refreshToken;
        }
        refreshToken = SpUtil.getString(getNameSpace(), REFRESH_TOKEN_KEY, EMPTY_VALUE);
        return refreshToken;
    }

    public TokenHelper setRefreshToken(String refreshToken) {
        SpUtil.putString(getNameSpace(), REFRESH_TOKEN_KEY, refreshToken);
        this.refreshToken = refreshToken;
        return this;
    }

    public String getAccessToken() {
        if (!TextUtil.isEmpty(accessToken)) {
            LogUtil.i(this.getClass().getName(), "accessToken:" + accessToken);
            return accessToken;
        }
        accessToken = SpUtil.getString(getNameSpace(), ACCESS_TOKEN_KEY, EMPTY_VALUE);
        LogUtil.i(this.getClass().getName(), "accessToken from SpUtil:" + accessToken);
        return accessToken;
    }

    public TokenHelper setAccessToken(String accessToken) {
        SpUtil.putString(getNameSpace(), ACCESS_TOKEN_KEY, accessToken);
        this.accessToken = accessToken;
        return this;
    }

    private String getNameSpace() {
        return UserInfoHelper.getCurrentUserNameSpace();
    }
}
