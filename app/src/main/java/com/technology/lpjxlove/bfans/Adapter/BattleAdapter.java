package com.technology.lpjxlove.bfans.Adapter;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.technology.lpjxlove.bfans.Bean.BattleEntity;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.Util.TimeTransformUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by LPJXLOVE on 2016/8/15.
 */
public class BattleAdapter extends BaseAdapter<BattleEntity,BattleAdapter.MyHolder> {
    private Action1<String> action1;


    public BattleAdapter(List<BattleEntity> data,Action1<String> action1) {
        super(data);
        this.action1=action1;
    }

    @Override
    protected boolean areItemsTheSame(BattleEntity oldItem, BattleEntity newItem) {
        return oldItem.getObjectId().equals(newItem.getObjectId());
    }

    @Override
    protected boolean areContentsTheSame(BattleEntity oldItem, BattleEntity newItem) {
        return oldItem.equals(newItem);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.battle_item_layout,null);
        return new MyHolder(view,action1);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        User user=data.get(position).getAuthor();
        if (user.getAvatarUrl()!=null){
            String path=user.getAvatarUrl();
            final Uri uri = Uri.parse(path);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(200, 200))
                    .build();
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setOldController(holder.iv_avatar.getController())
                    .setImageRequest(request)
                    .build();
            holder.iv_avatar.setController(draweeController);
        }else {
            holder.iv_avatar.setBackgroundResource(R.drawable.ic_person_black_24dp);
        }



        if (user.getNickName()==null){
            holder.tv_nick.setText(user.getUsername());
        }else {
            holder.tv_nick.setText(user.getNickName());
        }

        holder.tv_date.setText(TimeTransformUtils.TimeTransform(data.get(position).getCreatedAt()));
        //
        String state=data.get(position).getState();
        switch (state) {
            case "0":
                holder.tv_state.setText("招募中");
                break;
            case "1":
                holder.tv_state.setText("进行中");
                break;
            default:
                holder.tv_state.setText("已结束");
                break;
        }
        holder.tv_battleObject.setText(data.get(position).getBattleObject());
        holder.tv_battleType.setText(data.get(position).getBattleType());
        holder.tv_battle_date.setText(data.get(position).getStartDate());
        holder.tv_location.setText(data.get(position).getBattleLocation());

    }

    @Override
    public void onViewRecycled(MyHolder holder) {
        super.onViewRecycled(holder);
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_battleObject;
        TextView tv_battleType;
        TextView tv_battle_date;
        TextView tv_nick;
        TextView tv_date;
        TextView tv_state;
        TextView tv_location;
        SimpleDraweeView iv_avatar;
        CardView cardView;
        RelativeLayout item;
        public MyHolder(View itemView, final Action1<String> itemClick) {
            super(itemView);
            item= (RelativeLayout) itemView.findViewById(R.id.relative_layout_item);
            cardView= (CardView) itemView.findViewById(R.id.card_view);
            tv_nick= (TextView) itemView.findViewById(R.id.tv_nick);
            tv_date= (TextView) itemView.findViewById(R.id.tv_time);
            tv_battleType= (TextView) itemView.findViewById(R.id.tv_battle_type);
            tv_battle_date= (TextView) itemView.findViewById(R.id.tv_battle_date);
            tv_battleObject= (TextView) itemView.findViewById(R.id.tv_battle_object);
            tv_state= (TextView) itemView.findViewById(R.id.tv_state);
            tv_location= (TextView) itemView.findViewById(R.id.tv_location);
            iv_avatar= (SimpleDraweeView) itemView.findViewById(R.id.iv_avatar);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Observable<String> observable=Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            itemClick.call(String.valueOf(getAdapterPosition()));
                        }
                    });
                    observable.subscribe(itemClick);
                }
            });
        }
    }
}
