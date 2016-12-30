package com.technology.lpjxlove.bfans.Interface;

import java.util.List;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
public interface BasePresenter<T> {
    void onLoading(int taskID, int ways,Object...objects);
    void onLoadingSuccess(List<T> data);
    void onLoadingFailed(String tip);
}
