package com.technology.lpjxlove.bfans.Repository.Task;

import android.util.Log;

import com.technology.lpjxlove.bfans.Bean.BattleEntity;
import com.technology.lpjxlove.bfans.Bean.BattleJoinEntity;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.ResponseCodeUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
public class BattleJoinPeopleTask extends Task<BattleJoinEntity> {
    @Override
    public void Loading(final Observer<List<BattleJoinEntity>> observer) {
        final BattleEntity battleEntity= (BattleEntity) this.UpLoadObject;
        this.observable=Observable.create(new Observable.OnSubscribe<List<BattleJoinEntity>>() {
            @Override
            public void call(Subscriber<? super List<BattleJoinEntity>> subscriber) {
                BmobQuery<BattleJoinEntity> query=new BmobQuery<>();
                query.addWhereEqualTo("BattleEntity",battleEntity);
                query.include("JoinInPeople");
                query.findObjects(new FindListener<BattleJoinEntity>() {
                    @Override
                    public void done(List<BattleJoinEntity> list, BmobException e) {
                        if (e==null){
                            observer.onNext(list);

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
