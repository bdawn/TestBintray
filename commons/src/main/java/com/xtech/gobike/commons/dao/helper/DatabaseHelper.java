package com.xtech.gobike.commons.dao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.xtech.gobike.commons.config.XhConfig;
import com.xtech.gobike.commons.config.XhConfigConstants;
import com.xtech.gobike.commons.dao.RunningRecord;
import com.xtech.gobike.commons.dao.WiFiConfig;
import com.xtech.gobike.commons.helper.CommonContext;
import com.xtech.gobike.commons.helper.LogUtil;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据库的操作类
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private String dbName;
    private Map<String, Dao<?, ?>> daos = new HashMap<>();

    public DatabaseHelper(Context context, String dbName) {
        super(context, dbName, null, XhConfig.getConfigInt(XhConfigConstants.DATABASE_VERSION, 1));
        LogUtil.i(this.getClass().getName(), "dbVersion: " + XhConfig.getConfigInt(XhConfigConstants.DATABASE_VERSION, 1)
                + " dbName: " + dbName);
        this.dbName = dbName;
    }

    /**
     * 本地数据表注册,注意:此时将建立表
     */
    private static Set<Class<?>> dataTableClazz = addAllDatabaseClass();

    private static Set<Class<?>> addAllDatabaseClass() {
        Class<?>[] classes = {RunningRecord.class, WiFiConfig.class};
        List<Class<?>> list = Arrays.asList(classes);
        Set<Class<?>> classSet = new HashSet<>(list);
        return classSet;
    }

    @Override
    public void onCreate(SQLiteDatabase database,
                         ConnectionSource connectionSource) {
        LogUtil.i(this.getClass().getName(), "step in onCreate");
        try {
            for (Class<?> tableClazz : dataTableClazz) {
                LogUtil.i(this.getClass().getName(), "Class table creating: " + tableClazz.getSimpleName());
                TableUtils.createTable(connectionSource, tableClazz);
            }
        } catch (SQLException e) {
            LogUtil.e(this.getClass().getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 升级数据库
     *
     * @param database
     * @param connectionSource
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database,
                          ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            LogUtil.i(this.getClass().getName(), "onUpgrade");
            for (Class<?> tableClazz : dataTableClazz) {
                TableUtils.dropTable(connectionSource, tableClazz, true);
            }

            onCreate(database, connectionSource);
        } catch (SQLException e) {
            LogUtil.e(this.getClass().getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取数据库操作的bean操作
     *
     * @param clazz 数据库的映射类
     * @return 数据库操作的bean操作
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public synchronized <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) throws SQLException {
        D dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            dao = (D) daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        if (daos != null) {
            daos.clear();
        }

    }

    /**
     * 删除数据库
     *
     * @return 是否成功
     */
    public boolean deleteDatabase() {
        String dbName = this.dbName;
        Context mContext = CommonContext.getContext();
        if (mContext != null) {
            File f = mContext.getDatabasePath(dbName);
            if (f.exists()) {
                LogUtil.e("DB", "---delete SDCard DB---");
                boolean ret = f.delete();
                LogUtil.e("DB", "---delete SDCard DB---ret:" + ret);
            } else {
                LogUtil.e("DB", "---delete App DB---");
                mContext.deleteDatabase(dbName);
            }
            return true;
        } else {
            return false;
        }
    }
}
