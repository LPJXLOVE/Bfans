package com.technology.lpjxlove.bfans.MVP;

import com.technology.lpjxlove.bfans.MyApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
@Module
public class MainModule {

    private MyApplication myApplication;

    public MainModule(MyApplication myApplication) {
       this.myApplication=myApplication;
    }


    @Provides
    MyApplication provideMyApplication(){
        return myApplication;
    }


}
