package com.technology.lpjxlove.bfans.UI.Dialog;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.Util.RankTransFormUtils;

/**
 * Created by LPJXLOVE on 2017/1/11.
 */

public class BattleMemberDialog extends DialogFragment {
    private TextView mTvRank,mTvPosition,mTvHeight,mTvTeam,mTvNick;
    private SimpleDraweeView adAvatar;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity(), R.style.MyDialog);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.battle_member_dialog, null);
        b.setView(v);
        initView(v);
        setData();
        Log.i("test","dialogCreate");
        return b.create();
    }
    private void initView(View v) {
        mTvRank= (TextView) v.findViewById(R.id.tv_nick);
        mTvPosition= (TextView) v.findViewById(R.id.tv_position);
        mTvHeight= (TextView) v.findViewById(R.id.tv_height);
        mTvTeam= (TextView) v.findViewById(R.id.tv_team);
        adAvatar = (SimpleDraweeView) v.findViewById(R.id.iv_avatar);
        mTvNick= (TextView) v.findViewById(R.id.tv_nick);

    }

    public void setData(){
        User user= (User) getArguments().get("user");
        if (user==null){
            return;
        }
        String path=user.getAvatarUrl();
        if (TextUtils.isEmpty(path)){
            adAvatar.setImageResource(R.drawable.ic_person_black_24dp);
            return;
        }
        final Uri uri = Uri.parse(path);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(200, 200))
                .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setOldController(adAvatar.getController())
                .setImageRequest(request)
                .build();
        adAvatar.setController(draweeController);
        mTvRank.setText(RankTransFormUtils.RankTranFormToName(getActivity(),user.getLevelCount()));
        if (user.getNickName()==null){
            mTvNick.setText(user.getUsername());
        }else {
            mTvNick.setText(user.getNickName());
        }




    }

}
