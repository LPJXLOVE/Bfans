package com.technology.lpjxlove.bfans.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.technology.lpjxlove.bfans.Bean.Like;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.R;

import java.util.List;

/**
 * Created by LPJXLOVE on 2016/10/6.
 */

public class HorizontalAdapter extends NineGridAdapter {
    private List<User> data;

    public HorizontalAdapter(Context context, List<User>  data) {
        super(context);
        this.data=data;
    }



    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getUrl(int position) {
        return null;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view) {
        RoundingParams roundingParams=RoundingParams.fromCornersRadius(5f);
        roundingParams.setRoundAsCircle(true);
        String s=data.get(i).getAvatarUrl();
        final Uri uri=Uri.parse(s);
        SimpleDraweeView draweeView=new SimpleDraweeView(context);
        if (TextUtils.isEmpty(s)){
            draweeView.setBackgroundResource(R.drawable.ic_person_black_24dp);
        }else {
            draweeView.setImageURI(uri);
        }
        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setRoundingParams(roundingParams)
                .build();
            draweeView.setHierarchy(hierarchy);


        return draweeView;
    }
}
