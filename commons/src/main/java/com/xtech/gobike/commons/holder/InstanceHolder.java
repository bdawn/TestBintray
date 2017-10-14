package com.xtech.gobike.commons.holder;

import android.support.annotation.NonNull;

import com.xtech.gobike.commons.exception.XhException;
import com.xtech.gobike.commons.log.Log;
import com.xtech.gobike.commons.log.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 存储实例
 */
public class InstanceHolder {
    Log logger = LogFactory.getLog(InstanceHolder.class);

    /**
     * 注册类,存储实例
     */
    private final WeakHashMap<Class<?>, Object> instanceMap = new WeakHashMap<>();

    /**
     * 注册类,存储类
     */
    private Map<Class<?>, Class<?>> classMap = new HashMap<>();

    /**
     * 注册
     *
     * @param interfClazz 接口类
     * @param implClazz   实现类
     */
    public InstanceHolder register(Class<?> interfClazz, Class<?> implClazz) {
        classMap.put(interfClazz, implClazz);
        return this;
    }

    /**
     * 获取实现类
     *
     * @param interfClazz 接口类
     * @param <T>
     * @param initCallBack 回调接口
     * @return 实现类实例
     */
    @SuppressWarnings("unchecked")
    public <T> T getImplInstance(Class<? extends T> interfClazz, InitCallBack<T> initCallBack) {
        return getImplInstance(interfClazz, initCallBack, instanceMap);
    }

    /**
     * 获取实现类
     *
     * @param interfClazz  接口类
     * @param instanceMap
     * @param initCallBack 回调接口
     * @return 实现类实例
     */
    @SuppressWarnings("unchecked")
    private <T> T getImplInstance(Class<? extends T> interfClazz, InitCallBack<T> initCallBack,
                                  @NonNull final WeakHashMap<Class<?>, Object> instanceMap) {
        T implInstance = (T) instanceMap.get(interfClazz);
        if (implInstance != null) {
            return implInstance;
        }
        synchronized (instanceMap) {
            implInstance = (T) instanceMap.get(interfClazz);
            if (implInstance == null) {
                Class<? extends T> implClazz = (Class<? extends T>) classMap.get(interfClazz);
                try {
                    T obj = implClazz.newInstance();
                    instanceMap.put(interfClazz, obj);
                    if (initCallBack != null) {
                        initCallBack.init(obj);
                    }
                    implInstance = obj;
                } catch (Exception ex) {
                    logger.error(new XhException(ex));
                }
            }
        }
        return implInstance;
    }

    /**
     * 获取实现类
     *
     * @param interfClazz 接口类
     * @param <T>
     * @return 实现类实例
     */
    @SuppressWarnings("unchecked")
    public <T> T getImplInstance(Class<? extends T> interfClazz) {
        return getImplInstance(interfClazz, instanceMap);
    }

    /**
     * 获取实现类
     *
     * @param interfClazz 接口
     * @param instanceMap
     * @return 实现类实例
     */
    @SuppressWarnings("unchecked")
    private <T> T getImplInstance(Class<? extends T> interfClazz,
                                  @NonNull final WeakHashMap<Class<?>, Object> instanceMap) {
        T implInstance = (T) instanceMap.get(interfClazz);
        if (implInstance != null) {
            return implInstance;
        }
        synchronized (instanceMap) {
            implInstance = (T) instanceMap.get(interfClazz);
            if (implInstance == null) {
                Class<? extends T> implClazz = (Class<? extends T>) classMap.get(interfClazz);
                try {
                    T obj = implClazz.newInstance();
                    instanceMap.put(interfClazz, obj);
                    implInstance = obj;
                } catch (Exception ex) {
                    logger.error(new XhException(ex));
                }
            }
        }
        return implInstance;
    }

    public interface InitCallBack<T> {
        void init(T t) throws Exception;
    }
}
