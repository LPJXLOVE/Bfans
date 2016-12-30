package com.technology.lpjxlove.bfans.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.technology.lpjxlove.bfans.Adapter.AlbumAdapter;
import com.technology.lpjxlove.bfans.Adapter.PhotoAdapter;
import com.technology.lpjxlove.bfans.Bean.ImageBean;
import com.technology.lpjxlove.bfans.MVP.DaggerPhotoComponent;
import com.technology.lpjxlove.bfans.MVP.MainModule;
import com.technology.lpjxlove.bfans.MVP.MainView;
import com.technology.lpjxlove.bfans.MVP.PhotoPresenter;
import com.technology.lpjxlove.bfans.MyApplication;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.FilesUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.functions.Action1;

public class AlbumActivity extends AppCompatActivity implements MainView<Object> {

    @InjectView(R.id.recycle_view)
    RecyclerView recycleView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tv_finish)
    TextView tvFinish;
    @InjectView(R.id.recycle_view_preview)
    RecyclerView recycleViewPreview;
    @InjectView(R.id.relative_layout_bottom)
    RelativeLayout relativeLayoutBottom;
    private AlbumAdapter adapter;
    private Action1<String> ImageClickAction;//图片点击事件
    private Action1<Integer> checkBoxClickAction;//checkBox点击事件
    private Action1<ImageBean> deleteImageAction;//点击删除图片事件
    private int selectImageCount;
    private String finishText;
    private List<ImageBean> selectImageUrl;
    private PhotoAdapter photoPreviewAdapter;
    @Inject
    PhotoPresenter photoPresenter;
    private File tempFile;
    private Bundle saveInstanceState;
    private int flag;//标记哪个入口，有avatar_picture和normal_picture两个
    private List<ImageBean> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ButterKnife.inject(this);
        initData();
        this.saveInstanceState = savedInstanceState;

    }


    private void initData() {
        flag = getIntent().getIntExtra("flag", -1);
        if (flag == Constant.AVATAR_PICTURE) {
            relativeLayoutBottom.setVisibility(View.GONE);
        }
        //图片点击回调
        ImageClickAction = new Action1<String>() {
            @Override
            public void call(String s) {
                if (s.equals("camera")) {
                    openCamera();
                } else {

                    if (flag==Constant.AVATAR_PICTURE){
                        CropPhoto(Uri.parse(s));
                    }else {

                        // TODO: 2016/9/27 进入预览界面
                    }




                }

            }
        };
        //check_box点击事件回调
        checkBoxClickAction = new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                selectImageCount = integer;
                OnSuccess();
                if (integer == 10) {
                    showErrorFrame("你最多只能选择9张图片哦");
                }
                if (photoPreviewAdapter == null) {
                    selectImageUrl = adapter.getSelectImageList();
                    photoPreviewAdapter = new PhotoAdapter(ImageClickAction, deleteImageAction, selectImageUrl);
                    recycleViewPreview.setAdapter(photoPreviewAdapter);

                } else {
                    RefreshAdapter(null,0);
                }

            }
        };
        //删除图片回调
        deleteImageAction = new Action1<ImageBean>() {
            @Override
            public void call(ImageBean s) {
                if (s.ImageUrl.equals("camera")) {//当为手机拍照时
                    return;
                }
                s.isCheck = false;
                adapter.getSelectImageList().remove(s);
                adapter.notifyDataSetChanged();
                OnSuccess();
                photoPreviewAdapter.notifyDataSetChanged();

            }
        };


        selectImageUrl = getIntent().getParcelableArrayListExtra("selectPicture");
        if (selectImageUrl == null) {
            selectImageUrl = new ArrayList<>();
        }

        selectImageCount = selectImageUrl.size();
        photoPreviewAdapter = new PhotoAdapter(ImageClickAction, deleteImageAction, selectImageUrl);
        recycleViewPreview.setAdapter(photoPreviewAdapter);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        OnSuccess();
        GridLayoutManager m = new GridLayoutManager(this, 3);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(m);
        DaggerPhotoComponent.builder()
                .mainModule(new MainModule((MyApplication) getApplication()))
                .build()
                .inject(this);
        photoPresenter.setMainView(this);
        photoPresenter.onLoading(Constant.GET_ALBUM_PHOTO_TASK, -1);
        //水平布局的recycle_view
        recycleViewPreview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycleViewPreview.setHasFixedSize(true);
    }

    @Override
    public void setAdapter(List<Object> data) {
        List<ImageBean> list = new ArrayList<>();
        data.add(0, "head");
        for (int i = 0; i < data.size(); i++) {
            String s = data.get(i).toString();
            ImageBean imagebean = null;
            for (ImageBean ib : selectImageUrl) {
                if (ib.ImageUrl.equals(s)) {
                    imagebean = ib;
                    break;
                }
            }
            if (imagebean == null) {
                imagebean = new ImageBean();
                imagebean.ImageUrl = s;
                imagebean.isCheck = false;
            }
            list.add(imagebean);

        }
        adapter = new AlbumAdapter(list, ImageClickAction, checkBoxClickAction, selectImageUrl);
        adapter.setFlag(flag);
        imageList=list;
        adapter.setRecyclerView(recycleView);
        recycleView.setAdapter(adapter);
    }

    @Override
    public void showErrorFrame(String tip) {
        Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RefreshAdapter(List<Object> objects,int position) {
        photoPreviewAdapter.notifyDataSetChanged();
        recycleViewPreview.scrollToPosition(photoPreviewAdapter.getItemCount() - 1);
    }

    @Override
    public void OnSuccess() {
        if (adapter != null) {
            selectImageCount = adapter.getSelectImageList().size();
        }
        finishText = String.format(getString(R.string.photo_confirm), String.valueOf(selectImageCount));
        tvFinish.setText(finishText);
    }

    @Override
    public void LoadingData(int taskID, int ways) {

    }


    private void openCamera() {
        tempFile = FilesUtils.createCameraTempFile(saveInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "没有相机权限！", Toast.LENGTH_SHORT).show();
        } else {
            Intent take = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            take.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            startActivityForResult(take, Constant.OPEN_CAMERA_TASK);
        }

    }


    /**
     * @param uri 裁剪图片
     */
    public void CropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, Constant.CROP_PHOTO);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tempFile", tempFile);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImagePipeline pipeline = Fresco.getImagePipeline();
        pipeline.clearMemoryCaches();
        System.gc();
    }

    @OnClick(R.id.tv_finish)
    public void onClick() {
        selectImageUrl = adapter.getSelectImageList();
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("selectPicture", (ArrayList<? extends Parcelable>) selectImageUrl);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (flag == Constant.AVATAR_PICTURE) {//设置头像时
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case Constant.OPEN_CAMERA_TASK:
                        CropPhoto(Uri.fromFile(tempFile));
                        break;
                    case Constant.CROP_PHOTO:
                        setResult(RESULT_OK,data);
                        finish();
                        break;
                }

            }

        } else {
            ImageBean i = new ImageBean();
            i.ImageUrl = "file://" + tempFile.getAbsolutePath();
            i.isCheck = true;
            selectImageUrl.add(i);
            if (photoPreviewAdapter == null) {
                photoPreviewAdapter = new PhotoAdapter(ImageClickAction, deleteImageAction, selectImageUrl);
                recycleViewPreview.setAdapter(photoPreviewAdapter);
            } else {
                RefreshAdapter(null,0);
            }
            OnSuccess();
        }
    }
}
