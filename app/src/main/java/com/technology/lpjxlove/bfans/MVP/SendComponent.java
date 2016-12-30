package com.technology.lpjxlove.bfans.MVP;

import com.technology.lpjxlove.bfans.UI.AddBattleActivity;
import com.technology.lpjxlove.bfans.UI.AddCircleActivity;
import com.technology.lpjxlove.bfans.UI.AlbumActivity;
import com.technology.lpjxlove.bfans.UI.BattleDetailActivity;
import com.technology.lpjxlove.bfans.UI.MyActivity;
import com.technology.lpjxlove.bfans.UI.QrScanActivity;

import dagger.Component;

/**
 * Created by LPJXLOVE on 2016/9/27.
 */
@Component(modules = SendModule.class)
public interface SendComponent {
    void inject(AddBattleActivity AddBattleActivity);
    void inject(BattleDetailActivity BattleActivity);
    void inject(MyActivity myActivity);
    void inject(AddCircleActivity circleActivity);
    void inject(QrScanActivity qrScanActivity);
}
