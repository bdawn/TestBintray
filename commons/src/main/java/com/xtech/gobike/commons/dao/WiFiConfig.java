package com.xtech.gobike.commons.dao;

import com.j256.ormlite.field.DatabaseField;

/**
 * 连接过的wifi配置
 * Created by GX on 2017/8/14.
 */

public class WiFiConfig {
    //主键Id
    @DatabaseField(generatedId = true)
    protected int id;

    @DatabaseField
    protected String ssid;

    @DatabaseField
    protected String password;

    @DatabaseField
    protected int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
