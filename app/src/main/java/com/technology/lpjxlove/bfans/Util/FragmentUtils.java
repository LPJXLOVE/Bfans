package com.technology.lpjxlove.bfans.Util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by LPJXLOVE on 2016/11/26.
 */

public class FragmentUtils {

    public static<T extends AppCompatActivity> void replace(T baseActivity, @IdRes int container, Fragment fragment){
        FragmentManager manager=baseActivity.getSupportFragmentManager();
        manager.beginTransaction()
                .replace(container,fragment)
                .commit();
    }
       public static<T extends AppCompatActivity> void add(T baseActivity, @IdRes int container, Fragment fragment){
        FragmentManager manager=baseActivity.getSupportFragmentManager();
        manager.beginTransaction()
                .add(container,fragment)
                .commit();
    }
    public static<T extends AppCompatActivity> void hide(T baseActivity, Fragment fragment){
        FragmentManager manager=baseActivity.getSupportFragmentManager();
        manager.beginTransaction()
                .hide(fragment)
                .commit();
    }

    public static<T extends AppCompatActivity> void show(T baseActivity, Fragment fragment){
        FragmentManager manager=baseActivity.getSupportFragmentManager();
        manager.beginTransaction()
                .show(fragment)
                .commit();
    }



}
