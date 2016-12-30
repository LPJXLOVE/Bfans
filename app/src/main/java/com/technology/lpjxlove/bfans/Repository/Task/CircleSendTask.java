package com.technology.lpjxlove.bfans.Repository.Task;

import com.technology.lpjxlove.bfans.Bean.CircleEntity;
import com.technology.lpjxlove.bfans.Util.ResponseCodeUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
public class CircleSendTask extends Task<Object> {
  /*  @Override
    public void Loading(Observer<List<Object>> observer) {
        this.observable.subscribe(observer);
    }*/
    private static boolean have;
    private static int actualCount;
    private static int expectCount;

    @Override
    public void Loading(Observer<List<Object>> observer) {

    }

    @Override
    public void upLoading(final Observer<List<Object>> observer, final Object upLoadObject) {
        this.observable=Observable.create(new Observable.OnSubscribe<List<Object>>() {
            @Override
            public void call(Subscriber<? super List<Object>> subscriber) {
                final CircleEntity circleEntity= (CircleEntity) upLoadObject;
                final List<String> url=circleEntity.getBitmapUrl();
                String[] bitmapUrl;
                int count=url.size();
                expectCount=count;
                if (count>0){
                  bitmapUrl=new String[count];
                    for (int i = 0; i <count ; i++) {
                        bitmapUrl[i]=url.get(i);
                    }
                    BmobFile.uploadBatch(bitmapUrl, new UploadBatchListener() {
                        @Override
                        public void onSuccess(List<BmobFile> list, List<String> list1) {
                            actualCount++;
                            if (list.size()==list.size()){
                                url.clear();
                                for (String s:list1){
                                    url.add(s);

                                }
                                have=true;
                                circleEntity.setBitmapUrl(url);
                                if (actualCount ==expectCount){
                                    circleEntity.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if (e==null){
                                                List<Object> list=new ArrayList<Object>();
                                                observer.onNext(list);
                                            }else {
                                                Throwable throwable=new Throwable(e.getMessage());
                                                observer.onError(throwable);
                                            }
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onProgress(int i, int i1, int i2, int i3) {

                        }

                        @Override
                        public void onError(int i, String s) {
                            Throwable t=new Throwable(ResponseCodeUtils.TransForm(i));
                            observer.onError(t);
                        }
                    });
                }



                else if (!have){

                    circleEntity.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                List<Object> list=new ArrayList<Object>();
                                observer.onNext(list);
                            }else {
                                Throwable throwable=new Throwable(e.getMessage());
                                observer.onError(throwable);
                            }
                        }
                    });

                }




            }
        });
        observable.subscribeOn(Schedulers.newThread());
        observable.observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(observer);
    }
}
