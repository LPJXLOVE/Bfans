package com.technology.lpjxlove.bfans.Adapter;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LPJXLOVE on 2016/10/8.
 */

public abstract class BaseAdapter<T,V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {


    protected  List<T> temp;
    protected List<T> data;

    public BaseAdapter( List<T> data) {
        this.data = data;
        this.temp=new ArrayList<>(data);
    }


    public void setData(List<T> data) {
        this.data = data;
    }
    public List<T> getData(){
        return data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    protected abstract boolean areItemsTheSame(T oldItem, T newItem);
    protected abstract boolean areContentsTheSame(T oldItem, T newItem);


    public void notifyDiff(){



        DiffUtil.DiffResult diffResult=DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return temp.size();
            }

            @Override
            public int getNewListSize() {
                return data.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return BaseAdapter.this.areItemsTheSame(temp.get(oldItemPosition), data.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return BaseAdapter.this.areContentsTheSame(temp.get(oldItemPosition), data.get(newItemPosition));
            }

            @Nullable
            @Override
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                return super.getChangePayload(oldItemPosition, newItemPosition);
            }
        });

        diffResult.dispatchUpdatesTo(this);
        temp.clear();
        temp.addAll(data);
     }



}
