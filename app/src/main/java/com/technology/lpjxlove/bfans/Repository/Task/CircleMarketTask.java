package com.technology.lpjxlove.bfans.Repository.Task;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.technology.lpjxlove.bfans.Bean.CircleEntity;
import com.technology.lpjxlove.bfans.Bean.CircleEntityJsonParse;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.ResponseCodeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.helper.GsonUtil;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.FindListener;
import hugo.weaving.DebugLog;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
public class CircleMarketTask extends Task<CircleEntity> {
    private static int pager;
    @DebugLog
    @Override
    public void Loading(final Observer<List<CircleEntity>> observer) {
        Log.i("test","execute ");
        this.observable=Observable.create(new Observable.OnSubscribe<List<CircleEntity>>() {
            @Override
            public void call(Subscriber<? super List<CircleEntity>> subscriber) {
                int skip;
                User  user= BmobUser.getCurrentUser(User.class);
                String cloudCodeName="CircleMarket";
                JSONObject params=new JSONObject();
                if (loadingWays== Constant.LOADING_MORE_TASK){
                    skip=pager*10;

                }else {
                    skip=0;
                }

                try {
                    params.put("userId",user.getObjectId());
                    params.put("skip",skip);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AsyncCustomEndpoints cloudCode=new AsyncCustomEndpoints();
                cloudCode.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
                    @Override
                    public void done(Object o, BmobException e) {
                        if (e==null){
                            List<CircleEntity> data=new ArrayList<>();

                            Gson gson=new Gson();
                            CircleEntityJsonParse circleEntityJsonParse
                                    = gson.fromJson(o.toString(), CircleEntityJsonParse.class);
                            List<CircleEntityJsonParse.ResultsBean> resultsBeen=circleEntityJsonParse.getResults();
                            for(CircleEntityJsonParse.ResultsBean results:resultsBeen){
                                CircleEntity circleEntity=new CircleEntity();
                                User u=new User();
                                u.setAvatarUrl(results.getAuthor().getAvatarUrl());
                                u.setObjectId(results.getAuthor().getObjectId());
                                u.setNickName(results.getAuthor().getNickName());
                                u.setLevelCount(results.getAuthor().getLevelCount());
                                u.setUsername(results.getAuthor().getUsername());
                                circleEntity.setAuthor(u);
                                circleEntity.setContent(results.getContent());
                                circleEntity.setCreateAt(results.getCreatedAt());
                                circleEntity.setBitmapUrl(results.getBitmapUrl());
                                circleEntity.setObjectId(results.getObjectId());
                                circleEntity.setLike(results.isIsLike());
                                circleEntity.setLikeCount(results.getLikeCount());
                                circleEntity.setCommentCount(results.getCommentCount());
                                data.add(circleEntity);
                            }
                            if (loadingWays==Constant.LOADING_MORE_TASK ||loadingWays==Constant.INIT_DATA_TASK){
                                if (data.size()!=0){
                                    PlusPager();
                                }
                            }
                            observer.onCompleted();
                            observer.onNext(data);




                            Log.i(Constant.TAG, "CircleDone: pager is "+pager);

                        }else {
                            Log.i(Constant.TAG, "CircleMarketError: "+e.getMessage()+e.getErrorCode());
                            if (e.getErrorCode()!=9015){
                                Throwable throwable=new Throwable(ResponseCodeUtils.TransForm(e.getErrorCode()));
                                observer.onError(throwable);
                            }

                        }
                    }
                });


            }
        });
        this.observable.subscribeOn(Schedulers.newThread());
        this.observable.subscribe(observer);



    }
    private void PlusPager(){
        pager++;

    }


}
