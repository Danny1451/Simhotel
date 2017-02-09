package com.real.simhotel.presenter;

import com.real.simhotel.config.Constants;
import com.real.simhotel.config.Role;
import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.rx.DefaultSubscriber;
import com.real.simhotel.utils.PreferenceUtils;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.activity.LoginActivity;
import com.real.simhotel.view.iview.ILoginView;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liudan on 2016/12/8.
 */
public class LoginPresenter extends BasePresenter {

    private Subscription subscription;

    private ILoginView mView;

    public LoginPresenter(LoginActivity view){
        mView = view;
    }

    public void login(int userType , String userName, String pwd){
        //请求网络

        subscription = apiService.login(userType,userName,pwd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<Response<Integer>, Observable<Integer>>() {
                             @Override
                             public Observable<Integer> call(Response<Integer> stringResponse) {
                                 return RetrofitUtils.flatResponse(stringResponse);
                             }
                         }).subscribe(new LoginSubscriber(userType));


    }

    @Override
    public void destroy() {
        super.destroy();
        if (null != subscription)
            subscription.unsubscribe();
    }



    public class LoginSubscriber extends DefaultSubscriber<Integer>{

        int mUserType;

        public LoginSubscriber(int userType){
            super();
            mUserType = userType;
        }

        @Override
        public void onNext(Integer s) {
            super.onNext(s);

            //保存uid
            application.uid = s + "";
            KLog.d("uid = " + s);

            //根据登录结果 回调 view
            if (mUserType == Constants.USER_TYPE_STUDENT) {

                //设定学生的角色
                application.mRole = Integer.parseInt(PreferenceUtils.getCharacter(application));
                KLog.d("role  = " + application.mRole);
                mView.loginStudentSuccess(s);

            }else {

                application.mRole = Role.ROLE_TEACHER;
                mView.loginTeacherSuccess(s);
            }

        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            //加载失败
            mView.loginFaied(e.toString());

        }
    }
}
