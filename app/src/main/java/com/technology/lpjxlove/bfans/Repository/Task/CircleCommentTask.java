package com.technology.lpjxlove.bfans.Repository.Task;

import android.util.Log;

import com.technology.lpjxlove.bfans.Bean.BattleJoinEntity;
import com.technology.lpjxlove.bfans.Bean.Comment;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.ResponseCodeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
public class CircleCommentTask extends Task<Object> {

    @Override
    public void Loading(Observer<List<Object>> observer) {

    }

    @Override
    public void upLoading(final Observer<List<Object>> observer, Object upLoadObject) {
        Comment comment= (Comment) upLoadObject;
        String cloudCodeName="Comment";
        JSONObject params=new JSONObject();
        try {
            params.put("postId",comment.getObjectId());
            params.put("userId",comment.getAuthor().getObjectId());
            params.put("content",comment.getContent());

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
                    Log.i(Constant.TAG, "CommentDone: "+o.toString());

                }else {
                    Log.e(Constant.TAG, "CommentDone: "+e.getErrorCode()+e.getMessage());
                    if (e.getErrorCode()!=9015){
                        Throwable throwable=new Throwable(ResponseCodeUtils.TransForm(e.getErrorCode()));
                        observer.onError(throwable);
                    }

                }
            }
        });


    }
}
