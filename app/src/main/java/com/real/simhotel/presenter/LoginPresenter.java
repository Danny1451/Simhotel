package com.real.simhotel.presenter;

import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.rx.DefaultSubscriber;
import com.real.simhotel.view.activity.LoginActivity;

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

    private LoginActivity mView;

    public LoginPresenter(LoginActivity view){
        mView = view;
    }

    public void login(String userName, String pwd){
        //请求网络

        subscription = apiService.login(userName,pwd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<Response<String>, Observable<String>>() {
                             @Override
                             public Observable<String> call(Response<String> stringResponse) {
                                 return RetrofitUtils.flatResponse(stringResponse);
                             }
                         }).subscribe(new LoginSubscriber());


    }

    @Override
    public void destroy() {
        super.destroy();
        if (null != subscription)
            subscription.unsubscribe();
    }



    public class LoginSubscriber extends DefaultSubscriber<String>{

        @Override
        public void onNext(String s) {
            super.onNext(s);

            mView.loginStudentSuccess(1);

        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            mView.loginFaied(e.toString());
            //加载失败
        }
    }
}
