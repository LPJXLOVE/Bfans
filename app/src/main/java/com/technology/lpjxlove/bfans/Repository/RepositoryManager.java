package com.technology.lpjxlove.bfans.Repository;

import android.content.Context;
import android.widget.Toast;

import com.technology.lpjxlove.bfans.Interface.BasePresenter;
import com.technology.lpjxlove.bfans.Repository.Task.BattleAddTask;
import com.technology.lpjxlove.bfans.Repository.Task.BattleJoinPeopleTask;
import com.technology.lpjxlove.bfans.Repository.Task.BattleJoinTask;
import com.technology.lpjxlove.bfans.Repository.Task.BattleMarketTask;
import com.technology.lpjxlove.bfans.Repository.Task.CircleCommentTask;
import com.technology.lpjxlove.bfans.Repository.Task.CircleDetailTask;
import com.technology.lpjxlove.bfans.Repository.Task.CircleGoodTask;
import com.technology.lpjxlove.bfans.Repository.Task.CircleMarketTask;
import com.technology.lpjxlove.bfans.Repository.Task.CircleSendTask;
import com.technology.lpjxlove.bfans.Repository.Task.LoadingLocalPictureTask;
import com.technology.lpjxlove.bfans.Repository.Task.ModifyAvatarTask;
import com.technology.lpjxlove.bfans.Repository.Task.ModifyNickTask;
import com.technology.lpjxlove.bfans.Repository.Task.Task;
import com.technology.lpjxlove.bfans.Repository.Task.UpdateInstallation;
import com.technology.lpjxlove.bfans.Util.Constant;

import java.util.List;
import java.util.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observer;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
@Singleton
public class RepositoryManager<T> {
    private BasePresenter presenter;
    private int TaskID;
    private int LoadingWays;
    private Observer<List<T>> mCallBackObserver;
    private Context context;
    private Object object;

    public void setObject(Object object) {
        this.object = object;
    }

    @Inject
    public RepositoryManager() {

        mCallBackObserver=new Observer<List<T>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                presenter.onLoadingFailed(throwable.getMessage());
            }

            @SuppressWarnings("Unchecked")
            @Override
            public void onNext(List<T> ts) {
                presenter.onLoadingSuccess(ts);
              //  Log.i(Constant.TAG, "onNext: "+ts.size());
            }
        };


    }


    public void LoadingDataFromRemote() {

        Task task=null;
        switch (TaskID) {
            case Constant.BATTLE_MARKET_TASK://约战
                task=new BattleMarketTask();
                task.setLoadingWays(LoadingWays);
                break;
            case Constant.BATTLE_USER_TASK://个人战队页

                break;

            case Constant.BATTLE_JOIN_PEOPLE_TASK://约战成员
                task=new BattleJoinPeopleTask();
                task.setUpLoadObject(object);
                break;
            case Constant.CIRCLE_MARKET_TASK://动态
                task=new CircleMarketTask();
                task.setLoadingWays(LoadingWays);

                break;
            case Constant.CIRCLE_USER_TASK://个人动态页
                break;
            case Constant.REGISTER_TASK:

                break;

            case Constant.LOGIN_TASK:

                break;

            case Constant.UPDATE_INSTALLATION_TASK:
                task=new UpdateInstallation();
                task.setUpLoadObject(object);
                break;


            case Constant.GET_ALBUM_PHOTO_TASK:
                task=new LoadingLocalPictureTask(context);
                break;

            case Constant.QUERY_CIRCLE_COMMENT_AND_LIKES_TASK://评论点赞详情页
                task=CircleDetailTask.getInstance();
                task.setLoadingWays(LoadingWays);
                task.setUpLoadObject(object);
                break;


        }

        if (task!=null){
            task.Loading(mCallBackObserver);
        }

    }


    public void setContext(Context context) {
        this.context = context.getApplicationContext();
    }

    public void UpLoadDataToRemote(Object object){
        Task task=null;
        switch (TaskID){
            case Constant.BATTLE_JOIN_TASK://加入战队
                task=new BattleJoinTask();
                break;
            case Constant.BATTLE_SEND_TASK://新增约战
                task=new BattleAddTask();

                break;
            case Constant.CIRCLE_SEND_TASK://新增动态
                task=new CircleSendTask();
                break;
            case Constant.CIRCLE_COMMENT_TASK://动态评论
                task=new CircleCommentTask();
                break;
            case Constant.CIRCLE_LIKES_TASK://点赞
                task=new CircleGoodTask();
                break;
            case Constant.MODIFY_AVATAR_TASK://修改头像
                task=new ModifyAvatarTask();
                break;
            case Constant.MODIFY_NICK_TASK://修改昵称
                task=new ModifyNickTask();
                break;

        }

        if (task!=null){
            task.upLoading(mCallBackObserver,object);
        }


    }





    public BasePresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(BasePresenter presenter) {
        this.presenter = presenter;
    }

    public int getTaskID() {
        return TaskID;
    }

    public void setTaskID(int taskID) {
        TaskID = taskID;
    }

    public int getLoadingWays() {
        return LoadingWays;
    }

    public void setLoadingWays(int loadingWays) {
        LoadingWays = loadingWays;
    }
}
