package com.real.simhotel.presenter;

import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;
import com.real.simhotel.rx.DefaultSubscriber;
import com.real.simhotel.view.activity.teacher.TeacherMainActivity;
import com.real.simhotel.view.fragment.GroupListFragment;
import com.real.simhotel.view.fragment.HotelListFragment;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liudan on 2016/12/12.
 */
public class TeacherMainPresenter extends BasePresenter {

    private TeacherMainActivity mView;
    private HotelListFragment mHotelView;
    private GroupListFragment mGroupView;

    private Subscription subscription;

    public TeacherMainPresenter(TeacherMainActivity view, HotelListFragment hotelView,GroupListFragment groupView){
        mView = view;
        mHotelView = hotelView;
        mGroupView = groupView;
    }

    public void createHotel(){

    }

    public void createGroup(int trainsId,String groupName, String groupDes){

        apiService.createGroup(trainsId,groupName,groupDes).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .flatMap(new Func1<Response<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(Response<String> stringResponse) {
                        return RetrofitUtils.flatResponse(stringResponse);
                    }
                }).subscribe(new GroupCreateSubscriber());
    }

    public class GroupCreateSubscriber extends DefaultSubscriber<String>{

        @Override
        public void onError(Throwable e) {
            super.onError(e);
        }

        @Override
        public void onNext(String s) {
            super.onNext(s);
        }
    }
}
