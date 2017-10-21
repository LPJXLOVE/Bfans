package com.technology.lpjxlove.bfans.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.lpjxlove.bfans.Adapter.AddPhotoAdapter;
import com.technology.lpjxlove.bfans.Bean.CircleEntity;
import com.technology.lpjxlove.bfans.Bean.ImageBean;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.MVP.DaggerSendComponent;
import com.technology.lpjxlove.bfans.MVP.SendModule;
import com.technology.lpjxlove.bfans.MVP.SendTaskPresenter;
import com.technology.lpjxlove.bfans.MVP.SendTaskView;
import com.technology.lpjxlove.bfans.MyApplication;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.UI.CustomView.PreviewPhoto.PreviewActivity;
import com.technology.lpjxlove.bfans.Util.ActivityUtils;
import com.technology.lpjxlove.bfans.Util.Constant;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import rx.functions.Action1;

public class AddCircleActivity extends AppCompatActivity implements SendTaskView<CircleEntity> {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.et_circle)
    EditText etCircle;
    @InjectView(R.id.iv_add_photo)
    TextView ivAddPhoto;
    @InjectView(R.id.nine_layout)
    RecyclerView nineLayout;
    @InjectView(R.id.btn_commit)
    Button btnCommit;
    @InjectView(R.id.iv_add_video)
    TextView ivAddVideo;
    private List<ImageBean> list;
    private Action1<String> ImageClickAction;//图片点击事件
    private Action1<String> deleteImageAction;//点击删除图片事件
    private AddPhotoAdapter adapter;
    @Inject
    SendTaskPresenter sendPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_circle);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nineLayout.setLayoutManager(new GridLayoutManager(this,4));
        nineLayout.setHasFixedSize(true);
        ImageClickAction=new Action1<String>() {
            @Override
            public void call(String s) {
                if (s.equals("add")){
                    Intent i=new Intent(getApplicationContext(),AlbumActivity.class);
                    i.putParcelableArrayListExtra("selectPicture", (ArrayList<? extends Parcelable>) list);
                    startActivityForResult(i,Constant.GET_ALBUM_PHOTO_TASK);
                }else {
                    ArrayList<String> imageUrl=new ArrayList<>();
                    for (ImageBean imageBean:list){
                        String s1=imageBean.ImageUrl;
                        imageUrl.add(s1);
                    }

                    ActivityUtils.gotoPreviewActivity(AddCircleActivity.this,imageUrl,Integer.valueOf(s));
                    //Toast.makeText(AddCircleActivity.this, "进入预览界面！"+s, Toast.LENGTH_SHORT).show();
                }

            }
        };
        deleteImageAction=new Action1<String>() {
            @Override
            public void call(String position) {
                int p=Integer.valueOf(position);
                list.remove(p);
                adapter.notifyItemRemoved(p);
                if (adapter.getItemCount()==1){
                    dismissNineLayout();
                }
            }
        };

        DaggerSendComponent.builder()
                .sendModule(new SendModule((MyApplication)getApplication()))
                .build()
                .inject(this);
        sendPresenter.setSendTaskView(this);
    }

    @OnClick({R.id.iv_add_photo, R.id.iv_add_video, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_photo:
                Intent i=new Intent(this,AlbumActivity.class);
                i.putExtra("flag",Constant.NORMAL_PICTURE);
                startActivityForResult(i, Constant.GET_ALBUM_PHOTO_TASK);
                break;
            case R.id.iv_add_video:

                break;
            case R.id.btn_commit:
                setUpLoadingData();
                break;
        }
    }


    /**
     * 设置上传的资料
     */
    private void setUpLoadingData() {
        User user= BmobUser.getCurrentUser(User.class);
        CircleEntity circleEntity=new CircleEntity();
        circleEntity.setAuthor(user);
        String content=etCircle.getText().toString();

        if (!TextUtils.isEmpty(content)){
            circleEntity.setContent(content);
        }


        if (list!=null){
            List<String> bitmapUrl=new ArrayList<>();
            for (ImageBean image:list){
                String url=image.ImageUrl.substring(7);
                bitmapUrl.add(url);
            }
            circleEntity.setBitmapUrl(bitmapUrl);
        }


        sendPresenter.UpLoading(Constant.CIRCLE_SEND_TASK,circleEntity);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Constant.GET_ALBUM_PHOTO_TASK:
                if (resultCode==RESULT_OK){
                   list=data.getParcelableArrayListExtra("selectPicture");
                    adapter=new AddPhotoAdapter(ImageClickAction,deleteImageAction,list);
                    nineLayout.setAdapter(adapter);
                    if (list.size()>0){
                        showNineLayout();
                    }else {
                        dismissNineLayout();
                    }
                }
                break;
        }

    }
    /**
     * 展示nine layout
     */
    private void showNineLayout(){
        nineLayout.setVisibility(View.VISIBLE);
        ivAddPhoto.setVisibility(View.GONE);
        ivAddVideo.setVisibility(View.GONE);
    }

    /**
     * 隐藏nine layout
     */
    private void dismissNineLayout(){
        nineLayout.setVisibility(View.GONE);
        ivAddPhoto.setVisibility(View.VISIBLE);
        ivAddVideo.setVisibility(View.VISIBLE);
    }


    @Override
    public void showErrorFrame(String tip) {
        Snackbar.make(btnCommit,tip,Snackbar.LENGTH_SHORT).show();
      //  Toast.makeText(this, ""+tip, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showSuccess() {
        Snackbar.make(btnCommit,"发送成功！",Snackbar.LENGTH_SHORT).show();
       // Toast.makeText(this, "发送成功！", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void setData(List<CircleEntity> objects) {

    }

    @Override
    public void LoadingData(int taskID, int ways) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}



