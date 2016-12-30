package com.technology.lpjxlove.bfans.Service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.support.annotation.Nullable;


public class KeepAliveService extends Service {
    public static final int NOTIFICATION_ID = 1234;
    public static KeepAliveService keepAliveService;
    public volatile static KeepAliveService Instance;


    public static KeepAliveService getInstance(){
        if (Instance==null){
            synchronized (KeepAliveService.class){
                if (Instance==null){
                    Instance=new KeepAliveService();
                }
            }
        }
        return Instance;
    }

    public KeepAliveService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent i=new Intent(this,InnerService.class);
        i.setClassName("com.technogy.lpjxlove.banfs.Service","InnerService");
        startService(i);
        return Service.START_STICKY;
    }


    public static class InnerService extends Service {
        private KeepAliveService mkeepAliveService;
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            mkeepAliveService=KeepAliveService.getInstance();
            if (mkeepAliveService==null){
                throw new NullPointerException("keepAliveService is null");
            }
            setForeground(mkeepAliveService,this);
            return super.onStartCommand(intent, flags, startId);
        }


        public void setForeground(final KeepAliveService keepAliveService, final InnerService innerService) {
            if (keepAliveService != null) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {


                    keepAliveService.startForeground(NOTIFICATION_ID, new Notification());


                } else {

                    keepAliveService.startForeground(NOTIFICATION_ID, new Notification());
                    if (innerService != null) {
                        innerService.startForeground(NOTIFICATION_ID, new Notification());
                        innerService.stopSelf();
                    }
                }
            }
        }


    }


}
