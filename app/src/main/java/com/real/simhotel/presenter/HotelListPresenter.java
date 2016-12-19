package com.real.simhotel.presenter;

import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;
import com.real.simhotel.model.Hotel;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.rx.DefaultSubscriber;
import com.real.simhotel.view.IHotelListView;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liudan on 2016/12/12.
 */
public class HotelListPresenter extends BasePresenter {

    private IHotelListView mView;

    private Subscription subscription;

    public HotelListPresenter(IHotelListView view) {

        mView = view;

    }

    public void onUserClicked(Hotel hotelModel) {
        mView.viewHotel(hotelModel);
    }



    @Override
    public void requestData(Object... o) {
        super.requestData(o);


        mView.showLoading();

        subscription = apiService.getHotelList(1).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .flatMap(new Func1<Response<List<Hotel>>,Observable<List<Hotel>>>() {
                    @Override
                    public Observable<List<Hotel>> call(Response<List<Hotel>> listResponse) {
                        return RetrofitUtils.flatResponse(listResponse);
                    }
                }).subscribe(new HotelListSubscriber());


    }


    public class HotelListSubscriber extends DefaultSubscriber<List<Hotel>> {
        @Override
        public void onNext(List<Hotel> hotels) {
            super.onNext(hotels);

            if (hotels == null || hotels.size() == 0){

                mView.showEmptyView("获取数据异常");

            }else {
                //刷新界面
                mView.refreshView();
                mView.renderHotelList(hotels);

            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            mView.showError("网络异常");
        }
    }
}
