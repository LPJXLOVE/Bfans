package com.technology.lpjxlove.bfans.Util;

import android.support.v7.util.DiffUtil;

/**
 * Created by LPJXLOVE on 2016/10/4.
 */

public class DiffUtils extends DiffUtil.Callback {
    @Override
    public int getOldListSize() {
        return 0;
    }

    @Override
    public int getNewListSize() {
        return 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}
