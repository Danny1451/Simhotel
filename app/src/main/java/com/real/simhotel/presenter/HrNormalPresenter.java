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
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liudan on 2017/2/10.
 */
public class HrNormalPresenter extends BasePresenter {

    ISHrListView mView;

    private List<DynamicListModel> mViewData;


    Subscription mApplicantListSubs;

    public HrNormalPresenter(ISHrListView view){
        mView = view;
    }

    @Override
    public void requestData(Object... o) {
        //请求日常的员工

        mViewData = new ArrayList<>();

        mView.showLoading();

        mApplicantListSubs = apiService.getEmployedList(application.group.getId())
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
                        mView.refreshView();
                        mView.showError("加载失败" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(List<Applicant> applicantList) {

                        //渲染界面

                        mView.disMissLoading();
                        mView.refreshView();


                        //已经招聘人员表
                        mViewData = DynamicListModelFactory.parseFromEmployed(applicantList);


                        mView.renderApplicantsList(mViewData);
                    }
                });
    }

    public void fireApplicant(int pos){

        Applicant applicant = (Applicant)mViewData.get(pos).ext;



        mView.getDetailFragment().showLoading();

        apiService.deleteEmploy(application.group.getId(),applicant.getEmployId())
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

                        mView.showToast("解雇失败");
                        mView.getDetailFragment().refreshView();

                    }

                    @Override
                    public void onNext(String s) {

                        mView.disMissLoading();
                        mView.getDetailFragment().refreshView();
                        //删除成功
                        applicant.hasFired = true;
                        //刷新
                        mView.renderApplicantsList(mViewData);
                    }
                });



    }
}
