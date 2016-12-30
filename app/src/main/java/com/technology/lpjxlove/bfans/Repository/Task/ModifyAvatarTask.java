package com.technology.lpjxlove.bfans.Repository.Task;

import android.util.Log;

import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.Util.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by LPJXLOVE on 2016/10/21.
 */

public class ModifyAvatarTask extends Task<Object> {
    @Override
    public void Loading(Observer observer) {

    }
    @Override
    public void upLoading(final Observer<List<Object>> observer, final Object upLoadObject) {
      this.observable=Observable.create(new Observable.OnSubscribe<List<Object>>() {
          @Override
          public void call(Subscriber<? super List<Object>> subscriber) {
              String picPath= (String) upLoadObject;
              final BmobFile bmobFile=new BmobFile(new File(picPath));
              bmobFile.uploadblock(new UploadFileListener() {
                  @Override
                  public void done(BmobException e) {
                      if (e==null){
                          Log.i(Constant.TAG,"BmobFileUrl"+bmobFile.getUrl());
                          User user = BmobUser.getCurrentUser(User.class);
                          user.setAvatarUrl(bmobFile.getUrl());
                          user.update(new UpdateListener() {
                              @Override
                              public void done(BmobException e) {
                                  if (e==null){
                                      List<Object> list=new ArrayList<>();
                                      list.add("修改成功！");
                                      observer.onNext(list);
                                  }else {
                                      Throwable throwable=new Throwable(e.getMessage());
                                      observer.onError(throwable);
                                  }
                              }
                          });
                      }else {
                          Throwable t=new Throwable(e.getMessage());
                          observer.onError(t);

                      }
                  }
              });

          }
      });
        this.observable.subscribe(observer);

    }
}
