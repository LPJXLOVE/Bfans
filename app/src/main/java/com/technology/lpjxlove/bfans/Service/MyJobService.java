package com.technology.lpjxlove.bfans.Service;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {
    public MyJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {


        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
