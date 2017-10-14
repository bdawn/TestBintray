package com.xtech.gobike.commons.dao;


import com.xtech.gobike.commons.dao.helper.UserInfoHelper;
import com.xtech.gobike.commons.dao.record.RunningRecordDao;
import com.xtech.gobike.commons.dao.record.RunningRecordDaoImpl;
import com.xtech.gobike.commons.dao.wifiConfiguration.WiFiConfigDao;
import com.xtech.gobike.commons.dao.wifiConfiguration.WiFiConfigDaoImpl;
import com.xtech.gobike.commons.holder.InstanceHolder;

/**
 * Dao factory
 */
public class DaoFactory {

    private static InstanceHolder factoryHolder;

    static {
        factoryHolder = new InstanceHolder();
        factoryHolder.register(RunningRecordDao.class, RunningRecordDaoImpl.class);
        factoryHolder.register(WiFiConfigDao.class, WiFiConfigDaoImpl.class);
    }

    /**
     * 获取当前用户的dao
     *
     * @param interfaceClazz dao 接口
     * @param <T>            存储数据表的bean 类型
     * @param <ID>           数据主键类型
     * @param <E>            basedao子类
     * @return dao接口
     * @throws Exception
     */
    public static <T, ID, E extends BaseDao<T, ID>> E getDao(Class<E> interfaceClazz) throws Exception {
        return factoryHolder.getImplInstance(interfaceClazz, new InstanceHolder.InitCallBack<E>() {
            @Override
            public void init(E baseDao) throws Exception {
                baseDao.setNameSpace(UserInfoHelper.getCurrentUserNameSpace());
                baseDao.setCurrentDaoMode(true);
            }
        });
    }
}
