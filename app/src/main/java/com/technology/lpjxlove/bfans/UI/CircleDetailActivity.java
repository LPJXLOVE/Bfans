package com.technology.lpjxlove.bfans.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.imagepipeline.producers.JobScheduler;
import com.technology.lpjxlove.bfans.Adapter.CircleDetailAdapter;
import com.technology.lpjxlove.bfans.Bean.CircleDetailEntity;
import com.technology.lpjxlove.bfans.Bean.CircleEntity;
import com.technology.lpjxlove.bfans.Bean.Comment;
import com.technology.lpjxlove.bfans.Bean.Likes;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.MVP.DaggerPullAndPushComponent;
import com.technology.lpjxlove.bfans.MVP.PullAndPushModule;
import com.technology.lpjxlove.bfans.MVP.PullAndPushPresenter;
import com.technology.lpjxlove.bfans.MVP.PullAndPushView;
import com.technology.lpjxlove.bfans.MyApplication;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.UI.Dialog.ShareDialog;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.KeyboardUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import rx.functions.Action1;

public class CircleDetailActivity extends AppCompatActivity implements PullAndPushView<CircleDetailEntity> {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.recycle_view)
    RecyclerView recycleView;
    @InjectView(R.id.ed_comment)
    EditText etComment;
    @InjectView(R.id.tv_comment)
    TextView tvComment;
    @InjectView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    private Action1<String> commentClick, goodClick, cancelLikeClick, shareClick, avatarClick, imageClick;
    private Action1<String> commentItemClick, goodItemClick, goodEndClick;
    private CircleDetailAdapter adapter;
    private List<CircleEntity> data;
    @Inject
    PullAndPushPresenter Presenter;
    private String objectId;//post的objectId
    private boolean isChange;//判断是否进行了点赞或者取消赞的操作
    private boolean isFirst=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_circle_detail);
        ButterKnife.inject(this);
        initEvent();
        initData();
    }

    private void initData() {
        DaggerPullAndPushComponent.builder()
                .pullAndPushModule(new PullAndPushModule((MyApplication) getApplication(), this))
                .build()
                .inject(this);
        initHeadData();
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycleView.setHasFixedSize(true);
        adapter = new CircleDetailAdapter(this, data, avatarClick, commentClick, goodClick, cancelLikeClick, shareClick
                , commentItemClick, imageClick
                , goodItemClick, goodEndClick);
        recycleView.setAdapter(adapter);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                onBackPressed();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.black_overlay, R.color.colorPrimary);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Presenter.onLoading(Constant.QUERY_CIRCLE_COMMENT_AND_LIKES_TASK, Constant.REFRESH_TASK);
            }
        });
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int LastPosition = manager.findLastCompletelyVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && adapter != null && LastPosition == adapter.getItemCount() - 1) {
                    Presenter.onLoading(Constant.QUERY_CIRCLE_COMMENT_AND_LIKES_TASK, Constant.LOADING_MORE_TASK);
                    swipeLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeLayout.setRefreshing(true);
                        }
                    });

                }
            }
        });


    }

    /**
     * 加载头部资料
     */
    private void initHeadData() {
        data = new ArrayList<>();
        CircleEntity circleEntity = (CircleEntity) getIntent().getSerializableExtra("CircleEntity");
        objectId = circleEntity.getObjectId();
        data.add(circleEntity);//设置头部视图
        data.add(new CircleEntity());//设置没有评论的视图


    }


    private void initEvent() {
        avatarClick = new Action1<String>() {//头像点击监听
            @Override
            public void call(String s) {

            }
        };
        commentClick = new Action1<String>() {//评论点击监听
            @Override
            public void call(String s) {
                etComment.requestFocus();
                KeyboardUtil.showKeyboard(getApplicationContext(), etComment);
            }
        };
        goodClick = new Action1<String>() {//点赞点击监听
            @Override
            public void call(String s) {
                User u = BmobUser.getCurrentUser(User.class);
                Likes likes = new Likes();
                likes.setPostId(objectId);
                likes.setUserId(u.getObjectId());
                likes.setWay("0");
                data.get(0).setLike(true);
                isChange=true;
                Presenter.UpLoading(Constant.CIRCLE_LIKES_TASK, likes);
            }
        };
        cancelLikeClick = new Action1<String>() {//取消赞监听
            @Override
            public void call(String s) {
                User u = BmobUser.getCurrentUser(User.class);
                Likes likes = new Likes();
                likes.setPostId(objectId);
                likes.setUserId(u.getObjectId());
                likes.setWay("1");
                data.get(0).setLike(false);
                isChange=true;
                Presenter.UpLoading(Constant.CIRCLE_LIKES_TASK, likes);
            }
        };


        shareClick = new Action1<String>() {//分享点击监听
            @Override
            public void call(String s) {
                ShareDialog dialog = new ShareDialog();
                dialog.show(getSupportFragmentManager(), "share");

            }
        };
        imageClick = new Action1<String>() {//图片点击监听
            @Override
            public void call(String s) {

            }
        };
        commentItemClick = new Action1<String>() {//项目点击监听
            @Override
            public void call(String s) {

            }
        };

        goodItemClick = new Action1<String>() {//点赞者头像点击监听
            @Override
            public void call(String s) {
                Toast.makeText(CircleDetailActivity.this, "" + s, Toast.LENGTH_SHORT).show();
            }
        };

        goodEndClick = new Action1<String>() {//更多点击监听
            @Override
            public void call(String s) {
                Intent i = new Intent(getApplicationContext(), MoreGoodActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

    }

    @OnClick(R.id.tv_comment)
    public void onClick() {
        String content = etComment.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "亲，评论内容不能为空哦！", Toast.LENGTH_SHORT).show();
        } else {
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setAuthor(BmobUser.getCurrentUser(User.class));
            comment.setObjectId(objectId);//评论Post的id
            Presenter.UpLoading(Constant.CIRCLE_COMMENT_TASK, comment);
        }
    }


    @Override
    public void showErrorFrame(String tip) {
        swipeLayout.setRefreshing(false);
        if (tip==null){
            return;
        }
        Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showSuccess(String tip) {
        if (etComment != null) {
            etComment.setText(null);
            Presenter.onLoading(Constant.QUERY_CIRCLE_COMMENT_AND_LIKES_TASK, Constant.REFRESH_TASK);//评论成功后刷新数据
            KeyboardUtil.dismissKeyboard(this,etComment);
        }
        Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void setData(List<CircleDetailEntity> data) {
        CircleDetailEntity entity = data.get(0);
        List<Comment> comments=entity.getComments();
        List<User> likes=entity.getLikes();
        swipeLayout.setRefreshing(false);
        adapter.setCircleEntity(entity.getCircleEntity());
        adapter.setCommentData(comments);
        adapter.setLikeData(likes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void refreshAdapter(int position,Object o) {
        swipeLayout.setRefreshing(false);
        CircleEntity circleEntity= (CircleEntity) o;
        adapter.setCircleEntity(circleEntity);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isFirst){
            this.LoadingData(Constant.QUERY_CIRCLE_COMMENT_AND_LIKES_TASK, Constant.INIT_DATA_TASK);
            isFirst=false;
        }
    }

    @Override
    public void LoadingData(int taskID, int ways) {
        swipeLayout.setRefreshing(true);
        Presenter.onLoading(taskID, ways, objectId);
    }

    @Override
    public void onBackPressed() {
        CircleEntity circleEntity=adapter.getCircleEntity();
        if (circleEntity!=null){

            if (data.get(0).getLikeCount()!=circleEntity.getLikeCount()){
                isChange=true;
            }

            //返回最新结果
            Intent intent=new Intent();
            intent.putExtra("isLike",data.get(0).isLike());
            intent.putExtra("isChange",isChange);
            intent.putExtra("CommentCount",circleEntity.getCommentCount());
            setResult(RESULT_OK,intent);
        }

        super.onBackPressed();
    }
}
