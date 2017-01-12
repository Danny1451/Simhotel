package com.real.simhotel.view.iview;

import java.util.List;

/**
 * Created by liudan on 2016/12/7.
 */
public interface IBaseView {
    void showLoading();

    void disMissLoading();
//
    void showEmptyView(String msg);
//
    void refreshView();
//
    void showError(String msg);

    void showToast(String msg);
//
//
//    void hasNoMoreData();
//
//    void loadMoreFinish(List dates);
//
//    void showRefreshFinish(List score);
//
//    void showToastError();
}
