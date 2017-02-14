package com.real.simhotel.presenter;

import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;
import com.real.simhotel.events.EventCode;
import com.real.simhotel.events.StatusManager;
import com.real.simhotel.model.HotelTemplate;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.rx.DefaultSubscriber;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.adapter.DynamicListModelFactory;
import com.real.simhotel.view.fragment.student.CeoInitFragment;
import com.real.simhotel.view.iview.ICeoInitView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liudan on 2016/12/14.
 */
public class CeoInitPresenter extends BasePresenter {

    private ICeoInitView mView;

    //展示的Recycleview的 Model
    private List<DynamicListModel> viewModellist;

    //获取的酒店列表模板
    private List<HotelTemplate> dataModellist;

    private Subscription subscription;

    public CeoInitPresenter(CeoInitFragment view){
        mView = view;
    }


    @Override
    public void requestData(Object... o) {
        super.requestData(o);

        //等待中
        mView.showLoading();
        //这边请求初始化的 可配置参数

        KLog.d("id = " + application.mHotel.getId());

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

//        subscription =apiService.getHotelTemplateList(1).
//                observeOn(AndroidSchedulers.mainThread()).
//                subscribeOn(Schedulers.io()).
//                flatMap((listResponse)-> {
//                        return RetrofitUtils.flatResponse(listResponse);
//
//                });
//        list = new ArrayList();
//
//        DynamicListModel modelTest = new DynamicListModel();
//        modelTest.itemType = DynamicListModel.TYPE_SEEK;
//        modelTest.title = "酒店数量";
//        modelTest.max = 20;
//        modelTest.unit = "间";
//        modelTest.selectedValue = - 1;
//
//        DynamicListModel modelTest2 = new DynamicListModel();
//        modelTest2.itemType = DynamicListModel.TYPE_SEEK;
//        modelTest2.title = "价格";
//        modelTest2.max = 100;
//        modelTest2.unit = "元";
//        modelTest.selectedValue = - 1;
//
//        DynamicListModel modelTest3 = new DynamicListModel();
//        modelTest3.itemType = DynamicListModel.TYPE_RADIO_BUTTONS;
//        modelTest3.mChooseItems = new ArrayList<>();
//        modelTest3.mChooseItems.add("选项1");
//        modelTest3.mChooseItems.add("选项2");
//        modelTest3.mChooseItems.add("选项3");
//        modelTest3.mChooseItems.add("选项4");
//        modelTest3.title = "酒店位置";
//
//
//
//        list.add(modelTest2);
//        list.add(modelTest);
//        list.add(modelTest3);
//        //加载列表
//        mView.renderHotelInitItems(list);
    }

    public class HotelTempalteListSubscriber extends DefaultSubscriber<List<HotelTemplate>>{
        @Override
        public void onNext(List<HotelTemplate> hotelTemplates) {
            super.onNext(hotelTemplates);

            if (hotelTemplates == null || hotelTemplates.size() == 0){

                mView.showEmptyView("获取数据异常");

            }else {

                mView.refreshView();

                //真实的数据
                dataModellist = hotelTemplates;

                //转换到展示的数据
                viewModellist = DynamicListModelFactory.parseFromHotelTemplate(hotelTemplates);

                mView.renderHotelInitItems(viewModellist);
            }

        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
        }
    }


    //更新最小显示的 房间数
    public void updateMinusRoomNum(int locationId){


        DynamicListModel mLocationViewModel = viewModellist.get(0);
        DynamicListModel mNumsViewModel = viewModellist.get(1);


        //将第二栏的 最小值 设置为数据源的 最小值
        mNumsViewModel.minus = dataModellist.get(locationId).getRoomLeastNum();
        //重置
        mNumsViewModel.selectedValue = -1;


        //选中了 标签
        mLocationViewModel.selectedValue = locationId;


        //重新渲染 标签
        mView.renderHotelInitItems(viewModellist);


    }

    //请求参数
    public void requestParams(){


        //获取选择的 hotel_id 和 hotel num
        DynamicListModel mLocationViewModel = viewModellist.get(0);
        DynamicListModel mNumsViewModel = viewModellist.get(1);


        int hotelId = mLocationViewModel.selectedValue;
        int roomNums = mNumsViewModel.selectedValue;

        KLog.d("dad","hotelID = " + hotelId + " roomNums = " + roomNums);

        String res = "res";

        for (int i = 0 ; i < viewModellist.size() ; i++){
            res = res + viewModellist.get(i).title + viewModellist.get(i).selectedValue;
        }
        KLog.d("dad",viewModellist.toString());
        mView.showToast(res);

        mView.showLoading();
        apiService.createHotel(application.training.getId(),hotelId,roomNums)
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
                    }

                    @Override
                    public void onNext(String s) {

                        application.traingingStatusManager.changeGroupStatus(
                                EventCode.GroupCode.GROUP_CEO_HOTEL_INITTED,
                                new StatusManager.StatusChangeListener() {
                                    @Override
                                    public void OnChangedSuccess() {


                                        mView.showToast("创建成功");
                                    }

                                    @Override
                                    public void OnChangedFailed(String erro) {

                                    }
                                });


                    }
                });

    }


}
