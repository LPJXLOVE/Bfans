package com.technology.lpjxlove.bfans.UI.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.technology.lpjxlove.bfans.Adapter.BattleAdapter;
import com.technology.lpjxlove.bfans.Bean.BattleEntity;
import com.technology.lpjxlove.bfans.Interface.Callback;
import com.technology.lpjxlove.bfans.MVP.MainPresenter;
import com.technology.lpjxlove.bfans.MVP.MainView;
import com.technology.lpjxlove.bfans.MyApplication;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.UI.BattleDetailActivity;
import com.technology.lpjxlove.bfans.Util.Constant;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class BattleFragment extends Fragment implements  MainView<BattleEntity> {


    @InjectView(R.id.recycle_view)
    RecyclerView recycleView;
    @InjectView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    private Callback callBack;
    private BattleAdapter mBattleAdapter;
    private MainPresenter presenter;
    private Action1<String> ItemClickListener;

    public BattleFragment() {
        // Required empty public constructor
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_battle, container, false);
        ButterKnife.inject(this, view);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleView.setHasFixedSize(true);

        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager= (LinearLayoutManager) recyclerView.getLayoutManager();
                int LastPosition=manager.findLastCompletelyVisibleItemPosition();
               if (newState==RecyclerView.SCROLL_STATE_IDLE&&mBattleAdapter!=null&&LastPosition==mBattleAdapter.getItemCount()-1){
                  // Toast.makeText(getActivity(), "最后一个", Toast.LENGTH_SHORT).show();
                  presenter.onLoading(Constant.BATTLE_MARKET_TASK,Constant.LOADING_MORE_TASK);
                   swipeLayout.post(new Runnable() {
                       @Override
                       public void run() {
                           swipeLayout.setRefreshing(true);
                       }
                   });

               }
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    if (callBack==null){
                        return;
                    }
                    callBack.OnCallback("");
                }
            }

        });
        //swipe_layout初始化
        swipeLayout.setColorSchemeResources(R.color.cardview_dark_background,R.color.colorPrimary);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onLoading(Constant.BATTLE_MARKET_TASK,Constant.REFRESH_TASK);
            }
        });
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }


    private void initData() {
        ItemClickListener=new Action1<String>() {//项目点击事件
            @Override
            public void call(String s) {
                Intent intent = new Intent(getActivity(), BattleDetailActivity.class);
                BattleAdapter adapter=(BattleAdapter) recycleView.getAdapter();
                BattleEntity item=adapter.getData().get(Integer.valueOf(s));
                intent.putExtra("BattleEntity",item);
                startActivity(intent);
            }
        };
        if (presenter==null){
            MyApplication myApplication= (MyApplication) getActivity().getApplication();
            presenter=new MainPresenter(myApplication);
        }
        presenter.setMainView(this);
        presenter.onLoading(Constant.BATTLE_MARKET_TASK,Constant.INIT_DATA_TASK);
        swipeLayout.setRefreshing(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    public void setCallBack(Callback callBack) {
        this.callBack = callBack;
    }

    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
     /*   presenter.setMainView(this);*/
    }


    @Override
    public void setAdapter(List<BattleEntity> data) {
       // Toast.makeText(getActivity(), ""+data.size(), Toast.LENGTH_SHORT).show();
        mBattleAdapter = new BattleAdapter(data,ItemClickListener);
        recycleView.setAdapter(mBattleAdapter);
    }

    @Override
    public void showErrorFrame(String tip) {
        Toast.makeText(getActivity(), tip, Toast.LENGTH_SHORT).show();
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void RefreshAdapter(List<BattleEntity> news, int position) {
     //   mBattleAdapter.notifyDataSetChanged();
        mBattleAdapter.setData(news);
        mBattleAdapter.notifyDiff();
        recycleView.scrollToPosition(position);
    }

    @Override
    public void OnSuccess() {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void LoadingData(int taskID, int ways) {
            presenter.onLoading(Constant.BATTLE_MARKET_TASK,Constant.INIT_DATA_TASK);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
