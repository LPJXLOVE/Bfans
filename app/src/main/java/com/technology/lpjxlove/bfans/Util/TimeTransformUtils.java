package com.technology.lpjxlove.bfans.Util;

import android.util.Log;

import java.util.Calendar;

import java.util.Locale;

/**
 * Created by LPJXLOVE on 2016/9/8.
 */
public class TimeTransformUtils {


    //2016-10-09-下午15:36
    public static String TimeTransform(String date) {
        int years = Integer.valueOf(date.substring(0, 4));
        int months = Integer.valueOf(date.substring(5, 7));
        int days = Integer.valueOf(date.substring(8, 10));
        int hours = Integer.valueOf(date.substring(11, 13));
        Log.i("test",""+date);
        int minutes = Integer.valueOf(date.substring(14, 16));
        /*int years = Integer.valueOf(date.substring(0, 4));
        int months = Integer.valueOf(date.substring(5, 7));
        int days = Integer.valueOf(date.substring(8, 10));
        int hours = Integer.valueOf(date.substring(13, 15));
        int minutes = Integer.valueOf(date.substring(16, 18));*/
        Log.i("test", "TimeTransform: " + years + "" + months + "" + days + "" + hours + "" + minutes);
        Calendar c = Calendar.getInstance(Locale.CHINA);
        int y = c.get(Calendar.YEAR);
        int M = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DATE);
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);
        Log.i("test", "TimeTransform: " + y + "" + M + "" + d + "" + h + "" + m);
        if (years != y) {//年
            return date.substring(0,10);
        } else {
            if (months == M) {//月


                if (days == d) {//日
                    if (hours == h) {

                        if (m - minutes < 5) {//分钟
                            return "刚刚发布";

                        } else {
                            int i=m-minutes;
                            return String.valueOf(new StringBuilder(String.valueOf(i)).append("分钟前"));

                        }


                    } else {
                        int i=h-hours;
                        return String.valueOf(new StringBuilder(String.valueOf(i)).append("小时前"));
                    }


                } else {
                    int i=d-days;
                    return String.valueOf(new StringBuilder(String.valueOf(i)).append("天前"));
                }


            } else {
                int i=M-months;
                //当为相邻两个月
                if (i==1){
                    int day;
                    if (months==1||months==3||months==5||months==7||months==8||months==10||months==12){
                        day=31-(days-d);
                    }else if (months==2){
                        int ds;
                        //当为闰年时
                        if (((years % 4 == 0) && (years % 100 != 0)) || (years % 400 == 0)) {
                            ds=29;
                        } else {//为平年时
                            ds=28;
                        }
                        day=ds-(days-d);
                    }else {
                        day=30-(days-d);
                    }

                    return  String.valueOf(new StringBuilder(String.valueOf(day)).append("天前"));


                }

                return String.valueOf(new StringBuilder(String.valueOf(i)).append("个月前"));
            }

        }
    }


}
