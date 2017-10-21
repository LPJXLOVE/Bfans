package com.technology.lpjxlove.bfans.UI;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.technology.lpjxlove.bfans.Adapter.MyBattleAdapter;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.Util.ImageUtils;
import com.technology.lpjxlove.bfans.Util.WindowsUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyBattleActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.recycle_view)
    RecyclerView recycleView;
    @InjectView(R.id.tab_layout)
    AppBarLayout tabLayout;
    @InjectView(R.id.iv_background)
    ImageView ivBackground;
    @InjectView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    private int statusBarHeight=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_battle);
        ButterKnife.inject(this);
        setToolBarView();
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycleView.setHasFixedSize(true);
        // Toast.makeText(this, "测试3", Toast.LENGTH_SHORT).show();
        recycleView.setAdapter(new MyBattleAdapter());

    }

    /**
     * 设置toolBar
     */
    private void setToolBarView() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        int height= (int) WindowsUtils.dp2px(getApplicationContext(),56)+statusBarHeight;
        CollapsingToolbarLayout.LayoutParams p=new CollapsingToolbarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                ,height);
        p.setCollapseMode(CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN);
        toolbar.setLayoutParams(p);
        toolbar.setPadding(0,statusBarHeight,0,0);
        Bitmap b = ImageUtils.ProvideSmallBitmap(getResources(), R.drawable.my_battle_background, 200, 200);
        ivBackground.setImageBitmap(b);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

    }
}
