package com.technology.lpjxlove.bfans.Repository.Task;

import android.util.Log;

import com.technology.lpjxlove.bfans.Bean.BattleJoinEntity;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.ResponseCodeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;
import rx.Observer;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
public class BattleJoinTask extends Task<BattleJoinEntity> {
    @Override
    public void Loading(Observer<List<BattleJoinEntity>> observer) {

    }
    @Override
    public void upLoading(final Observer<List<Object>> observer, Object upLoadObject) {
        BattleJoinEntity joinEntity= (BattleJoinEntity) upLoadObject;
        String cloudCodeName="JoinBattle";
        JSONObject params=new JSONObject();
        try {
            params.put("BattleId",joinEntity.getBattleEntity().getObjectId());
            params.put("userId",joinEntity.getJoinInPeople().getObjectId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AsyncCustomEndpoints cloudCode=new AsyncCustomEndpoints();
        cloudCode.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object o, BmobException e) {
                if (e==null){
                    List<Object> data=new ArrayList<>();
                    data.add(o);
                    observer.onNext(data);
                    Log.i(Constant.TAG, "JoinDone: "+o.toString());

                }else {
                    if (e.getErrorCode()!=9015){
                        Throwable throwable=new Throwable(ResponseCodeUtils.TransForm(e.getErrorCode()));
                        observer.onError(throwable);
                    }

                }
            }
        });

/*
        joinEntity.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    observer.onNext(null);

                }else {
                    Throwable throwable=new Throwable(e.getLocalizedMessage());
                    observer.onError(throwable);

                }

            }
        });*/

    }
}
