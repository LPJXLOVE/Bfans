package com.technology.lpjxlove.bfans.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import cn.bmob.push.PushConstants;

/**
 * Created by LPJXLOVE on 2017/1/12.
 */

public class MyPushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            Log.d("test", "客户端收到推送内容："+intent.getStringExtra("msg"));
        }
        Toast.makeText(context, "来了", Toast.LENGTH_SHORT).show();
    }
}
