package com.xtech.gobike.commons.dao;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * 数据库基础类
 */
public abstract class BaseData implements Serializable {

    public static final String GMT_UPDATE_TIME = "gmt_update_time";

    public static final String GMT_CREATE_TIME = "gmt_create_time";
    /**
     * 更新时间
     */
    @DatabaseField(canBeNull = false, columnName = GMT_UPDATE_TIME)
    protected String gmtUpdateTime;
    /**
     * 记录创建时间
     */
    @DatabaseField(canBeNull = false, columnName = GMT_CREATE_TIME)
    protected String gmtCreateTime;

    public String getGmtUpdateTime() {
        return gmtUpdateTime;
    }

    public void setGmtUpdateTime(String gmtUpdateTime) {
        this.gmtUpdateTime = gmtUpdateTime;
    }

    public String getGmtCreateTime() {
        return gmtCreateTime;
    }

    public void setGmtCreateTime(String gmtCreateTime) {
        this.gmtCreateTime = gmtCreateTime;
    }

    @Override
    public String toString() {
        return "BaseData{" +
                "gmtUpdateTime='" + gmtUpdateTime + '\'' +
                ", gmtCreateTime='" + gmtCreateTime + '\'' +
                '}';
    }
}
