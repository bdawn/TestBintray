package com.xtech.gobike.commons.helper;

import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 对话框工具类
 */
public class DialogUtil {

    private static SweetAlertDialog mProgressDialog;


    public static final void showResultDialog(Context context, String msg,
                                              String title) {
        if (msg == null) return;
        String rmsg = msg.replace(",", "\n");
        new SweetAlertDialog(context)
                .setTitleText(title)
                .setContentText(msg)
                .setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    public static final void showProgressDialog(Context context, String title) {
        dismissDialog();
        mProgressDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        mProgressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mProgressDialog.setTitleText(title);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public static final void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}

