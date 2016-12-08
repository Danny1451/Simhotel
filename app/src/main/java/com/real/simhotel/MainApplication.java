package com.real.simhotel;

import android.app.Application;
import android.content.Context;

import com.real.simhotel.internal.dl.components.ApplicationComponent;
import com.real.simhotel.internal.dl.components.DaggerApplicationComponent;
import com.real.simhotel.internal.dl.modules.ApplicationModule;

/**
 * Created by liudan on 2016/12/7.
 */
public class MainApplication extends Application {
    public static Context mContext;
    private ApplicationComponent mComponent;

    public static ApplicationComponent getComponent() {
        return ((MainApplication) mContext.getApplicationContext()).mComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;


        mComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }




}
