package com.technology.lpjxlove.bfans.Adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LPJXLOVE on 2016/12/26.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> viewSparseArray;
    private View view;


    public ViewHolder(View itemView) {
        super(itemView);
        viewSparseArray = new SparseArray<>();
        view = itemView;
    }

    public static ViewHolder get(Context context, @LayoutRes int Id) {
        View view = LayoutInflater.from(context).inflate(Id, null);
        return new ViewHolder(view);
    }

    public static ViewHolder get(ViewGroup parent, @LayoutRes int Id, boolean attachToRoot) {
        View view = LayoutInflater.from(parent.getContext()).inflate(Id, parent, attachToRoot);
        return new ViewHolder(view);
    }

    /**
     * 通过Id查找View
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewById(@IdRes int id) {
        View childView = viewSparseArray.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewSparseArray.put(id, childView);
        }
        return (T) childView;
    }

}
