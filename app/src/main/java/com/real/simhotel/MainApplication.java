package com.real.simhotel;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.real.simhotel.config.Constants;
import com.real.simhotel.events.StatusManager;
import com.real.simhotel.internal.dl.components.ApplicationComponent;
import com.real.simhotel.internal.dl.components.DaggerApplicationComponent;
import com.real.simhotel.internal.dl.modules.ApiServiceModule;
import com.real.simhotel.internal.dl.modules.ApplicationModule;
import com.real.simhotel.model.GroupDetailVo;
import com.real.simhotel.model.Hotel;
import com.real.simhotel.model.Training;
import com.real.simhotel.utils.CrashHandler;
import com.real.simhotel.utils.PreferenceUtils;
import com.real.simhotel.utils.log.KLog;

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
    public Training training;

    //当前的小组
    public GroupDetailVo group;

    public StatusManager traingingStatusManager;



    public static ApplicationComponent getComponent() {
        return ((MainApplication) mContext.getApplicationContext()).mComponent;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        mHotel = new Hotel();
        mHotel.setId(1234556);

        training = new Training();
        training.setId(1);


        String baseUrl = PreferenceUtils.getIpAdress(mContext);

        if (TextUtils.isEmpty(baseUrl))
            baseUrl = Constants.API_BASE_URL;

        KLog.d("base url =" + baseUrl);

        mComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiServiceModule(new ApiServiceModule(baseUrl)).build();

        //初始化CrashReport系统
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(mContext);

        traingingStatusManager = new StatusManager(mComponent.apiService(),this);


    }




}
