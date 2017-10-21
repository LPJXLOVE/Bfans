package com.technology.lpjxlove.bfans.UI.CustomView.PreviewPhoto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.technology.lpjxlove.bfans.Bean.ImageBean;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.UI.Fragment.ImageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PreviewActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    @InjectView(R.id.view_pager)
    MultipleTouchViewPager mViewPager;
    private List<ImageFragment> mImageFragmentData;
    private int location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.inject(this);
        ArrayList<String> image=getIntent().getStringArrayListExtra("image");
        location=getIntent().getIntExtra("position",-1);
        if (image==null){
            return;
        }
        mImageFragmentData =new ArrayList<>();
        for (String s:image){
            ImageFragment imageFragment=ImageFragment.newInstance(s);
            mImageFragmentData.add(imageFragment);
        }

        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
              return mImageFragmentData.get(position);
            }

            @Override
            public int getCount() {
                return mImageFragmentData.size();
            }


        };
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(location);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
