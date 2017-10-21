package com.technology.lpjxlove.bfans.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by LPJXLOVE on 2016/12/26.
 */

public abstract class BaseTwoAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected List<T> data;
    private OnItemClickListener clickListener;

    public BaseTwoAdapter(List<T> data) {
        this.data = data;
    }

    public OnItemClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }


    /**
     * 绑定数据
     *
     * @param viewHolder
     * @param position
     */
    abstract void onBind(ViewHolder viewHolder, int position);

    /**
     * 设置layout
     *
     * @return
     */
    abstract SparseIntArray getLayoutId();


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (clickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(position);
                }
            });
        }
        onBind(holder, position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.get(parent.getContext(), getLayoutId().get(viewType));
    }


    public interface OnItemClickListener {
        void onClick(int position);
    }

}
