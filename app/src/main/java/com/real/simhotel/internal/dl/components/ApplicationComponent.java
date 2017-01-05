package com.real.simhotel.internal.dl.components;

import android.content.Context;

import com.real.simhotel.MainApplication;
import com.real.simhotel.data.ApiService;
import com.real.simhotel.internal.dl.modules.ApiServiceModule;
import com.real.simhotel.internal.dl.modules.ApplicationModule;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.view.base.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by liudan on 2016/12/7.
 */
@Singleton
@Component(modules = {ApplicationModule.class, ApiServiceModule.class})
public interface ApplicationComponent {

    void inject(BasePresenter presenter);

    void inject(BaseActivity activity);

    Context context();

    MainApplication application();

    ApiService apiService();
}
