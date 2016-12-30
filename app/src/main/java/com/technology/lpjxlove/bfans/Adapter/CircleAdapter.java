package com.technology.lpjxlove.bfans.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.technology.lpjxlove.bfans.Bean.CircleEntity;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.UI.CustomView.GoodView;
import com.technology.lpjxlove.bfans.UI.CustomView.NineLayout;
import com.technology.lpjxlove.bfans.Util.TimeTransformUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by LPJXLOVE on 2016/8/15.
 */
public class CircleAdapter extends BaseAdapter<CircleEntity,CircleAdapter.MyHolder> {
    private Action1<CircleEntity> itemClick;
    private Action1<String> commentClick,goodNormalClick,goodPressClick, shareClick, avatarClick, imageClick;
    private Context context;

    public CircleAdapter(Context context,List<CircleEntity> data, Action1<String> imageClick, Action1<String> avatarClick, Action1<String> shareClick, Action1<String> goodNormalClick,Action1<String> goodPressClick, Action1<String> commentClick, Action1<CircleEntity> itemClick) {
        super(data);
        this.context=context;
        this.imageClick = imageClick;
        this.avatarClick = avatarClick;
        this.shareClick = shareClick;
        this.goodNormalClick = goodNormalClick;
        this.commentClick = commentClick;
        this.itemClick = itemClick;
        this.goodPressClick=goodPressClick;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.circle_item_layout, null);
        MyHolder holder = new MyHolder(view);
        holder.setImageClick(imageClick);
        holder.setAvatarClick(avatarClick);
        holder.setCommentItemClick(itemClick);
        holder.setCommentClick(commentClick);
        holder.setShareClick(shareClick);
        holder.setGoodClick(goodNormalClick,goodPressClick);
        holder.setList(data);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        User user=data.get(position).getAuthor();
        if (user.getAvatarUrl()!=null){
            String path=user.getAvatarUrl();
            final Uri uri = Uri.parse(path);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(100, 100))
                    .build();
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setOldController(holder.ivAvatar.getController())
                    .setImageRequest(request)
                    .build();
            holder.ivAvatar.setController(draweeController);
        }else {
            holder.ivAvatar.setBackgroundResource(R.drawable.ic_person_black_24dp);
        }

        if (TextUtils.isEmpty(user.getNickName())){
            holder.tvNick.setText(user.getUsername());
        }else {
            holder.tvNick.setText(user.getNickName());
        }

        holder.tvTime.setText(TimeTransformUtils.TimeTransform(data.get(position).getCreateTime()));
        String content=data.get(position).getContent();

        if (TextUtils.isEmpty(content)){
            holder.tvContent.setVisibility(View.GONE);
        }else {
            holder.tvContent.setText(content);
        }



        assert data.get(position).getBitmapUrl()!=null;
        NineGridPhotoAdapter adapter=new NineGridPhotoAdapter(context,data.get(position).getBitmapUrl());
        holder.nineLayout.setVisibility(View.VISIBLE);
        holder.nineLayout.setAdapter(adapter,1);

        if (data.get(position).isLike()){
            holder.ivGood.setPress(true);
            holder.ivGood.setImageResource(R.drawable.ic_good_full);
        }else {
            holder.ivGood.setPress(false);
            holder.ivGood.setImageResource(R.drawable.ic_good);
        }
        holder.tvGoodCount.setText(String.valueOf(data.get(position).getLikeCount()));
        holder.tvCommentCount.setText(String.valueOf(data.get(position).getCommentCount()));

    }
    @Override
    protected boolean areItemsTheSame(CircleEntity oldItem, CircleEntity newItem) {
        return oldItem.equals(newItem);
    }

    @Override
    protected boolean areContentsTheSame(CircleEntity oldItem, CircleEntity newItem) {
        return oldItem.equals(newItem);
    }



    static class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener,GoodView.OnLikesListener {
        @InjectView(R.id.iv_avatar)
        SimpleDraweeView ivAvatar;
        @InjectView(R.id.tv_rank)
        TextView tvNick;
        @InjectView(R.id.tv_time)
        TextView tvTime;
        @InjectView(R.id.tv_state)
        TextView ivJoin;
        @InjectView(R.id.nine_layout)
        NineLayout nineLayout;
        @InjectView(R.id.tv_content)
        TextView tvContent;
        @InjectView(R.id.iv_qr_scan)
        ImageView ivMessage;
        @InjectView(R.id.tv_comment_count)
        TextView tvCommentCount;
        @InjectView(R.id.iv_good)
        GoodView ivGood;
        @InjectView(R.id.tv_good_count)
        TextView tvGoodCount;
        @InjectView(R.id.iv_share)
        ImageView ivShare;
        @InjectView(R.id.tv_share)
        TextView tvShare;
        @InjectView(R.id.relative_layout_item)
        RelativeLayout relativeLayout;
        private Observable<String> observable;
        private Observable<CircleEntity> observable2;
        private Action1<String> commentClick,goodNormalClick,goodPressClick,shareClick,avatarClick,imageClick;
        private Action1<CircleEntity> commentItemClick;
        private List<CircleEntity> list;

        public void setList(List<CircleEntity> list) {
            this.list = list;
        }

        void setCommentClick(Action1<String> commentClick) {
            this.commentClick = commentClick;
        }

        void setGoodClick(Action1<String> goodNormalClick, Action1<String> goodPressClick) {
            this.goodNormalClick = goodNormalClick;
            this.goodPressClick=goodPressClick;
        }

        void setShareClick(Action1<String> shareClick) {
            this.shareClick = shareClick;
        }

        void setAvatarClick(Action1<String> avatarClick) {
            this.avatarClick = avatarClick;
        }

        void setImageClick(Action1<String> imageClick) {
            this.imageClick = imageClick;
        }

        void setCommentItemClick(Action1<CircleEntity> commentItemClick) {
            this.commentItemClick = commentItemClick;
        }

        MyHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            ivAvatar.setOnClickListener(this);
            ivMessage.setOnClickListener(this);
            ivShare.setOnClickListener(this);
            relativeLayout.setOnClickListener(this);
            ivGood.setOnClickListener(this);
            ivGood.setOnLikesListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_avatar:
                    observable=Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            avatarClick.call("");
                        }
                    });
                    observable.subscribe(avatarClick);
                    break;
                case R.id.iv_qr_scan:
                    observable=Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            commentClick.call("");
                        }
                    });
                    observable.subscribe(commentClick);
                    break;
                case R.id.iv_good:
               /*  if (ivGood.isPress()){//被赞时
                     observable=Observable.create(new Observable.OnSubscribe<String>() {
                         @Override
                         public void call(Subscriber<? super String> subscriber) {
                             goodPressClick.call(String.valueOf(getAdapterPosition()));
                         }
                     });
                     observable.subscribe(goodPressClick);
                 }else {//取消赞时
                     observable=Observable.create(new Observable.OnSubscribe<String>() {
                         @Override
                         public void call(Subscriber<? super String> subscriber) {
                             goodNormalClick.call(String.valueOf(getAdapterPosition()));
                         }
                     });
                     observable.subscribe(goodNormalClick);

                 }
*/
                    break;
                case R.id.iv_share:
                    observable=Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            shareClick.call("");
                        }
                    });
                    observable.subscribe(shareClick);
                    break;

                case R.id.relative_layout_item:
                    observable2=Observable.create(new Observable.OnSubscribe<CircleEntity>() {
                        @Override
                        public void call(Subscriber<? super CircleEntity> subscriber) {
                            commentItemClick.call(list.get(getLayoutPosition()));
                        }
                    });
                    observable2.subscribe(commentItemClick);
                    break;
            }
        }

        @Override
        public void Likes() {
            observable=Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    int count=Integer.valueOf(tvGoodCount.getText().toString());
                    count++;
                    list.get(getAdapterPosition()).setLike(true);
                    tvGoodCount.setText(String.valueOf(count));
                    goodPressClick.call(list.get(getAdapterPosition()).getObjectId());
                }
            });
            observable.subscribe(goodPressClick);
        }

        @Override
        public void cancelLikes() {
            observable=Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    int count=Integer.valueOf(tvGoodCount.getText().toString());
                    count--;
                    list.get(getAdapterPosition()).setLike(false);
                    tvGoodCount.setText(String.valueOf(count));
                    goodNormalClick.call(list.get(getAdapterPosition()).getObjectId());
                }
            });
            observable.subscribe(goodNormalClick);
        }
    }

}
