package com.technology.lpjxlove.bfans;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.technology.lpjxlove.bfans.Repository.DaggerRepositoryComponent;
import com.technology.lpjxlove.bfans.Repository.RepositoryComponent;
import com.technology.lpjxlove.bfans.Repository.RepositoryModule;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import hugo.weaving.DebugLog;


/**
 * Created by LPJXLOVE on 2016/9/9.
 */
public class MyApplication extends Application {
    private RepositoryComponent mRepositoryComponent;
    @DebugLog
    @Override
    public void onCreate() {

        if (BuildConfig.DEBUG) {
          /*  StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());*/
        }
        super.onCreate();
        Bmob.initialize(this, getString(R.string.BmobAppID));
        Fresco.initialize(this);
        BmobInstallation.getCurrentInstallation().save();

        LeakCanary.install(this);
        CrashHandler.getInstance().init(this);
        CrashReport.initCrashReport(getApplicationContext(),"c3e70333f3",true);

        mRepositoryComponent = DaggerRepositoryComponent.builder()
                .repositoryModule(new RepositoryModule())
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();


    }

    public RepositoryComponent getRepositoryComponent() {
        return mRepositoryComponent;
    }
}
