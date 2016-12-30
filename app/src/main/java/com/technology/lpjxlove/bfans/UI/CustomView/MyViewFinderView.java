package com.technology.lpjxlove.bfans.UI.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.DisplayUtils;
import me.dm7.barcodescanner.core.ViewFinderView;

/**
 * Created by LPJXLOVE on 2016/12/27.
 */

public class MyViewFinderView extends ViewFinderView {
    private int distance=10;
    public MyViewFinderView(Context context) {
        super(context);
    }

    public MyViewFinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void drawLaser(Canvas canvas) {
       // super.drawLaser(canvas);
        Rect framingRect = this.getFramingRect();
     /*   this.mLaserPaint.setAlpha(SCANNER_ALPHA[this.scannerAlpha]);
        this.scannerAlpha = (this.scannerAlpha + 1) % SCANNER_ALPHA.length;*/
        //int middle = framingRect.height() / 2 + framingRect.top;

        int middle=framingRect.top+distance;
        distance+=10;
        if (middle>=framingRect.top+framingRect.height()){
            middle=framingRect.top;
            distance=10;
        }
        canvas.drawRect((float)(framingRect.left + 2), (float)(middle - 1), (float)(framingRect.right - 1), (float)(middle + 2), this.mLaserPaint);
        this.postInvalidateDelayed(30L, framingRect.left - 10, framingRect.top - 10, framingRect.right + 10, framingRect.bottom + 10);
    }

    @Override
    public void drawViewFinderBorder(Canvas canvas) {
        super.drawViewFinderBorder(canvas);
    }

    @Override
    public void drawViewFinderMask(Canvas canvas) {
        super.drawViewFinderMask(canvas);
    }

    @Override
    public synchronized void updateFramingRect() {


        super.updateFramingRect();
    }
}
