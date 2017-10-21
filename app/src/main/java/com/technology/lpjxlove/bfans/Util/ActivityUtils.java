package com.technology.lpjxlove.bfans.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.technology.lpjxlove.bfans.UI.CircleDetailActivity;
import com.technology.lpjxlove.bfans.UI.CustomView.ActSwitchAnimTool;
import com.technology.lpjxlove.bfans.UI.CustomView.PreviewPhoto.PreviewActivity;

import java.util.ArrayList;

import static com.technology.lpjxlove.bfans.R.styleable.View;

/**
 * Created by LPJXLOVE on 2016/9/7.
 */
public class ActivityUtils {

   /* public static void gotoActivity(Context context, Class<? extends Activity> activityClass){
        Intent intent=new Intent(context,activityClass);
        context.startActivity(intent);
    }*/

    public static <T extends Activity> void gotoActivity(T currentActivity, Class<? extends Activity> activityClass) {
        Intent intent = new Intent(currentActivity, activityClass);
        currentActivity.startActivity(intent);
    }


    public static <T extends Activity> void gotoPreviewActivity(T currentActivity, ArrayList<String> data,int s) {
        Intent i=new Intent(currentActivity, PreviewActivity.class);
        i.putStringArrayListExtra("image",data);
        i.putExtra("position",s);
        currentActivity.startActivity(i);
    }

    public static <T extends Activity> void gotoActivityForResult(T currentActivity
            , Class<? extends Activity> activityClass
            , int requestCode) {
        Intent intent = new Intent(currentActivity, activityClass);
        currentActivity.startActivityForResult(intent, requestCode);
    }


    public static void gotoActivityWithFlag(Context context, Class<?> activityClass, int flag) {
        Intent intent = new Intent(context, activityClass);
        intent.setFlags(flag);
        context.startActivity(intent);
    }


    public static void gotoActivityWithAnim(AppCompatActivity activity, Class<?> activityClass, android.view.View view) {
        Intent intent = new Intent(activity, activityClass);
        new ActSwitchAnimTool(activity).setAnimType(ActSwitchAnimTool.MODE_SPREAD)
                .target(view)
              /*  .setShrinkBack(true)*/
                .setmColorStart(Color.parseColor("#cddc39"))
                .setmColorEnd(Color.parseColor("#FF5777"))
                .startActivity(intent, false)
                .build();
    }


}
