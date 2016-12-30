package com.technology.lpjxlove.bfans.UI.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AlertDialog;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.technology.lpjxlove.bfans.Interface.Callback;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.Util.Constant;

import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by LPJXLOVE on 2016/9/11.
 */
public class SelectBattleTypeDialog extends DialogFragment implements View.OnClickListener {
    private TextView tv_first, tv_second, tv_third;
    private String text;
    private Callback callback;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity(), R.style.MyDialog);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.select_type_layout, null);
        initEvent(v);
        b.setView(v);
       /* Dialog dialog=new Dialog(getActivity());
        WindowManager.LayoutParams params =dialog.getWindow().getAttributes();
        dialog.getWindow().setAttributes(params);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.select_type_layout,null);
        dialog.setContentView(v);*/
        return b.create();
    }

    private void initEvent(View v) {
        tv_first = (TextView) v.findViewById(R.id.tv_first);
        tv_second = (TextView) v.findViewById(R.id.tv_second);
        tv_third = (TextView) v.findViewById(R.id.tv_third);
        tv_first.setOnClickListener(this);
        tv_second.setOnClickListener(this);
        tv_third.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_first:
                text="1vs1";
                break;
            case R.id.tv_second:
                text="3vs3";
                break;
            case R.id.tv_third:
                text="5vs5";
                break;
        }

        Message a=new Message();
        a.what=Constant.SELECT_BATTLE_TYPE;
        a.obj=text;
        callback.OnCallback(a);
        this.dismiss();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
