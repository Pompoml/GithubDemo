/*
 * FileName:	ReleaseViewManager.java
 * Copyright:	kyson
 * Author: 		kysonX
 * Description:	<文件描述>
 * History:		2014-11-20 1.00 初始版本
 *//*

package com.ancun.mainactivity.ui;

import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

import com.ancun.mainactivity.doub.HandView;
import com.ancun.mainactivity.doub.LineView;


*/
/**
 * 视图总体的管理<Br>
 * API start and end control the {@link WindowManager}<Br>
 *
 * @author kysonX
 *//*

public class HandLineWindow {
    // 当前的悬浮窗是否正在运行
    private boolean mIsRunning = false;

    private static HandLineWindow sInstance;

    private Context mContext;

    private WindowManager mWindowManager;

    // which can control ballons
    private HandView mHandView;
    // which moved base on handview
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

    */
/**
     * start show views ,actually only handview
     *//*

    public void start() {
        Log.i("kyson", "Start HandLineWindow");
        end();
        mHandView = new HandView(mContext);
        mLineView = new LineView(mContext);
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        int y = ViewUtil.getStatusBarHeight(mContext) + 72;
        int x = mWindowManager.getDefaultDisplay().getWidth() * 3 / 4;
        mHandView.attachToWindow(x, y);
        mLineView.attachToWindow(x, y, mHandView.getContentWidth() / 2);
        setupListener();
        mIsRunning = true;
    }

    */
/**
     * release view from {@link WindowManager}
     *//*

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
                if (mHandView != null) {
                    mHandView.backToTop();
                }
                */
/*if (canFly && mBalloonGroup != null) {
                    mBalloonGroup.startFly();
                }*//*

            }
        });


    }


    */
/**
     * @return 返回 mIsRunning
     *//*

    public boolean isRunning() {
        return mIsRunning;
    }

    */
/**
     * @param mIsRunning
     *//*

    public void setIsRunning(boolean mIsRunning) {
        this.mIsRunning = mIsRunning;
    }
}
*/
