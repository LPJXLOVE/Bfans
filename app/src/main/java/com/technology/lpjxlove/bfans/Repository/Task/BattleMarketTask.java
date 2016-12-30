package com.technology.lpjxlove.bfans.Repository.Task;

import android.util.Log;

import com.technology.lpjxlove.bfans.Bean.BattleEntity;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.ResponseCodeUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import hugo.weaving.DebugLog;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
public class BattleMarketTask extends Task<BattleEntity> {
    private static int pager;

    @DebugLog
    @Override
    public void Loading(final Observer<List<BattleEntity>> observer) {
        Log.i(Constant.TAG, "pager: "+pager);
        this.observable= Observable.create(new Observable.OnSubscribe<List<BattleEntity>>() {
            @Override
            public void call(Subscriber<? super List<BattleEntity>> subscriber) {
                BmobQuery<BattleEntity> query=new BmobQuery<>();
                query.order("-createdAt");
                query.include("Author");
                query.setLimit(5);
                if (loadingWays== Constant.LOADING_MORE_TASK){
                    query.setSkip(pager*5);

                }else {
                    query.setSkip(0);
                }
                query.findObjects(new FindListener<BattleEntity>() {
                    @Override
                    public void done(List<BattleEntity> list, BmobException e) {
                        if (e==null){

                            if (list!=null){
                                Log.i(Constant.TAG, "done: Battle size"+list.size());
                                if (loadingWays==Constant.LOADING_MORE_TASK ||loadingWays==Constant.INIT_DATA_TASK &&list.size()!=0){
                                    PlusPager();
                                }

                                observer.onNext(list);

                            }

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


    private void PlusPager(){
        pager++;

    }

}
