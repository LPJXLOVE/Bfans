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
import com.technology.lpjxlove.bfans.UI.CustomView.LoadLayout;

/**
 * Created by LPJXLOVE on 2016/9/11.
 */
public class LoadingDialog extends DialogFragment{
    private   LoadLayout loadLayout;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder b = new AlertDialog.Builder(getActivity(), R.style.MyDialog);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.loading_layout, null);
        initEvent(v);
        b.setView(v);
        b.setCancelable(false);
        return b.create();
    }

    private void initEvent(View v) {
        loadLayout= (LoadLayout) v.findViewById(R.id.load_layout);
        loadLayout.setText(getTag());
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        loadLayout.Dismiss();
    }
}
