package com.technology.lpjxlove.bfans.UI.CustomView;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;

/**
 * Created by LPJXLOVE on 2016/12/27.
 */

public class BarcodeScannerView extends me.dm7.barcodescanner.core.BarcodeScannerView {
    public BarcodeScannerView(Context context) {
        super(context);
    }

    public BarcodeScannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {


    }
}
