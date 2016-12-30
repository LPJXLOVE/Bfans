package com.technology.lpjxlove.bfans.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.Util.ImageUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyCircleActivity extends AppCompatActivity {

    @InjectView(R.id.head_iv)
    ImageView headIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle);
        ButterKnife.inject(this);
        RenderScript rs = RenderScript.create(this);
        Bitmap bitmap = ImageUtils.ProvideSmallBitmap(getResources(),R.drawable.test,100,100);
        Allocation allocation = Allocation.createFromBitmap(rs, bitmap);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, allocation.getElement());
        blur.setInput(allocation);
        blur.setRadius(25);
        blur.forEach(allocation);
        allocation.copyTo(bitmap);
        headIv.setImageBitmap(bitmap);

    }
}
