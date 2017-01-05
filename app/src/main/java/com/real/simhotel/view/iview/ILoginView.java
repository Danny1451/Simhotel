package com.real.simhotel.view.iview;

/**
 * Created by liudan on 2016/12/8.
 */
public interface ILoginView extends IBaseView {
    void loginFaied(String reason);
    void loginStudentSuccess(int uid);
    void loginTeacherSuccess(int uid);
}
