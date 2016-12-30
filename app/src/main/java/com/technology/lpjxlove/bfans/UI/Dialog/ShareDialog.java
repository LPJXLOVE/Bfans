package com.technology.lpjxlove.bfans.UI.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import com.technology.lpjxlove.bfans.Adapter.ShareItemAdapter;
import com.technology.lpjxlove.bfans.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LPJXLOVE on 2016/9/4.
 */
public class ShareDialog extends DialogFragment {

    @InjectView(R.id.recycle_view)
    RecyclerView recycleView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
      //  View view = LayoutInflater.from(getActivity()).inflate(R.layout.share_dialog_layout, null);
        //recycleView= (RecyclerView) view.findViewById(R.id.recycle_view);

        Dialog dialog = new Dialog(getActivity(),R.style.MyShareDialogStyle);
        dialog.setContentView(R.layout.share_dialog_layout);
        Window window = dialog.getWindow();
        assert window != null;
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
        return dialog;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.share_dialog_layout, null);
        ButterKnife.inject(this, rootView);
        GridLayoutManager manager=new GridLayoutManager(getActivity(),3);
        recycleView.setLayoutManager(manager);
        recycleView.setAdapter(new ShareItemAdapter(null));
        // FIXME: 2016/10/11
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
