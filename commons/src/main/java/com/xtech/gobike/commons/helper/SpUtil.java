package com.xtech.gobike.commons.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;


import com.xtech.gobike.commons.config.AppConstants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SpUtil {
    private static final String TAG = SpUtil.class.getSimpleName();
    private static Editor editor = null;
    private static SharedPreferences sharedPreferences = null;


    private static SharedPreferences getSharedPreferences(String preferenceName) {
        return CommonContext.getContext().getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }

    public static void putString(String preferenceName, String key, String value) {
        Editor editor = getSharedPreferences(preferenceName).edit();
        editor.putString(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static String getString(String preferenceName, String key, String defaultValue) {
        return getSharedPreferences(preferenceName).getString(key, defaultValue);
    }

    private static SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = CommonContext.getContext().getSharedPreferences(AppConstants.SP_ALL_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    private static Editor getEditor() {
        if (editor == null) {
            editor = getSharedPreferences().edit();
        }
        return editor;
    }

    public static void putBoolean(String key, boolean booleVal) {
        getEditor().putBoolean(key, booleVal);
        SharedPreferencesCompat.apply(editor);
    }

    public static void putInt(String key, int intVal) {
        getEditor().putInt(key, intVal);
        SharedPreferencesCompat.apply(editor);
    }

    public static void putFloat(String key, float floatVal) {
        getEditor().putFloat(key, floatVal);
        SharedPreferencesCompat.apply(editor);
    }

    public static void putString(String key, String stringVal) {
        getEditor().putString(key, stringVal);
        SharedPreferencesCompat.apply(editor);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getSharedPreferences().getBoolean(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        return getSharedPreferences().getInt(key, defaultValue);
    }

    public static float getFloat(String key, int defaultValue) {
        return getSharedPreferences().getFloat(key, defaultValue);
    }

    public static String getString(String key, String defaultValue) {
        return getSharedPreferences().getString(key, defaultValue);
    }

    public static void remove(String key) {
        getEditor().remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 储存对象信息 注意：储存的对象要实现序列化接口
     *
     * @param key 保存的key名称
     * @param obj 保存对象
     */
    public static void saveObject(String key, Object obj) {
        SharedPreferences preferences = CommonContext.getContext().getSharedPreferences(
                AppConstants.SP_ALL_NAME, Context.MODE_PRIVATE);
        // 创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(obj);
            // 将字节流编码成base64的字符窜
            String oAuth_Base64 = new String(Base64Util.encode(baos.toByteArray()));
            Editor editor = preferences.edit();
            editor.putString(key, oAuth_Base64);

            editor.commit();
        } catch (Exception e) {
            LogUtil.e(TAG, "saveObject encounter error. key：" + key, e);
        }
    }

    /**
     * 读取对象信息
     *
     * @param key 读取的key名称
     * @return 对象信息
     */
    public static Object getObject(String key) {
        SharedPreferences preferences = getSharedPreferences();
        String productBase64 = preferences.getString(key, "");

        if (TextUtils.isEmpty(productBase64)) {
            LogUtil.i(TAG, "偏好设置获取对象为空,key:" + key);
            return null;
        }

        Object result = null;
        try {
            // 读取字节
            byte[] base64 = Base64Util.decode(productBase64);
            // 封装到字节流
            ByteArrayInputStream bais = new ByteArrayInputStream(base64);

            // 再次封装
            ObjectInputStream bis = new ObjectInputStream(bais);
            try {
                // 读取对象
                result = bis.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                LogUtil.e(TAG, "偏好设置获取对象错误", e);
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            LogUtil.e(TAG, "偏好设置获取对象错误", e);
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e(TAG, "偏好设置获取对象错误", e);
        }
        if (result == null) {
            LogUtil.e(TAG, "获取结果为空，没有数据");
        }
        return result;
    }

    /**
     * 通过反射查找apply方法,如果存在,则用apply,否则用commit
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                LogUtil.e(TAG, e.getMessage());
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor Editor
         */
        public static void apply(Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
                LogUtil.e(TAG, e.getMessage());
            } catch (IllegalAccessException e) {
                LogUtil.e(TAG, e.getMessage());
            } catch (InvocationTargetException e) {
                LogUtil.e(TAG, e.getMessage());
            }
            editor.commit();
        }
    }
}
