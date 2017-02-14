package com.real.simhotel.view.fragment.student;

import android.os.Bundle;
import android.view.View;

import com.real.simhotel.R;
import com.real.simhotel.presenter.HrNormalPresenter;
import com.real.simhotel.presenter.base.Presenter;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.base.BaseFragment;
import com.real.simhotel.view.iview.ISHrListView;

import java.util.List;

/**
 * Created by liudan on 2017/2/10.
 */
public class HrNormalFragment extends BaseFragment<HrNormalPresenter> implements ISHrListView{
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
        return R.layout.fragment_hr_normal;
    }

    @Override
    protected HrNormalPresenter getChildPresenter() {
        return new HrNormalPresenter(this);
    }

    @Override
    protected View getLoaingTargetView() {
        return null;
    }

    @Override
    public void renderApplicantsList(List<DynamicListModel> applicantsList) {

    }
}
