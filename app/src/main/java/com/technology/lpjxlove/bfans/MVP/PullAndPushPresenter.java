package com.technology.lpjxlove.bfans.MVP;

import com.technology.lpjxlove.bfans.Bean.CircleDetailEntity;
import com.technology.lpjxlove.bfans.Bean.CircleEntity;
import com.technology.lpjxlove.bfans.Bean.Comment;
import com.technology.lpjxlove.bfans.Bean.Entity;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.Interface.BasePresenter;
import com.technology.lpjxlove.bfans.MyApplication;
import com.technology.lpjxlove.bfans.Repository.RepositoryManager;
import com.technology.lpjxlove.bfans.Util.Constant;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by LPJXLOVE on 2016/10/21.
 */

public class PullAndPushPresenter implements BasePresenter<CircleDetailEntity> {
    private PullAndPushView pullAndPushView;
    private MyApplication myApplication;
    private int ways,taskId;
    private RepositoryManager m;
    private List<CircleDetailEntity> oldData;
    private String firstEntityObject;
    private String lastEntityObject;



    @Inject
    public PullAndPushPresenter(MyApplication myApplication,PullAndPushView pullAndPushView) {
        this.myApplication = myApplication;
        this.pullAndPushView=pullAndPushView;
    }

    @Override
    public void onLoading(int taskID, int ways, Object... objects) {
        this.taskId=taskID;
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
    @SuppressWarnings("Unchecked")
    @Override
    public void onLoadingSuccess(List<CircleDetailEntity> data) {
        if (taskId== Constant.CIRCLE_COMMENT_TASK){
            pullAndPushView.showSuccess("评论成功！");
        }else if (taskId==Constant.CIRCLE_LIKES_TASK){
            pullAndPushView.showSuccess("likes");
        }

            if (data!=null&&data.size()!=0){
                if (taskId==Constant.QUERY_CIRCLE_COMMENT_AND_LIKES_TASK){
                    if (ways==Constant.INIT_DATA_TASK){
                        oldData=data;
                        List<Comment> comments=oldData.get(0).getComments();
                        if (comments!=null){
                            //得到第一个item的ObjectId
                            firstEntityObject=comments.get(0).getObjectId();
                        }else {//comment为空时初始化
                            comments=new ArrayList<>();
                        }
                        oldData.get(0).setComments(comments);
                        List<User> likes=oldData.get(0).getLikes();
                        if (likes==null){//likes为空时初始化
                            likes=new ArrayList<>();
                        }
                        oldData.get(0).setLikes(likes);

                        pullAndPushView.setData(oldData);
                    }
                    else {
                        //set comment data
                        List<Comment> comments=data.get(0).getComments();
                        if (comments!=null){
                            if (ways==Constant.REFRESH_TASK){

                                for (Comment c :comments) {
                                    if (!c.getObjectId().equals(firstEntityObject)){
                                        oldData.get(0).getComments().add(0,c);
                                    }else {
                                        break;
                                    }
                                }
                                firstEntityObject=oldData.get(0).getComments().get(0).getObjectId();
                            }else {//加载更多
                                for (Comment c:comments){
                                    oldData.get(0).getComments().add(c);
                                }

                            }

                        }

                        //set likes data
                       oldData.get(0).setLikes(data.get(0).getLikes());


                        pullAndPushView.refreshAdapter(1,data.get(0).getCircleEntity());


                    }

                }/*else if (taskId==Constant.CIRCLE_LIKES_TASK){
                    oldData.get(0).setLikes(data.get(0).getLikes());
                    pullAndPushView.refreshAdapter(1,data.get(0).getCircleEntity());
                }*/



            }else {
                pullAndPushView.showErrorFrame(null);
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
        if (tip.equals("otherError")){
            pullAndPushView.showErrorFrame(null);
        }else {

            pullAndPushView.showErrorFrame(tip);
        }

    }
}
