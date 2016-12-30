package com.technology.lpjxlove.bfans.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by LPJXLOVE on 2016/9/11.
 */
public class BattleJoinEntity extends BmobObject {
    private User JoinInPeople;
    private BattleEntity BattleEntity;

    public com.technology.lpjxlove.bfans.Bean.BattleEntity getBattleEntity() {
        return BattleEntity;
    }

    public void setBattleEntity(com.technology.lpjxlove.bfans.Bean.BattleEntity battleEntity) {
        BattleEntity = battleEntity;
    }

    public User getJoinInPeople() {
        return JoinInPeople;
    }

    public void setJoinInPeople(User joinInPeople) {
        JoinInPeople = joinInPeople;
    }
}
