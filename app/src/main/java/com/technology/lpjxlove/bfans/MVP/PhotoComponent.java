package com.technology.lpjxlove.bfans.MVP;

import com.technology.lpjxlove.bfans.UI.AlbumActivity;

import dagger.Component;

/**
 * Created by LPJXLOVE on 2016/9/27.
 */
@Component(modules = MainModule.class)
public interface PhotoComponent {
    void inject(AlbumActivity albumActivity);
}
