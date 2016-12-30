package com.technology.lpjxlove.bfans.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technology.lpjxlove.bfans.Interface.ItemClickListener;
import com.technology.lpjxlove.bfans.R;

import java.util.List;

/**
 * Created by LPJXLOVE on 2016/9/11.
 */
public class SelectObjectAdapter extends RecyclerView.Adapter<SelectObjectAdapter.MyHolder> {
    private String[] strings;
    private ItemClickListener itemClickListener;
    public SelectObjectAdapter(String[] strings) {
        this.strings = strings;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.select_object_item,null);
        return new MyHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
    holder.tv_name.setText(strings[position]);
    }

    @Override
    public int getItemCount() {
        return strings.length;
    }

    static class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_name;
        private ItemClickListener listener;
        public MyHolder(View itemView,ItemClickListener listener) {
            super(itemView);
            this.listener=listener;
            tv_name= (TextView) itemView.findViewById(R.id.tv_item);
            tv_name.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.OnItemClick(getAdapterPosition(),v);
        }
    }
}
