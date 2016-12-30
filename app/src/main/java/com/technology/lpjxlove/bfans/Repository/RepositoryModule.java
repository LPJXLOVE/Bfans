package com.technology.lpjxlove.bfans.Repository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
@Module
public class RepositoryModule {

    @Provides
    RepositoryManager getApplicationRepositoryManager(){
        return new RepositoryManager();
    }
}
