package com.technology.lpjxlove.bfans.UI.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.technology.lpjxlove.bfans.R;

/**
 * Created by LPJXLOVE on 2016/5/7.
 */
public class DampingView extends View {
    private Paint mTextPaint;//文字画笔
    private int mTextWidth;
    private int  TEXT_SIZE;
    private int mTextColor;
    private String Text;
    private String DefaultText="开心一每天";
    private Path mPath;
    private int PAINT_TEXT_SIZE=40;
    private int INITOFFSET=PAINT_TEXT_SIZE;
    private int MAX_OFFSET=PAINT_TEXT_SIZE;
    private int OFFSET;
    private int invalidateCount =9;//绘制的次数
    private int count= invalidateCount;




    public DampingView(Context context) {
        this(context,null);
    }

    public DampingView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DampingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.DampingView, defStyleAttr,0);
        Text= DefaultText;
        TEXT_SIZE= (int) a.getDimension(R.styleable.DampingView_TextSize,16.0f);
        mTextColor=a.getColor(R.styleable.DampingView_TextColor, Color.DKGRAY);
        a.recycle();
        initPaint();
    }

    public void setTEXT_SIZE(int TEXT_SIZE) {
        this.TEXT_SIZE = TEXT_SIZE;
    }

    public void setText(String text) {
        Text = text;
        mTextWidth= (int) mTextPaint.measureText(Text);
    }

    private void initPaint() {
        mTextPaint=new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(TEXT_SIZE);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextWidth= (int) mTextPaint.measureText(Text);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int baselineSpace=40;
        setMeasuredDimension(mTextWidth, baselineSpace+PAINT_TEXT_SIZE);
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

     drawLineText(canvas);

    }

    private void drawLineText(Canvas canvas) {
        if (mPath==null){
            mPath=new Path();
            mPath.moveTo(0,PAINT_TEXT_SIZE);
            mPath.lineTo(mTextWidth,PAINT_TEXT_SIZE);
            mPath.close();
        }else {
            drawQuad();
            OFFSET=getOFFSET();
        }

        if (count< invalidateCount){
            count++;
            postInvalidate();
        }else {

            OFFSET=0;
            drawQuad();

        }

        canvas.drawTextOnPath(Text,mPath,0, 0, mTextPaint);


    }


    /**
     * 开启动画
     */
    public void startAnim(){
        count=0;
        postInvalidate();
    }



    private int getOFFSET() {
        int SPACE = 6;
        if (OFFSET>=MAX_OFFSET){

            return OFFSET- SPACE;
        } else if (OFFSET<=INITOFFSET){

            return OFFSET+ SPACE;
        }else {

            return 0;
        }


    }

    public void drawQuad(){
        mPath.reset();
        mPath.moveTo(0, PAINT_TEXT_SIZE);
        //   mPath.quadTo(mTextWidth/2, OFFSET,mTextWidth, PAINT_TEXT_SIZE);
        // mPath.quadTo(0, PAINT_TEXT_SIZE, 5, PAINT_TEXT_SIZE);
        mPath.quadTo(mTextWidth / 2, PAINT_TEXT_SIZE + OFFSET, mTextWidth, PAINT_TEXT_SIZE);
        //mPath.quadTo(mTextWidth * 5 / 6, PAINT_TEXT_SIZE, mTextWidth, PAINT_TEXT_SIZE);
        mPath.close();
        //canvas.drawPath(mPath, mTextPaint);
    }





}
