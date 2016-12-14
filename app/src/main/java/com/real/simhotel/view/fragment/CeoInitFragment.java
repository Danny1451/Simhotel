package com.real.simhotel.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.real.simhotel.R;
import com.real.simhotel.presenter.CeoInitPresenter;
import com.real.simhotel.presenter.Presenter;
import com.real.simhotel.view.base.BaseFragment;

/**
 * Created by liudan on 2016/12/14.
 */
public class CeoInitFragment extends BaseFragment<CeoInitPresenter> {
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
        return R.layout.fragment_ceo_init;
    }

    @Override
    protected CeoInitPresenter getChildPresenter() {
        return new CeoInitPresenter();
    }

    @Override
    protected View getLoaingTargetView() {
        return null;
    }
}
