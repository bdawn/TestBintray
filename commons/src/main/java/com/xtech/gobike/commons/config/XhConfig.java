package com.xtech.gobike.commons.config;

import com.xtech.gobike.commons.helper.CommonContext;
import com.xtech.gobike.commons.helper.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>config.properties  为总配置文件，配置环境
 * <p>/conf/*.properties配置文件为具体环境中的各自的配置文件
 * <p>约定:
 * <ol>
 * <li>dev开发环境
 * <li>test测试环境
 * <li>online线上环境
 * </ol>
 *
 */
public class XhConfig {

    private static final String tag = XhConfig.class.getSimpleName();

    /**
     * 根据配置文件，查询配置项
     *
     * @param key {@link XhConfigConstants}
     * @return 配置值
     * @see “config.properties”,/conf/*.properties配置文件中的值
     */
    public static String getConfig(String key) {
        Properties conf = getConfigProperties();
        return conf.getProperty(key);
    }

    /**
     * 根据配置文件项，查询int值，没有默认0
     *
     * @param key {@link XhConfigConstants}
     * @return 配置值
     */
    public static int getConfigInt(String key) {
        return getConfigInt(key, 0);
    }

    /**
     * 根据配置文件项，查询int值，没有默认defaultValue
     *
     * @param key          {@link XhConfigConstants}
     * @param defaultValue 默认值
     * @return 配置值
     */
    public static int getConfigInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(getConfig(key));
        } catch (Exception e) {
            LogUtil.e(tag, "getConfig int parse error, key:" + key, e);
        }
        return defaultValue;
    }

    public static String getServerUrl() {
        return getConfig(XhConfigConstants.GO_RUN_API_URL);
    }

    private static Properties conf;

    private static Properties getConfigProperties() {
        if (conf == null) {
            String environment = getMainConfigProperties().getProperty("environment", "online");
            Properties props = new Properties();
            InputStream in = null;
            try {
                in = getInputStreamAssets("conf/" + environment + ".properties");
                props.load(in);
            } catch (IOException e) {
                LogUtil.e(tag, "error while get " + "conf/" + environment + ".properties", e);
            } finally {
                try {
                    in.close();
                } catch (Exception ignored) {

                }
            }
            conf = props;
        }
        return conf;
    }

    /**
     * 读取/assets/config.properties文件
     *
     * @return Properties
     */
    private static Properties getMainConfigProperties() {
        Properties props = new Properties();
        InputStream in = null;
        try {
            in = getInputStreamAssets("config.properties");
            props.load(in);
        } catch (IOException e) {
            LogUtil.e(tag, "error while get /assets/config.properties", e);
        } finally {
            try {
                in.close();
            } catch (Exception ignored) {

            }
        }
        return props;
    }

    private static InputStream getInputStreamAssets(String fileName) throws IOException {
        return CommonContext.getContext().getAssets().open(fileName);
    }
}
