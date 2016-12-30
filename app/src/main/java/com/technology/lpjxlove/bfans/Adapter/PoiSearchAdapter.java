package com.technology.lpjxlove.bfans.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.technology.lpjxlove.bfans.R;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by LPJXLOVE on 2016/9/23.
 */
public class PoiSearchAdapter extends RecyclerView.Adapter<PoiSearchAdapter.MyHolder>  {
    private List<PoiItem> poiItemList;
    private Action1<PoiItem> action1;

    public PoiSearchAdapter(List<PoiItem> poiItemList, Action1<PoiItem> action1) {
        this.poiItemList = poiItemList;
        this.action1 = action1;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder myHolder;
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.point_item_layout,null,false);
        myHolder=new MyHolder(view,action1,poiItemList);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.tv_describe.setText(poiItemList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return poiItemList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        TextView tv_describe;
        RelativeLayout Rl_item;
        Observable<PoiItem> observable;
        public MyHolder(View itemView, final Action1<PoiItem> action1, final List<PoiItem> data) {
            super(itemView);
            tv_describe= (TextView) itemView.findViewById(R.id.tv_describe);
            Rl_item = (RelativeLayout) itemView.findViewById(R.id.RL_item);
            Rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   observable=Observable.create(new Observable.OnSubscribe<PoiItem>() {
                       @Override
                       public void call(Subscriber<? super PoiItem> subscriber) {
                          action1.call(data.get(getAdapterPosition()));
                       }
                   });
                    observable.subscribe(action1);

                }
            });
        }
    }
}
