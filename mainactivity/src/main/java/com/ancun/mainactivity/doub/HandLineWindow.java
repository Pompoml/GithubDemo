
package com.ancun.mainactivity.doub;

import android.content.Context;
import android.view.WindowManager;

import com.ancun.mainactivity.ui.ViewUtil;


public class HandLineWindow {
    private boolean mIsRunning = false;

    private static HandLineWindow sInstance;

    private Context mContext;

    private WindowManager mWindowManager;

    private HandView mHandView;
    private LineView mLineView;


    public static HandLineWindow getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new HandLineWindow(context);
        }
        return sInstance;
    }

    private HandLineWindow(Context context) {
        this.mContext = context;
    }

    public void start() {

        end();
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        mHandView = new HandView(mContext);
        mLineView = new LineView(mContext);
        int y = ViewUtil.getStatusBarHeight(mContext) + 72;
        int x = mWindowManager.getDefaultDisplay().getWidth() * 3 / 4;
        mHandView.attachToWindow(x, y);
        mLineView.attachToWindow(x, y, mHandView.getContentWidth() / 2);
        setupListener();
        mIsRunning = true;
    }


    public void end() {
        if (mHandView != null) {
            mHandView.release();
            mHandView = null;
        }
        if (mLineView != null) {
            mLineView.release();
            mLineView = null;
        }
        mIsRunning = false;
    }

    private void setupListener() {
        mHandView.setOnLooseListener(new HandView.onLooseListener() {

            @Override
            public void onLoose(boolean canFly, int x, int y) {
                if (mLineView != null) {
                    mLineView.pack(true);
                }

                /*if (canFly && mBalloonGroup != null) {
                    mBalloonGroup.startFly();
                }*/
            }
        });

        mHandView.setOnHandMovedListener(new HandView.onHandMovedListener() {

            @Override
            public void onHandMoved(int x, int y) {
                if (mLineView != null) {
                    mLineView.updateLineView(x, y, mHandView.getContentWidth() / 2);
                }

            }
        });

        mLineView.setOnLinePackedListener(new LineView.OnLinePackedListener() {

            @Override
            public void onLinePacked() {
                if (mHandView != null) {
                    mHandView.backToTop();
                }

            }
        });

    }


    /**
     * @return 返回 mIsRunning
     */
    public boolean isRunning() {
        return mIsRunning;
    }

    /**
     * @param mIsRunning
     */
    public void setIsRunning(boolean mIsRunning) {
        this.mIsRunning = mIsRunning;
    }
}
