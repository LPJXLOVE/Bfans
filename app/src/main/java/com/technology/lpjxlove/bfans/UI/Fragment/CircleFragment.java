package com.technology.lpjxlove.bfans.UI.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.technology.lpjxlove.bfans.Adapter.CircleAdapter;
import com.technology.lpjxlove.bfans.Bean.CircleEntity;
import com.technology.lpjxlove.bfans.Bean.Likes;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.Interface.Callback;
import com.technology.lpjxlove.bfans.MVP.MainPresenter;
import com.technology.lpjxlove.bfans.MVP.MainView;
import com.technology.lpjxlove.bfans.MyApplication;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.UI.CircleDetailActivity;
import com.technology.lpjxlove.bfans.UI.Dialog.ShareDialog;
import com.technology.lpjxlove.bfans.Util.Constant;

import java.util.List;

import cn.bmob.v3.BmobUser;
import rx.functions.Action1;


/**
 * 动态
 */
public class CircleFragment extends Fragment implements MainView<CircleEntity>,SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecycleView;
    private Callback callback;
    private MainPresenter presenter;
    private Action1<String> commentClick,goodNormalClick,goodPressClick,shareClick,avatarClick,imageClick;
    private Action1<CircleEntity> commentItemClick;
    private CircleAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isInit;//用于判断是否已经加载过数据
    private CircleEntity circleEntity;
    public CircleFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initEvent();
        View view=inflater.inflate(R.layout.fragment_circle, container, false);
        mRecycleView= (RecyclerView) view.findViewById(R.id.relative_layout);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.cardview_dark_background,R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager= (LinearLayoutManager) recyclerView.getLayoutManager();
                int LastPosition=manager.findLastCompletelyVisibleItemPosition();
                if (newState==RecyclerView.SCROLL_STATE_IDLE&&adapter!=null&&LastPosition==adapter.getItemCount()-1){
                    presenter.onLoading(Constant.BATTLE_MARKET_TASK,Constant.LOADING_MORE_TASK);
                    swipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(true);
                        }
                    });

                }
                if (newState==RecyclerView.SCROLL_STATE_DRAGGING){
                    if (callback==null){
                        return;
                    }
                    callback.OnCallback("");
                }
            }
        });

        return view;
    }

    //懒加载
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser&&!isInit){
            if (presenter==null){
                presenter=new MainPresenter((MyApplication) getActivity().getApplication());
            }
            presenter.onLoading(Constant.CIRCLE_MARKET_TASK,Constant.INIT_DATA_TASK);
            isInit=true;
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    private void initEvent() {
        avatarClick=new Action1<String>() {//头像点击监听
            @Override
            public void call(String s) {

            }
        };
        commentClick=new Action1<String>() {//评论点击监听
            @Override
            public void call(String s) {

            }
        };
        goodNormalClick=new Action1<String>() {//取消点赞点击监听
            @Override
            public void call(String s) {
                User u = BmobUser.getCurrentUser(User.class);
                Likes likes = new Likes();
                likes.setPostId(s);
                likes.setUserId(u.getObjectId());
                likes.setWay("1");
                presenter.UpLoading(Constant.CIRCLE_LIKES_TASK, likes);

            }
        };
        goodPressClick=new Action1<String>() {//点赞点击监听
            @Override
            public void call(String s) {
                User u = BmobUser.getCurrentUser(User.class);
                Likes likes = new Likes();
                likes.setPostId(s);
                likes.setUserId(u.getObjectId());
                likes.setWay("0");
                presenter.UpLoading(Constant.CIRCLE_LIKES_TASK, likes);
            }
        };

        shareClick=new Action1<String>() {//分享点击监听
            @Override
            public void call(String s) {
                ShareDialog d=new ShareDialog();
              //// FIXME: 2016/10/11
                d.show(getFragmentManager(),"share");


            }
        };
        imageClick=new Action1<String>() {//图片点击监听
            @Override
            public void call(String s) {

            }
        };
        commentItemClick=new Action1<CircleEntity>() {//整个item点击监听
            @Override
            public void call(CircleEntity circle) {
                circleEntity=circle;
                Intent i=new Intent(getActivity(), CircleDetailActivity.class);
                i.putExtra("CircleEntity",circleEntity);
                startActivityForResult(i,Constant.CIRCLE_MARKET_TASK);

            }
        };




    }





    public void setPresenter(MainPresenter presenter) {
       this.presenter=presenter;
        this.presenter.setMainView(this);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void setAdapter(List<CircleEntity> data) {
           adapter=new CircleAdapter(getActivity(),data,imageClick,avatarClick,shareClick,goodNormalClick,goodPressClick,commentClick,commentItemClick);
           mRecycleView.setAdapter(adapter);

    }

    @Override
    public void showErrorFrame(String tip) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getActivity(), ""+tip, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void RefreshAdapter(List<CircleEntity> circleEntities, int position) {
            adapter.setData(circleEntities);
            adapter.notifyDiff();
            mRecycleView.scrollToPosition(position);
    }

    @Override
    public void OnSuccess() {
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void LoadingData(int taskID, int ways) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Constant.CIRCLE_MARKET_TASK){//回调判断是否要改变点赞的状态和点赞、评论数目
            if (resultCode== Activity.RESULT_OK&&data!=null){
                int CommentCount=data.getIntExtra("CommentCount",0);
                circleEntity.setCommentCount(CommentCount);
                boolean isChange=data.getBooleanExtra("isChange",false);

                if (!isChange){
                    adapter.notifyDataSetChanged();
                    return;
                }

                boolean isLike=data.getBooleanExtra("isLike",false);
                if (isLike){
                   int count= circleEntity.getLikeCount()+1;
                    circleEntity.setLikeCount(count);
                }else {
                    int count= circleEntity.getLikeCount()-1;
                    circleEntity.setLikeCount(count);
                }
                circleEntity.setLike(isLike);
                adapter.notifyDataSetChanged();
            }
        }


    }

    @Override
    public void onRefresh() {
        presenter.onLoading(Constant.CIRCLE_MARKET_TASK,Constant.REFRESH_TASK);

    }
}
