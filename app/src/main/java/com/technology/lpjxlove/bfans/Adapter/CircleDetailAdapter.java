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
import com.technology.lpjxlove.bfans.Bean.Comment;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.UI.CustomView.GoodView;
import com.technology.lpjxlove.bfans.UI.CustomView.HorizontalView;
import com.technology.lpjxlove.bfans.UI.CustomView.NineLayout;
import com.technology.lpjxlove.bfans.Util.TimeTransformUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by LPJXLOVE on 2016/10/5.
 */

public class CircleDetailAdapter extends RecyclerView.Adapter<CircleDetailAdapter.MyHolder> {
    private static final int TOP_LAYOUT = 0x1;
    private static final int COMMENT_LAYOUT = 0x2;
    private static final int BOTTOM_LAYOUT = 0x3;
    private static final int EMPTY_LAYOUT = 0x4;
    private List<CircleEntity> data;
    private Context context;
    private Action1<String> commentClick, goodClick, cancelLikeClick, shareClick, avatarClick, imageClick;
    private Action1<String> commentItemClick;
    private Action1<String> goodItemClick, goodEndClick;
    private List<User> likeData;
    private List<Comment> commentData;
    private boolean haveBuild;//用于判断是否已经设置过点赞列表
    private CircleEntity circleEntity;//刷新数据后的CircleEntity

    public void setCircleEntity(CircleEntity circleEntity) {
        this.circleEntity = circleEntity;
    }

    public void setCommentData(List<Comment> commentData) {
        this.commentData = commentData;
    }

    public CircleEntity getCircleEntity() {
        return circleEntity;
    }

    public void setLikeData(List<User> likeData) {
        this.likeData = likeData;
    }

    public CircleDetailAdapter(Context context, List<CircleEntity> data, Action1<String> avatarClick, Action1<String> commentClick, Action1<String> goodClick
            , Action1<String> cancelLikeClick,
                               Action1<String> shareClick,
                               Action1<String> commentItemClick
            , Action1<String> imageClick, Action1<String> goodItemClick, Action1<String> goodEndClick) {
        this.context = context;
        this.data = data;
        this.avatarClick = avatarClick;
        this.commentClick = commentClick;
        this.goodClick = goodClick;
        this.cancelLikeClick = cancelLikeClick;
        this.shareClick = shareClick;
        this.commentItemClick = commentItemClick;
        this.imageClick = imageClick;
        this.goodItemClick = goodItemClick;
        this.goodEndClick = goodEndClick;

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TOP_LAYOUT;
        }

        if (commentData == null || commentData.size() == 0) {
            return EMPTY_LAYOUT;//没有评论时显示无内容布局
        } else {

            if (commentData.size()==circleEntity.getCommentCount()&&position==commentData.size()+1) {
                return BOTTOM_LAYOUT;//当加载完全部数据后，显示最后一页提示布局
            } else {
                return COMMENT_LAYOUT;
            }
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId;
        View view;
        MyHolder myHolder = null;
        switch (viewType) {
            case TOP_LAYOUT:
                layoutId = R.layout.circle_detail_top_layout;
                view = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
                myHolder = new MyHolder(view, TOP_LAYOUT, context);
                myHolder.setLikes(likeData);
                break;
            case COMMENT_LAYOUT:
                layoutId = R.layout.circle_comment_layout;
                view = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
                myHolder = new MyHolder(view, COMMENT_LAYOUT, context);
                break;
            case BOTTOM_LAYOUT:
                layoutId = R.layout.circle_bottom_layout;
                view = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
                myHolder = new MyHolder(view, BOTTOM_LAYOUT, context);
                break;
            case EMPTY_LAYOUT:
                layoutId = R.layout.empty_content;
                view = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
                myHolder = new MyHolder(view);
                break;
        }

        assert myHolder != null;
        myHolder.setAvatarClick(avatarClick);
        myHolder.setGoodClick(goodClick);
        myHolder.setCancelLike(cancelLikeClick);
        myHolder.setCommentClick(commentClick);
        myHolder.setShareClick(shareClick);
        myHolder.setImageClick(imageClick);
        myHolder.setCommentItemClick(commentItemClick);
        myHolder.setGoodItemClick(goodItemClick);
        myHolder.setGoodEndClick(goodEndClick);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        if (position == 0) {
            User user = data.get(position).getAuthor();
            if (user.getAvatarUrl() != null) {
                String path = user.getAvatarUrl();
                final Uri uri = Uri.parse(path);
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                        .setResizeOptions(new ResizeOptions(100, 100))
                        .build();
                DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                        .setOldController(holder.ivAvatar.getController())
                        .setImageRequest(request)
                        .build();
                holder.ivAvatar.setController(draweeController);
            } else {
                holder.ivAvatar.setBackgroundResource(R.drawable.ic_person_black_24dp);
            }

            if (TextUtils.isEmpty(user.getNickName())) {
                holder.tvNick.setText(user.getUsername());
            } else {
                holder.tvNick.setText(user.getNickName());
            }

            holder.tvTime.setText(TimeTransformUtils.TimeTransform(data.get(position).getCreateTime()));
            //设置内容
            String content = data.get(position).getContent();
            if (content == null) {
                holder.tvContent.setVisibility(View.GONE);
            } else {
                holder.tvContent.setText(content);
            }
            //设置图片
            assert data.get(position).getBitmapUrl() != null;
            NineGridPhotoAdapter adapter = new NineGridPhotoAdapter(context, data.get(position).getBitmapUrl());
            holder.nineLayout.setVisibility(View.VISIBLE);
            holder.nineLayout.setAdapter(adapter, 1);
            holder.nineLayout.setOnItemClickListener(new NineLayout.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    imageClick.call(String.valueOf(position));
                }
            });

