package com.xtech.gobike.commons.net;

/**
 * 请求的2个接口声明
 */
public interface XhRequestListener<T> {

    /**
     * 请求成功
     *
     * @param result 返回请求的结果
     */
    void onRequestSuccess(T result);

    /**
     * 请求失败
     *
     * @param code 错误代码
     * @param msg  错误消息
     */
    void onRequestFail(String code, String msg);
}
