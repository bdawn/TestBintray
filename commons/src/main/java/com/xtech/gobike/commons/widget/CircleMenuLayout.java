package com.xtech.gobike.commons.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.xtech.gobike.commons.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 首页旋转菜单
 * Created by GX on 2017/8/24.
 */

public class CircleMenuLayout extends ViewGroup {
    public static final String TAG = CircleMenuLayout.class.getName();
    private int mRadius;
    /**
     * 该容器内child item的默认尺寸
     */
    private static final float RADIO_DEFAULT_CHILD_DIMENSION = 0.22f;

    /**
     * 计算右侧item放大的最大倍数的系数
     */
    private static final float MAX_SIZE = 0.01f;
    /**
     * 菜单的中心child的默认尺寸
     */
    private float RADIO_DEFAULT_CENTERITEM_DIMENSION = 1 / 3f;
    /**
     * 该容器的内边距,无视padding属性，如需边距请用该变量
     */
    private static final float RADIO_PADDING_LAYOUT = 1 / 20f;

    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private static final int FLINGABLE_VALUE = 50;

    /**
     * 如果移动角度达到该值，则屏蔽点击
     */
    private static final int NOCLICK_VALUE = 3;

    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private int mFlingableValue = FLINGABLE_VALUE;
    /**
     * 该容器的内边距,无视padding属性，如需边距请用该变量
     */
    private float mPadding;

    /**
     * 布局时的开始角度
     */
    private double mStartAngle = 0;
    /**
     * 菜单项的文本
     */
    private String[] mItemTexts;
    /**
     * 菜单项的图标
     */
    private int[] mItemImgs;

    /**
     * 菜单项的背景id
     */
    private int[] mItemBkgIds;

    private String[] mItemShadeColors;

    /**
     * 菜单的个数
     */
    private int mMenuItemCount = 6;

    /**
     * 检测按下到抬起时旋转的角度
     */
    private float mTmpAngle;
    /**
     * 检测按下到抬起时使用的时间
     */
    private long mDownTime;

    /**
     * 判断是否正在自动滚动
     */
    private boolean isFling;

    /**
     * 加速滚动是否完成
     */
    private boolean completed = true;

    /**
     * 当前在目标区域的item 下标
     */
    int position = 0;

    /**
     * 旋转至指定的item 下标
     */
    int target = 0;

    @SuppressLint("UseSparseArrays")
    private Map<Integer, Double> itemsAngle = new HashMap<>();


