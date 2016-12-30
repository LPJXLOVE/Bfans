package com.technology.lpjxlove.bfans.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technology.lpjxlove.bfans.Bean.CircleEntity;
import com.technology.lpjxlove.bfans.Bean.ShareEntity;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.Repository.Task.QqShareTask;
import com.technology.lpjxlove.bfans.Repository.Task.QqZoneShareTask;
import com.technology.lpjxlove.bfans.Repository.Task.WeChatCircleShareTask;
import com.technology.lpjxlove.bfans.Repository.Task.WeChatShareTask;
import com.technology.lpjxlove.bfans.Repository.Task.WeiBoShareTask;

/**
 * Created by LPJXLOVE on 2016/3/11.
 */
public class ShareItemAdapter extends RecyclerView.Adapter<ShareItemAdapter.MyHolder> {
    private CircleEntity circleEntity;

    public ShareItemAdapter(CircleEntity circleEntity) {
        this.circleEntity = circleEntity;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder myHolder ;
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.share_item_layout,null);
        myHolder =new MyHolder(view,circleEntity);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        switch (position){
            case 0:
                holder.iv.setImageResource(R.drawable.ic_wechat_circle);
                holder.tv.setText("朋友圈");
                break;
            case 1:
                holder.iv.setImageResource(R.drawable.ic_wechat);
                holder.tv.setText("微信");
                break;
            case 2:
                holder.iv.setImageResource(R.drawable.ic_qq);
                holder.tv.setText("QQ");
                break;
            case 3:
                holder.iv.setImageResource(R.drawable.ic_qq_zone);
                holder.tv.setText("QQ空间");
                break;
            case 4:
                holder.iv.setImageResource(R.drawable.ic_weibo);
                holder.tv.setText("新浪微博");
                break;
        }

    }




    @Override
    public int getItemCount() {
        return 5;
    }

    static class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout shareItem;
        ImageView iv;
        TextView tv;
        CircleEntity circleEntity;

        public MyHolder(View itemView, CircleEntity circleEntity) {
            super(itemView);
            this.circleEntity=circleEntity;
            iv= (ImageView) itemView.findViewById(R.id.share_image);
            tv= (TextView) itemView.findViewById(R.id.tv_text);
            shareItem= (RelativeLayout) itemView.findViewById(R.id.share_parent);
            shareItem.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            ShareEntity shareEntity=null;
            switch (getAdapterPosition()){

                case 0:
                    shareEntity=new WeChatCircleShareTask();
                    break;
                case 1:
                    shareEntity=new WeChatShareTask();

                    break;
                case 2:
                    shareEntity=new QqShareTask();

                    break;
                case 3:
                    shareEntity=new QqZoneShareTask();

                    break;
                case 4:
                    shareEntity=new WeiBoShareTask();

                    break;

            }
            assert shareEntity!=null;
            shareEntity.shareContent(circleEntity);


        }
    }
}
