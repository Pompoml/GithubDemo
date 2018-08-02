/*
package com.ancun.mainactivity.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.ancun.mainactivity.R;
import com.nineoldandroids.view.ViewHelper;


*/
/**
 * 拉手的视图
 *
 * @author Kyson
 *//*

public class HandView extends View {

    private Context mContext;

    private WindowManager mWindowManager;

    private WindowManager.LayoutParams mLayoutParams;

    private onLooseListener mOnLooseListener;


    private Bitmap mHandGrasp;

    private int mHandY;

    private float mViewX;
    private float mViewY;

    private float mRawX;
    private float mRawY;


    private Paint mLinePaint;

    private float mLineLength;

    private float mOriY;

    public HandView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public HandView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        mHandGrasp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.grasp);

        mLinePaint = new Paint();
        mLinePaint.setStrokeWidth(30f);
        mLinePaint.setColor(getResources().getColor(R.color.balloonperformer_common_red));
        mLinePaint.setAntiAlias(true);
        mLineLength = mWindowManager.getDefaultDisplay().getHeight();
    }

    */
/**
     * attach handview to {@link WindowManager}
     *
     * @param y
     *//*

    public void attachToWindow(int x, int y) {
        if (this.getParent() != null) {
            return;
        }

        mLayoutParams = new WindowManager.LayoutParams();

        if (checkAlertWindowPermisson(mContext)) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        }

        // mLayoutParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.width = LayoutParams.MATCH_PARENT;
        mLayoutParams.height = LayoutParams.MATCH_PARENT;
        mLayoutParams.x = x;
        mLayoutParams.y = y;

        mHandY = y;
        mWindowManager.addView(this, mLayoutParams);

        mOriY = -mLineLength + y + ViewUtil.getStatusBarHeight(mContext);

        updateLineView(x, y, getContentWidth() / 2);
    }

    public void updateLineView(int x, int y, int offsetX) {
        ViewHelper.setTranslationX(this, x + offsetX);
        ViewHelper.setTranslationY(this, y - mLineLength + ViewUtil.getStatusBarHeight(mContext));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mHandGrasp, 0, 0, null);
        canvas.drawLine(0, 0, 0, mLineLength, mLinePaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(ViewUtil.measureSize(widthMeasureSpec, mHandGrasp.getWidth()),
                ViewUtil.measureSize(heightMeasureSpec, (int) (mHandGrasp.getHeight())));
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                postInvalidate();
                mViewX = event.getX();
                mViewY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                postInvalidate();
                mRawX = event.getRawX();
                mRawY = event.getRawY() - ViewUtil.getStatusBarHeight(mContext);
                updateViewPositionWithNotify((int) (mRawX - mViewX), (int) (mRawY - mViewY));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                onActionUp(event);
                break;
        }
        return true;
    }

    private void onActionUp(MotionEvent event) {
        postInvalidate();
        onActionLoose();
    }

    */
/**
     * on hand loose
     *//*

    private void onActionLoose() {
        if (mOnLooseListener != null) {
            int len = ViewUtil.getStatusBarHeight(mContext) + 72;

            backToTop();

            mOnLooseListener.onLoose(mLayoutParams.y > len * 1.8f, mLayoutParams.x, mLayoutParams.y);
        }
    }

    */
/**
     * put the hand to top
     *//*

    public void backToTop() {
        postInvalidate();
        ValueAnimator animation = ValueAnimator.ofInt(mLayoutParams.y, mHandY);
        animation.setDuration(250);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int y = (Integer) animation.getAnimatedValue();
                updateViewPosition(mLayoutParams.x, y);
            }
        });
        animation.start();
    }

    */
/**
     * 释放资源
     *//*

    public void release() {
        if (this.getParent() != null) {
            mWindowManager.removeView(this);
        }
    }

    */
/**
     * update {@link HandView} positon when moved auto
     *//*

    private void updateViewPosition(int x, int y) {
        if (this.getParent() == null) {
            return;
        }
        //mLayoutParams.x = x;
        mLayoutParams.y = y;
        mWindowManager.updateViewLayout(this, mLayoutParams);
    }

    */
/**
     * when moved by touch,notify {@link HandView} moved event
     *
     * @param x
     * @param y
     *//*

    private void updateViewPositionWithNotify(int x, int y) {
        updateViewPosition(x, y);
    }


    public interface onLooseListener {
        void onLoose(boolean canFly, int x, int y);
    }

    */
/**
     * @param mOnLooseListener
     *//*

    public void setOnLooseListener(onLooseListener mOnLooseListener) {
        this.mOnLooseListener = mOnLooseListener;
    }

    */
/**
     * get content width,actually the bitmap width
     *
     * @return
     *//*

    public int getContentWidth() {
        return mHandGrasp.getWidth();
    }

    private boolean checkAlertWindowPermisson(Context context) {
        String packname = context.getPackageName();
        return (PackageManager.PERMISSION_GRANTED == context.getPackageManager().checkPermission("android.permission.SYSTEM_ALERT_WINDOW", packname));
    }
}
*/
