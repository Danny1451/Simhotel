package com.real.simhotel.presenter;

import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.view.iview.ISHrListView;

/**
 * Created by liudan on 2017/2/10.
 */
public class HrNormalPresenter extends BasePresenter {

    ISHrListView mView;

    public HrNormalPresenter(ISHrListView view){
        mView = view;
    }

    @Override
    public void requestData(Object... o) {
        //请求日常的员工
    }
}
