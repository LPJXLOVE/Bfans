package com.technology.lpjxlove.bfans.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.amap.api.services.help.Tip;
import com.technology.lpjxlove.bfans.R;

import java.util.List;

/**
 * Created by LPJXLOVE on 2016/9/23.
 */
public class InputTipAdapter extends BaseAdapter {
    private Context context;
    private List<Tip> data;

    public InputTipAdapter(Context context, List<Tip> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
          convertView= LayoutInflater.from(context).inflate(R.layout.input_tip_item_layout,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_address= (TextView) convertView.findViewById(R.id.tv_address);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_title.setText(data.get(position).getName());
        viewHolder.tv_address.setText(data.get(position).getAddress());
        return convertView;
    }
    static class ViewHolder{
        TextView tv_title;
        TextView tv_address;
    }
}
