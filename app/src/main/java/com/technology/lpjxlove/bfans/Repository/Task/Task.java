package com.technology.lpjxlove.bfans.Repository.Task;

import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
public abstract class Task<T> {
    protected int loadingWays;
    protected Observable<List<T>> observable;
    protected Object UpLoadObject;

    public abstract void Loading(Observer<List<T>> observer);
    public void upLoading(Observer<List<Object>> observer, Object upLoadObject){

    }

    public void setUpLoadObject(Object upLoadObject) {
        UpLoadObject = upLoadObject;
    }

    public void setLoadingWays(int loadingWays) {
        this.loadingWays = loadingWays;
    }
}
