package com.ancun.mainactivity.doub;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.ancun.mainactivity.R;
import com.ancun.mainactivity.ui.ViewUtil;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;


/**
 * 拉手的视图
 *
 * @author Kyson
 */
public class HandView extends View {

    private Context mContext;

    private WindowManager.LayoutParams mLp;

    private WindowManager mWindowManager;

    private onLooseListener mOnLooseListener;

    private onHandMovedListener mOnHandMovedListener;

    private Paint mPaint;

    private Bitmap mHandGrasp;

    private Bitmap mHandLoose;

    private int mOriY;

    private float mViewX;
    private float mViewY;

    private float mRawX;
    private float mRawY;

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
        this.mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mHandGrasp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.grasp);
        mHandLoose = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.loose);
    }

    public void attachToWindow(int x, int y) {
        if (this.getParent() != null) {
            return;
        }
        mLp = new WindowManager.LayoutParams();
        mLp.type = LayoutParams.TYPE_SYSTEM_ALERT;
        mLp.format = PixelFormat.RGBA_8888;
        mLp.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE;
        mLp.gravity = Gravity.LEFT | Gravity.TOP;
        mLp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLp.x = x;
        mOriY = y;
        mLp.y = mOriY;
        mWindowManager.addView(this, mLp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mHandGrasp, 0, 0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                ViewUtil.measureSize(widthMeasureSpec, mHandGrasp.getWidth()),
                ViewUtil.measureSize(heightMeasureSpec, mHandGrasp.getHeight()));
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

    private void onActionLoose() {
        if (mOnLooseListener != null) {
            int len = ViewUtil.getStatusBarHeight(mContext) + 72;
            mOnLooseListener.onLoose(mLp.y > len * 1.8f, mLp.x, mLp.y);
        }
    }

    public void backToTop() {
        postInvalidate();
        ValueAnimator animation = ValueAnimator.ofInt(mLp.y, mOriY);
        animation.setDuration(500);
        animation.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int y = (Integer) animation.getAnimatedValue();
                updateViewPosition(mLp.x, y);
            }
        });
        animation.start();
    }

    public void release() {
        if (this.getParent() != null) {
            mWindowManager.removeView(this);
        }
    }

    private void updateViewPosition(int x, int y) {
        if (this.getParent() == null) {
            return;
        }
        //mLp.x = x;
        mLp.y = y;
        mWindowManager.updateViewLayout(this, mLp);
    }

    private void updateViewPositionWithNotify(int x, int y) {
        updateViewPosition(x, y);
        if (mOnHandMovedListener != null) {
            mOnHandMovedListener.onHandMoved(mLp.x, mLp.y);
        }
    }

    public interface onLooseListener {
        void onLoose(boolean canFly, int x, int y);
    }

    public interface onHandMovedListener {
        void onHandMoved(int x, int y);
    }

    public void setOnLooseListener(onLooseListener mOnLooseListener) {
        this.mOnLooseListener = mOnLooseListener;
    }

    public void setOnHandMovedListener(onHandMovedListener mOnHandMovedListener) {
        this.mOnHandMovedListener = mOnHandMovedListener;
    }

    public int getContentWidth() {
        return mHandGrasp.getWidth();
    }

}
