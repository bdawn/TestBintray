package com.xtech.gobike.commons.helper;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xtech.gobike.commons.R;


/**
 * toast 帮助工具类
 */
public class ToastHelper {
    private static TextView textView;
    private static Toast toast;
    private static View toastView;

    private static void createToast() {
        toastView = LayoutInflater.from(CommonContext.getContext())
                .inflate(R.layout.mk_common_toast, null);
        textView = (TextView) toastView.findViewById(R.id.txtViewContent);
        toast = new Toast(CommonContext.getContext());
        toast.setView(toastView);
        toast.setGravity(1, 0, 0);
    }

    private static void setIcon(IconType paramIconType, ImageView paramImageView) {
        if (paramIconType == null) {
            paramImageView.setBackgroundResource(R.drawable.common_toast_icon_info);
            return;
        }
        switch (paramIconType) {
            case INFO:
                paramImageView.setBackgroundResource(R.drawable.common_toast_icon_info);
                break;
            case COMPLETE:
                paramImageView.setBackgroundResource(R.drawable.common_toast_icon_complete);
                break;
            case ERROR:
                paramImageView.setBackgroundResource(R.drawable.common_toast_icon_error);
                break;
        }
    }

    private static void _showToast(final IconType iconType, final String msg, final int toastDuration) {
        UiThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                __showToast(iconType, msg, toastDuration);
            }
        });
    }

    private static void __showToast(IconType iconType, String msg, int toastDuration) {
        if (toast == null)
            createToast();
        textView.setText(msg);
        toast.setDuration(toastDuration);
        toast.show();
    }

    /**
     * 显示完成Toast
     *
     * @param id 字符串资源id
     */
    public static void showLongCompleteMessage(int id) {
        showLongCompleteMessage(TextUtil.getString(id));
    }

    /**
     * 显示完成Toast
     *
     * @param msg 提示信息
     */
    public static void showLongCompleteMessage(final String msg) {
        _showToast(IconType.COMPLETE, msg, Toast.LENGTH_LONG);
    }

    /**
     * 显示错误Toast
     *
     * @param id 字符串资源id
     */
    public static void showLongError(int id) {
        showLongError(TextUtil.getString(id));
    }

    /**
     * 显示错误Toast
     *
     * @param msg 提示信息
     */
    public static void showLongError(String msg) {
        _showToast(IconType.ERROR, msg, Toast.LENGTH_LONG);
    }

    /**
     * 显示提示Toast
     *
     * @param id 字符串资源id
     */
    public static void showLongInfo(int id) {
        showLongInfo(TextUtil.getString(id));
    }

    /**
     * 显示提示Toast
     *
     * @param msg 提示信息
     */
    public static void showLongInfo(String msg) {
        _showToast(IconType.INFO, msg, Toast.LENGTH_LONG);
    }

    /**
     * 显示完成Toast
     *
     * @param id 字符串资源id
     */
    public static void showShortCompleted(int id) {
        showShortCompleted(TextUtil.getString(id));
    }

    /**
     * 显示完成Toast
     *
     * @param msg 提示信息
     */
    public static void showShortCompleted(String msg) {
        _showToast(IconType.COMPLETE, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示错误Toast
     *
     * @param id 字符串资源id
     */
    public static void showShortError(int id) {
        showShortError(TextUtil.getString(id));
    }

    /**
     * 显示错误Toast
     *
     * @param msg 提示信息
     */
    public static void showShortError(String msg) {
        _showToast(IconType.ERROR, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示提示Toast
     *
     * @param id 字符串资源id
     */
    public static void showShortInfo(int id) {
        showShortInfo(TextUtil.getString(id));
    }

    /**
     * 显示提示Toast
     *
     * @param msg 提示信息
     */
    public static void showShortInfo(String msg) {
        _showToast(IconType.INFO, msg, Toast.LENGTH_SHORT);
    }

    public enum IconType {
        INFO, COMPLETE, ERROR
    }
}