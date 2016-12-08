package com.real.simhotel.presenter;

import android.content.Context;

import com.real.simhotel.MainApplication;
import com.real.simhotel.data.ApiService;
import com.real.simhotel.view.BaseView;

import javax.inject.Inject;

/**
 * Created by liudan on 2016/12/7.
 */
public class BasePresenter  implements Presenter {



    @Inject
    ApiService apiService;

    public BasePresenter(){
        MainApplication.getComponent().inject(this);
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void requestData(Object... o) {

    }
}
