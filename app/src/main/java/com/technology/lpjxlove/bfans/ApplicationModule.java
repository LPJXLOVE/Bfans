package com.technology.lpjxlove.bfans;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
@Module
public class ApplicationModule {
    private final Context mContext;

    public ApplicationModule(Context mContext) {
        this.mContext = mContext;
    }

    @Singleton
    @Provides
    Context getApplicationContext(){
        return mContext;
    }


}
