package com.xtech.gobike.commons.net;

/**
 * 网络请求
 */

public interface NetTaskListener {
    /**
     * 网络任务处理成功
     */
    void onTaskCompleted(String res);

    /**
     * 网络任务处理失败
     */
    void onTaskFailed(String code, String res);
}