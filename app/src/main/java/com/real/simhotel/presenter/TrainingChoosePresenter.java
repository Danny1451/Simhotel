package com.real.simhotel.presenter;

import com.real.simhotel.R;
import com.real.simhotel.config.Constants;
import com.real.simhotel.config.Role;
import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;
import com.real.simhotel.model.GroupDetailVo;
import com.real.simhotel.model.Training;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.rx.DefaultSubscriber;
import com.real.simhotel.utils.PreferenceUtils;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.adapter.DynamicListModelFactory;
import com.real.simhotel.view.iview.ITrainingView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
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


    public TrainingChoosePresenter(ITrainingView view){
        super();
        mView = view;
    }

    //点击了确认进入实例
    public void confirmEnterTraining(Training training){

        //存到Application中
        application.training = training;


        if (application.mRole == Role.ROLE_TEACHER) {

            //老师的话
            mView.enterTrainingForTeacher(training);

        }else {

            //如果是学生的话

            if (training.getGroupDetailVos() != null && training.getGroupDetailVos().size() != 0){



                if(training.getGroupDetailVos().get(0).checkHasMyPos(Integer.parseInt(PreferenceUtils.getCharacter(application)))){

                    //直接进入实例
                    mView.enterTrainingForStudent(training,application.mRole);

                    //更新group
                    application.group = application.training.getGroupDetailVos().get(0);

                }else {

                    //先绑定
                    mView.showLoading();
                    String deviceNumber = PreferenceUtils.getTeamNum(application) + PreferenceUtils.getCharacter(application);
                    apiService.chooseGroupRole(application.training.getId().toString(),deviceNumber,application.uid)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .flatMap(new Func1<Response<GroupDetailVo>, Observable<GroupDetailVo>>() {
                                @Override
                                public Observable<GroupDetailVo> call(Response<GroupDetailVo> stringResponse) {
                                    return RetrofitUtils.flatResponse(stringResponse);
                                }
                            })
                            .subscribe(new Subscriber<GroupDetailVo>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                    mView.disMissLoading();
                                    mView.showToast(application.getString(R.string.request_failed));
                                }

                                @Override
                                public void onNext(GroupDetailVo s) {

                                    mView.disMissLoading();

                                    mView.showToast(application.getString(R.string.training_join_success));

                                    application.group = s;

                                    mView.enterTrainingForStudent(training,application.mRole);
                                }
                            });
                }




            }else {

                mView.showToast(application.getString(R.string.training_join_permission));

            }

        }


    }

    @Override
    public void destroy() {
        super.destroy();

        if (mTrainingList != null)
            mTrainingList.unsubscribe();


    }

    /**
     * 请求实例列表
     * @param userType
     */
    public void requestTrainingList(int userType){

        if (userType == Constants.USER_TYPE_TEACHER){

            mTrainingList = apiService.getTrainingListForTeacher(application.uid)
                    .observeOn(AndroidSchedulers.mainThread())//事件的消费线程 回调的线程
                    .subscribeOn(Schedulers.io())//事件的产生线程
                    .flatMap(new Func1<Response<List<Training>>, Observable<List<Training>>>() {
                        @Override
                        public Observable<List<Training>> call(Response<List<Training>> listResponse) {
                            //获取 data 数据
                            return RetrofitUtils.flatResponse(listResponse);
                        }
                    })
                    .map(new Func1<List<Training>, List<DynamicListModel>>() {

                        @Override
                        public List<DynamicListModel> call(List<Training> trainings) {
                            //数据转换到 viewmodel
                            return DynamicListModelFactory.parseFromTraining(trainings);
                        }
                    })
                    .subscribe(new TrainingListSubscriber());








        }else {

            //获取位置坐标
            String pos = PreferenceUtils.getTeamNum(application) + PreferenceUtils.getCharacter(application);


            mTrainingList = apiService.getTrainingListForStudent(application.uid , pos)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Func1<Response<List<Training>>, Observable<List<Training>>>() {
                        @Override
                        public Observable<List<Training>> call(Response<List<Training>> listResponse) {
                            return RetrofitUtils.flatResponse(listResponse);
                        }
                    })
                    .map(new Func1<List<Training>, List<DynamicListModel>>() {
                        @Override
                        public List<DynamicListModel> call(List<Training> trainings) {

                            return DynamicListModelFactory.parseFromTraining(trainings);
                        }
                    })
                    .subscribe(new TrainingListSubscriber());

        }

    }

    public void createTraining(String name,String time, String hireTime,String equipTime){

        KLog.d(" 创建Training " + name + time + hireTime + equipTime);


        //默认三个小组
        mTrainingList = apiService.createTraining(
                application.uid,
                name,
                Integer.parseInt(time),
                Integer.parseInt(hireTime),
                Integer.parseInt(hireTime),
                Integer.parseInt(equipTime),
                0.12,
                Integer.parseInt(equipTime),
                3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<Response<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(Response<String> stringResponse) {
                        return RetrofitUtils.flatResponse(stringResponse);
                    }
                }).subscribe(new TrainingCreateSubscriber());

    }


    /**
     * 老师创建实例
     */
    public class TrainingCreateSubscriber extends DefaultSubscriber<String>{
        @Override
        public void onNext(String s) {
            super.onNext(s);

            mView.closeDialog();
            mView.reloadTrainingList();

        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            mView.showToast(application.getString(R.string.training_create_failed));
            KLog.e(e.toString());
        }
    }

    /**
     * 获取实例列表
     */
    public class TrainingListSubscriber extends DefaultSubscriber<List<DynamicListModel>>{
        @Override
        public void onNext(List<DynamicListModel> trainings) {
            super.onNext(trainings);

            if (trainings == null || trainings.size() == 0){

                mView.showEmptyView(application.getString(R.string.request_failed));

            }else {

                mView.refreshView();


                //渲染界面
                mView.renderTrainingsList(trainings);


            }


        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            KLog.e(e.toString());
        }
    }
}
