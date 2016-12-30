package com.technology.lpjxlove.bfans.MVP;

import com.technology.lpjxlove.bfans.Interface.BaseView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LPJXLOVE on 2016/9/9.
 */
public interface MainView<T> extends BaseView,Serializable{
    void setAdapter(List<T> data);
    void showErrorFrame(String tip);
    void RefreshAdapter(List<T> news,int position);
    void OnSuccess();

}
