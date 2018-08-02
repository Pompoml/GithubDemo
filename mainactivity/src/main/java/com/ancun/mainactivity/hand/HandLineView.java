package com.ancun.mainactivity.hand;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ancun.mainactivity.R;
import com.ancun.mainactivity.ui.ViewUtil;


public class HandLineView extends FrameLayout {

    private Context mContext;

    private Paint mLinePaint;

    private Bitmap mHandGrasp;

    private float mViewX;
    private float mViewY;

    private float mRawX;
    private float mRawY = 700;

    public HandLineView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public HandLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public HandLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mLinePaint = new Paint();
        mLinePaint.setStrokeWidth(30f);
        mLinePaint.setColor(getResources().getColor(R.color.balloonperformer_common_red));
        mLinePaint.setAntiAlias(true);

        mHandGrasp = BitmapFactory.decodeResource(context.getResources(), R.drawable.grasp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 设置wrap_content的默认宽 / 高值
        // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        int mWidth = 400;
        int mHeight = 800 + mHandGrasp.getHeight();

        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, mHeight);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, 700, mLinePaint);
        int left = getWidth() / 2 - mHandGrasp.getWidth() / 2;
        canvas.drawBitmap(mHandGrasp, left, mRawY, null);

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
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                break;
        }
        return true;
    }


}
