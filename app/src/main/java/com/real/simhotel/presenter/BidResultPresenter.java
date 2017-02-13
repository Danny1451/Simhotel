package com.real.simhotel.presenter;

import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;
import com.real.simhotel.model.Applicant;
import com.real.simhotel.model.Quote;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.adapter.DynamicListModelFactory;
import com.real.simhotel.view.fragment.student.BidResultFragment;
import com.real.simhotel.view.iview.ISHrBidResultView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liudan on 2016/12/28.
 */
public class BidResultPresenter extends BasePresenter {


    ISHrBidResultView mView;

    Subscription mApplicantListSubs;
    private List<DynamicListModel> mViewData;
    List<Applicant> mData;

    public BidResultPresenter(BidResultFragment view){

        this.mView = view;

    }

    @Override
    public void requestData(Object... o) {

        mData = new ArrayList<>();

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
                        mView.showError("加载失败");
                    }

                    @Override
                    public void onNext(List<Applicant> applicants) {


                        //渲染界面

                        mView.disMissLoading();
                        mData = applicants;

                        mViewData = DynamicListModelFactory.parseFromApplicants(mData);


                        mView.renderApplicantsList(mViewData);


                    }
                });




        //默认选中1
//        onClickApplicantsRow(0,model1);

    }

//    //更新右侧的list
//    public void onClickApplicantsRow(int pos , DynamicListModel model){
//
////        mView.getDetailFragment().updateWithModel(model);
//
//        //获取结果
//
//        int quoteprice = ((Applicant)model.ext).quotePrice;
//
//
//        List<DynamicListModel> res = new ArrayList<>();
//
//        res.add(DynamicListModelFactory.modelForApplicantsBidResult(1,"万达喜来登酒店",quoteprice + ""));
//        res.add(DynamicListModelFactory.modelForApplicantsBidResult(2,"希尔顿酒店",quoteprice - 100 + ""));
//        res.add(DynamicListModelFactory.modelForApplicantsBidResult(3,"如家酒店",quoteprice - 200 + ""));
//        res.add(DynamicListModelFactory.modelForApplicantsBidResult(4,"怡莱酒店",quoteprice - 300 + ""));
//        res.add(DynamicListModelFactory.modelForApplicantsBidResult(5,"汉庭酒店",quoteprice - 400 + ""));
//        res.add(DynamicListModelFactory.modelForApplicantsBidResult(6,"好的酒店",quoteprice - 500 + ""));
//        res.add(DynamicListModelFactory.modelForApplicantsBidResult(7,"苏果酒店",quoteprice - 600 + ""));
//        res.add(DynamicListModelFactory.modelForApplicantsBidResult(8,"大润发酒店",quoteprice - 700 + ""));
//
////        mView.loadBidResultList(pos,res);
//
//    }


    public void requestDetailList(Applicant applicant){

        mView.showLoading();

        apiService.getEmployQuotes(applicant.getEmployId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<List<Quote>>, Observable<List<Quote>>>() {
                    @Override
                    public Observable<List<Quote>> call(Response<List<Quote>> listResponse) {
                        return RetrofitUtils.flatResponse(listResponse);
                    }
                })
                .subscribe(new Subscriber<List<Quote>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showToast("刷新失败");
                        mView.disMissLoading();
                    }

                    @Override
                    public void onNext(List<Quote> quotes) {

                        mView.disMissLoading();
                        applicant.quotes = quotes;
                        //转为viewmodel 然后渲染
                        mView.renderQuotesList(DynamicListModelFactory.parseFromQuotes(applicant.quotes));
                    }
                });

    }
}
