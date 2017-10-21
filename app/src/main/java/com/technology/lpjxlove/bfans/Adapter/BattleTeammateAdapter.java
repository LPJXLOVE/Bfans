package com.technology.lpjxlove.bfans.Adapter;

import android.content.Context;
import android.net.Uri;
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
import com.technology.lpjxlove.bfans.Bean.BattleJoinEntity;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.Util.RankTransFormUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by LPJXLOVE on 2016/8/15.
 */
public class BattleTeammateAdapter extends BaseAdapter<BattleJoinEntity,BattleTeammateAdapter.MyHolder> {
    private Action1<Integer> ItemClick;
    private Context context;

    public BattleTeammateAdapter(Context context, List<BattleJoinEntity> data,Action1<Integer> itemClick) {
        super(data);
        this.context=context;
        this.ItemClick=itemClick;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.teammate_item,null);
        return new MyHolder(view,ItemClick);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        User user=data.get(position).getJoinInPeople();
        if (user.getAvatarUrl()!=null){
            String path=user.getAvatarUrl();
            final Uri uri = Uri.parse(path);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(200, 200))
                    .build();
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setOldController(holder.ivAvatar.getController())
                    .setImageRequest(request)
                    .build();
            holder.ivAvatar.setController(draweeController);
        }else {
            holder.ivAvatar.setBackgroundResource(R.drawable.ic_person_black_24dp);
        }
        holder.tvRank.setText(RankTransFormUtils.RankTranFormToName(context,user.getLevelCount()));





    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected boolean areItemsTheSame(BattleJoinEntity oldItem, BattleJoinEntity newItem) {
        return  oldItem.getObjectId().equals(newItem.getObjectId());
    }

    @Override
    protected boolean areContentsTheSame(BattleJoinEntity oldItem, BattleJoinEntity newItem) {
        return  oldItem.equals(newItem);
    }



    static class MyHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivAvatar;
        TextView tvRank;
        RelativeLayout relativeLayout_item;
        public MyHolder(View itemView, final Action1<Integer> itemClick) {
            super(itemView);
            ivAvatar= (SimpleDraweeView) itemView.findViewById(R.id.iv_avatar);
            tvRank= (TextView) itemView.findViewById(R.id.tv_nick);
            relativeLayout_item= (RelativeLayout) itemView.findViewById(R.id.relative_layout_item);
            relativeLayout_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Observable<Integer> observable=Observable.create(new Observable.OnSubscribe<Integer>() {
                        @Override
                        public void call(Subscriber<? super Integer> subscriber) {
                            itemClick.call(getAdapterPosition());
                        }
                    });
                    observable.subscribe(itemClick);
                }
            });

        }
    }
}
