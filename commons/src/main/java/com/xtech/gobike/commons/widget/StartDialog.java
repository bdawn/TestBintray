package com.xtech.gobike.commons.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtech.gobike.commons.R;
import com.xtech.gobike.commons.helper.ScreenUtil;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class StartDialog extends Dialog {
    // 继承dialog重写构造方法
    public StartDialog(@NonNull Context context) {
        super(context, R.style.DialogStyle);
    }

    TextView tvTime;
    RelativeLayout panel;

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(getContext(), R.layout.dialog_start, null);
        setContentView(view);
        // 设置是否可以关闭当前控件
        setCancelable(false);
        // 找到tv_time控件
        tvTime = (TextView) findViewById(R.id.tv_time);
        panel = (RelativeLayout) findViewById(R.id.panel);
        new DownTimer().start();
    }

    @Override
    public void show() {
        super.show();
        int width = ScreenUtil.getScreenWidth();
        int height = ScreenUtil.getScreenHeight();
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        setContentView(view, layoutParams);
    }

    // 继承CountDownTimer类
    class DownTimer extends CountDownTimer {
        private AlphaAnimation alphaAnimation;

        public DownTimer() {
            // 设置时间4秒
            super(4500, 1000);
            alphaAnimation = new AlphaAnimation(1, 0);
            alphaAnimation.setDuration(1000);
            alphaAnimation.setRepeatCount(-1);
        }

        // 重写CountDownTimer的两个方法
        @Override
        public void onTick(long millisUntilFinished) {
            Log.e("DownTimer","millisUntilFinished:" + millisUntilFinished);
            if (millisUntilFinished > 2000) {
                tvTime.setText((millisUntilFinished / 1000 - 1) + "");
                panel.startAnimation(alphaAnimation);
            } else {
                tvTime.setText("GO");
                panel.clearAnimation();
                return;
            }

//            ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
//                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//            animation.setDuration(900);                       //执行时间
//            animation.setRepeatCount(-1);                   //重复执行动画
//            animation.setRepeatMode(Animation.REVERSE);    //重复 缩小和放大效果
                        //使用View启动动画
        }

        @Override
        public void onFinish() {
            if (this != null) {
                panel.clearAnimation();
                dismiss();
            }
        }
    }
}