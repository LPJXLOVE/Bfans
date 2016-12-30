package com.technology.lpjxlove.bfans.MVP;

import com.technology.lpjxlove.bfans.Interface.BaseView;

import java.util.List;

/**
 * Created by LPJXLOVE on 2016/10/21.
 */

public interface PullAndPushView<T> extends BaseView {
    void showErrorFrame(String tip);
    void showSuccess(String tip);
    void setData(List<T> data);
    void refreshAdapter(int position, Object o);
}
