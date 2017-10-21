package com.technology.lpjxlove.bfans.Repository.Task;

import android.util.Log;

import com.technology.lpjxlove.bfans.Bean.MyBmobInstallation;
import com.technology.lpjxlove.bfans.Bean.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.Observer;

/**
 * Created by LPJXLOVE on 2017/1/12.
 */

public class UpdateInstallation extends Task<String> {


    @Override
    public void Loading(Observer<List<String>> observer) {

        BmobQuery<MyBmobInstallation> query=new BmobQuery<>();
        query.addWhereEqualTo("installationId",UpLoadObject);
        query.findObjects(new FindListener<MyBmobInstallation>() {
            @Override
            public void done(List<MyBmobInstallation> list, BmobException e) {
                if (e==null){
                    if (list.size()>0) {

                      /*  MyBmobInstallation m=list.get(0);
                        m.setUid(BmobUser.getCurrentUser(User.class).getObjectId());
                        m.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    Log.d("Task", "installation update done");
                                }else {
                                    Log.d("Task", "installation update failed");
                                }

                            }
                        });*/
                        MyBmobInstallation m=list.get(0);
                        List<String> channels=new ArrayList<>();
                        channels.add("test");
                        m.setChannels(channels);
                        m.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e!=null){
                                    Log.d("test",e.getMessage());
                                }
                            }
                        });
                    }
                }else {
                    Log.d("Task", "failed");
                }
            }
        });

    }
}
