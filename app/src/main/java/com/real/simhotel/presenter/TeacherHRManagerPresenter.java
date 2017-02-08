package com.real.simhotel.presenter;

import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;
import com.real.simhotel.events.EventCode;
import com.real.simhotel.events.TrainingStatusManager;
import com.real.simhotel.model.Applicant;
import com.real.simhotel.model.Quote;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.rx.DefaultSubscriber;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.adapter.DynamicListModelFactory;
import com.real.simhotel.view.iview.ITHRManagerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
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
    Subscription mApplicantCreateSub;
    Subscription mDeleteApplicantSub;

    public TeacherHRManagerPresenter(ITHRManagerView view){

        mView = view;

    }


//    //将报价 展示到界面中
//    public List<DynamicListModel> parseQuteToViewModel(List<Quote> quotes){
//        List<DynamicListModel> dynamicListModels = new ArrayList<>();
//
//        for (int i = 0 ; i < quotes.size() ; i++ ){
//
//            Quote quote = quotes.get(i);
//
//            //转换到显示model
//            dynamicListModels.add(DynamicListModelFactory.modelForApplicantsBidResult(i+1,quote.hotelName,"报价:" + quote.prcie + ""));
//
//
//        }
//
//        return dynamicListModels;
//    }

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




//    /**
//     * 获得报价
//     */
//    public void testAddQutes(){
//
//
//        if (finishNum >= mDataList.size()) {
//
//            //报价增加完
//            hasFinishQute = true;
//            return;
//        }
//
//        //遍历增加报价
//        for (int i = 0 ; i < mDataList.size() ; i++ ){
//
//            Applicant applicant = mDataList.get(i);
//            DynamicListModel viewModel = mViewModelList.get(i);
//
//            //增加报价
//            Quote quote = Quote.testQuote("酒店" + finishNum ,applicant.getExpectMonthIncome() + finishNum * 10);
//            applicant.quotes.add(quote);
//
//            applicant.quotePrice = applicant.getExpectMonthIncome();
//
//            //转换到显示模型
//            viewModel.info = "报价:" + applicant.quotePrice;
//            viewModel.ext = parseQuteToViewModel(applicant.quotes);
//
//
//        }
//
//        finishNum ++ ;
//
//    }

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


    public void requestApplicantListQuotes(int pos){

        //请求候选人的报价
        mView.showLoading();

        Applicant model = mDataList.get(pos);

        apiService.getEmployQuotes(model.getEmployId())
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
                        model.quotes = quotes;
                        mView.renderQuotesList(DynamicListModelFactory.parseFromQuotes(model.quotes));
                    }
                });

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
                }).subscribe(new Subscriber<List<Applicant>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Applicant> applicants) {

                        //
                        mDataList = applicants;

                        mViewModelList = DynamicListModelFactory.parseFromApplicants(mDataList);
                        mView.renderApplicantsList(mViewModelList);
                    }
                });
    }

    public void removeApplicant(int pos){

        mView.showLoading();

        //删除成功
        if (pos > mViewModelList.size() || pos < 0) {
            mView.disMissLoading();
            return;
        }


        //删除候选人
        mDeleteApplicantSub = apiService
                .deleteEmploy(application.mTraining.getId(),mDataList.get(pos).getEmployId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(Response<String> stringResponse) {
                        return RetrofitUtils.flatResponse(stringResponse);
                    }
                }).subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        mView.disMissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {

                        //删除失败
                        mView.disMissLoading();
                        mView.showToast("删除失败");
                    }

                    @Override
                    public void onNext(String s) {

                        //删除成功
                        if (pos > mViewModelList.size() || pos < 0)
                            return;


                        mViewModelList.remove(pos);
                        mDataList.remove(pos);

                        mView.renderApplicantsList(mViewModelList);
                    }
                });

    }

    public void createApplicant(Applicant model){

        model.setTrainingId(application.mTraining.getId());

        mView.showLoading();

        //请求新建候选人
        mApplicantCreateSub = apiService.createEmploy(model.getTrainingId(),model.getLevel(),model.getExpectMonthIncome(),model.getExpectWorkPlace())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<Integer>, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Response<Integer> integerResponse) {
                        return RetrofitUtils.flatResponse(integerResponse);
                    }
                }).subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                        mView.disMissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {

                        mView.disMissLoading();
                        mView.showToast("新建失败");
                    }

                    @Override
                    public void onNext(Integer integer) {


                        mDataList.add(model);

                        DynamicListModel viewModel = DynamicListModelFactory.modelForApplicant(model);

                        mViewModelList.add(viewModel);

                        //移除弹框
                        mView.removeAddApplicantDialog();

                        //刷新列表
                        mView.renderApplicantsList(mViewModelList);


                    }
                });


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


        //推送结果
        application.broadCastManager.changeTrainingStatus(application.mTraining.getId(),
                EventCode.TEACHER_START_HIRE,
                new TrainingStatusManager.TraingStatusChangeListener() {
                    @Override
                    public void OnChangedSuccess() {

                        mView.disMissLoading();

                        //更新列表
                        mView.renderApplicantsList(mViewModelList);

                        mView.updateConfirmStatus("发送结果");

                        mView.updateGroupStatus(finishNum + "/" + mDataList.size());
                    }

                    @Override
                    public void OnChangedFailed(String erro) {

                        mView.disMissLoading();

                        mView.showToast("推送结果失败,请稍后再试");

                        KLog.d(erro);
                    }
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


//    /*
//    * 刷新界面
//    */
//    public void updateResult(){
//
//        if (hasFinishQute){
//            mView.showToast("已经全部更新完成");
//            return;
//        }
//
//
//        //更新
//        mView.updateGroupStatus("更新中");
//
//        mView.showLoading();
//
//        //增加报价
//        testAddQutes();
//
//        Observable.timer(2, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(aLong -> {
//
//                    mView.disMissLoading();
//                    //刷新界面
//                    mView.updateGroupStatus(finishNum + "/" + mDataList.size());
//                    mView.renderApplicantsList(mViewModelList);
//
//                });
//
//    }


}
