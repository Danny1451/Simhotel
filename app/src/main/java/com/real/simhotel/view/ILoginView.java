package com.real.simhotel.view;

import com.real.simhotel.view.IBaseView;

/**
 * Created by liudan on 2016/12/8.
 */
public interface ILoginView extends IBaseView {
    void loginFaied(String reason);
    void loginStudentSuccess(int role);
    void loginTeacherSuccess(int role);
}
