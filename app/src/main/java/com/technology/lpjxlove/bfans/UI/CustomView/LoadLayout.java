package com.technology.lpjxlove.bfans.UI.CustomView;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.technology.lpjxlove.bfans.R;

/**
 * Created by LPJXLOVE on 2016/5/8.
 */
public class LoadLayout extends FrameLayout {
    private ImageView LoadImgage;//加载项中顶部图片
    private DampingView LoadText;//跳动文字控件
    private String text;
    private int TextColor;//文字颜色
    private int TextSize;//文字大小
    private Context context;
    private Drawable LoadDrawable;




    public LoadLayout(Context context) {
        this(context,null);
    }

    public LoadLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.LoadLayout,defStyleAttr, 0);
        TextColor=a.getColor(R.styleable.LoadLayout_LoadTextColor, Color.DKGRAY);
        TextSize= (int) a.getDimension(R.styleable.LoadLayout_LoadTextSize,16f);
        text=a.getString(R.styleable.LoadLayout_LoadText);
        LoadDrawable=a.getDrawable(R.styleable.LoadLayout_LoadImage);
        a.recycle();
        InitData();
    }

    private void InitData() {
       View view= LayoutInflater.from(context).inflate(R.layout.loadinglayout,this);
        LoadText= (DampingView) view.findViewById(R.id.load_text);
        LoadImgage= (ImageView) view.findViewById(R.id.imageView);
        LoadText.setmTextColor(TextColor);
        LoadText.setTEXT_SIZE(TextSize);
        LoadText.setText(text);
        LoadImgage.setImageDrawable(LoadDrawable);
        startAnim();
    }

   private Animator down, up;
    protected void startAnim(){
        down= AnimatorInflater.loadAnimator(context, R.animator.enter);
        down.setTarget(LoadImgage);
        up=AnimatorInflater.loadAnimator(context, R.animator.exit);
        up.setTarget(LoadImgage);
        down.start();
        down.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                LoadText.startAnim();
                up.start();


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        up.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                down.start();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    public void setText(String text) {
        this.text = text;
        postInvalidate();
    }

    public void Dismiss(){
        down.removeAllListeners();
        up.removeAllListeners();
        down.cancel();
        up.cancel();
    }






}
