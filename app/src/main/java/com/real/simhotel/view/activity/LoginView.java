package com.real.simhotel.view.activity;

import com.real.simhotel.view.BaseView;

/**
 * Created by liudan on 2016/12/8.
 */
public interface LoginView extends BaseView {
    void loginFaied(String reason);
    void loginStudentSuccess(int role);
    void loginTeacherSuccess(int role);
}
