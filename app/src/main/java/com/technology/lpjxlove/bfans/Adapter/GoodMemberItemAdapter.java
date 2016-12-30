package com.technology.lpjxlove.bfans.Adapter;

import android.content.Context;
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
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.R;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by LPJXLOVE on 2016/8/15.
 */
public class GoodMemberItemAdapter extends RecyclerView.Adapter<GoodMemberItemAdapter.MyHolder> {
    private Action1<String> action1;
    private List<User> imageListString;
    private Context context;

    public GoodMemberItemAdapter(Action1<String> action1, List<User> imageListString) {
        this.action1 = action1;
        this.imageListString = imageListString;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.good_member_item, null);
        return new MyHolder(view, action1, imageListString);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {


        holder.iv_photo.getWidth();
        holder.iv_photo.getPaddingRight();


        //当size小于9时，设置Add图标
      /*  if (position == imageListString.size()) {
            holder.iv_photo.setBackgroundResource(R.drawable.add);
        } else {*/
            final Uri uri = Uri.parse("http://p2.so.qhmsg.com/sdr/960_768_/t0156c6d8ef76ddd1d7.jpg");
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(200, 200))
                    .build();
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setOldController(holder.iv_photo.getController())
                    .setImageRequest(request)
                    .build();
            holder.iv_photo.setController(draweeController);
      /*  }*/

    }

    @Override
    public int getItemCount() {




        return 15;
       /* if (imageListString.size() < 9) {
            return imageListString.size() + 1;
        } else {
            return imageListString.size();
        }*/

    }

    static class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SimpleDraweeView iv_photo;
        private Action1<String> action1;
        private Observable<String> observable;
        private List<User> data;

        public MyHolder(View itemView, Action1<String> action1, List<User> data) {
            super(itemView);
            this.action1 = action1;

            this.data = data;
            iv_photo = (SimpleDraweeView) itemView.findViewById(R.id.iv_avatar);
            iv_photo.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_avatar:
                    observable = Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            if (getAdapterPosition()==data.size()){
                                action1.call("add");
                            }else {
                                action1.call(getAdapterPosition()+"");
                            }

                        }
                    });
                    observable.subscribe(action1);

                    break;



            }

        }
    }
}
