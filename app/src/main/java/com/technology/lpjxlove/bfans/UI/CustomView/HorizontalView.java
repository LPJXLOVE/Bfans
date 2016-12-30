package com.technology.lpjxlove.bfans.UI.CustomView;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.technology.lpjxlove.bfans.Adapter.NineGridAdapter;
import com.technology.lpjxlove.bfans.Bean.User;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.Util.WindowsUtils;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by LPJXLOVE on 2016/10/6.
 */

public class HorizontalView extends LinearLayout implements View.OnClickListener {

    private NineGridAdapter adapter;
    private int totalWidth;
    private int gap;
    private Action1<String> endClick;
    private Action1<String> itemClick;
    public HorizontalView(Context context) {
        super(context);
        init();
    }

    public HorizontalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.setOrientation(HORIZONTAL);
    }
    public NineGridAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(final NineGridAdapter adapter) {
        totalWidth=WindowsUtils.getScreenWidthPixels(this.getContext())-getPaddingRight()*2;
        this.adapter = adapter;
        int size=adapter.getCount();
        removeAllViews();
        int width= (int) WindowsUtils.dp2px(getContext(),24);
        gap= (int) WindowsUtils.dp2px(getContext(),4);
        for (int i=0;i<size;i++){
            final int position=i;
            SimpleDraweeView iv= (SimpleDraweeView) adapter.getView(i,null);
            final LayoutParams params=new LayoutParams(width,width);
            params.setMargins(gap,gap,0,gap);
            iv.setLayoutParams(params);
            iv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Observable<String> o=Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            itemClick.call(String.valueOf(position));
                        }
                    });
                    if (itemClick!=null){
                        o.subscribe(itemClick);
                    }
                }
            });
            addView(iv);
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int size=getChildCount();
        int width;
        for (int i = 0; i <size ; i++) {
            View v=getChildAt(i);
            width=(v.getWidth()+gap)*(i+1);
            Log.i("test",width+"屏幕宽度"+totalWidth);
            if (width>totalWidth){
                SimpleDraweeView view= (SimpleDraweeView) getChildAt(i-1);
                view.setImageResource(R.drawable.ic_more);
                view.setOnClickListener(this);
                for (int j=i;j<size;j++){
                    removeViewAt(i);
                    Log.i("test","我在执行"+j );
                    Log.i("test","子View"+size);
                }
                break;
            }
        }
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void onClick(View v) {
        Observable<String> observable=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                endClick.call("end");
            }
        });
        if (endClick==null){
            return;
        }
        observable.subscribe(endClick);

    }

    public Action1<String> getItemClick() {
        return itemClick;
    }

    public void setItemClick(Action1<String> itemClick) {
        this.itemClick = itemClick;
    }

    public Action1<String> getEndClick() {
        return endClick;
    }

    public void setEndClick(Action1<String> endClick) {
        this.endClick = endClick;
    }
}
