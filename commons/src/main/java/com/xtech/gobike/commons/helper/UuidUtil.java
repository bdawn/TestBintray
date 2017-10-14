package com.xtech.gobike.commons.helper;

import com.xtech.gobike.commons.dao.helper.UserInfoHelper;
import com.xtech.gobike.commons.exception.XhException;
import com.xtech.gobike.commons.log.Log;
import com.xtech.gobike.commons.log.LogFactory;

import java.util.Random;
import java.util.UUID;

/**
 * uuid生成
 */
public class UuidUtil {

    private static final Log log = LogFactory.getLog(UuidUtil.class);

    /**
     * UUID 生成uuid策略
     *
     * @return UUID
     */
    public static String generateUUID() {
        try {
            // 登录后getCurrentUser
            String mobile = UserInfoHelper.getCurrentUserNameSpace();

            int random = new Random().nextInt(100);
            return DateUtil.getDateYYYYMMDDHHMMSS() + "-" + mobile + "-" + random;
        } catch (Exception e) {
            log.error(new XhException("generateUUID encounter error"));
        }
        // 没有登录或其他异常情况，使用uuid策略
        return DateUtil.getDateYYYYMMDDHHMMSS() + "-" + UUID.randomUUID();
    }
}
