package com.xtech.gobike.commons.config;

public class BaseFilePath {

    /**
     * 程序文件系统根目录
     */
    public static final String BASE_DIR = "/mnt/sdcard/gorun/";

    /**
     * data数据根目录
     */
    public static final String BASE_DATA_DIR = BASE_DIR+"data/";

    /**
     * 资源文件根目录
     */
    public static final String BASE_RES_DIR = BASE_DIR+"resource/";

    /**
     * 配置文件根目录
     */
    public static final String BASE_CFG_DIR = BASE_DIR+"config/";

    /**
     * 日志根目录
     */
    public static final String BASE_LOG_DIR = BASE_DIR+"logs/";

    /**
     * 基础配置
     */
    public static final String BASE_CFG = BaseFilePath.BASE_CFG_DIR + "base.cfg";

}
