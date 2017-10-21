package com.technology.lpjxlove.bfans.UI;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.Util.ActivityUtils;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.LocationUtils;

import java.lang.ref.WeakReference;

import cn.bmob.v3.BmobUser;
import hugo.weaving.DebugLog;

public class SplashActivity extends Activity implements LocationUtils.OnFinishListener {
  //  private LocationUtils locationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();

    }


    private void init() {
       /* locationUtils=new LocationUtils(this);
        locationUtils.startLocation();
*/
        MyHandle myHandle = new MyHandle(this);
        Message message = myHandle.obtainMessage();
        User user = BmobUser.getCurrentUser(User.class);
        if (user == null) {
            message.what = Constant.GO_REGISTER;
        } else {
            message.what = Constant.GO_HOME;
        }
        myHandle.sendMessageDelayed(message, 0);

    }






    @Override
    public void onFinish() {

    }


    static class MyHandle extends Handler{
        WeakReference<SplashActivity> activity;

        public MyHandle(SplashActivity activity) {
          this.activity=new WeakReference<SplashActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Activity a=activity.get();
            if (a!= null) {
                switch (msg.what){
                    /*进入主页面*/
                    case Constant.GO_HOME:
                        ActivityUtils.gotoActivity(a,MainActivity.class);
                        a.finish();
                        break;
                    /*进入登录页面*/
                    case Constant.GO_LOGIN:
                        ActivityUtils.gotoActivity(a,LoginActivity.class);
                        a.finish();
                        break;
                    /*进入注册页面*/
                    case Constant.GO_REGISTER:
                        ActivityUtils.gotoActivity(a,RegisterActivity.class);
                        a.finish();
                        break;
                }

            }


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  locationUtils.onDestroy();
    }
}
