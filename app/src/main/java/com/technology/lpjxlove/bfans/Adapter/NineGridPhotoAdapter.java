package com.technology.lpjxlove.bfans.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
import rx.functions.Action1;

/**
 * Created by LPJXLOVE on 2016/9/8.
 */
public class NineGridPhotoAdapter extends NineGridAdapter {
    public Action1<String> getAddClickListener() {
        return addClickListener;
    }

    public void setAddClickListener(Action1<String> addClickListener) {
        this.addClickListener = addClickListener;
    }

    private Action1<String> addClickListener;
    private Context context;
    public NineGridPhotoAdapter(Context context, List list) {
        super(list);
        this.context=context;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getUrl(int position) {
        return null;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int i, View view) {
       SimpleDraweeView iv=new SimpleDraweeView(context);
            String path= (String) list.get(i);
            final Uri uri = Uri.parse(path);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(200, 200))
                    .build();
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setOldController(iv.getController())
                    .setImageRequest(request)
                    .build();
            iv.setController(draweeController);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return iv;
    }
}
