package com.technology.lpjxlove.bfans.UI;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import com.technology.lpjxlove.bfans.MVP.DaggerMainComponent;
import com.technology.lpjxlove.bfans.MVP.MainModule;
import com.technology.lpjxlove.bfans.MVP.MainPresenter;
import com.technology.lpjxlove.bfans.MyApplication;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.UI.Fragment.CircleFragment;
import com.technology.lpjxlove.bfans.UI.Fragment.MainFragment;
import com.technology.lpjxlove.bfans.UI.Fragment.MainPagerFragment;
import com.technology.lpjxlove.bfans.UI.Fragment.MyFragment;
import com.technology.lpjxlove.bfans.Util.ActivityUtils;
import com.technology.lpjxlove.bfans.Util.FragmentUtils;
import com.technology.lpjxlove.bfans.Util.LocationUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.push.lib.service.PushService;
import hugo.weaving.DebugLog;

public class MainActivity extends AppCompatActivity implements MainPagerFragment.onPageChangeListener {
   /* @InjectView(R.id.view_pager)
    ViewPager mViewPager;*/
    @InjectView(R.id.design_navigation_view)
   SpaceNavigationView mBottomNavigationView;
    private boolean isFirst = true;
    private MainPagerFragment mainPagerFragment;
    private MyFragment myFragment;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        //  BmobPush.startWork(this);
        initBottomNavigationBar();
       // initMainPresenter();
        mainPagerFragment = MainPagerFragment.newInstance("123", "123");
        myFragment=MyFragment.newInstance("123","123");
        FragmentUtils.add(this, R.id.frame, mainPagerFragment);
        FragmentUtils.add(this,R.id.frame,myFragment);
        FragmentUtils.hide(this,myFragment);
        mBottomNavigationView.initWithSaveInstanceState(savedInstanceState);

    }

   /* private void initMainPresenter() {
        DaggerMainComponent.builder()
                .mainModule(new MainModule((MyApplication)getApplication()))
                .build()
                .inject(this);
    }*/

    private void initBottomNavigationBar() {
        mBottomNavigationView.addSpaceItem(new SpaceItem("主页",R.drawable.ic_home_black_24dp));
       // mBottomNavigationView.addSpaceItem(new SpaceItem("其它",R.drawable.ic_news));
      //  mBottomNavigationView.addSpaceItem(new SpaceItem("消息",R.drawable.ic_textsms_black_24dp));
        mBottomNavigationView.addSpaceItem(new SpaceItem("我",R.drawable.ic_person_black_24dp));
        //mBottomNavigationView.showIconOnly();
        mBottomNavigationView.setCentreButtonIconColorFilterEnabled(false);
        //mBottomNavigationView.showBadgeAtIndex(0,1, Color.RED);
        mBottomNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                if (isFirst){
                    ActivityUtils.gotoActivity(MainActivity.this,AddBattleActivity.class);
                }else {
                    ActivityUtils.gotoActivity(MainActivity.this,AddCircleActivity.class);
                }
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
               switch (itemIndex){
                   case 0:
                       FragmentUtils.hide(MainActivity.this,myFragment);
                       FragmentUtils.show(MainActivity.this,mainPagerFragment);
                       break;
                   case 1:
                       FragmentUtils.hide(MainActivity.this,mainPagerFragment);
                       FragmentUtils.show(MainActivity.this,myFragment);
                       break;
               }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });
    }





    @Override
    protected void onResume() {
        super.onResume();
        // frame.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //frame.setVisibility(View.GONE);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public void startService() {

        //启动保活服务
        Intent intent = new Intent(this, PushService.class);
        startService(intent);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mainPagerFragment.onActivityResult(requestCode,resultCode,data);
       // Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
    }

    //点击返回桌面，不退出软件
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }


    @Override
    public void onPageChange(boolean isFirst) {
        this.isFirst=isFirst;
    }
}
