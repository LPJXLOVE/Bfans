package com.technology.lpjxlove.bfans.UI;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.technology.lpjxlove.bfans.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.relex.photodraweeview.PhotoDraweeView;

public class MyCircleActivity extends AppCompatActivity {

    @InjectView(R.id.head_iv)
    ImageView headIv;
    @InjectView(R.id.photo)
    PhotoDraweeView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle);
        ButterKnife.inject(this);
    /*    RenderScript rs = RenderScript.create(this);
        Bitmap bitmap = ImageUtils.ProvideSmallBitmap(getResources(),R.drawable.test,100,100);
        Allocation allocation = Allocation.createFromBitmap(rs, bitmap);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, allocation.getElement());
        blur.setInput(allocation);
        blur.setRadius(25);
        blur.forEach(allocation);
        allocation.copyTo(bitmap);
        headIv.setImageBitmap(bitmap);*/
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(Uri.parse("http://p0.so.qhmsg.com/sdr/512_768_/t01bc3a8bdbdeb195ea.jpg"));
        controller.setOldController(photo.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null || photo == null) {
                    return;
                }
                photo.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        photo.setController(controller.build());

    }
}
