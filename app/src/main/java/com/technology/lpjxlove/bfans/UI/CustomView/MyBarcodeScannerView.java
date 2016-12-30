package com.technology.lpjxlove.bfans.UI.CustomView;

import android.content.Context;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.*;

/**
 * Created by LPJXLOVE on 2016/12/27.
 */

public abstract class MyBarcodeScannerView extends me.dm7.barcodescanner.core.BarcodeScannerView {
    public MyBarcodeScannerView(Context context) {
        super(context);
    }

    public MyBarcodeScannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected IViewFinder createViewFinderView(Context context) {
        return new MyViewFinderView(context);
    }
}
