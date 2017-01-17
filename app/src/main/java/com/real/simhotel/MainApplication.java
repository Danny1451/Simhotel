package com.real.simhotel;

import android.app.Application;
import android.content.Context;

import com.real.simhotel.events.BroadCastManager;
import com.real.simhotel.internal.dl.components.ApplicationComponent;
import com.real.simhotel.internal.dl.components.DaggerApplicationComponent;
import com.real.simhotel.internal.dl.modules.ApiServiceModule;
import com.real.simhotel.internal.dl.modules.ApplicationModule;
import com.real.simhotel.model.Hotel;
import com.real.simhotel.model.Training;
import com.real.simhotel.utils.CrashHandler;

/**
 * Created by liudan on 2016/12/7.
 */
public class MainApplication extends Application {
    public static Context mContext;
    private ApplicationComponent mComponent;

    //酒店实例
    public Hotel mHotel;

    //角色
    public int mRole;

    //用户id 老师或者学生
    public String uid;

    //当前的实例
    public Training mTraining;

    public BroadCastManager broadCastManager;

    public static ApplicationComponent getComponent() {
        return ((MainApplication) mContext.getApplicationContext()).mComponent;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        mHotel = new Hotel();
        mHotel.setId(1234556);

        mTraining = new Training();
        mTraining.setId(1);

        mComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiServiceModule(new ApiServiceModule()).build();

        //初始化CrashReport系统
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(mContext);

        broadCastManager = new BroadCastManager(mComponent.apiService(),this);
    }




}
