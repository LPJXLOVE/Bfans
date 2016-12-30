package com.technology.lpjxlove.bfans.Repository.Task;

import com.technology.lpjxlove.bfans.Bean.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by LPJXLOVE on 2016/10/21.
 */

public class ModifyNickTask extends Task<Object> {
    @Override
    public void Loading(Observer<List<Object>> observer) {

    }


    @Override
    public void upLoading(final Observer<List<Object>> observer, final Object upLoadObject) {
        this.observable= Observable.create(new Observable.OnSubscribe<List<Object>>() {
            @Override
            public void call(Subscriber<? super List<Object>> subscriber) {
                User user= BmobUser.getCurrentUser(User.class);
                user.setNickName(upLoadObject.toString());
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){
                            List<Object> list=new ArrayList<Object>();
                            list.add("success");
                            observer.onNext(list);
                        }else {
                            Throwable throwable=new Throwable(e.getMessage());
                            observer.onError(throwable);
                        }
                    }
                });
            }
        });
        this.observable.subscribe(observer);
    }
}
