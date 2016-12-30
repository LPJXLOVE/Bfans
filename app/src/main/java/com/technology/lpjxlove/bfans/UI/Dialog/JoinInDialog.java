package com.technology.lpjxlove.bfans.UI.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.technology.lpjxlove.bfans.Interface.Callback;
import com.technology.lpjxlove.bfans.R;

/**
 * Created by LPJXLOVE on 2016/9/11.
 */
public class JoinInDialog extends DialogFragment{
    private Callback callback;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity(), R.style.MyDialog);
        b.setMessage("你确定报名约战？");
        b.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.OnCallback("");

            }
        });
        b.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return b.create();
    }

    private void initEvent(View v) {
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
