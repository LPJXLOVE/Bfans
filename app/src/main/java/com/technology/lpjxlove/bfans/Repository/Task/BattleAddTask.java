package com.technology.lpjxlove.bfans.Repository.Task;

import android.util.Log;
import android.widget.Toast;

import com.technology.lpjxlove.bfans.Bean.BattleEntity;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.ResponseCodeUtils;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static com.technology.lpjxlove.bfans.Util.Constant.TAG;

/**
 * Created by LPJXLOVE on 2016/10/17.
 */

public class BattleAddTask extends Task<Object> {

    @Override
    public void Loading(Observer observer) {

    }

   /* @Override
    public void upLoading(final Observer<List<Object>> observer,Object o) {
        entity= (BattleEntity) o;
        Log.i(TAG, "setUpLoadObject: "+entity);
        this.observable= Observable.create(new Observable.OnSubscribe<List<Object>>() {
            @Override
            public void call(Subscriber<? super List<Object>> subscriber) {

                entity.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        Log.i(TAG, "done: "+s);
                        if (e==null){
                            observer.onNext(null);
                        }else {

                            observer.onError(e);
                        }
                    }
                });

            }
        });
        this.observable.subscribe(observer);
    }*/


    @Override
    public void upLoading(final Observer<List<Object>> observer, Object upLoadObject) {
       final BattleEntity entity= (BattleEntity) upLoadObject;
        Log.i(TAG, "setUpLoadObject: "+entity);
        this.observable= Observable.create(new Observable.OnSubscribe<List<Object>>() {
            @Override
            public void call(Subscriber<? super List<Object>> subscriber) {
                entity.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        Log.i(TAG, "send done: "+s);
                        if (e==null){
                            observer.onNext(null);
                        }else {
                            if (e.getErrorCode()!=9015){
                                Throwable throwable=new Throwable(ResponseCodeUtils.TransForm(e.getErrorCode()));
                                observer.onError(throwable);
                            }
                        }
                    }
                });

            }
        });
        this.observable.subscribe(observer);
    }
}
