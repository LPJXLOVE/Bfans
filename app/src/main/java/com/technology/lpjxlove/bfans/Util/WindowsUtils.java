package com.technology.lpjxlove.bfans.Util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by LPJXLOVE on 2016/10/6.
 */

public class WindowsUtils {
    public static int getScreenWidthPixels(Context context){
        DisplayMetrics metric=new DisplayMetrics();
        WindowManager m= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        m.getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }
    public static int getScreenHeightPixels(Context context){
        DisplayMetrics metric=new DisplayMetrics();
        WindowManager m= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        m.getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }
    private static float getScreenDensity(Context context){
        DisplayMetrics metric=new DisplayMetrics();
        WindowManager m= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        m.getDefaultDisplay().getMetrics(metric);
        return metric.density;
    }

    public static float dp2px(Context context, int dp){
        return getScreenDensity(context)*dp+0.5f;
    }
}
