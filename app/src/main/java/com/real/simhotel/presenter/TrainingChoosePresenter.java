package com.real.simhotel.presenter;

import android.util.Log;

import com.real.simhotel.config.Constants;
import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;
import com.real.simhotel.model.Training;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.rx.DefaultSubscriber;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.adapter.DynamicListModelFactory;
import com.real.simhotel.view.iview.ITrainingView;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liudan on 2017/1/5.
 */
public class TrainingChoosePresenter extends BasePresenter {

    ITrainingView mView;

    Subscription mTrainingList;


    //展示model
    List<DynamicListModel> viewModelList;

    //实际数据的model
    List<Training> dataModelList;

    public TrainingChoosePresenter(ITrainingView view){
        super();
        mView = view;
    }

    /**
     * 请求实例列表
     * @param userType
     */
    public void requestTrainingList(int userType){

        if (userType == Constants.USER_TYPE_TEACHER){



            mTrainingList = apiService.getTrainingListForTeacher(application.uid)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Func1<Response<List<Training>>, Observable<List<Training>>>() {
                        @Override
                        public Observable<List<Training>> call(Response<List<Training>> listResponse) {
                            return RetrofitUtils.flatResponse(listResponse);
                        }
                    }).subscribe(new TeacherTrainingListSubscriber());


        }else {

            mTrainingList = apiService.getTrainingListForStudent(application.uid)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Func1<Response<List<Training>>, Observable<List<Training>>>() {
                        @Override
                        public Observable<List<Training>> call(Response<List<Training>> listResponse) {
                            return RetrofitUtils.flatResponse(listResponse);
                        }
                    }).subscribe(new TeacherTrainingListSubscriber());

        }

    }

    public void createTraining(String name,String time, String hireTime,String equipTime){

        KLog.d(" 创建Training " + name + time + hireTime + equipTime);

        mView.refreshView();
    }


    public class TeacherTrainingListSubscriber extends DefaultSubscriber<List<Training>>{
        @Override
        public void onNext(List<Training> trainings) {
            super.onNext(trainings);

            if (trainings == null || trainings.size() == 0){

                mView.showEmptyView("获取数据异常");

            }else {

                Log.d("AAAAA","++++++++++++++++");
                mView.refreshView();


                dataModelList = trainings;

                //数据转换
                viewModelList = DynamicListModelFactory.parseFromTraining(trainings);

                //渲染界面
                mView.renderTrainingsList(viewModelList);

                //默认选中第一行
                mView.selectTrainingRow(0);
            }


        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            KLog.e(e.toString());
        }
    }
}
