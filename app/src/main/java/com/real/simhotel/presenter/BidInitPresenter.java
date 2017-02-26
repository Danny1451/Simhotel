package com.real.simhotel.presenter;

import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;
import com.real.simhotel.model.Applicant;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.adapter.DynamicListModelFactory;
import com.real.simhotel.view.iview.ISHrListView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liudan on 2016/12/23.
 */
public class BidInitPresenter extends BasePresenter {

    private ISHrListView mView;

    private List<DynamicListModel> mViewData;

    private List<Applicant> mData;

    Subscription mApplicantListSubs;

    public BidInitPresenter(ISHrListView view){

        mView = view;
    }

    @Override
    public void requestData(Object... o) {

        mViewData = new ArrayList<>();

        mView.showLoading();

        mApplicantListSubs = apiService.getEmployTemplate(application.training.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<List<Applicant>>, Observable<List<Applicant>>>() {
                    @Override
                    public Observable<List<Applicant>> call(Response<List<Applicant>> listResponse) {
                        return RetrofitUtils.flatResponse(listResponse);
                    }
                })
                .subscribe(new Subscriber<List<Applicant>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {


                        mView.disMissLoading();
                        e.printStackTrace();
                        mView.showError("加载失败" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(List<Applicant> applicants) {


                        //渲染界面

                        mView.disMissLoading();
                        mView.refreshView();
                        mData = applicants;

                        mViewData = DynamicListModelFactory.parseFromApplicants(mData);


                        mView.renderApplicantsList(mViewData);


                    }
                });


    }



    //更新左侧的列表的报价
    public void updateApplicantsRow(int pos,int seekValue){

        //得到 model 和 选择的value
        DynamicListModel model = mViewData.get(pos);
        Applicant applicant = (Applicant) model.ext;


        mView.showLoading();

        applicant.quotePrice = seekValue * 1000;
        //报价
        apiService.bidEmploy(application.group.getId(),applicant.getEmployId(),applicant.quotePrice)
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

                        mView.showToast("报价失败");
                    }

                    @Override
                    public void onNext(String s) {

                        mView.disMissLoading();

                        mView.refreshView();
                        //更新值
                        applicant.quotePrice = seekValue;

                        //更新界面
                        model.info = applicant.quotePrice * 1000 + "元";

                        //刷新界面
                        mView.renderApplicantsList(mViewData);

                    }
                });



    }


}
