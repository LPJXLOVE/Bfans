package com.technology.lpjxlove.bfans.UI;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.Util.ActivityUtils;
import com.technology.lpjxlove.bfans.Util.ResponseCodeUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import rx.Observable;
import rx.Observer;

public class LoginActivity extends Activity  {

    @InjectView(R.id.et_number)
    EditText mEtNumber;
    @InjectView(R.id.et_password)
    EditText mEtPassword;
    @InjectView(R.id.btn_login)
    Button mBtnLogin;
    @InjectView(R.id.tv_go_Sign_in)
    TextView mTvGoSign;
    @InjectView(R.id.forget_password)
    TextView mForgetPassword;
  /*  @InjectView(R.id.iv_qq)
    ImageView mIvQQ;
    @InjectView(R.id.iv_we_chat)
    ImageView mIvWeChat;
    @InjectView(R.id.iv_wei_bo)
    ImageView mIvWeiBo;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.btn_login, R.id.tv_go_Sign_in, /*R.id.forget_password, R.id.iv_qq, R.id.iv_we_chat, R.id.iv_wei_bo*/})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Login();
                break;
            case R.id.tv_go_Sign_in:
                ActivityUtils.gotoActivity(this,RegisterActivity.class);
                break;
            case R.id.forget_password:
                break;
         /*   case R.id.iv_qq:
                break;
            case R.id.iv_we_chat:
                break;
            case R.id.iv_wei_bo:
                break;*/
        }
    }

    private void Login(){
        String number=mEtNumber.getText().toString();
        String password=mEtPassword.getText().toString();
      if (TextUtils.isEmpty(number)){
          Toast.makeText(LoginActivity.this, "手机号码不能为空哦", Toast.LENGTH_SHORT).show();
      }else if (TextUtils.isEmpty(password)){
          Toast.makeText(LoginActivity.this, "密码不能为空哦", Toast.LENGTH_SHORT).show();
      }else {
          User user=new User();
          user.setUsername(number);
          user.setPassword(password);
          Observable<User> login=user.loginObservable(User.class);
          login.subscribe(new Observer<User>() {
              @Override
              public void onCompleted() {

              }

              @Override
              public void onError(Throwable throwable) {
                  Log.d("info", "onError: "+throwable.getMessage());
                  Toast.makeText(LoginActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
              }
              @Override
              public void onNext(User user) {
                  ActivityUtils.gotoActivity(LoginActivity.this,MainActivity.class);
                  finish();
                  Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
              }
          });


      }


    }


}
