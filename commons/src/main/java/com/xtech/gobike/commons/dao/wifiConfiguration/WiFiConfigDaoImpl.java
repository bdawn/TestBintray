package com.xtech.gobike.commons.dao.wifiConfiguration;

import com.xtech.gobike.commons.dao.BaseDaoImpl;
import com.xtech.gobike.commons.dao.DaoException;
import com.xtech.gobike.commons.dao.WiFiConfig;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by GX on 2017/8/14.
 */

public class WiFiConfigDaoImpl extends BaseDaoImpl<WiFiConfig,Object>  implements WiFiConfigDao {
    @Override
    public Class<WiFiConfigDao> getCurrentDaoClass() {
        return WiFiConfigDao.class;
    }

    @Override
    public Class<WiFiConfig> getBeanClass() {
        return WiFiConfig.class;
    }

    @Override
    public WiFiConfig getWiFiConfigBySSID(String ssid) throws DaoException, SQLException {
        List<WiFiConfig> list = getDao().queryForEq("ssid",ssid);
        if (list == null || list.size() <= 0)return null;
        return list.get(0);
    }

    @Override
    public void deleteWiFiConfigBySSID(String ssid) throws DaoException, SQLException {
        getDao().deleteBuilder().where().eq("ssid",ssid);
    }
}
