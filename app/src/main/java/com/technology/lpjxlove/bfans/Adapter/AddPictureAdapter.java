package com.technology.lpjxlove.bfans.Adapter;

import android.net.Uri;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.technology.lpjxlove.bfans.Bean.ImageBean;
import com.technology.lpjxlove.bfans.R;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by LPJXLOVE on 2016/10/8.
 */
@Deprecated
public class AddPictureAdapter extends BaseAdapter<ImageBean,AddPictureAdapter.MyHolder> {
    private Action1<String> action1;
    private Action1<ImageBean> action2;


    public AddPictureAdapter(Action1<String> action1, Action1<ImageBean> action2,List<ImageBean> data) {
        super(data);
        this.action1=action1;
        this.action2=action2;
    }

    @Override
    protected boolean areItemsTheSame(ImageBean oldItem, ImageBean newItem) {
        return oldItem.equals(newItem);
    }

    @Override
    protected boolean areContentsTheSame(ImageBean oldItem, ImageBean newItem) {
        return oldItem.equals(newItem);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_picture_item_layout, null);
        return new AddPictureAdapter.MyHolder(view, action1, action2, data);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        //当size小于9时，设置Add图标
        if (position == data.size()) {
            holder.iv_photo.setBackgroundResource(R.drawable.add);
            holder.iv_delete.setVisibility(View.GONE);
        } else {
            final Uri uri = Uri.parse(data.get(position).ImageUrl);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(200, 200))
                    .build();
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setOldController(holder.iv_photo.getController())
                    .setImageRequest(request)
                    .build();
            holder.iv_photo.setController(draweeController);
        }

    }


    @Override
    public void notifyDiff() {
        super.notifyDiff();
        DiffUtil.DiffResult diffResult=DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                if (temp.size() < 9) {
                    return temp.size() + 1;
                } else {
                    return temp.size();
                }
            }

            @Override
            public int getNewListSize() {
                if (data.size() < 9) {
                    return data.size() + 1;
                } else {
                    return data.size();
                }
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return temp.get(oldItemPosition).equals(data.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return temp.get(oldItemPosition).equals(data.get(newItemPosition));
            }
        });

        diffResult.dispatchUpdatesTo(this);
        temp.clear();
        temp.addAll(data);

    }

    @Override
    public int getItemCount() {
        if (data.size() < 9) {
            return data.size() + 1;
        } else {
            return data.size();
        }
    }

    static class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        SimpleDraweeView iv_photo;
        ImageView iv_delete;
        private Action1<String> action1;
        private Observable<String> observable;
        private Action1<ImageBean> action2;
        private Observable<ImageBean> observable2;
        private List<ImageBean> data;

        public MyHolder(View itemView, Action1<String> action1, Action1<ImageBean> action2, List<ImageBean> data) {
            super(itemView);
            this.action1 = action1;
            this.action2 = action2;
            this.data = data;
            iv_photo = (SimpleDraweeView) itemView.findViewById(R.id.iv_img);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            iv_delete.setOnClickListener(this);
            iv_photo.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_img:
                    observable = Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            if (getAdapterPosition()==data.size()){
                                action1.call("add");
                            }else {
                                action1.call(data.get(getAdapterPosition()).ImageUrl);
                            }

                        }
                    });
                    observable.subscribe(action1);

                    break;
                case R.id.iv_delete:
                    observable2 = Observable.create(new Observable.OnSubscribe<ImageBean>() {
                        @Override
                        public void call(Subscriber<? super ImageBean> subscriber) {
                            if (getAdapterPosition()!=data.size()){
                                action2.call(data.get(getAdapterPosition()));
                            }

                        }
                    });
                    observable2.subscribe(action2);

                    break;


            }

        }
    }
}
