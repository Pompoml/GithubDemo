package com.ancun.mainactivity.doub;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;

import com.ancun.mainactivity.R;
import com.ancun.mainactivity.ui.ViewUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;


/**
 * 线的view<Br>
 *
 * @author Kyson
 */
public class LineView extends View {

    private Context mContext;

    private WindowManager mWindowManager;

    private FrameLayout mContainer;

    private Paint mPaint;

    private float mLineLength;

    private OnLinePackedListener mOnLinePackedListener;

    private float mOriY;

    public LineView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(15f);
        mPaint.setAntiAlias(true);
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mLineLength = mWindowManager.getDefaultDisplay().getHeight();
    }

    public void attachToWindow(int x, int y, int offsetX) {
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

        wmParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
        // 设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.FILL_PARENT;
        wmParams.height = WindowManager.LayoutParams.FILL_PARENT;

        if (mContainer == null) {
            mContainer = new FrameLayout(mContext);
        }
        // 添加容器
        if (mContainer.getParent() == null) {
            mWindowManager.addView(mContainer, wmParams);
        }

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        mOriY = -mLineLength + y + ViewUtil.getStatusBarHeight(mContext);
        // 如果view没有被加入到某个父组件中，则加入WindowManager中
        if (this.getParent() == null) {
            mContainer.addView(this, lp);
            updateLineView(x, y, offsetX);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(getResources().getColor(R.color.balloonperformer_common_blue));
        canvas.drawLine(0, 0, 0, mLineLength, mPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                ViewUtil.measureSize(widthMeasureSpec, (int) mPaint.getStrokeWidth()),
                ViewUtil.measureSize(heightMeasureSpec, (int) mLineLength));
    }

    /**
     * 绳子收起
     */
    public void pack(final boolean isNotify) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "translationY", mOriY);
        animator.setDuration(520);
        animator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {
                if (isNotify && mOnLinePackedListener != null) {
                    mOnLinePackedListener.onLinePacked();
                }
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                if (isNotify && mOnLinePackedListener != null) {
                    //mOnLinePackedListener.onLinePacked();
                }
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                if (isNotify && mOnLinePackedListener != null) {
                    //mOnLinePackedListener.onLinePacked();
                }
            }
        });
        animator.start();
    }


    public void updateLineView(int x, int y, int offsetX) {
        if (this.getParent() != null) {
            ViewHelper.setTranslationX(this, x + offsetX);
            ViewHelper.setTranslationY(this, y - mLineLength + ViewUtil.getStatusBarHeight(mContext));
        }

    }

    public void release() {
        if (mContainer != null && mContainer.getParent() != null) {
            mContainer.removeAllViews();
            mWindowManager.removeView(mContainer);
        }
    }

    public interface OnLinePackedListener {
        void onLinePacked();
    }

    public void setOnLinePackedListener(OnLinePackedListener mOnLinePackedListener) {
        this.mOnLinePackedListener = mOnLinePackedListener;
    }

}
