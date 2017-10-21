package com.technology.lpjxlove.bfans.UI.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.lpjxlove.bfans.MVP.MainPresenter;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.UI.QrScanActivity;
import com.technology.lpjxlove.bfans.UI.SelectCityActivity;
import com.technology.lpjxlove.bfans.Util.ActivityUtils;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.LocationUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPagerFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @InjectView(R.id.tab_layout)
    TabLayout mTabLayout;
    @InjectView(R.id.iv_qr_scan)
    ImageView ivQrScan;
    @InjectView(R.id.tv_location)
    TextView tvLocation;
    @InjectView(R.id.relative_layout)
    RelativeLayout relativeLayout;
    @InjectView(R.id.view_pager)
    ViewPager mViewPager;
    boolean isFirst;
    private static final int SELECT_CITY_TASK = 0x12;
    private LocationUtils locationUtils;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private onPageChangeListener mListener;
    private MainPresenter mainPresenter;

    public MainPresenter getMainPresenter() {
        return mainPresenter;
    }

    public void setMainPresenter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    public MainPagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainPagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainPagerFragment newInstance(String param1, String param2) {
        MainPagerFragment fragment = new MainPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_pager, container, false);
        ButterKnife.inject(this, view);
        initView();
        return view;
    }


    private void initView() {
        final List<Fragment> list = new ArrayList<>();
        BattleFragment battleFragment = new BattleFragment();
        CircleFragment circleFragment = new CircleFragment();
        list.add(battleFragment);
        list.add(circleFragment);
        final FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
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
                mListener.onPageChange(isFirst);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //定位
        locationUtils = new LocationUtils(getActivity());
        locationUtils.setOnFinishListener(new LocationUtils.OnFinishListener() {
            @Override
            public void onFinish() {
                initLocation();
            }
        });
        locationUtils.startLocation();



    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onPageChangeListener){
            mListener= (onPageChangeListener) context;
        }else {
            throw new IllegalStateException("继承参数错误！");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
       mListener=null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initLocation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        locationUtils.onDestroy();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.iv_qr_scan, R.id.tv_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_qr_scan:
                ActivityUtils.gotoActivity(getActivity(), QrScanActivity.class);
                break;
            case R.id.tv_location:
                ActivityUtils.gotoActivityForResult(getActivity(), SelectCityActivity.class, SELECT_CITY_TASK);
                break;
        }
    }

    private void initLocation(){
        if (tvLocation!=null){
            tvLocation.setText(Constant.Config.cityName);
        }
    }



    public interface onPageChangeListener{
        void onPageChange(boolean isFirst);
    }



}
