package com.xtech.gobike.commons.dao;

/**
 *跑步记录的标志位常量
 */

public class RecordConstants {
    //删除状态
    public static int STATUS_DELETED = 1;
    //存在状态
    public static int STATUS_EXISTED = 0;

    //未上传
    public static int STATUS_UPLOAD_UNFINISHED = 0;
    //上传中
    public static int STATUS_UPLOAD_RUNNING = 1;
    //已上传
    public static int STATUS_UPLOAD_SUCCEED = 2;
}
