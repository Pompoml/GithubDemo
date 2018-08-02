package com.ancun.mainactivity.hand;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.view.Gravity;
import android.view.WindowManager;

/**
 * desc:
 * date: 2018/1/11
 * author: ancun
 */

public class HandLineWindow {

    private Context mCtx;

    private WindowManager mWindowManager;
    private HandLineView mHandLineView;

    public HandLineWindow(Context ctx) {
        this.mCtx = ctx;

    }

    public void showWindowManager() {

        mHandLineView = new HandLineView(mCtx);

        mWindowManager = (WindowManager) mCtx.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams windowViewParams = new WindowManager.LayoutParams();
        windowViewParams.gravity = Gravity.TOP | Gravity.LEFT;
        //判断权限，选择不同的弹窗方式
        if (checkAlertWindowPermisson(mCtx)) {
            windowViewParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            //windowViewParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        } else {
            windowViewParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        }
        windowViewParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //windowViewParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        windowViewParams.format = PixelFormat.RGBA_8888;
        Point size = new Point();
        mWindowManager.getDefaultDisplay().getSize(size);

        windowViewParams.x = size.x * 2 / 3;
        windowViewParams.y = 0;

        windowViewParams.width = 400;
        windowViewParams.height = 1500;

        mWindowManager.addView(mHandLineView, windowViewParams);


    }

    public void removeWindowManager() {
        mWindowManager.removeView(mHandLineView);
    }


    /**
     * 判断是否有系统提示框权限
     *
     * @param context
     * @return
     */
    private static boolean checkAlertWindowPermisson(Context context) {
        String packname = context.getPackageName();
        return (PackageManager.PERMISSION_GRANTED == context.getPackageManager().checkPermission("android.permission.SYSTEM_ALERT_WINDOW", packname));
    }

    private int getStatusBarHeight(Context ctx) {
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = ctx.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }


}
