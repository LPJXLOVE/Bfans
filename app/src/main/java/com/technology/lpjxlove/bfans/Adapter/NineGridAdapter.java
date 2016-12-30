package com.technology.lpjxlove.bfans.Adapter;

/**
 * Created by LPJXLOVE on 2016/4/4.
 */

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * 适配器
 */
public abstract class NineGridAdapter {
    protected Context context;
    protected List list;

    public NineGridAdapter(Context context) {
        this.context = context;
    }

    public NineGridAdapter( List list) {
        this.list = list;
    }

    public abstract int getCount();

    public abstract String getUrl(int positopn);

    public abstract Object getItem(int position);

    public abstract long getItemId(int position);

    public abstract View getView(int i, View view);
}
