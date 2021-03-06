package com.technology.lpjxlove.bfans.UI;

import android.content.Intent;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.Interface.Callback;
import com.technology.lpjxlove.bfans.MVP.DaggerSendComponent;
import com.technology.lpjxlove.bfans.MVP.SendModule;
import com.technology.lpjxlove.bfans.MVP.SendTaskPresenter;
import com.technology.lpjxlove.bfans.MVP.SendTaskView;
import com.technology.lpjxlove.bfans.MyApplication;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.UI.CustomView.WaveView;
import com.technology.lpjxlove.bfans.UI.Dialog.ModifyNameDialog;
import com.technology.lpjxlove.bfans.Util.ActivityUtils;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.ImageUtils;
import com.technology.lpjxlove.bfans.Util.RankTransFormUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class MyActivity extends AppCompatActivity implements Callback, SendTaskView<Object>,WaveView.OnWaveAnimationListener {

    @InjectView(R.id.iv_avatar)
    SimpleDraweeView ivAvatar;
    @InjectView(R.id.tv_rank)
    TextView tvNick;
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_level)
    TextView tvLevel;
    @InjectView(R.id.relativeLayout_level)
    RelativeLayout relativeLayoutLevel;
    @InjectView(R.id.relativeLayout_circle)
    RelativeLayout relativeLayoutCircle;
    @InjectView(R.id.relative_layout_team)
    RelativeLayout relativeLayoutTeam;
    @InjectView(R.id.tv_location)
    TextView tvLocation;
    @InjectView(R.id.tv_battle_object)
    TextView tvBattleObject;
    @InjectView(R.id.tv_battle_type)
    TextView tvBattleType;

    @InjectView(R.id.relative_layout_item)
    RelativeLayout relativeLayoutItem;
    @Inject
    SendTaskPresenter sendPresenter;
    @InjectView(R.id.wave_view)
    WaveView waveView;
    @InjectView(R.id.imageView6)
    ImageView imageView6;
    @InjectView(R.id.textView8)
    TextView textView8;
    @InjectView(R.id.imageView7)
    ImageView imageView7;
    @InjectView(R.id.imageView4)
    ImageView imageView4;
    @InjectView(R.id.textView7)
    TextView textView7;
    @InjectView(R.id.imageView5)
    ImageView imageView5;
    @InjectView(R.id.iv_location)
    ImageView ivLocation;
    @InjectView(R.id.textView2)
    TextView textView2;
    @InjectView(R.id.tv_battle_)
    TextView tvBattle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        User user = BmobUser.getCurrentUser(User.class);
        if (user != null) {//设置头像
            if (user.getAvatarUrl() != null) {
                String path = user.getAvatarUrl();
                final Uri uri = Uri.parse(path);
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                        .setResizeOptions(new ResizeOptions(200, 200))
                        .build();
                DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                        .setOldController(ivAvatar.getController())
                        .setImageRequest(request)
                        .build();
                ivAvatar.setController(draweeController);
            }
            //设置昵称
            if (user.getNickName() != null) {
                tvNick.setText(user.getNickName());
            } else {
                tvNick.setText(user.getUsername());
            }

        }

        //用户等级设置
        assert user != null;
        tvLevel.setText(RankTransFormUtils.RankTranFormToName(this, user.getLevelCount()));
        DaggerSendComponent.builder().sendModule(new SendModule((MyApplication) getApplication()))
                .build()
                .inject(this);
        sendPresenter.setSendTaskView(this);
        waveView.setOnWaveAnimationListener(this);
    }

    @OnClick({R.id.iv_avatar, R.id.tv_rank, R.id.iv_back, R.id.relativeLayout_level, R.id.relativeLayout_circle, R.id.relative_layout_team, R.id.relative_layout_item})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar:
                Intent i = new Intent(getApplicationContext(), AlbumActivity.class);
                i.putExtra("flag", Constant.AVATAR_PICTURE);
                startActivityForResult(i, Constant.AVATAR_PICTURE);
                break;
            case R.id.tv_rank:
                ModifyNameDialog dialog = new ModifyNameDialog();
                dialog.setCallback(this);
                dialog.show(getSupportFragmentManager(), tvNick.getText().toString());
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.relativeLayout_level:
                break;
            case R.id.relativeLayout_circle:
                ActivityUtils.gotoActivity(this, MyCircleActivity.class);
                break;
            case R.id.relative_layout_team:
                ActivityUtils.gotoActivity(this,MyBattleActivity.class);
                break;
            case R.id.relative_layout_item:
                break;
        }
    }

    @Override
    public void OnCallback(Object object) {
        if (object == null) {
            return;
        }
        sendPresenter.UpLoading(Constant.MODIFY_NICK_TASK, object);
        tvNick.setText(object.toString());
    }

    @Override
    public void showErrorFrame(String tip) {
        Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess() {
        Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setData(List<Object> objects) {

    }

    @Override
    public void LoadingData(int taskID, int ways) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String picPath = ImageUtils.CompressBitmap(data, Environment.getExternalStorageDirectory().toString(), "avatar.png");
            Log.i(Constant.TAG, "onActivityResult: picPath " + picPath);
            sendPresenter.UpLoading(Constant.MODIFY_AVATAR_TASK, picPath);

        }
    }

    @Override
    public void OnWaveAnimation(float y) {

    }
}
