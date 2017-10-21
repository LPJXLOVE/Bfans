package com.technology.lpjxlove.bfans.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.help.Tip;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.technology.lpjxlove.bfans.Bean.BattleEntity;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.Interface.Callback;

import com.technology.lpjxlove.bfans.MVP.DaggerSendComponent;
import com.technology.lpjxlove.bfans.MVP.SendModule;
import com.technology.lpjxlove.bfans.MVP.SendTaskPresenter;
import com.technology.lpjxlove.bfans.MVP.SendTaskView;
import com.technology.lpjxlove.bfans.MyApplication;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.UI.Dialog.LoadingDialog;
import com.technology.lpjxlove.bfans.UI.Dialog.SelectBattleObjectDialog;
import com.technology.lpjxlove.bfans.UI.Dialog.SelectBattleTypeDialog;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.LocationUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import immortalz.me.library.TransitionsHeleper;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.method.ColorShowMethod;
import immortalz.me.library.method.InflateShowMethod;
import immortalz.me.library.method.ShowMethod;
import immortalz.me.library.view.RenderView;

public class AddBattleActivity extends AppCompatActivity implements  Callback, SendTaskView<Object>,OnDateSetListener{

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tv_location)
    TextView tvLocation;
    @InjectView(R.id.linear_layout_location)
    LinearLayout LLLocation;
    @InjectView(R.id.tv)
    TextView tv;
    @InjectView(R.id.tv_challenge_type)
    TextView tvChallengeType;
    @InjectView(R.id.relativeLayout_type)
    RelativeLayout RLType;
    @InjectView(R.id.tv_person)
    TextView tvPerson;
    @InjectView(R.id.relativeLayout_person)
    RelativeLayout RLPerson;
    @InjectView(R.id.tv_battle_)
    TextView tvDate;
    @InjectView(R.id.relativeLayout_date)
    RelativeLayout RLDate;
    @InjectView(R.id.btn_commit)
    Button btnCommit;
    private boolean selectType,selectObject,selectTime;
    @Inject
    public SendTaskPresenter sendTaskPresenter;
    private String objects,type, date,location,city;
    private double latitude,longitude;
    private  LoadingDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_battle);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        /*TransitionsHeleper.getInstance().setShowMethod(new ColorShowMethod(android.R.color.white,R.color.colorPrimary) {
            @Override
            public void loadCopyView(InfoBean bean, ImageView copyView) {

            }
        }).show(this,null);*/


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String text = getDateToString(System.currentTimeMillis());
        tvDate.setText(text);
        date=text;
        if (Constant.Config.districtName==null){
            final LocationUtils locationUtils=new LocationUtils(this);
            locationUtils.startLocation();
            locationUtils.setOnFinishListener(new LocationUtils.OnFinishListener() {
                @Override
                public void onFinish() {
                    StringBuilder builder=new StringBuilder(Constant.Config.districtName);
                    builder.append(Constant.Config.streetName);
                    tvLocation.setText(builder);
                    locationUtils.onDestroy();

                }
            });
        }else {

            StringBuilder builder=new StringBuilder(Constant.Config.districtName);
            builder.append(Constant.Config.streetName);
            tvLocation.setText(builder);
            location=tvLocation.getText().toString();
            city=Constant.Config.cityName;
            longitude= Constant.Config.Longitude;
            latitude= Constant.Config.Latitude;

        }
        DaggerSendComponent.builder()
                .sendModule(new SendModule((MyApplication)getApplication()))
                .build()
                .inject(this);
        sendTaskPresenter.setSendTaskView(this);

    }

    @OnClick({R.id.tv_location, R.id.relativeLayout_type, R.id.relativeLayout_person, R.id.relativeLayout_date, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                Intent i=new Intent(getApplicationContext(),MapActivity.class);
                startActivityForResult(i,0);
                break;
            case R.id.relativeLayout_type:
                SelectBattleTypeDialog dialog = new SelectBattleTypeDialog();
                dialog.show(getSupportFragmentManager(), "select_type");
                dialog.setCallback(this);
                break;
            case R.id.relativeLayout_person:
                SelectBattleObjectDialog d = new SelectBattleObjectDialog();
                d.show(getSupportFragmentManager(), "select_object");
                d.setCallback(this);
                break;
            case R.id.relativeLayout_date:
                showTimePickerDialog();

                break;
            case R.id.btn_commit:
                JudgeContent();
                break;
        }
    }

    private void showTimePickerDialog() {
        TimePickerDialog mTimePickerDialog = new TimePickerDialog.Builder()
                .setMinMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setWheelItemTextSize(12)
                .setCallBack(this)
                .build();
        mTimePickerDialog.show(getSupportFragmentManager(),"TimePicker");
    }




    public String getDateToString(long time) {
        SimpleDateFormat sf;
        Date d = new Date(time);
       if (d.getHours()>12){
           sf = new SimpleDateFormat("yyyy-MM-dd-下午HH:mm", Locale.CHINA);
       }else {
           sf=new SimpleDateFormat("yyyy-MM-dd-早上HH:mm",Locale.CHINA);
       }
        return sf.format(d);
    }




    /**
     * 判断内容是否为空
     */
    private void JudgeContent() {
        String showText;
        if (!selectType){
            showText="请填写约战类型";
            Snackbar.make(btnCommit,showText,Snackbar.LENGTH_SHORT).show();
         //   Toast.makeText(AddBattleActivity.this, showText, Toast.LENGTH_SHORT).show();
        }else if (!selectObject){
            showText="请填写约战对象";
            Snackbar.make(btnCommit,showText,Snackbar.LENGTH_SHORT).show();
          //  Toast.makeText(AddBattleActivity.this, showText, Toast.LENGTH_SHORT).show();
        }/*else if (!selectTime){
            showText="请填写约战日期";
            Toast.makeText(AddBattleActivity.this, showText, Toast.LENGTH_SHORT).show();
        }*/else if (tvLocation==null){
            showText="请填写约战地点";
            Snackbar.make(btnCommit,showText,Snackbar.LENGTH_SHORT).show();
           // Toast.makeText(AddBattleActivity.this, showText, Toast.LENGTH_SHORT).show();
        }else {



            if (sendTaskPresenter==null){
                return;
            }
            User user= BmobUser.getCurrentUser(User.class);
            BattleEntity entity=new BattleEntity();
            entity.setAuthor(user);
            entity.setBattleType(type);
            entity.setBattleObject(objects);
            entity.setBattleLocation(location);
            entity.setStartDate(date);
            entity.setLongitude(longitude);
            entity.setLatitude(latitude);
            entity.setState("0");
            entity.setCity(city);
            sendTaskPresenter.UpLoading(Constant.BATTLE_SEND_TASK,entity);
        }
    }

    @Override
    public void OnCallback(Object object) {
        Message message = (Message) object;
        String text = message.obj.toString();
        switch (message.what) {
            case Constant.SELECT_BATTLE_TYPE:
                selectType=true;
                tvChallengeType.setText(text);
                type=text;
                break;
            case Constant.SELECT_OBJECT_TYPE:
                selectObject=true;
                tvPerson.setText(text);
                objects=text;
                break;
        }
    }

    @Override
    public void showErrorFrame(String tip) {
        Snackbar.make(btnCommit,tip,Snackbar.LENGTH_SHORT).show();
      //  Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
        load.dismiss();
    }

    @Override
    public void showSuccess() {
       // Snackbar.make(btnCommit, "发起成功！",Snackbar.LENGTH_SHORT).show();
        Toast.makeText(this, "发起成功！", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void setData(List<Object> objects) {

    }

    @Override
    public void LoadingData(int taskID, int ways) {
        load=new LoadingDialog();
        load.show(getSupportFragmentManager(),"发起中");

    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long milliseconds) {
        String text = getDateToString(milliseconds);
        selectTime=true;
        tvDate.setText(text);
        date =text;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            Tip tip=data.getParcelableExtra("Location");
            tvLocation.setText(tip.getAddress());
            location=tip.getAddress();
            longitude=tip.getPoint().getLongitude();
            latitude=tip.getPoint().getLatitude();
        }
    }
}
