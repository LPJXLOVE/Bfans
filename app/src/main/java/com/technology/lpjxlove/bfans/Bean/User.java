package com.technology.lpjxlove.bfans.Bean;


import cn.bmob.v3.BmobUser;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
public class User extends BmobUser {
    private int LevelCount;//用户等级
    private String AvatarUrl;//用户头像的URL
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getLevelCount() {
        return LevelCount;
    }

    public void setLevelCount(int levelCount) {
        LevelCount = levelCount;
    }

    public String getAvatarUrl() {
        return AvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        AvatarUrl = avatarUrl;
    }
}
