package com.technology.lpjxlove.bfans.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;

import com.technology.lpjxlove.bfans.Bean.BattleEntity;
import com.technology.lpjxlove.bfans.R;

import java.util.List;

/**
 * Created by LPJXLOVE on 2017/1/12.
 */

public class RecentlyBattleAdapter extends BaseTwoAdapter<BattleEntity> {


    public RecentlyBattleAdapter(List<BattleEntity> data) {
        super(data);
    }

    @Override
    void onBind(ViewHolder viewHolder, int position) {


    }

    @Override
    SparseIntArray getLayoutId() {
        SparseIntArray array=new SparseIntArray();
        array.put(0, R.layout.recently_battle_item);
        return array;
    }

}