    public CircleMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 无视padding
        setPadding(0, 0, 0, 0);
    }

    /**
     * 设置布局的宽高，并策略menu item宽高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resWidth = 0;
        int resHeight = 0;

        // 根据传入的参数，分别获取测量模式和测量值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        setMeasuredDimension(width, height);

        // 获得半径
        mRadius = Math.min(getMeasuredWidth(), getMeasuredHeight());

        // menu item数量
        final int count = getChildCount();
        // menu item尺寸
        int childSize = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);
        // menu item测量模式
        int childMode = MeasureSpec.EXACTLY;

        // 迭代测量
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            if (child.getVisibility() == GONE) {
                continue;
            }

            // 计算menu item的尺寸；以及和设置好的模式，去对item进行测量
            int makeMeasureSpec;

            if (child.getId() == R.id.id_circle_menu_item_center) {
                makeMeasureSpec = MeasureSpec.makeMeasureSpec(
                        (int) (mRadius * RADIO_DEFAULT_CENTERITEM_DIMENSION),
                        childMode);
            } else {
                float f = 1;
                if (itemsAngle.size() > 0 && itemsAngle.get(i) > 120 && itemsAngle.get(i) < 240) {
                    f = getMultiple(60 - (int) Math.abs(180 - itemsAngle.get(i)));
                }
                makeMeasureSpec = MeasureSpec.makeMeasureSpec((int) (childSize * f),
                        childMode);
            }
            child.measure(makeMeasureSpec, makeMeasureSpec);
        }

        mPadding = RADIO_PADDING_LAYOUT * mRadius;

    }

    public void setMenuItemBkgIds(int[] ids, String[] mItemShadeColors) {
        this.mItemBkgIds = ids;
        this.mItemShadeColors = mItemShadeColors;
    }

    /**
     * 设置item的点击事件
     */
    private void setEvent() {
        for (int i = 0; i < mMenuItemCount; i++) {
            final int j = i;
            View v = getChildAt(i + 1);
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position != j + 1) {
                        target = j + 1;
                        new AutoAccelerateRunnable(itemsAngle.get(j + 1) > 180 ? -1 : 1).run();
                        return;
                    }
                    if (mOnMenuItemClickListener != null) {
                        mOnMenuItemClickListener.itemClick(v, j);
                    }
                }
            });
        }
    }

    /**
     * 设置menu item的位置
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int layoutRadius = mRadius;

        // Laying out the child views
        final int childCount = getChildCount();

        int left, top;
        // menu item 的尺寸
        int cWidth;
        // 计算，中心点到menu item中心的距离
        float tmp;

        // 根据menu item的个数，计算角度
        float angleDelay = 360 / (getChildCount() - 1);

        // 遍历去设置menuitem的位置
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (child.getId() == R.id.id_circle_menu_item_center)
                continue;

            if (child.getVisibility() == GONE) {
                continue;
            }

            mStartAngle %= 360;
            itemsAngle.put(i, mStartAngle);
            if (mStartAngle > 120 && mStartAngle < 240) {
                float f = getMultiple(60 - (int) Math.abs(180 - mStartAngle));
                cWidth = (int) (layoutRadius * RADIO_DEFAULT_CHILD_DIMENSION * f);
                tmp = layoutRadius / 2f - cWidth / 2 - mPadding * ((1.18f - f) / 0.18f);

            } else {
                cWidth = (int) (layoutRadius * RADIO_DEFAULT_CHILD_DIMENSION);
                tmp = layoutRadius / 2f - cWidth / 2 - mPadding;
            }


            // tmp cosa 即menu item中心点的横坐标
            left = getMeasuredWidth()
                    / 2
                    + (int) Math.round(tmp
                    * Math.cos(Math.toRadians(mStartAngle)) - 1 / 2f
                    * cWidth);
            // tmp sina 即menu item的纵坐标
            top = layoutRadius
                    / 2
                    + (int) Math.round(tmp
                    * Math.sin(Math.toRadians(mStartAngle)) - 1 / 2f
                    * cWidth);

            child.layout(left, top, left + cWidth, top + cWidth);
            if (mStartAngle > 150 && mStartAngle <= 210 && mOnMenuItemClickListener != null && position != i) {
                mOnMenuItemClickListener.scrollToChild(child, i);
                position = i;
            }
            if (mStartAngle > 120 && mStartAngle <= 240) {
                String color = mItemShadeColors[i - 1].substring(mItemShadeColors[i - 1].indexOf("#") + 1);
                child.setBackground(getDrawable(color, (int) ((60 - Math.abs(180 - mStartAngle)) / 10 * 4), mItemBkgIds[i - 1]));
            } else {
                child.setBackgroundResource(mItemBkgIds[i - 1]);
            }
            // 叠加尺寸
            mStartAngle += angleDelay;
        }

        // 找到中心的view，如果存在设置onclick事件
        View cView = findViewById(R.id.id_circle_menu_item_center);
        if (cView != null) {
            cView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mOnMenuItemClickListener != null) {
                        mOnMenuItemClickListener.itemCenterClick(v);
                    }
                }
            });
            // 设置center item位置
            int cl = layoutRadius / 2;
            int cr = cl + cView.getMeasuredWidth();
            cView.layout(cl, cl - cView.getMeasuredWidth() / 2, cr, cr);
        }
    }

    private float getMultiple(int r) {

        return 1f + ((float) r) * MAX_SIZE;
    }

    /**
     * 动态生成item的背景
     *
     * @param color 背影基色 “ffffff”
     * @param width 背影的宽度
     * @param id    背景中间部分的资源id
     * @return 生成的背景
     */
    public Drawable getDrawable(String color, int width, int id) {
        Drawable[] drawables = new Drawable[width + 1];
        int t = 2;
        for (int i = 0; i < drawables.length - 1; i++) {
            drawables[i] = getShapeDrawable("#" + (t < 16 ? ("0" + Integer.toHexString(t)) : Integer.toHexString(t)) + color);
            t += i / 4;
        }
        GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(id);
        drawables[drawables.length - 1] = drawable;

        return new LayerDrawable(drawables);
    }

    private Drawable getShapeDrawable(String color) {
        GradientDrawable gradientDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.item_bkg_circle_menu);
        gradientDrawable.setColor(transToInt(color));
        return gradientDrawable;
    }

    private static int transToInt(String s) {
        return Color.parseColor(s);
    }

    /**
     * 获得默认该layout的尺寸
     */
    private int getDefaultWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    /**
     * 记录上一次的x，y坐标
     */
    private float mLastX;
    private float mLastY;

    /**
     * 自动滚动的Runnable
     */
    private AutoFlingRunnable mFlingRunnable;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mLastX = x;
                mLastY = y;
                mDownTime = System.currentTimeMillis();
                mTmpAngle = 0;
                completed = true;
                // 如果当前已经在快速滚动
                if (isFling) {
                    // 移除快速滚动的回调
                    removeCallbacks(mFlingRunnable);
                    isFling = false;
                    return true;
                }

                break;
            case MotionEvent.ACTION_MOVE:


                //  获得开始的角度
                float start = getAngle(mLastX, mLastY);
                //获得当前的角度
                float end = getAngle(x, y);

                // 如果是一、四象限，则直接end-start，角度值都是正值
                if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
                    mStartAngle += end - start;
                    mTmpAngle += end - start;
                } else
                // 二、三象限，色角度值是付值
                {
                    mStartAngle += start - end;
                    mTmpAngle += start - end;
                }
                // 重新布局
                requestLayout();

                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:

                // 计算，每秒移动的角度
                float anglePerSecond = mTmpAngle * 1000
                        / (System.currentTimeMillis() - mDownTime);

                // 如果达到该值认为是快速移动
                if (Math.abs(anglePerSecond) > mFlingableValue && !isFling) {
                    // post一个任务，去自动滚动
                    post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));

                    return true;
                }
                autoFlingOver();

                // 如果当前旋转角度超过NOCLICK_VALUE屏蔽点击
                if (Math.abs(mTmpAngle) > NOCLICK_VALUE) {
                    return true;
                }

                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 根据触摸的位置，计算角度
     */
    private float getAngle(float xTouch, float yTouch) {
        double x = xTouch - (mRadius / 2d);
        double y = yTouch - (mRadius / 2d);
        return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
    }

    /**
     * 根据当前位置计算象限
     */
    private int getQuadrant(float x, float y) {
        int tmpX = (int) (x - mRadius / 2);
        int tmpY = (int) (y - mRadius / 2);
        if (tmpX >= 0) {
            return tmpY >= 0 ? 4 : 1;
        } else {
            return tmpY >= 0 ? 3 : 2;
        }

    }

    /**
     * 自动滚动的任务
     */
    private class AutoFlingRunnable implements Runnable {

        private float angelPerSecond;

        AutoFlingRunnable(float velocity) {
            this.angelPerSecond = velocity;
        }

        public void run() {
            // 如果小于20,则停止
            if ((int) Math.abs(angelPerSecond) < 10) {
                isFling = false;
                autoFlingOver();
                return;
            }
            isFling = true;
            // 不断改变mStartAngle，让其滚动，/30为了避免滚动太快
            mStartAngle += (angelPerSecond / 40);
            // 逐渐减小这个值
            angelPerSecond /= 1.0222F;
            postDelayed(this, 20);

            // 重新布局
            requestLayout();
        }
    }

    private void autoFlingOver() {
        for (Integer i :
                itemsAngle.keySet()) {
            if (Math.abs(itemsAngle.get(i) - 180) < 30) {
                target = i;
                new AutoAccelerateRunnable(itemsAngle.get(i) > 180 ? -1 : 1).run();
                return;
            }

        }
    }


    /**
     * 加速滚动
     */
    private class AutoAccelerateRunnable implements Runnable {

        private float angelPerSecond = 20;

        /**
         * 旋转方向，1顺时针，-1逆时针
         */
        private int direction = 1;

        AutoAccelerateRunnable(int direction) {
            this.direction = direction;
            angelPerSecond *= direction;
            completed = false;
        }

        public void run() {
            if (completed) {
                return;
            } else {
                completed = false;
            }
            if (Math.abs(itemsAngle.get(target) - 180) <= 10) {
                int angle = 180 - 60 * (target - 1);
                if (angle < 0) {
                    angle += 360;
                }
                mStartAngle = angle;
                requestLayout();
                return;
            }

            // 不断改变mStartAngle，让其滚动，/30为了避免滚动太快
            mStartAngle += (angelPerSecond / 30);
            // 逐渐增加这个值
            angelPerSecond += 20 * direction;
            postDelayed(this, 10);

            // 重新布局
            requestLayout();
        }
    }

    /**
     * MenuItem的点击事件接口
     */
    public interface OnMenuItemClickListener {
        void itemClick(View view, int pos);

        void itemCenterClick(View view);

        void scrollToChild(View view, int position);
    }

    /**
     * MenuItem的点击事件接口
     */
    private OnMenuItemClickListener mOnMenuItemClickListener;

    /**
     * 设置MenuItem的点击事件接口
     */
    public void setOnMenuItemClickListener(
            OnMenuItemClickListener mOnMenuItemClickListener) {
        this.mOnMenuItemClickListener = mOnMenuItemClickListener;
        setEvent();
    }
}
