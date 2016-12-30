package com.technology.lpjxlove.bfans.Repository;

import com.technology.lpjxlove.bfans.ApplicationModule;

import dagger.Component;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
@Component(modules = {RepositoryModule.class, ApplicationModule.class})
public interface RepositoryComponent {
    RepositoryManager getRepositoryManager();
}
