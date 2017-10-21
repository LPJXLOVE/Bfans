package com.technology.lpjxlove.bfans;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LPJXLOVE on 2017/1/13.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static CrashHandler INSTANCE = new CrashHandler();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    private CrashHandler() {
    }


    public static CrashHandler getInstance() {
        return INSTANCE;
    }


    public void init(Context context) {
        this.mContext = context.getApplicationContext();
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    private void phoneInfo(StringBuilder builder) throws PackageManager.NameNotFoundException {
        PackageManager manager = mContext.getPackageManager();
        PackageInfo packageInfo = manager.getPackageInfo(mContext.getPackageName()
                , PackageManager.GET_ACTIVITIES);
        builder.append("\n")
                //app版本号
                .append("App_Version: ")
                .append(packageInfo.versionName)
                .append("_")
                .append(packageInfo.versionCode)
                .append("\n")
                //sdk版本
                .append("OS Version")
                .append(Build.VERSION.RELEASE)
                .append("_")
                .append(Build.VERSION.SDK_INT)
                .append("\n")
                //手机制造商
                .append("Vendor: ")
                .append(Build.MANUFACTURER)
                .append("\n")
                //手机型号
                .append("Model: ")
                .append(Build.MODEL)
                .append("\n")
                //cpu架构
                .append("CPU ABI:")
                .append(Build.CPU_ABI);
    }


    @Override
    public void uncaughtException(final Thread thread, Throwable ex) {
        long currentTime = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(currentTime);

        final StringBuilder builder = new StringBuilder();
        builder.append("异常类型：")
                .append(ex.toString())
                .append("\n");
        StackTraceElement[] elements=ex.getStackTrace();
        for (StackTraceElement stackTraceElement:elements){
            String className=stackTraceElement.getClassName();
            if (isOwnPackage(className)){
                builder.append("异常的包名：")
                        .append(className)
                        .append("\n")
                        .append("异常的方法行：")
                        .append(stackTraceElement.getLineNumber())
                        .append("\n")
                        .append("异常的方法名：")
                        .append(stackTraceElement.getMethodName());
                break;
            }
        }
        try {
            phoneInfo(builder);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }

        Log.e(TAG, "uncaughtException: " + builder);
        final  String b= String.valueOf(builder);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Intent i=new Intent();
                i.setAction("com.technology.lpjxlove.detailcrashinfo");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("ErrorInfo",b);
                i.putExtra("flag",true);
                mContext.startActivity(i);
              //  Toast.makeText(mContext, ""+b, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

        }).start();





        //如果系统提供了默认的异常处理器，则交给系统去处理结束程序，否则由自己结束
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, ex);

        } else {
            Process.killProcess(Process.myPid());
        }

    }

    private void showErrorDialog(StringBuilder message){
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        builder.setTitle("异常详细")
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Process.killProcess(Process.myPid());

                    }
                });
    }




    /**
     * 检查包名是否是自已的包
     * @param content
     * @return
     */
    private boolean isOwnPackage(String content){
        Pattern pattern=Pattern.compile(mContext.getPackageName());
        Matcher matcher=pattern.matcher(content);
        return matcher.find();
    }


}
