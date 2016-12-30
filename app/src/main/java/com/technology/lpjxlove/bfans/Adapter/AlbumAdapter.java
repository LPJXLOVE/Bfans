package com.technology.lpjxlove.bfans.Adapter;

import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.technology.lpjxlove.bfans.Bean.ImageBean;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.Util.Constant;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by LPJXLOVE on 2016/9/25.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private final List<ImageBean> data;
    private Action1<String> action1;
    private Action1<Integer> action2;
    private List<ImageBean> selectImageList;
    private int cameraImageCount;
    private RecyclerView recyclerView;
    private int flag;
    private CompoundButton.OnCheckedChangeListener mOnImageSelectListener;

    public AlbumAdapter(List<ImageBean> data, Action1<String> action1, Action1<Integer> action2, List<ImageBean> selectImageList) {
        this.data = data;
        this.action1 = action1;
        this.action2 = action2;
        this.selectImageList=selectImageList;
    }


    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public List<ImageBean> getSelectImageList() {
        return selectImageList;
    }

    public void setSelectImageList(List<ImageBean> selectImageList) {
        this.selectImageList = selectImageList;
    }

    public List<ImageBean> getData() {
        return data;
    }


    public CompoundButton.OnCheckedChangeListener getmOnImageSelectListener() {
        return mOnImageSelectListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item_layout, null);
        viewHolder = new ViewHolder(v, action1, data,flag);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        resetCheckState(holder);
        //设置check_box和图片alpha的状态
        ImageBean imageBean = data.get(position);
        holder.cb_check_box.setChecked(imageBean.isCheck);
        if (imageBean.isCheck){
            holder.iv_image.setAlpha(0.5f);
        }else {
            holder.iv_image.setAlpha(1f);
        }


        holder.cb_check_box.setTag(imageBean);
        holder.cb_check_box.setOnCheckedChangeListener(mOnImageSelectListener);
        holder.iv_image.setTag(data.get(position).ImageUrl);
        holder.iv_image.setBackgroundResource(R.color.gray);
        changeImageSelect();
        final Uri uri = Uri.parse(data.get(position).ImageUrl);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(200, 200))
                .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setOldController(holder.iv_image.getController())
                .setImageRequest(request)
                .build();
        holder.iv_image.setController(draweeController);

        if (position == 0 && holder.iv_image.getTag().equals(data.get(0).ImageUrl)) {
            holder.iv_image.setBackgroundResource(R.drawable.ic_camera_alt_black_24dp);
            holder.cb_check_box.setVisibility(View.GONE);
        } else {


            if (flag==Constant.NORMAL_PICTURE){
                holder.cb_check_box.setVisibility(View.VISIBLE);
            }else {//当为头像设置时
                holder.cb_check_box.setVisibility(View.GONE);
            }

        }



    }



    private void changeImageSelect(){

        if (mOnImageSelectListener == null) {
            mOnImageSelectListener = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    ImageBean img = (ImageBean) compoundButton.getTag();
                    img.isCheck = b;
                    SimpleDraweeView d= (SimpleDraweeView) recyclerView.findViewWithTag(img.ImageUrl);
                    refreshSelectCount(compoundButton, img, d);
                }
            };
        }



    }


    /**
     *
     * 刷新check_box和图片alpha的状态
     * @param button
     * @param imageBean
     * @param view
     */
    private void refreshSelectCount(CompoundButton button, ImageBean imageBean, SimpleDraweeView view) {
        if (null != imageBean) {
            boolean select = imageBean.isCheck;
            if (select) {
                if (!selectImageList.contains(imageBean) && selectImageList.size() - cameraImageCount < 9 - cameraImageCount) {
                    selectImageList.add(imageBean);
                    view.setAlpha(0.5f);
                } else {

                    imageBean.isCheck = false;
                    button.setChecked(false);
                    action2.call(10);

                }

            } else {
                if (selectImageList.contains(imageBean)) {
                    selectImageList.remove(imageBean);
                }
                view.setAlpha(1f);
                button.setChecked(false);
            }
        }
        action2.call(selectImageList.size());

    }


    /**
     * 重置CheckBox状态
     *
     * @param viewHolder
     */
    private void resetCheckState(ViewHolder viewHolder) {
        viewHolder.cb_check_box.setOnCheckedChangeListener(null);
        viewHolder.cb_check_box.setChecked(false);
        viewHolder.iv_image.setAlpha(1f);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SimpleDraweeView iv_image;
        CheckBox cb_check_box;
        private Action1<String> action1;
        private Observable<String> observable;
        private List<ImageBean> data;

        public ViewHolder(View itemView, Action1<String> action1, List<ImageBean> data,int flag) {
            super(itemView);
            this.action1 = action1;
            this.data = data;
            iv_image = (SimpleDraweeView) itemView.findViewById(R.id.iv_image);
            cb_check_box = (CheckBox) itemView.findViewById(R.id.cb_check);
            iv_image.setOnClickListener(this);
            if (flag==Constant.AVATAR_PICTURE){
                cb_check_box.setVisibility(View.GONE);
            }

        }

        @Override
        public void onClick(View v) {
            observable = Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    if (getLayoutPosition() == 0) {
                        action1.call("camera");
                    } else {
                        action1.call(data.get(getAdapterPosition()).ImageUrl);
                    }
                }
            });
            observable.observeOn(AndroidSchedulers.mainThread());
            observable.subscribe(action1);
        }
    }
}
