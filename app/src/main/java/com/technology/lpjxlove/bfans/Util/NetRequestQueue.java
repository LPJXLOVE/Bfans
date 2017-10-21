package com.technology.lpjxlove.bfans.Util;

import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;

/**
 * Created by LPJXLOVE on 2017/1/17.
 * 网络请求队列,用于解决短时间内多次加载网络问题
 */

public class NetRequestQueue {

    private static SparseIntArray queue = new SparseIntArray();

    public static boolean hasRequest(int taskId, int ways) {
        if (queue.get(taskId) == 0) {
            queue.put(taskId, ways);
            return false;
        }

        return true;
    }

    public static boolean hasRequest(int taskId) {
        if (queue.get(taskId) == 0) {
            queue.put(taskId, Integer.MAX_VALUE);
            return false;
        }

        return true;
    }


    public static void removeQueue(int taskId){
        queue.delete(taskId);
    }





}

