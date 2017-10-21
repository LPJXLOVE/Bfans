package com.technology.lpjxlove.bfans.UI.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.technology.lpjxlove.bfans.Adapter.RecentlyBattleAdapter;
import com.technology.lpjxlove.bfans.Bean.BattleEntity;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.Interface.Callback;

import com.technology.lpjxlove.bfans.MVP.SendTaskPresenter;
import com.technology.lpjxlove.bfans.MVP.SendTaskView;
import com.technology.lpjxlove.bfans.MyApplication;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.UI.AlbumActivity;
import com.technology.lpjxlove.bfans.UI.CustomView.PreviewPhoto.PreviewActivity;
import com.technology.lpjxlove.bfans.UI.CustomView.WaveView;
import com.technology.lpjxlove.bfans.UI.Dialog.LoadingDialog;
import com.technology.lpjxlove.bfans.UI.Dialog.ModifyNameDialog;
import com.technology.lpjxlove.bfans.UI.MyBattleActivity;
import com.technology.lpjxlove.bfans.UI.MyCircleActivity;
import com.technology.lpjxlove.bfans.Util.ActivityUtils;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.ImageUtils;
import com.technology.lpjxlove.bfans.Util.RankTransFormUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

import static android.app.Activity.RESULT_OK;
import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends Fragment implements Callback, SendTaskView<Object>, WaveView.OnWaveAnimationListener  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @InjectView(R.id.iv_avatar)
    SimpleDraweeView ivAvatar;
    @InjectView(R.id.tv_nick)
    TextView tvNick;
    @InjectView(R.id.wave_view)
    WaveView waveView;
    @InjectView(R.id.top)
    RelativeLayout top;
    @InjectView(R.id.imageView3)
    ImageView imageView3;
    @InjectView(R.id.textView5)
    TextView textView5;
    @InjectView(R.id.tv_level)
    TextView tvLevel;
    @InjectView(R.id.relativeLayout_level)
    RelativeLayout relativeLayoutLevel;
    @InjectView(R.id.imageView6)
    ImageView imageView6;
    @InjectView(R.id.textView8)
    TextView textView8;
    @InjectView(R.id.imageView7)
    ImageView imageView7;
    @InjectView(R.id.relativeLayout_circle)
    RelativeLayout relativeLayoutCircle;
    @InjectView(R.id.imageView4)
    ImageView imageView4;
    @InjectView(R.id.textView7)
    TextView textView7;
    @InjectView(R.id.imageView5)
    ImageView imageView5;
    @InjectView(R.id.relative_layout_team)
    RelativeLayout relativeLayoutTeam;
    @InjectView(R.id.imageView)
    ImageView imageView;
    @InjectView(R.id.textView9)
    TextView textView9;
    @InjectView(R.id.relative_layout_setting)
    RelativeLayout relativeLayoutSetting;
    @InjectView(R.id.recycle_view)
    RecyclerView recycleView;
    SendTaskPresenter sendPresenter;
    private LoadingDialog upLoadingDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
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
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.inject(this, view);
        MyApplication application= (MyApplication) getActivity().getApplication();
        sendPresenter=new SendTaskPresenter(application);
        initData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
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
        tvLevel.setText(RankTransFormUtils.RankTranFormToName(getContext(), user.getLevelCount()));
      /*  DaggerSendComponent.builder().sendModule(new SendModule((MyApplication) getApplication()))
                .build()
                .inject(this);*/
        sendPresenter.setSendTaskView(this);
        waveView.setOnWaveAnimationListener(this);


        recycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        new LinearSnapHelper().attachToRecyclerView(recycleView);
        recycleView.setHasFixedSize(true);
        List<BattleEntity> entities = new ArrayList<>();
        entities.add(new BattleEntity());
        entities.add(new BattleEntity());
        entities.add(new BattleEntity());
        entities.add(new BattleEntity());
        entities.add(new BattleEntity());
        entities.add(new BattleEntity());
        entities.add(new BattleEntity());
        recycleView.setAdapter(new RecentlyBattleAdapter(entities));


    }




    @OnClick({R.id.tv_nick, R.id.iv_avatar, R.id.relativeLayout_level, R.id.relativeLayout_circle, R.id.relative_layout_team, R.id.relative_layout_setting, R.id.recycle_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar:
                Intent i = new Intent(getApplicationContext(), AlbumActivity.class);
                i.putExtra("flag", Constant.AVATAR_PICTURE);
                startActivityForResult(i, Constant.AVATAR_PICTURE);
                break;
            case R.id.tv_nick:
                ModifyNameDialog dialog = new ModifyNameDialog();
                dialog.setCallback(this);
                dialog.show(getFragmentManager(), tvNick.getText().toString());
                break;
            case R.id.relativeLayout_level:
                break;
            case R.id.relativeLayout_circle:
                ActivityUtils.gotoActivity(getActivity(), PreviewActivity.class);
                break;
            case R.id.relative_layout_team:
                ActivityUtils.gotoActivity(getActivity(), MyBattleActivity.class);
                break;
            case R.id.relative_layout_setting:
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
        if (upLoadingDialog!=null){
            upLoadingDialog.dismiss();
        }
        Snackbar.make(ivAvatar, tip, Snackbar.LENGTH_LONG).show();
       // Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess() {
        if (upLoadingDialog!=null){
            upLoadingDialog.dismiss();
        }
        Snackbar.make(ivAvatar, "修改成功", Snackbar.LENGTH_LONG).show();
        // Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setData(List<Object> objects) {

    }

    @Override
    public void LoadingData(int taskID, int ways) {

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String picPath = ImageUtils.CompressBitmap(data, Environment.getExternalStorageDirectory().toString(), "avatar.png");
            Log.i(Constant.TAG, "onActivityResult: picPath " + picPath);
            sendPresenter.UpLoading(Constant.MODIFY_AVATAR_TASK, picPath);
            upLoadingDialog=new LoadingDialog();
            upLoadingDialog.show(getFragmentManager(),"上传头像中...");

        }
    }

    @Override
    public void OnWaveAnimation(float y) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        upLoadingDialog.dismiss();
    }
}


