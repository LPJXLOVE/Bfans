package com.technology.lpjxlove.bfans.Util;

import android.content.Context;
import android.hardware.input.InputManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by LPJXLOVE on 2016/10/9.
 */

public class KeyboardUtil {
    public static void showKeyboard(Context context, View view){
        InputMethodManager input= (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            input.showSoftInput(view,0);
    }
    public static void dismissKeyboard(Context context, View view){
        InputMethodManager input= (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        input.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
