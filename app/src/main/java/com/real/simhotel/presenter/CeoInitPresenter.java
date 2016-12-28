package com.real.simhotel.presenter;

import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;
import com.real.simhotel.model.Hotel;
import com.real.simhotel.model.HotelTemplate;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.rx.DefaultSubscriber;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.adapter.DynamicListModelFactory;
import com.real.simhotel.view.fragment.student.CeoInitFragment;
import com.real.simhotel.view.iview.ICeoInitView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liudan on 2016/12/14.
 */
public class CeoInitPresenter extends BasePresenter {

    private ICeoInitView mView;

    private List<DynamicListModel> viewModellist;

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
//        modelTest3.itemType = DynamicListModel.TYPE_CHOOSE;
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


    public void updateMinusRoomNum(int locationId){

        //将第二栏的 最小值 设置为数据源的 最小值
        viewModellist.get(1).minus = dataModellist.get(locationId).getRoomLeastNum();
        //重置
        viewModellist.get(1).selectedValue = -1;


        //选中了 标签
        viewModellist.get(0).selectedValue = locationId;


        //重新渲染 标签
        mView.renderHotelInitItems(viewModellist);


    }

    //请求参数
    public void requestParams(){

        String res = "res";

        for (int i = 0 ; i < viewModellist.size() ; i++){
            res = res + viewModellist.get(i).title + viewModellist.get(i).selectedValue;
        }
        KLog.d("dad",viewModellist.toString());
        mView.showToast(res);
    }
}
