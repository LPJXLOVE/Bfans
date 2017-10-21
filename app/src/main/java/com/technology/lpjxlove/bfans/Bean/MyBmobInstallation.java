package com.technology.lpjxlove.bfans.Bean;

import android.content.Context;

import cn.bmob.v3.BmobInstallation;

/**
 * Created by LPJXLOVE on 2017/1/12.
 * 推送相关
 */

public class MyBmobInstallation extends BmobInstallation {
    private String uid;

    public MyBmobInstallation() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
