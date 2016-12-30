package com.technology.lpjxlove.bfans.UI;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.technology.lpjxlove.bfans.Interface.Callback;
import com.technology.lpjxlove.bfans.MVP.DaggerMainComponent;
import com.technology.lpjxlove.bfans.MVP.MainModule;
import com.technology.lpjxlove.bfans.MVP.MainPresenter;
import com.technology.lpjxlove.bfans.MyApplication;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.Service.KeepAliveService;
import com.technology.lpjxlove.bfans.UI.Fragment.BattleFragment;
import com.technology.lpjxlove.bfans.UI.Fragment.CircleFragment;
import com.technology.lpjxlove.bfans.Util.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

public class MainActivity extends AppCompatActivity implements Callback {
    @InjectView(R.id.iv_qr_scan)
    ImageView ivMessage;
    @InjectView(R.id.floating_my)
    FloatingActionButton floatingMy;
    @InjectView(R.id.floating_add)
    FloatingActionButton floatingAdd;
    @InjectView(R.id.floating_setting)
    FloatingActionButton floatingSetting;
    @InjectView(R.id.floating_home)
    FloatingActionButton floatingHome;
    @InjectView(R.id.frame)
    FrameLayout frame;
    @InjectView(R.id.tv_location)
    TextView tvLocation;
    private boolean isExpand;
    private boolean isFirst = true;


    @Inject
    MainPresenter mainPresenter;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_square);
        ButterKnife.inject(this);
        startService();
        initPresenter();


        final List<Fragment> list = new ArrayList<>();
        BattleFragment battleFragment = new BattleFragment();
        CircleFragment circleFragment = new CircleFragment();
        list.add(battleFragment);
        list.add(circleFragment);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);
        final FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public CharSequence getPageTitle(int position) {
                List<String> title = new ArrayList<>();
                title.add("战场");
                title.add("动态");
                return title.get(position);
            }

            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        };
        mViewPager.setAdapter(fragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                isFirst = position == 0;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        battleFragment.setCallBack(this);
        if (mainPresenter==null){
            initPresenter();
        }
        battleFragment.setPresenter(mainPresenter);
        circleFragment.setCallback(this);
        MainPresenter circlePresenter=new MainPresenter((MyApplication)getApplication());
        circleFragment.setPresenter(circlePresenter);


    }
    //初始化Presenter
    private void initPresenter() {
        DaggerMainComponent.builder()
                .mainModule(new MainModule((MyApplication) getApplication()))
                .build()
                .inject(this);
    }

    @OnClick({R.id.iv_qr_scan, R.id.floating_my, R.id.floating_add, R.id.floating_setting, R.id.floating_home,R.id.tv_location})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_location:


                break;
            case R.id.iv_qr_scan:
                ActivityUtils.gotoActivity(this, QrScanActivity.class);
                break;
            case R.id.floating_my:
                ActivityUtils.gotoActivity(this, MyActivity.class);
                break;
            case R.id.floating_add:
                if (isFirst) {

                   ActivityUtils.gotoActivity(this, AddBattleActivity.class);
                    //ActivityUtils.gotoActivityWithAnim(this,AddBattleActivity.class,floatingAdd);TransitionsHeleper.startAcitivty((Activity) mContext, RvDetailActivity.class,
                   // TransitionsHeleper.startActivity(this,AddBattleActivity.class,floatingAdd);


                } else {
                    ActivityUtils.gotoActivity(this, AddCircleActivity.class);
                }
                break;
            case R.id.floating_setting:

                break;
            case R.id.floating_home:
                addAnimation();
                break;
        }
    }


    private void addAnimation() {
        if (!isExpand) {
            Animator a = AnimatorInflater.loadAnimator(this, R.animator.rorate_x);
            Animator b = AnimatorInflater.loadAnimator(this, R.animator.rorate_y);
            Animator c = AnimatorInflater.loadAnimator(this, R.animator.rorate_xy);
            a.setTarget(floatingMy);
            b.setTarget(floatingAdd);
            c.setTarget(floatingSetting);
            a.start();
            b.start();
            c.start();
            isExpand = true;
        } else {
            Animator d = AnimatorInflater.loadAnimator(this, R.animator.rorate_x_);
            Animator e = AnimatorInflater.loadAnimator(this, R.animator.rorate_y_);
            Animator f = AnimatorInflater.loadAnimator(this, R.animator.rorate_xy_);
            d.setTarget(floatingMy);
            e.setTarget(floatingAdd);
            f.setTarget(floatingSetting);
            d.start();
            e.start();
            f.start();
            isExpand = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        frame.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        frame.setVisibility(View.GONE);
    }

    //fragment中的滑动回调
    @Override
    public void OnCallback(Object object) {
        if (isExpand) {
            addAnimation();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public void startService() {

        //启动保活服务
        Intent intent = new Intent(this, KeepAliveService.class);
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

    //点击返回桌面，不退出软件
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
