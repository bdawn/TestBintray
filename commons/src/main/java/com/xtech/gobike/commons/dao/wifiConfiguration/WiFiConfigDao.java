package com.xtech.gobike.commons.dao.wifiConfiguration;

import com.xtech.gobike.commons.dao.BaseDao;
import com.xtech.gobike.commons.dao.DaoException;
import com.xtech.gobike.commons.dao.WiFiConfig;

import java.sql.SQLException;

/**
 * wifi配置dao
 * Created by GX on 2017/8/14.
 */

public interface WiFiConfigDao extends BaseDao<WiFiConfig,Object> {

    WiFiConfig getWiFiConfigBySSID(String ssid) throws DaoException, SQLException;

    void deleteWiFiConfigBySSID(String ssid) throws DaoException, SQLException;
}
