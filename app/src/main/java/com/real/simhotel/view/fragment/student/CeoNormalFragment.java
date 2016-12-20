package com.real.simhotel.view.fragment.student;

import android.os.Bundle;
import android.view.View;

import com.real.simhotel.presenter.CeoInitPresenter;
import com.real.simhotel.presenter.CeoNormalPresenter;
import com.real.simhotel.presenter.base.Presenter;
import com.real.simhotel.view.base.BaseFragment;

/**
 * Created by liudan on 2016/12/16.
 */
public class CeoNormalFragment extends BaseFragment{

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected CeoNormalPresenter getChildPresenter() {
        return new CeoNormalPresenter(this);
    }

    @Override
    protected View getLoaingTargetView() {
        return null;
    }
}
