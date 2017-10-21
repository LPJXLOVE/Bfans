package com.technology.lpjxlove.bfans.Repository.Task;

import android.util.Log;

import com.technology.lpjxlove.bfans.Bean.CircleDetailEntity;
import com.technology.lpjxlove.bfans.Bean.CircleEntity;
import com.technology.lpjxlove.bfans.Bean.Comment;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.NetRequestQueue;
import com.technology.lpjxlove.bfans.Util.ResponseCodeUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by LPJXLOVE on 2016/10/29.
 */

public class CircleDetailTask extends Task<CircleDetailEntity> {

    private static CircleDetailTask Instance;
    private static int pager;
    private static int tempPager;




    /**
     * 单例模式
     * @return
     */
    public static CircleDetailTask getInstance() {
        if (Instance==null){
            Instance=new CircleDetailTask();
        }
        return Instance;
    }




    @Override
    public void Loading(final Observer<List<CircleDetailEntity>> observer) {

        this.observable= Observable.create(new Observable.OnSubscribe<List<CircleDetailEntity>>() {
            @Override
            public void call(Subscriber<? super List<CircleDetailEntity>> subscriber) {
                final CircleDetailEntity circleDetailEntity=new CircleDetailEntity();
                final CircleEntity circleEntity=new CircleEntity();
                circleEntity.setObjectId(UpLoadObject.toString());
                BmobQuery<CircleEntity> CQuery=new BmobQuery<>();
                CQuery.getObject(UpLoadObject.toString(), new QueryListener<CircleEntity>() {
                    @Override
                    public void done(final CircleEntity circleEntity, BmobException e) {
                        if (e==null){
                            circleDetailEntity.setCircleEntity(circleEntity);
                            BmobQuery<User> query=new BmobQuery<>();
                            query.setLimit(12);
                            query.addWhereRelatedTo("Like",new BmobPointer(circleEntity));
                            query.findObjects(new FindListener<User>() {
                                @Override
                                public void done(List<User> list, BmobException e) {
                                    if (e==null){
                                        if (list.size()!=0){
                                            circleDetailEntity.setLikes(list);
                                        }

                                        //query comment data
                                        BmobQuery<Comment> bQuery=new BmobQuery<>();
                                        bQuery.setLimit(5);
                                        if (loadingWays==Constant.INIT_DATA_TASK||loadingWays==Constant.REFRESH_TASK){
                                            pager=0;
                                        }else {
                                            bQuery.setSkip(tempPager*5);
                                        }

                                        bQuery.include("Author");
                                        bQuery.order("+createAt");
                                        bQuery.addWhereRelatedTo("Comment",new BmobPointer(circleEntity));
                                        bQuery.findObjects(new FindListener<Comment>() {
                                            @Override
                                            public void done(List<Comment> list, BmobException e) {
                                                if (e==null){
                                                    if (list.size()!=0){
                                                      /*  int s=list.size()-1;
                                                        List<Comment> comments=new ArrayList<Comment>();
                                                        for (int i = s; i >-1 ; i--) {
                                                            Comment c=list.get(i);
                                                            comments.add(c);
                                                        }*/
                                                        circleDetailEntity.setComments(list);
                                                    }
                                                    List<CircleDetailEntity> data=new ArrayList<>();
                                                    data.add(circleDetailEntity);
                                                    if (loadingWays!=Constant.REFRESH_TASK&&list.size()!=0){
                                                        pager++;
                                                        tempPager=pager;
                                                    }
                                                    observer.onNext(data);


                                                    Log.i(Constant.TAG, "CircleDetailCommentDone: "+list.get(0).getContent());
                                                }else {
                                                    Log.e(Constant.TAG, "CircleDetailCommentDone: "+e.getErrorCode()+e.getMessage());
                                                    if (e.getErrorCode()!=9015){
                                                        Throwable t=new Throwable(ResponseCodeUtils.TransForm(e.getErrorCode()));
                                                        observer.onError(t);
                                                    }else {
                                                        observer.onError(new Throwable("otherError"));
                                                    }
                                                }

                                            }
                                        });
                                        //Log.i(Constant.TAG, "CircleDetailLikeDoneError: "+list.get(0).getNickName());
                                    }else {
                                        Log.e(Constant.TAG, "CircleDetailLikeDoneError: "+e.getErrorCode()+e.getMessage());
                                        if (e.getErrorCode()!=9015){
                                            Throwable t=new Throwable(ResponseCodeUtils.TransForm(e.getErrorCode()));
                                            observer.onError(t);
                                        }
                                    }
                                }
                            });


                        }else {
                            Log.e(Constant.TAG, "CircleEntityDone: "+e.getErrorCode()+e.getMessage());
                            if (e.getErrorCode()!=9015){
                                Throwable t=new Throwable(ResponseCodeUtils.TransForm(e.getErrorCode()));
                                observer.onError(t);
                            }
                        }

                    }
                });



















            }
        });
        this.observable.subscribe(observer);
    }
}
