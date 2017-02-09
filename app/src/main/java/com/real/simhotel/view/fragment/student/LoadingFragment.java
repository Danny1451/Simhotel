package com.real.simhotel.view.fragment.student;

import android.os.Bundle;
import android.view.View;

import com.real.simhotel.R;
import com.real.simhotel.presenter.base.Presenter;
import com.real.simhotel.view.base.BaseFragment;
import com.real.simhotel.view.fragment.BaseDetailFragment;

/**
 * Created by liudan on 2017/2/9.
 */
public class LoadingFragment extends BaseFragment {

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
        return R.layout.fragment_loading;
    }

    @Override
    protected Presenter getChildPresenter() {
        return null;
    }

    @Override
    protected View getLoaingTargetView() {
        return null;
    }
}
