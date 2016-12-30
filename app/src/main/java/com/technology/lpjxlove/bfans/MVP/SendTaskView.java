package com.technology.lpjxlove.bfans.MVP;

import com.technology.lpjxlove.bfans.Interface.BaseView;

import java.util.List;

/**
 * Created by LPJXLOVE on 2016/9/17.
 */
public interface SendTaskView<T> extends BaseView {
    void showErrorFrame(String tip);
    void showSuccess();
    void setData(List<T> objects);
}
