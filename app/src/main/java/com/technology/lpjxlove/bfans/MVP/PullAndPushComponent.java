package com.technology.lpjxlove.bfans.MVP;

import com.technology.lpjxlove.bfans.UI.CircleDetailActivity;
import com.technology.lpjxlove.bfans.UI.MainActivity;

import dagger.Component;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
@Component(modules = PullAndPushModule.class)
public interface PullAndPushComponent {
    void inject(CircleDetailActivity circleDetailActivity);
}
