package com.technology.lpjxlove.bfans.UI.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.technology.lpjxlove.bfans.Interface.Callback;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.Util.Constant;

/**
 * Created by LPJXLOVE on 2016/9/11.
 */
public class ModifyNameDialog extends DialogFragment{
   private EditText mETContent;
    private Callback callback;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity(), R.style.MyDialog);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.modify_name_layout, null);
        initEvent(v);
        b.setView(v);
        b.setPositiveButton("好", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.OnCallback(mETContent.getText().toString());

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
        mETContent= (EditText) v.findViewById(R.id.et_name);
        String text=getTag();
        mETContent.setText(text);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
