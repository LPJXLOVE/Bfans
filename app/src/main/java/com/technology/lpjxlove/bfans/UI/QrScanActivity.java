package com.technology.lpjxlove.bfans.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.zxing.Result;
import com.technology.lpjxlove.bfans.Bean.BattleEntity;
import com.technology.lpjxlove.bfans.Bean.BattleJoinEntity;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.MVP.DaggerSendComponent;
import com.technology.lpjxlove.bfans.MVP.SendModule;
import com.technology.lpjxlove.bfans.MVP.SendTaskPresenter;
import com.technology.lpjxlove.bfans.MVP.SendTaskView;
import com.technology.lpjxlove.bfans.MyApplication;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.UI.CustomView.MyZXingScannerView;
import com.technology.lpjxlove.bfans.UI.Dialog.JoinInDialog;
import com.technology.lpjxlove.bfans.UI.Dialog.LoadingDialog;
import com.technology.lpjxlove.bfans.Util.Constant;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobUser;


public class QrScanActivity extends AppCompatActivity implements MyZXingScannerView.ResultHandler,SendTaskView {
    @InjectView(R.id.scan_view)
    MyZXingScannerView scannerView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    SendTaskPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_qr_scan);
        ButterKnife.inject(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();

    }

    private void init() {
        DaggerSendComponent.builder()
                .sendModule(new SendModule((MyApplication)getApplication()))
                .build()
                .inject(this);
        presenter.setSendTaskView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.startCamera();
        scannerView.setResultHandler(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        BattleJoinEntity e=new BattleJoinEntity();
        BattleEntity entity=new BattleEntity();
        entity.setObjectId(result.getText());
        User u= BmobUser.getCurrentUser(User.class);
        e.setBattleEntity(entity);
        e.setJoinInPeople(u);
        presenter.UpLoading(Constant.BATTLE_JOIN_TASK,e);
    //    Toast.makeText(this, "" + result.getText(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showErrorFrame(String tip) {

        Toast.makeText(this, ""+tip, Toast.LENGTH_SHORT).show();
        scannerView.resumeCameraPreview(this);
    }

    @Override
    public void showSuccess() {

        Toast.makeText(this, "参战成功！", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void setData(List objects) {

    }

    @Override
    public void LoadingData(int taskID, int ways) {

    }
}
