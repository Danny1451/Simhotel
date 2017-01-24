package com.real.simhotel.presenter;

import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;
import com.real.simhotel.model.Applicant;
import com.real.simhotel.model.Quote;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.rx.DefaultSubscriber;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.adapter.DynamicListModelFactory;
import com.real.simhotel.view.iview.ITHRManagerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liudan on 2017/1/11.
 */
public class TeacherHRManagerPresenter extends BasePresenter {

    ITHRManagerView mView;

    int finishNum = 0 ;
    private Boolean hasFinishQute = false;

    private List<Applicant> mDataList;
    private List<DynamicListModel> mViewModelList;

    Subscription mApplicantListSub;
    Subscription mApplicantCreate;

    public TeacherHRManagerPresenter(ITHRManagerView view){

        mView = view;

    }


    //将报价 展示到界面中
    public List<DynamicListModel> parseQuteToViewModel(List<Quote> quotes){
        List<DynamicListModel> dynamicListModels = new ArrayList<>();

        for (int i = 0 ; i < quotes.size() ; i++ ){

            Quote quote = quotes.get(i);

            //转换到显示model
            dynamicListModels.add(DynamicListModelFactory.modelForApplicantsBidResult(i+1,quote.hotelName,"报价:" + quote.prcie + ""));


        }

        return dynamicListModels;
    }

    // 清空报价
    private void cleanQutes(){
        for (int i = 0 ; i < mDataList.size() ; i++ ){

            Applicant applicant = mDataList.get(i);
            DynamicListModel viewModel = mViewModelList.get(i);

            applicant.quotes = new ArrayList<>();
            //转换到显示模型
            viewModel.info = "报价:" + applicant.quotePrice;
            viewModel.ext =  new ArrayList<>();

        }
    }




    /**
     * 获得报价
     */
    public void testAddQutes(){


        if (finishNum >= mDataList.size()) {

            //报价增加完
            hasFinishQute = true;
            return;
        }


        //遍历增加报价
        for (int i = 0 ; i < mDataList.size() ; i++ ){

            Applicant applicant = mDataList.get(i);
            DynamicListModel viewModel = mViewModelList.get(i);

            //增加报价
            Quote quote = Quote.testQuote("酒店" + finishNum ,applicant.getExpectMonthIncome() + finishNum * 10);
            applicant.quotes.add(quote);

            applicant.quotePrice = applicant.getExpectMonthIncome();

            //转换到显示模型
            viewModel.info = "报价:" + applicant.quotePrice;
            viewModel.ext = parseQuteToViewModel(applicant.quotes);



        }

        finishNum ++ ;

    }

    @Override
    public void requestData(Object... o) {
        super.requestData(o);

        mDataList = new ArrayList<>();
        mViewModelList = new ArrayList<>();

        //切换至详情状态
        mView.transToDetailFragment();



        //请求列表
        requestApplicantList();

    }

    public void requestApplicantList(){
        //请求列表
        mApplicantListSub = apiService
                .getEmployTemplate(application.mTraining.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<List<Applicant>>, Observable<List<Applicant>>>(){
                    @Override
                    public Observable<List<Applicant>> call(Response<List<Applicant>> listResponse) {
                        return RetrofitUtils.flatResponse(listResponse);
                    }
                }).subscribe(new ApplicantListsSubscriber());
    }

    public void removeApplicant(int pos){


        if (pos > mViewModelList.size() || pos < 0)
            return;


        mViewModelList.remove(pos);
        mDataList.remove(pos);

        mView.renderApplicantsList(mViewModelList);

    }

    public void createApplicant(Applicant model){

        model.setTrainingId(application.mTraining.getId());
        mDataList.add(model);


        //请求新建候选人
        mApplicantCreate = apiService.createEmploy(model.getTrainingId(),model.getLevel(),model.getExpectMonthIncome(),model.getExpectWorkPlace())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<Integer>, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Response<Integer> integerResponse) {
                        return RetrofitUtils.flatResponse(integerResponse);
                    }
                }).subscribe(new ApplicantCreateSubscriber(model));



    }

    /**
     * 推送候选人
     */
    public void pushApplicants(){

        //转换至等待结果
        mView.transToInitListFragment();
        //数据viewModel 的 ext的模型切换 为 viewModel 的 list

        //清空报价
        cleanQutes();

        //跳转到客源推送界面
        mView.showLoading();

        //开始获取客源
        Observable.timer(application.mTraining.getId(),TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {


                    mView.disMissLoading();

                    //更新列表
                    mView.renderApplicantsList(mViewModelList);

                    mView.updateConfirmStatus("发送结果");

                    mView.updateGroupStatus(finishNum + "/" + mDataList.size());
                });


    }

    /**
     * 推送结果
     */
    public void pushResult(){


        //清空报价
        cleanQutes();

        //清空报价状态
        finishNum = 0;
        hasFinishQute = false;


        //跳转到客源推送界面
        mView.showLoading();

        Observable.timer(2,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {


                    mView.disMissLoading();

                    //更新显示
                    mView.updateGroupStatus(finishNum + "/" + mDataList.size());

                    //更新列表
                    mView.renderApplicantsList(mViewModelList);

                    mView.updateGroupStatus(finishNum + "/" + mDataList.size());
                    mView.updateConfirmStatus("发送二次报价结果");
                });

    }


    /*
    * 刷新界面
    */
    public void updateResult(){

        if (hasFinishQute){
            mView.showToast("已经全部更新完成");
            return;
        }


        //更新
        mView.updateGroupStatus("更新中");

        mView.showLoading();
        //增加报价
        testAddQutes();

        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {

                    mView.disMissLoading();
                    //刷新界面
                    mView.updateGroupStatus(finishNum + "/" + mDataList.size());
                    mView.renderApplicantsList(mViewModelList);

                });

    }

    public class ApplicantListsSubscriber extends DefaultSubscriber<List<Applicant>>{
        @Override
        public void onNext(List<Applicant> applicants) {
            super.onNext(applicants);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
        }
    }

    public class ApplicantCreateSubscriber extends DefaultSubscriber<Integer>{

        Applicant mModel;
        public ApplicantCreateSubscriber(Applicant model){
            super();
            mModel= model;
        }

        @Override
        public void onNext(Integer integer) {
            super.onNext(integer);

            DynamicListModel viewModel = new DynamicListModel(DynamicListModel.TYPE_TITLE_INFO);

            viewModel.title = mModel.getLevelStr();
            viewModel.info = "期望:" +mModel.getExpectMonthIncome() ;
            viewModel.ext = mModel;

            mViewModelList.add(viewModel);

            //移除弹框
            mView.removeAddApplicantDialog();

            mView.renderApplicantsList(mViewModelList);

        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
        }
    }
}
