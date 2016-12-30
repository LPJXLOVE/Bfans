package com.technology.lpjxlove.bfans.MVP;

import com.technology.lpjxlove.bfans.Bean.Entity;
import com.technology.lpjxlove.bfans.Interface.BasePresenter;
import com.technology.lpjxlove.bfans.MyApplication;
import com.technology.lpjxlove.bfans.Repository.RepositoryManager;
import com.technology.lpjxlove.bfans.Util.Constant;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
public class MainPresenter implements BasePresenter<Entity> {
    private MainView mainView;
    private MyApplication myApplication;
    private int ways;
    private  RepositoryManager m;
    private List<Entity> data;
    private String object1,object2;
    private int taskId;

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    @Inject
    public MainPresenter(MyApplication myApplication) {
       this.myApplication=myApplication;

    }

    @Override
    public void onLoading(int taskID, int ways,Object...objects) {
        this.ways=ways;
        if (m==null){
            m=myApplication.getRepositoryComponent().getRepositoryManager();
        }
        if (objects.length>0){
            m.setObject(objects[0]);
        }
        m.setPresenter(this);
        m.setLoadingWays(ways);
        m.setTaskID(taskID);
        m.LoadingDataFromRemote();

    }
    @SuppressWarnings("unchecked")
    @Override
    public void onLoadingSuccess(List<Entity> list) {
        mainView.OnSuccess();
        if (taskId==Constant.CIRCLE_LIKES_TASK){
            return;
        }

        if (ways== Constant.INIT_DATA_TASK){
            this.data=list;
            mainView.setAdapter(data);
            object1=data.get(0).getObjectId();
            object2=data.get(data.size()-1).getObjectId();
        }else {


            if (ways==Constant.REFRESH_TASK){//剔除重复的item

                for (Entity o:list){
                    if (o.getObjectId().equals(object1)){
                        break;
                    }else {
                        data.add(0,o);
                    }
                }
                object1=data.get(0).getObjectId();
                mainView.RefreshAdapter(data,0);

            }else {
                //用于标识RecycleView要滚动到的位置
                int position=data.size();
                for (Entity e:list){
                    data.add(e);
                }

                mainView.RefreshAdapter(data,position);
            }



        }
    }


    public void UpLoading(int taskID, Object... objects){
        this.taskId=taskID;
        if (m==null){
            m=myApplication.getRepositoryComponent().getRepositoryManager();
        }
        m.setContext(myApplication);
        m.setPresenter(this);
        m.setTaskID(taskID);
        m.UpLoadDataToRemote(objects[0]);
    }




    @Override
    public void onLoadingFailed(String tip) {
        mainView.showErrorFrame(tip);
    }

}
