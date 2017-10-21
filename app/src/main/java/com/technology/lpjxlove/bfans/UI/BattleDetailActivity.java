package com.technology.lpjxlove.bfans.UI;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.technology.lpjxlove.bfans.Adapter.BattleTeammateAdapter;
import com.technology.lpjxlove.bfans.Bean.BattleEntity;
import com.technology.lpjxlove.bfans.Bean.BattleJoinEntity;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.Interface.Callback;
import com.technology.lpjxlove.bfans.MVP.DaggerSendComponent;
import com.technology.lpjxlove.bfans.MVP.SendModule;
import com.technology.lpjxlove.bfans.MVP.SendTaskPresenter;
import com.technology.lpjxlove.bfans.MVP.SendTaskView;
import com.technology.lpjxlove.bfans.MyApplication;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.UI.Dialog.BattleMemberDialog;
import com.technology.lpjxlove.bfans.UI.Dialog.JoinInDialog;
import com.technology.lpjxlove.bfans.UI.Dialog.LoadingDialog;
import com.technology.lpjxlove.bfans.UI.Dialog.QRDialog;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.TimeTransformUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import rx.functions.Action1;

public class BattleDetailActivity extends AppCompatActivity implements Callback,SendTaskView<BattleJoinEntity> {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.iv_avatar)
    SimpleDraweeView ivAvatar;
    @InjectView(R.id.tv_nick)
    TextView tvNick;
    @InjectView(R.id.tv_time)
    TextView tvTime;
    @InjectView(R.id.tv_state)
    TextView ivJoin;
    @InjectView(R.id.tv_content)
    TextView tvContent;
    @InjectView(R.id.tv_battle_)
    TextView tvDate;
    @InjectView(R.id.iv_location)
    ImageView ivLocation;
    @InjectView(R.id.tv_location)
    TextView tvLocation;
    @InjectView(R.id.tv_battle)
    TextView tvBattle;
    @InjectView(R.id.tv_battle_type)
    TextView tvBattle_type;
    @InjectView(R.id.recycle_view)
    RecyclerView recycleView;
    @InjectView(R.id.tv_battle_object)
    TextView tvBattleObject;
    @InjectView(R.id.relative_layout_item)
    RelativeLayout relativeLayoutItem;
    @InjectView(R.id.tv_number)
    TextView tvNumber;
    @InjectView(R.id.textView2)
    TextView textView2;
    @InjectView(R.id.card_view)
    CardView cardView;
    @Inject
    SendTaskPresenter sendPresenter;
    private Action1<Integer> itemClick;
    private BattleEntity e;
    private int taskId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_battle_detail);

        ButterKnife.inject(this);
        toolbar.inflateMenu(R.menu.qr_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
               /* WindowManager manager= getWindow().getWindowManager();
                WindowManager.LayoutParams p=new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
                p.gravity= Gravity.CENTER;
                p.type= WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
                View v= LayoutInflater.from(getApplicationContext()).inflate(R.layout.qr_layout,null);
                manager.addView(v,p);*/
                QRDialog d=new QRDialog();
                d.show(getSupportFragmentManager(),e.getObjectId());


                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });
        recycleView.setLayoutManager(new GridLayoutManager(this, 3));
        recycleView.setHasFixedSize(true);
       // recycleView.setAdapter(new BattleTeammateAdapter(null,itemClick));
        initData();
    }

    private void initData() {
        e = (BattleEntity) getIntent().getSerializableExtra("BattleEntity");
        User user = e.getAuthor();
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
        } else {
            ivAvatar.setBackgroundResource(R.drawable.ic_person_black_24dp);
        }


        if (user.getNickName() == null) {
            tvNick.setText(user.getUsername());
        } else {
            tvNick.setText(user.getNickName());
        }

        tvTime.setText(TimeTransformUtils.TimeTransform(e.getCreatedAt()));
        tvBattleObject.setText(e.getBattleObject());
        tvBattle_type.setText(e.getBattleType());
        tvDate.setText(e.getStartDate());
        tvLocation.setText(e.getBattleLocation());
        DaggerSendComponent.builder()
                .sendModule(new SendModule((MyApplication)getApplication()))
                .build()
                .inject(this);
        sendPresenter.setSendTaskView(this);

        itemClick=new Action1<Integer>() {//item点击事件
            @Override
            public void call(Integer integer) {
                BattleJoinEntity entity=adapter.getData().get(integer);
                Bundle bundle=new Bundle();
                bundle.putSerializable("user",entity.getJoinInPeople());
                BattleMemberDialog dialog=new BattleMemberDialog();
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(),"detail");
            }
        };

        sendPresenter.onLoading(Constant.BATTLE_JOIN_PEOPLE_TASK,0,e);//加载约战成员


    }

    @OnClick({R.id.iv_avatar, R.id.tv_nick, R.id.tv_state, R.id.tv_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar:
                break;
            case R.id.tv_nick:
                break;
            case R.id.tv_state:
                JoinInDialog d=new JoinInDialog();
                d.setCallback(this);
                d.show(getSupportFragmentManager(),"Join");
                break;
            case R.id.tv_location:
                break;
        }
    }


    //参战Button回调
    @Override
    public void OnCallback(Object object) {
        User user= BmobUser.getCurrentUser(User.class);
        BattleJoinEntity entity=new BattleJoinEntity();
        entity.setBattleEntity(e);
        entity.setJoinInPeople(user);
        sendPresenter.UpLoading(Constant.BATTLE_JOIN_TASK,entity);
        taskId=Constant.BATTLE_JOIN_TASK;
    }

    @Override
    public void showErrorFrame(String tip) {
        if (dialog!=null){
            dialog.dismiss();
        }//约战成功后，刷新约战成员
        if (taskId==Constant.BATTLE_JOIN_TASK){

            sendPresenter.onLoading(Constant.BATTLE_JOIN_PEOPLE_TASK,0,e);
        }
        Snackbar.make(relativeLayoutItem,tip,Snackbar.LENGTH_SHORT).show();
       // Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess() {
        if (dialog!=null){
            dialog.dismiss();
        }


    }
    private BattleTeammateAdapter adapter;
    @Override
    public void setData(List<BattleJoinEntity> objects) {

        BattleJoinEntity battleJoinEntity=new BattleJoinEntity();
        battleJoinEntity.setBattleEntity(e);
        battleJoinEntity.setJoinInPeople(e.getAuthor());//构造发起人资料
        objects.add(0,battleJoinEntity);
        tvNumber.setText("已经报名："+objects.size());//报名人数设置
        if (adapter==null){
            adapter=new BattleTeammateAdapter(this,objects,itemClick);
            recycleView.setAdapter(adapter);
        }else {
            adapter.setData(objects);
            adapter.notifyDiff();
        }

    }

    LoadingDialog dialog;
    @Override
    public void LoadingData(int taskID, int ways) {
         dialog=new LoadingDialog();
        dialog.show(getSupportFragmentManager(),"参战中...");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendPresenter.setSendTaskView(null);
        sendPresenter=null;
    }
}
