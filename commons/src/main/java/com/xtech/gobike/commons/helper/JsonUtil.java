package com.xtech.gobike.commons.helper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 字符串处理类
 */
public class JsonUtil {

    /**
     *
     * @param jsonStr
     * @param key
     * @return
     */
    public static String getValueFromJsonStringByKey(String jsonStr, String key){
        String res = "";
        try {
            JSONObject jObj = new JSONObject(jsonStr);
            res = jObj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

}
