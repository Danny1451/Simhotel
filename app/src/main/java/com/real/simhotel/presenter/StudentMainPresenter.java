package com.real.simhotel.presenter;

import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.view.activity.student.StudentMainActivity;
import com.real.simhotel.view.base.BaseFragment;

/**
 * Created by liudan on 2016/12/9.
 */
public class StudentMainPresenter extends BasePresenter {

    private StudentMainActivity mView;


    private BaseFragment mRoleFragment;
    public StudentMainPresenter(StudentMainActivity View){ this.mView = mView;}

    @Override
    public void destroy() {
        super.destroy();

    }
}
