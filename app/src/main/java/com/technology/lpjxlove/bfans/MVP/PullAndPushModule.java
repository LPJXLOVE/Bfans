package com.technology.lpjxlove.bfans.MVP;

import com.technology.lpjxlove.bfans.MyApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
@Module
public class PullAndPushModule {

    private MyApplication myApplication;
    private PullAndPushView pullAndPushView;

    public PullAndPushModule(MyApplication myApplication,PullAndPushView pullAndPushView) {
       this.myApplication=myApplication;
        this.pullAndPushView=pullAndPushView;
    }


    @Provides
    MyApplication provideMyApplication(){
        return myApplication;
    }
    @Provides
    PullAndPushView providePullAndPushView(){
        return pullAndPushView;
    }


}
