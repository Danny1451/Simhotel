package com.real.simhotel.events;

import com.real.simhotel.MainApplication;
import com.real.simhotel.data.ApiService;
import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;
import com.real.simhotel.rx.DefaultSubscriber;
import com.real.simhotel.utils.log.KLog;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liudan on 2017/1/17.
 */
public class BroadCastManager {

    ApiService mApiService;
    MainApplication mApplication;

    Subscription mRepeat;
    Subscription mRequest;

    Boolean isRuning = false;
    /**
     * 轮询间隔
     */
    public static final int TIME_INTERVAL = 30;

    public BroadCastManager(ApiService apiService , MainApplication application){

        mApiService = apiService;
        mApplication = application;

    }


    /**
     * 开始轮询
     */
    public void startScheduling(){

        if (mRepeat == null) {
            isRuning = true;
            //轮询
            mRepeat = Observable.interval(0, TIME_INTERVAL, TimeUnit.SECONDS)
                    .observeOn(Schedulers.io())//都在子线程中
                    .subscribeOn(Schedulers.io())
                    .subscribe(aLong -> {

                        if (isRuning) {
                            //请求
                            mRequest = mApiService
                                    .getTrainingStatus(mApplication.mTraining.getId())
                                    .observeOn(Schedulers.io())
                                    .subscribeOn(Schedulers.io())
                                    .flatMap(new Func1<Response<StatusEvent>, Observable<StatusEvent>>() {
                                        @Override
                                        public Observable<StatusEvent> call(Response<StatusEvent> statusEventResponse) {
                                            return RetrofitUtils.flatResponse(statusEventResponse);
                                        }
                                    })
                                    .subscribe(new TrainingStatusSubscriber());
                        }



                    });
        }else {

            isRuning = true;
        }
    }


    /**
     * 暂停轮询
     */
    public void pauseScheduling(){

        isRuning = false;

        if (mRequest != null)
            mRequest.unsubscribe();

    }

    /**
     * 停止轮询
     */
    public void stopScheduling(){

        //先暂停
        pauseScheduling();

        //取消循环
        if (mRepeat != null)
            mRepeat.unsubscribe();

        mRequest = null;
        mRepeat = null;
    }


    /**
     * 老师创建实例
     */
    public class TrainingStatusSubscriber extends DefaultSubscriber<StatusEvent> {
        @Override
        public void onNext(StatusEvent event) {
            super.onNext(event);

            EventBus.getDefault().post(event);

        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            //获取失败
            KLog.e(e.toString());
        }
    }


}
