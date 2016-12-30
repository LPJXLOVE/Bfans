package com.technology.lpjxlove.bfans.MVP;

import android.util.Log;
import android.widget.Toast;

import com.technology.lpjxlove.bfans.Interface.BasePresenter;
import com.technology.lpjxlove.bfans.MyApplication;
import com.technology.lpjxlove.bfans.Repository.RepositoryManager;
import com.technology.lpjxlove.bfans.Util.Constant;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by LPJXLOVE on 2016/9/17.
 */
public class SendTaskPresenter implements BasePresenter<Object> {
    private SendTaskView sendTaskView;
    private MyApplication application;
    private RepositoryManager m;
    private int taskId;
    @Inject
    public SendTaskPresenter(MyApplication myApplication) {
        this.application=myApplication;
    }

    public void setSendTaskView(SendTaskView sendTaskView) {
        this.sendTaskView = sendTaskView;
    }

    @Override
    public void onLoading(int taskID, int ways,Object...objects) {
        this.taskId=taskID;
        if (m==null){
            m=application.getRepositoryComponent().getRepositoryManager();
        }
        m.setContext(application);
        m.setPresenter(this);
        m.setTaskID(taskID);
        m.setObject(objects[0]);
        m.LoadingDataFromRemote();
    }

    @Override
    public void onLoadingSuccess(List<Object> data) {
        if (taskId== Constant.BATTLE_JOIN_TASK){

            sendTaskView.showErrorFrame(data.get(0).toString());//无法失败还是成功都回调这个
        }else {

            if (taskId==Constant.BATTLE_JOIN_PEOPLE_TASK){//约战成员加载
                sendTaskView.setData(data);
            }
            sendTaskView.showSuccess();


        }

    }


    public void UpLoading(int taskID, Object... objects){
        this.taskId=taskID;
        sendTaskView.LoadingData(taskID,0);
        if (m==null){
            m=application.getRepositoryComponent().getRepositoryManager();
        }
        m.setContext(application);
        m.setPresenter(this);
        m.setTaskID(taskID);
        m.UpLoadDataToRemote(objects[0]);

    }



    @Override
    public void onLoadingFailed(String tip) {
        sendTaskView.showErrorFrame(tip);
    }
}
