package com.technology.lpjxlove.bfans.Util;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by LPJXLOVE on 2016/10/21.
 */

public class ImageUtils {

    public static String  CompressBitmap(Intent data,String path, String name){
        if (data==null){
            return null;
        }
        Bitmap bitmap=data.getExtras().getParcelable("data");
        FileOutputStream fos;
        File file =new File(path,name);
        try {
            fos=new FileOutputStream(file);
            assert bitmap != null;
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getPath();
    }


    public static Bitmap ProvideSmallBitmap(Resources res, @DrawableRes int ResId, int reqWidth, int reqHeight){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(res,ResId,options);
        options.inSampleSize=CalculateSampleSize(options,reqWidth, reqHeight);
        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeResource(res,ResId,options);
    }


    private static int CalculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        int height=options.outHeight;
        int width=options.outWidth;
        int SampleSize=1;
        if (height>reqHeight||width>reqWidth){
            if (width>height){
                SampleSize=Math.round(height/(float)reqHeight);
            }else {
                SampleSize=Math.round(width/(float)reqWidth);
            }
        }
        return SampleSize;
    }







}
