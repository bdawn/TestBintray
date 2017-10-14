package com.xtech.gobike.commons.api;


import com.xtech.gobike.commons.exception.ApiException;
import com.xtech.gobike.commons.holder.InstanceHolder;

public class DatabaseFactory {
    private static InstanceHolder instanceHolder;

    static {
        instanceHolder = new InstanceHolder();
        instanceHolder.register(RunningRecordApi.class, RunningRecordApiImpl.class);
    }

    public static <T> T getApi(Class<? extends T> interfaceClazz) {
        try {
            return instanceHolder.getImplInstance(interfaceClazz);
        } catch (Exception e) {
            throw new ApiException("DeviceFactory init interface:" + interfaceClazz, e);
        }
    }
}
