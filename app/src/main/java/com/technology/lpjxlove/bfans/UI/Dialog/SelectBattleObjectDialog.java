package com.technology.lpjxlove.bfans.UI.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technology.lpjxlove.bfans.Adapter.SelectObjectAdapter;
import com.technology.lpjxlove.bfans.Bean.BattleEntity;
import com.technology.lpjxlove.bfans.Interface.Callback;
import com.technology.lpjxlove.bfans.Interface.ItemClickListener;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.Util.Constant;

import java.util.List;

/**
 * Created by LPJXLOVE on 2016/9/11.
 */
public class SelectBattleObjectDialog extends DialogFragment implements ItemClickListener {
    private RecyclerView recyclerView;
    private Callback callback;
    private String[] data;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity(), R.style.MyDialog);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.select_object_layout, null);
        b.setView(v);
       initEvent(v);
        return b.create();
    }


  /*  @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.select_object_item, null);
        initEvent(v);
        return v;
    }*/

    private void initEvent(View v) {
        recyclerView= (RecyclerView) v.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        data= getActivity().getResources().getStringArray(R.array.client_level);
        SelectObjectAdapter adapter=new SelectObjectAdapter(data);
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void OnItemClick(Object object, View view) {
        Message a=new Message();
        a.what=Constant.SELECT_OBJECT_TYPE;
        a.obj=data[(int) object];
        callback.OnCallback(a);
        this.dismiss();
    }
}
