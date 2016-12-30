package com.technology.lpjxlove.bfans.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.ResponseCodeUtils;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/*
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
*/

/**
 *
 */
public class RegisterActivity extends Activity {

    @InjectView(R.id.et_number)
    EditText etNumber;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.et_verifyCode)
    EditText etVerifyCode;
    @InjectView(R.id.tv_get_verify_code)
    TextView tvGetVerifyCode;
    @InjectView(R.id.btn_sign_in)
    Button btnSignIn;
    @InjectView(R.id.tv_go_Login)
    TextView tvGoLogin;
    private boolean isClick;
    private MyHandler m;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        initSMSSDK();
    }

    private void initSMSSDK() {
        m=new MyHandler(this);
        SMSSDK.initSDK(this, getString(R.string.MobAppKey), getString(R.string.MobAppSecret));
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        m.sendEmptyMessage(SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE);
                        //提交验证码成功
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        m.sendEmptyMessage(SMSSDK.EVENT_GET_VERIFICATION_CODE);
                        //获取验证码成功
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    Message a=m.obtainMessage();
                    a.what=SMSSDK.RESULT_ERROR;
                    a.obj=((Throwable) data).getMessage();
                    m.sendMessage(a);
                }
            }
        };
        SMSSDK.registerEventHandler(eh);
    }


    @OnClick({R.id.tv_get_verify_code, R.id.btn_sign_in, R.id.tv_go_Login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_verify_code:
                getVerifyCode();
                break;
            case R.id.btn_sign_in:
                VerifyCode();
                break;
            case R.id.tv_go_Login:
                ActivityUtils.gotoActivity(this, LoginActivity.class);
                this.finish();
                break;
        }
    }

    private void VerifyCode() {
        if (TextUtils.isEmpty(etNumber.getText())) {
            Toast.makeText(RegisterActivity.this, "手机号码不能为空！", Toast.LENGTH_SHORT).show();
        } else if (!isClick) {
            Toast.makeText(RegisterActivity.this, "请先获取验证码！！", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(etPassword.getText())) {
            Toast.makeText(RegisterActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(etVerifyCode.getText())) {
            Toast.makeText(RegisterActivity.this, "验证码不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            SMSSDK.submitVerificationCode("86",etNumber.getText().toString(),etVerifyCode.getText().toString());
        }

    }

    /**
     * 注册操作
     */
    public void goSignUp() {

        User user = new User();
        user.setUsername(etNumber.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                 m.sendEmptyMessage(Constant.SUCCESS);

                } else {
                    Message message=m.obtainMessage();
                    message.obj=e;
                    message.what=Constant.FAILED;
                    m.sendMessage(message);

                }
            }
        });

    }

    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        if (TextUtils.isEmpty(etNumber.getText())) {
            Toast.makeText(RegisterActivity.this, "手机号码不能为空！", Toast.LENGTH_SHORT).show();
        } else {

            SMSSDK.getVerificationCode("86",etNumber.getText().toString());
        }

    }


    static class MyHandler extends Handler {
        WeakReference<Activity> activity;
        int count = 60;

        public MyHandler(Activity activity) {
            this.activity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            RegisterActivity a = (RegisterActivity) activity.get();
            if (a != null) {

                switch (msg.what){
                    case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                        MyHandler handler = new MyHandler(a);
                        handler.sendEmptyMessageAtTime(Constant.COUNT, 1000);
                        a.tvGetVerifyCode.setEnabled(false);
                        a.isClick=true;
                        break;
                    case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                        count=-1;
                        a.goSignUp();
                        break;
                    case SMSSDK.RESULT_ERROR:
                        Toast.makeText(a.getApplicationContext(), ""+msg.obj, Toast.LENGTH_SHORT).show();

                    case Constant.SUCCESS:
                        Toast.makeText(a.getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
                       ActivityUtils.gotoActivityWithFlag(a.getApplicationContext(), LoginActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);
                        a.finish();
                        break;
                    case Constant.FAILED:
                        BmobException e= (BmobException) msg.obj;
                        Log.d("info",e.getMessage());
                        Toast.makeText(a.getApplicationContext(), ResponseCodeUtils.TransForm(e.getErrorCode()), Toast.LENGTH_SHORT).show();
                        break;
                }

                if (msg.what== Constant.COUNT){
                    if (count > 0) {
                        count--;
                        StringBuilder builder = new StringBuilder();
                        builder.append(count);
                        builder.append("后秒重试！");
                        a.tvGetVerifyCode.setText(builder);
                        this.sendEmptyMessageDelayed(Constant.COUNT, 1000);
                    } else {
                        a.isClick=false;
                        a.tvGetVerifyCode.setText(a.getResources().getString(R.string.get_verify_code));
                        a.tvGetVerifyCode.setEnabled(true);
                    }
                }









            }


        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
