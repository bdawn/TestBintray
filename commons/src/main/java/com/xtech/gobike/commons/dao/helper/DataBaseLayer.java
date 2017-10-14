package com.xtech.gobike.commons.dao.helper;

import android.content.Context;

import com.xtech.gobike.commons.helper.CommonContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库分用户管理，分离数据库管理逻辑
 */
public class DataBaseLayer {
    private DataBaseLayer() {
    }

    private static volatile DataBaseLayer instance;
    Map<String, DatabaseHelper> databases = new HashMap<>();

    public static DataBaseLayer getInstance() {
        if (instance == null) {
            synchronized (DataBaseLayer.class) {
                if (instance == null) {
                    instance = new DataBaseLayer();
                }
            }
        }
        return instance;
    }

    /**
     * 启动事务，针对当前使用的数据库
     */
    public void beginTransactionCurrentDB() {
        getCurrentDataBaseHelper().getWritableDatabase().beginTransaction();
    }

    /**
     * 提交事务回滚
     */
    public void endTransactionCurrentDBRollback() {
        getCurrentDataBaseHelper().getWritableDatabase().endTransaction();
    }

    /**
     * 提交事务成功提交事务
     */
    public void endTransactionCurrentDBSuccessFul() {
        getCurrentDataBaseHelper().getWritableDatabase().setTransactionSuccessful();
        getCurrentDataBaseHelper().getWritableDatabase().endTransaction();
    }

    private Context getContext() {
        return CommonContext.getContext();
    }

    public DatabaseHelper getDataBaseLayer(String namespace) {
        DatabaseHelper helper = databases.get(namespace);
        if (helper != null) {
            return helper;
        }
        synchronized (DataBaseLayer.class) {
            helper = databases.get(namespace);
            if (helper != null) {
                return helper;
            }
            helper = new DatabaseHelper(this.getContext(), namespace);
            databases.put(namespace, helper);
        }
        return helper;
    }

    public DatabaseHelper getCurrentDataBaseHelper() {
        return getDataBaseLayer(UserInfoHelper.getCurrentUserNameSpace());
    }
}
