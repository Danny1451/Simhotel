package com.real.simhotel.events;

import com.real.simhotel.MainApplication;
import com.real.simhotel.data.ApiService;
import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;
import com.real.simhotel.utils.log.KLog;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liudan on 2017/1/17.
 * 接收酒店状态改变的广播
 */
public class TrainingStatusManager {

    ApiService mApiService;
    MainApplication mApplication;

    Subscription mRepeat;
    Subscription mRequest;

    Boolean isRuning = false;
    /**
     * 轮询间隔
     */
    public static final int TIME_INTERVAL = 30;

    public interface TraingStatusChangeListener{
        void OnChangedSuccess();
        void OnChangedFailed(String erro);
    }

    public TrainingStatusManager(ApiService apiService , MainApplication application){

        mApiService = apiService;
        mApplication = application;

    }


    /**
     * 开始轮询
     */
    public void startScheduling(){

        //如果已经开始的话 就不在重新启动
        if (mRepeat == null) {
            isRuning = true;
            //轮询
            mRepeat = Observable.interval(0, TIME_INTERVAL, TimeUnit.SECONDS)
                    .observeOn(Schedulers.io())//都在子线程中
                    .subscribeOn(Schedulers.io())
                    .subscribe(aLong -> {

                        //判断是否暂停
                        if (isRuning) {
                            //请求

                            requestStatus();
                        }



                    });
        }else {

            isRuning = true;
        }
    }


    private void requestStatus(){
        mRequest = mApiService
                .getTrainingStatus(mApplication.training.getId())
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<Response<StatusEvent>, Observable<StatusEvent>>() {
                    @Override
                    public Observable<StatusEvent> call(Response<StatusEvent> statusEventResponse) {
                        return RetrofitUtils.flatResponse(statusEventResponse);
                    }
                })
                .subscribe(new Subscriber<StatusEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        //获取失败
                        KLog.e(e.toString());
                    }

                    @Override
                    public void onNext(StatusEvent event) {

                        //直接发送事件
                        EventBus.getDefault().post(event);
                    }
                });
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


    public void manualUpdate(){
        requestStatus();
    }


    /**
     * 改变实例的状态
     * @param trainingId
     * @param traininStatus
     * @param listener
     */
    public void changeTrainingStatus(int trainingId, int traininStatus, TraingStatusChangeListener listener){

        Subscription request = mApiService.updateTrainingStatus(trainingId,traininStatus)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(Response<String> stringResponse) {
                        return RetrofitUtils.flatResponse(stringResponse);
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.OnChangedFailed(e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        //直接发送事件
                        StatusEvent event = new StatusEvent();
                        event.setStatusDes("");
                        event.setTrainingStatus(traininStatus);
                        EventBus.getDefault().post(event);

                        //改变成功之后 手动触发 发送一条事件
                        listener.OnChangedSuccess();
                    }
                });
    }


}
