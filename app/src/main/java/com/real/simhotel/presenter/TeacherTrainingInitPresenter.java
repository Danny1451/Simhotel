package com.real.simhotel.presenter;

import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;
import com.real.simhotel.model.Hotel;
import com.real.simhotel.model.HotelTemplate;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.rx.DefaultSubscriber;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.adapter.DynamicListModelFactory;
import com.real.simhotel.view.base.BaseFragment;
import com.real.simhotel.view.iview.ITrainingInitView;
import com.real.simhotel.view.iview.ITrainingView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
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
        subscription =apiService.getHotelTemplateList(1).
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

        mDataList.add(template);

        DynamicListModel model = new DynamicListModel(DynamicListModel.TYPE_TITLE_INFO);
        model.title = template.getLocationName();
        model.info = template.getUpdateTime();
        model.ext = template;
        mViewModelList.add(model);

        mView.renderTemlplateList(mViewModelList);

    }

    /**
     * 初始化酒店 开始酒店
     */
    public void initHotel(){

        //初始化成功
        mView.initSuccess();
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

