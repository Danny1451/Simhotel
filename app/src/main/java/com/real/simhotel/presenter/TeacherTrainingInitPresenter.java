package com.real.simhotel.presenter;

import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;
import com.real.simhotel.events.EventCode;
import com.real.simhotel.events.StatusManager;
import com.real.simhotel.model.HotelTemplate;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.rx.DefaultSubscriber;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.adapter.DynamicListModelFactory;
import com.real.simhotel.view.iview.ITrainingInitView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liudan on 2017/1/10.
 */
public class TeacherTrainingInitPresenter extends BasePresenter {

    ITrainingInitView mView;

    private Subscription subscription;

    private List<HotelTemplate> mDataList;
    private List<DynamicListModel> mViewModelList;

    public TeacherTrainingInitPresenter(ITrainingInitView view){
        mView = view;

    }

    @Override
    public void destroy() {
        super.destroy();
        if (subscription!=null)
            subscription.unsubscribe();
    }

    @Override
    public void requestData(Object... o) {
        super.requestData(o);


        //请求参数
        subscription =apiService.getHotelTemplateList(application.training.getId()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                flatMap(new Func1<Response<List<HotelTemplate>>, Observable<List<HotelTemplate>>>() {
                    @Override
                    public Observable<List<HotelTemplate>> call(Response<List<HotelTemplate>> listResponse) {
                        return RetrofitUtils.flatResponse(listResponse);
                    }
                }).subscribe(new HotelTempalteListSubscriber());


    }


    /**
     * 删除指定模板
     * @param pos
     */
    public void removeTemplate(int pos){

        //todo 未添加服务端逻辑

        mView.showToast("暂时未添加逻辑");

        //删除模板

        if (pos > mViewModelList.size() || pos < 0)
            return;

        mViewModelList.remove(pos);
        mDataList.remove(pos);

        mView.renderTemlplateList(mViewModelList);
    }

    /**
     * 增加模板
     * @param template
     */
    public void createHotelTemplate(HotelTemplate template){

//        mDataList.add(template);
//
//        DynamicListModel model = new DynamicListModel(DynamicListModel.TYPE_TITLE_INFO);
//        model.title = template.getLocationName();
//        model.info = template.getUpdateTime();
//        model.ext = template;
//        mViewModelList.add(model);
//
//        mView.renderTemlplateList(mViewModelList);

        mView.showLoading();

        apiService.createHotelTemplate(application.training.getId(),
                template.getLocation(),
                template.getRoomLeastNum(),
                template.getRoomCost(),
                template.getRoomIncome(),
                template.getCleanNum(),
                template.getEquipDeprePer(),12)
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

                        mView.disMissLoading();


                        mView.showToast("创建失败");
                    }

                    @Override
                    public void onNext(String s) {

                        mView.disMissLoading();

                        mView.dismissDialog();
                        //重新请求列表
                        requestData();
                    }
                });
    }

    /**
     * 初始化酒店 开始酒店
     */
    public void initHotel(){

        //修改状态

        mView.showLoading();
        application.traingingStatusManager.changeTrainingStatus(
                EventCode.TraingingCode.TRAINING_BUILDED,
                new StatusManager.StatusChangeListener() {
                    @Override
                    public void OnChangedSuccess() {

                        application.training.setTrainingStatus(EventCode.TraingingCode.TRAINING_BUILDED);
                        mView.disMissLoading();
                        //初始化成功
                        mView.initSuccess();
                    }

                    @Override
                    public void OnChangedFailed(String erro) {

                        mView.disMissLoading();

                        mView.showToast("初始化失败,稍后再试");
                    }
                });


    }


    public class HotelTempalteListSubscriber extends DefaultSubscriber<List<HotelTemplate>> {
        @Override
        public void onNext(List<HotelTemplate> hotelTemplates) {
            super.onNext(hotelTemplates);

            if (hotelTemplates == null || hotelTemplates.size() == 0){

                mView.showEmptyView("获取数据异常");

            }else {

                mView.refreshView();

                //真实的数据
                mDataList = hotelTemplates;

                //转换到展示的数据
                mViewModelList = DynamicListModelFactory.parseFromHotelTemplateForTeacher(hotelTemplates);

                mView.renderTemlplateList(mViewModelList);
            }

        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
        }
    }
}