            //设置点赞的样式
            if (data.get(position).isLike()){
                holder.ivGood.setPress(true);
                holder.ivGood.setImageResource(R.drawable.ic_good_full);
            }else {
                holder.ivGood.setPress(false);
                holder.ivGood.setImageResource(R.drawable.ic_good);
            }
            holder.tvGoodCount.setText(String.valueOf(data.get(position).getLikeCount()));
            holder.tvCommentCount.setText(String.valueOf(data.get(position).getCommentCount()));


            if (likeData != null && !haveBuild&&likeData.size()>0) {//设置点赞列表
                holder.horizontalView.setAdapter(new HorizontalAdapter(context, likeData));
                holder.horizontalView.setVisibility(View.VISIBLE);
                haveBuild = true;
            }/*else {
                holder.horizontalView.setVisibility(View.GONE);
            }*/

            if (circleEntity != null) {//刷新重置评论和点赞的数目
                holder.tvGoodCount.setText(String.valueOf(circleEntity.getLikeCount()));
                holder.tvCommentCount.setText(String.valueOf(circleEntity.getCommentCount()));
            }


        } else {

            //设置评论内容
            if (commentData != null && commentData.size() != 0) {
                if (commentData.size()==circleEntity.getCommentCount()&&position==commentData.size()+1){
                    return;
                }
                //设置评论者头像图片
                User user = commentData.get(position - 1).getAuthor();
                if (user.getAvatarUrl() != null) {
                    String path = user.getAvatarUrl();
                    final Uri uri = Uri.parse(path);
                    ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                            .setResizeOptions(new ResizeOptions(200, 200))
                            .build();
                    DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                            .setOldController(holder.ivAvatar.getController())
                            .setImageRequest(request)
                            .build();
                    holder.ivAvatar.setController(draweeController);
                } else {
                    holder.ivAvatar.setBackgroundResource(R.drawable.ic_person_black_24dp);
                }

                if (TextUtils.isEmpty(user.getNickName())) {
                    holder.tvNick.setText(user.getUsername());
                } else {
                    holder.tvNick.setText(user.getNickName());
                }

                holder.tvTime.setText(TimeTransformUtils.TimeTransform(commentData.get(position - 1).getCreatedAt()));
                //设置评论内容
                holder.tvContent.setText(commentData.get(position - 1).getContent());

            }

        }


    }

    @Override
    public int getItemCount() {
        int count;
        if (commentData == null) {
            count = data.size();
        } else {
            if (commentData.size()==circleEntity.getCommentCount()) {
                count = commentData.size() + 2;
            } else {
                count = commentData.size() + 1;
            }
        }

        return count;
        // return commentData==null?data.size():commentData.size()+1;
    }

    static class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener, GoodView.OnLikesListener {
        // @InjectView(R.id.iv_avatar)
        SimpleDraweeView ivAvatar;
        //   @InjectView(R.id.tv_nick)
        TextView tvNick;
        //    @InjectView(R.id.tv_time)
        TextView tvTime;
        //     @InjectView(R.id.iv_join)
        TextView ivJoin;
        //      @InjectView(R.id.nine_layout)
        NineLayout nineLayout;
        //      @InjectView(R.id.tv_content)
        TextView tvContent;
        //      @InjectView(R.id.iv_message)
        ImageView ivMessage;
        //     @InjectView(R.id.tv_comment_count)
        TextView tvCommentCount;
        //      @InjectView(R.id.iv_good)
        GoodView ivGood;
        //      @InjectView(R.id.tv_good_count)
        TextView tvGoodCount;
        //       @InjectView(R.id.iv_share)
        ImageView ivShare;
        //       @InjectView(R.id.tv_share)
        TextView tvShare;
        //       @InjectView(R.id.recycle_view_good)
        //   RecyclerView recycleViewGood;
        RelativeLayout relayout_item;
        HorizontalView horizontalView;
        List<User> likes;

        private Observable<String> observable;
        private Action1<String> commentClick, goodClick, shareClick, avatarClick, imageClick, cancelLike;
        private Action1<String> commentItemClick;
        private Action1<String> goodItemClick, goodEndClick;

        public void setLikes(List<User> likes) {
            this.likes = likes;
        }

        public void setCommentClick(Action1<String> commentClick) {
            this.commentClick = commentClick;
        }

        public void setCancelLike(Action1<String> cancelLike) {
            this.cancelLike = cancelLike;
        }

        public void setGoodClick(Action1<String> goodClick) {
            this.goodClick = goodClick;
        }

        public void setShareClick(Action1<String> shareClick) {
            this.shareClick = shareClick;
        }

        public void setAvatarClick(Action1<String> avatarClick) {
            this.avatarClick = avatarClick;
        }

        public void setImageClick(Action1<String> imageClick) {
            this.imageClick = imageClick;
        }

        public void setCommentItemClick(Action1<String> commentItemClick) {
            this.commentItemClick = commentItemClick;
        }

        public void setGoodItemClick(Action1<String> goodItemClick) {
            this.goodItemClick = goodItemClick;
        }

        public void setGoodEndClick(Action1<String> goodEndClick) {
            this.goodEndClick = goodEndClick;
        }


        MyHolder(View itemView) {
            super(itemView);
        }

        MyHolder(View itemView, int ViewType, Context context) {
            super(itemView);
            ivAvatar = (SimpleDraweeView) itemView.findViewById(R.id.iv_avatar);
            tvNick = (TextView) itemView.findViewById(R.id.tv_nick);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            nineLayout = (NineLayout) itemView.findViewById(R.id.nine_layout);
            if (ivAvatar != null) {
                ivAvatar.setOnClickListener(this);
            }
            if (ViewType == TOP_LAYOUT) {
                ivMessage = (ImageView) itemView.findViewById(R.id.iv_qr_scan);
                tvCommentCount = (TextView) itemView.findViewById(R.id.tv_comment_count);
                ivGood = (GoodView) itemView.findViewById(R.id.iv_good);
                tvGoodCount = (TextView) itemView.findViewById(R.id.tv_good_count);
                ivShare = (ImageView) itemView.findViewById(R.id.iv_share);
                horizontalView = (HorizontalView) itemView.findViewById(R.id.horizontal_linear);
                ivMessage.setOnClickListener(this);
                ivGood.setOnClickListener(this);
                ivGood.setOnLikesListener(this);
                ivShare.setOnClickListener(this);
                horizontalView.setVisibility(View.GONE);
                horizontalView.setItemClick(new Action1<String>() {
                    @Override
                    public void call(final String s) {
                        final Observable<String> goodItem = Observable.create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(Subscriber<? super String> subscriber) {
                                goodItemClick.call(s);
                            }
                        });
                        goodItem.subscribe(goodItemClick);

                    }
                });
                horizontalView.setEndClick(new Action1<String>() {
                    @Override
                    public void call(final String s) {
                        final Observable<String> goodEnd = Observable.create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(Subscriber<? super String> subscriber) {
                                goodEndClick.call(s);
                            }
                        });
                        goodEnd.subscribe(goodEndClick);
                    }
                });


            } else if (ViewType == COMMENT_LAYOUT) {
                relayout_item = (RelativeLayout) itemView.findViewById(R.id.relative_layout_item);
                relayout_item.setOnClickListener(this);
            }

        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_avatar:
                    observable = Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            avatarClick.call("");
                        }
                    });
                    observable.subscribe(avatarClick);
                    break;
                case R.id.iv_qr_scan:
                    observable = Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            commentClick.call("");
                        }
                    });
                    observable.subscribe(commentClick);

                    break;
                case R.id.iv_good:
                  /*  observable = Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            goodClick.call("");
                        }
                    });
                    observable.subscribe(goodClick);*/

                    break;
                case R.id.iv_share:
                    observable = Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            shareClick.call("");
                        }
                    });
                    observable.subscribe(shareClick);
                    break;
                case R.id.relative_layout_item:
                    observable = Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            commentItemClick.call(String.valueOf(getAdapterPosition() - 1));
                        }
                    });
                    observable.subscribe(commentItemClick);

                    break;
            }
        }

        @Override
        public void Likes() {//点赞
            int count = Integer.valueOf(tvGoodCount.getText().toString());
            count++;
            tvGoodCount.setText(String.valueOf(count));
            observable = Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    goodClick.call("");
                }
            });
            observable.subscribe(goodClick);
        }

        @Override
        public void cancelLikes() {//取消赞
            int count = Integer.valueOf(tvGoodCount.getText().toString());
            count--;
            tvGoodCount.setText(String.valueOf(count));
            observable = Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    cancelLike.call("");
                }
            });
            observable.subscribe(cancelLike);
        }
    }

}
