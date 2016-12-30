package com.technology.lpjxlove.bfans.UI.CustomView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.technology.lpjxlove.bfans.R;

import rx.functions.Action1;

/**
 * Created by LPJXLOVE on 2016/10/11.
 */

public class GoodView extends ImageView {
    private boolean isPress;
    private OnLikesListener onLikesListener;

    public GoodView(Context context) {
        super(context);

    }

    public GoodView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public GoodView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void init() {
        if (isPress) {
            setImageResource(R.drawable.ic_good);
            isPress = false;
            onLikesListener.cancelLikes();
        } else {
            setImageResource(R.drawable.ic_good_full);
            zoomInAnimation();
            isPress = true;
            onLikesListener.Likes();
        }
    }

    public void setOnLikesListener(OnLikesListener onLikesListener) {
        this.onLikesListener = onLikesListener;
    }

    public boolean isPress() {
        return isPress;
    }

    public void setPress(boolean press) {
        isPress = press;
       /* if (isPress()){
            setImageResource(R.drawable.ic_good_full);
        }else {
            setImageResource(R.drawable.ic_good);
        }*/


    }

    @Override
    public boolean performClick() {
        init();
        return super.performClick();
    }

    /**
     * 放大动画
     */
    private void zoomInAnimation() {
        ObjectAnimator a1 = ObjectAnimator.ofFloat(this, "scaleX", 2f, 1f);
        ObjectAnimator a2 = ObjectAnimator.ofFloat(this, "scaleY", 2f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(a1, a2);
        set.setDuration(300);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
    }

    /**
     * 缩小动画
     */
    private void zoomOutAnimation() {


    }


    /**
     * 点赞与取消赞的监听
     */
    public interface OnLikesListener {
        void Likes();

        void cancelLikes();
    }

}
