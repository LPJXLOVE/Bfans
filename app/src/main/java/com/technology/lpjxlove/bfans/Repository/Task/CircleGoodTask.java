package com.technology.lpjxlove.bfans.Repository.Task;

import android.util.Log;

import com.technology.lpjxlove.bfans.Bean.Comment;
import com.technology.lpjxlove.bfans.Bean.Likes;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.ResponseCodeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;
import rx.Observer;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
public class CircleGoodTask extends Task<Object> {

    @Override
    public void Loading(Observer observer) {

    }
    @Override
    public void upLoading(final Observer<List<Object>> observer, Object upLoadObject) {
        Likes likes= (Likes) upLoadObject;
        String cloudCodeName="Like";
        JSONObject params=new JSONObject();
        try {
            params.put("postId",likes.getPostId());
            params.put("userId",likes.getUserId());
            params.put("way",likes.getWay());


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
                    Log.i(Constant.TAG, "LikeDone: "+o.toString());

                }else {

                    Log.e(Constant.TAG, "LikeDone: "+e.getErrorCode()+e.getMessage());
                    if (e.getErrorCode()!=9015){
                        Throwable throwable=new Throwable(ResponseCodeUtils.TransForm(e.getErrorCode()));
                        observer.onError(throwable);
                    }

                }
            }
        });


    }



}
