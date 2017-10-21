package com.technology.lpjxlove.bfans.MVP;

import com.technology.lpjxlove.bfans.Interface.BasePresenter;
import com.technology.lpjxlove.bfans.MyApplication;
import com.technology.lpjxlove.bfans.Repository.RepositoryManager;
import com.technology.lpjxlove.bfans.Util.Constant;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by LPJXLOVE on 2016/9/27.
 */
public class PhotoPresenter implements BasePresenter<Object> {
    private MainView mainView;
    private MyApplication myApplication;
    private   RepositoryManager m;
    @Inject
    public PhotoPresenter(MyApplication myApplication){
        this.myApplication=myApplication;
    }


    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onLoading(int taskID, int ways,Object...objects) {
        if (m==null){
            m=myApplication.getRepositoryComponent().getRepositoryManager();
        }
        m.setContext(myApplication);
        m.setPresenter(this);
        m.setTaskID(taskID);
        m.LoadingDataFromRemote();


    }
    @SuppressWarnings("Unchecked")
    @Override
    public void onLoadingSuccess(List<Object> data) {
        mainView.setAdapter(data);

    }

    @Override
    public void onLoadingFailed(String tip) {

    }

    @Override
    public void onComplete() {

    }
}
