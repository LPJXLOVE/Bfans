package com.technology.lpjxlove.bfans.MVP;

import com.technology.lpjxlove.bfans.UI.CircleDetailActivity;
import com.technology.lpjxlove.bfans.UI.MainActivity;

import dagger.Component;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
@Component(modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
