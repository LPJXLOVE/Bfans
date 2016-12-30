package com.technology.lpjxlove.bfans.Repository.Task;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;

import com.technology.lpjxlove.bfans.UI.AlbumActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LPJXLOVE on 2016/9/27.
 */
public class LoadingLocalPictureTask extends Task<Object> {
    private Context context;

    public LoadingLocalPictureTask(Context context) {
        this.context = context;
    }

    @Override
    public void Loading(final Observer<List<Object>> observer) {
        this.observable= Observable.create(new Observable.OnSubscribe<List<Object>>() {
            @Override
            public void call(Subscriber<? super List<Object>> subscriber) {

                observer.onNext(getImageFromStorage());

            }
        });
        observable.subscribeOn(Schedulers.io());
        observable.observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(observer);

    }

    private List<Object> getImageFromStorage(){
        List<Object> photoUrl=new ArrayList<>();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = context.getContentResolver();
        //只查询jpeg和png的图片
        Cursor mCursor = mContentResolver.query(mImageUri, null,
                MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);

        if (mCursor == null) {
            return null;
        }

        while (mCursor.moveToNext()) {
            //获取图片的路径
            String path = mCursor.getString(mCursor
                    .getColumnIndex(MediaStore.Images.Media.DATA));
            //获取该图片的父路径名
            String absolute = "file://" + path;
            photoUrl.add(absolute);
        }

        mCursor.close();

        return photoUrl;

    }




}
