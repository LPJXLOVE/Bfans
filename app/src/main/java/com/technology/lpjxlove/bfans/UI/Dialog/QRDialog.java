package com.technology.lpjxlove.bfans.UI.Dialog;

import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.UI.CustomView.LoadLayout;
import com.technology.lpjxlove.bfans.Util.ActivityUtils;
import com.technology.lpjxlove.bfans.Util.FilesUtils;
import com.technology.lpjxlove.bfans.Util.ImageUtils;
import com.technology.lpjxlove.bfans.Util.QRCodeUtil;
import com.technology.lpjxlove.bfans.Util.WindowsUtils;

import java.io.File;

/**
 * Created by LPJXLOVE on 2016/9/11.
 */
public class QRDialog extends DialogFragment{


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity(), R.style.MyTransparentDialog);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.qr_item_layout, null);
        initEvent(v);
        b.setView(v);
        b.setCancelable(true);
        return b.create();
    }

    private void initEvent(View v) {
        File f=new File(Environment.getExternalStorageDirectory().getPath() +"/QR.png");
        ImageView iv= (ImageView) v.findViewById(R.id.iv_qr);
        int width= (int) WindowsUtils.dp2px(getContext(),150);
       boolean b= QRCodeUtil.createQRImage(getTag(),width,
                width,null,f.getPath());
        if (b){
            iv.setImageBitmap(BitmapFactory.decodeFile(f.getPath()));
        }
    }
}
