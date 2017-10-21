package com.technology.lpjxlove.bfans.Adapter;

import android.net.Uri;
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
 * Created by LPJXLOVE on 2016/8/15.
 */
public class AddPhotoAdapter extends RecyclerView.Adapter<AddPhotoAdapter.MyHolder> {
    private Action1<String> action1;
    private Action1<String> action2;
    private List<ImageBean> imageListString;

    public AddPhotoAdapter(Action1<String> action1, Action1<String> action2, List<ImageBean> imageListString) {
        this.action1 = action1;
        this.action2 = action2;
        this.imageListString = imageListString;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_picture_item_layout, null);
        return new MyHolder(view, action1, action2, imageListString);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        if (position==imageListString.size()){
            holder.iv_photo.setTag("add");
        }else {
            holder.iv_photo.setTag(imageListString.get(position));
        }

        //当size小于9时，设置Add图标
        if (position == imageListString.size()) {
            if (holder.iv_photo.getTag().equals("add")){
                holder.iv_photo.setBackgroundResource(R.drawable.add);
            }
            holder.iv_delete.setVisibility(View.GONE);
        } else {
                if (holder.iv_photo.getTag().equals(imageListString.get(position))){
                    final Uri uri = Uri.parse(imageListString.get(position).ImageUrl);
                    ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                            .setResizeOptions(new ResizeOptions(200, 200))
                            .build();
                    DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                            .setOldController(holder.iv_photo.getController())
                            .setImageRequest(request)
                            .build();
                    holder.iv_photo.setController(draweeController);
                    holder.iv_delete.setVisibility(View.VISIBLE);
                }
            }




    }

    @Override
    public int getItemCount() {
        if (imageListString.size() < 9) {
            return imageListString.size() + 1;
        } else {
            return imageListString.size();
        }

    }

    static class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SimpleDraweeView iv_photo;
        ImageView iv_delete;
        private Action1<String> action1;
        private Observable<String> observable;
        private Action1<String> action2;
        private Observable<String> observable2;
        private List<ImageBean> data;

        public MyHolder(View itemView, Action1<String> action1, Action1<String> action2, List<ImageBean> data) {
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
                                action1.call(String.valueOf(getAdapterPosition()));
                            }

                        }
                    });
                    observable.subscribe(action1);

                    break;
                case R.id.iv_delete:
                    observable2 = Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            if (getAdapterPosition()!=data.size()){
                                action2.call(String.valueOf(getAdapterPosition()));
                            }

                        }
                    });
                    observable2.subscribe(action2);

                    break;


            }

        }
    }
}
