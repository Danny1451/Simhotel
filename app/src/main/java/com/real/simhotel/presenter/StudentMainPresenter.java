package com.real.simhotel.presenter;

import com.real.simhotel.config.Role;
import com.real.simhotel.data.Response;
import com.real.simhotel.data.RetrofitUtils;
import com.real.simhotel.events.BaseStatus;
import com.real.simhotel.events.EventCode;
import com.real.simhotel.events.StatusManager;
import com.real.simhotel.events.TrainStatus;
import com.real.simhotel.model.Student;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.base.BaseFragment;
import com.real.simhotel.view.fragment.student.BidInitFragment;
import com.real.simhotel.view.fragment.student.BidResultFragment;
import com.real.simhotel.view.fragment.student.CeoInitFragment;
import com.real.simhotel.view.fragment.student.CeoNormalFragment;
import com.real.simhotel.view.fragment.student.HrNormalFragment;
import com.real.simhotel.view.fragment.student.LoadingFragment;
import com.real.simhotel.view.iview.IStudentMainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liudan on 2016/12/9.
 */
public class StudentMainPresenter extends BasePresenter {


    IStudentMainView mView;

    BaseFragment mDetailFragment;


    public StudentMainPresenter(IStudentMainView view) {
        mView = view;
        mDetailFragment = new LoadingFragment();
    }

    public void startUpdateStatus() {

        //获取轮询管理 开始轮询
        application.traingingStatusManager.startScheduling();

        EventBus.getDefault().register(this);


    }


    /**
     * 注册接收广播事件 用来切换界面
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseStatus event) {

        KLog.e("receive event " + event.getStatus() + " " + event.getDes());

        int status = event.getStatus();

        //根据角色 接收不同的广播事件
        if (application.mRole == Role.ROLE_STU_CEO) {

            //角色为CEO

            if (event.getClass() == TrainStatus.class) {
                //实训事件
                switch (status) {
                    case EventCode.TraingingCode.TRAINING_BUILDING:
                        mDetailFragment = new LoadingFragment();
                        mView.updateDetailFragment(mDetailFragment);
                        application.traingingStatusManager.consumeStatus(event);

                        break;
                    case EventCode.TraingingCode.TRAINING_BUILDED:

                        //还会建立完成的话是初始化界面
                        if (application.traingingStatusManager.getCurrentGroupStatus() <
                                EventCode.GroupCode.GROUP_CEO_HOTEL_INITING) {
                            //修改状态
                            application.traingingStatusManager.changeGroupStatus(
                                    EventCode.GroupCode.GROUP_CEO_HOTEL_INITING,
                                    new StatusManager.StatusChangeListener() {
                                        @Override
                                        public void OnChangedSuccess() {


                                            //消费事件
                                            application.traingingStatusManager.consumeStatus(event);
                                        }

                                        @Override
                                        public void OnChangedFailed(String erro) {

                                        }
                                    });
                        }else {
                            application.traingingStatusManager.consumeStatus(event);
                        }

                        break;
                    case EventCode.TraingingCode.TRAINING_HIRE_START: {


                        //还会建立完成的话是初始化界面
                        if (application.traingingStatusManager.getCurrentGroupStatus() ==
                                EventCode.GroupCode.GROUP_CEO_HOTEL_INITTED) {
                            //修改状态
                            application.traingingStatusManager.changeGroupStatus(
                                    EventCode.GroupCode.GROUP_CEO_HIRE_ING,
                                    new StatusManager.StatusChangeListener() {
                                        @Override
                                        public void OnChangedSuccess() {


                                            //消费事件
                                            application.traingingStatusManager.consumeStatus(event);
                                        }

                                        @Override
                                        public void OnChangedFailed(String erro) {

                                        }
                                    });
                        }else {
                            application.traingingStatusManager.consumeStatus(event);
                        }


                    }
                    break;
                    default:
//                        //其他情况默认为日常
                        mDetailFragment = new CeoNormalFragment();
                        mView.updateDetailFragment(mDetailFragment);
//                        //消费事件
                        application.traingingStatusManager.consumeStatus(event);
                }

            } else {

                switch (status){
                    case EventCode.GroupCode.GROUP_CEO_HOTEL_INITING:{
                        //切换到酒店创建页面 或者 日常界面
                        mDetailFragment = new CeoInitFragment();
                        mView.updateDetailFragment(mDetailFragment);
                        //消费事件
                        application.traingingStatusManager.consumeStatus(event);
                        break;
                    }

                    case EventCode.GroupCode.GROUP_CEO_HOTEL_INITTED:{

                        //酒店初始化完成
                        mDetailFragment = new CeoNormalFragment();
                        mView.updateDetailFragment(mDetailFragment);
                        //消费事件
                        application.traingingStatusManager.consumeStatus(event);
                        break;
                    }
                    case EventCode.GroupCode.GROUP_CEO_HIRE_ING:{
                        //正在处理招聘
                        if (mDetailFragment.getClass() == CeoNormalFragment.class){
                            //招聘中
                            mDetailFragment.handlerStatus(event);
                        }else {
                            //更新界面再刷新
                            mDetailFragment = new CeoNormalFragment();
                            mView.updateDetailFragment(mDetailFragment);
                            mDetailFragment.handlerStatus(event);
                        }
                    }
                    default:
                        if (mDetailFragment.getClass() != CeoNormalFragment.class){
                            //回到日常
                            mDetailFragment = new CeoNormalFragment();
                            mView.updateDetailFragment(mDetailFragment);
                        }
                        //消费事件
                        application.traingingStatusManager.consumeStatus(event);


                }

            }


        } else if (application.mRole == Role.ROLE_STU_HR) {

            if (event.getClass() == TrainStatus.class) {
                //实例状态

                switch (status) {
                    case EventCode.TraingingCode.TRAINING_HIRE_PUSH_APPLICANT:{
                        //推送候选人 用户后几轮竞价
                        //正在竞价
                        if (application.traingingStatusManager.getCurrentGroupStatus() ==
                                EventCode.GroupCode.GROUP_HR_HIRE_RESULT_SHOW) {
                            //进入展示
                            application.traingingStatusManager.changeGroupStatus(
                                    EventCode.GroupCode.GROUP_HR_HIRE_BIDDING,
                                    new StatusManager.StatusChangeListener() {
                                        @Override
                                        public void OnChangedSuccess() {
                                            application.traingingStatusManager.consumeStatus(event);

                                        }

                                        @Override
                                        public void OnChangedFailed(String erro) {

                                        }
                                    }
                            );
                        }else {
                            application.traingingStatusManager.consumeStatus(event);
                        }
                    }
                    break;
                    case EventCode.TraingingCode.TRAINING_HIRE_PUSH_RESULT: {
                        //正在展示候选人结果
                        if (application.traingingStatusManager.getCurrentGroupStatus() ==
                                EventCode.GroupCode.GROUP_HR_HIRE_BIDDING) {
                            //进入展示
                            application.traingingStatusManager.changeGroupStatus(
                                    EventCode.GroupCode.GROUP_HR_HIRE_RESULT_SHOW,
                                    new StatusManager.StatusChangeListener() {
                                        @Override
                                        public void OnChangedSuccess() {
                                            application.traingingStatusManager.consumeStatus(event);

                                        }

                                        @Override
                                        public void OnChangedFailed(String erro) {

                                        }
                                    }
                            );
                        }else {
                            application.traingingStatusManager.consumeStatus(event);
                        }
                        break;

                    }

//                    case EventCode.TraingingCode.TRAINING_HIRE_FINISHED: {
//                        //招聘结束 显示日常
//                        mDetailFragment = new HrNormalFragment();
//                        mView.updateDetailFragment(mDetailFragment);
//                        application.traingingStatusManager.consumeStatus(event);
//                        break;
//                    }

                    default: {
                        //和自己无关的事件 直接消费 不做任何处理

                        application.traingingStatusManager.consumeStatus(event);

                    }

                }
            } else {

                switch (status){
                    case EventCode.GroupCode.GROUP_CEO_HIRE_CONFIRM: {

                        //CEO 决定招聘
                        application.traingingStatusManager.changeGroupStatus(
                                EventCode.GroupCode.GROUP_HR_HIRE_BIDDING,
                                new StatusManager.StatusChangeListener() {
                                    @Override
                                    public void OnChangedSuccess() {

                                        application.traingingStatusManager.consumeStatus(event);

                                    }

                                    @Override
                                    public void OnChangedFailed(String erro) {

                                    }
                                });


                    }
                    break;
                    case EventCode.GroupCode.GROUP_HR_HIRE_BIDDING: {

                        //正在竞价
                        mDetailFragment = new BidInitFragment();
                        mView.updateDetailFragment(mDetailFragment);

                        application.traingingStatusManager.consumeStatus(event);
                        break;

                    }
                    case EventCode.GroupCode.GROUP_HR_HIRE_RESULT_SHOW:{
                        //正在展示结果

                        mDetailFragment = new BidResultFragment();
                        mView.updateDetailFragment(mDetailFragment);
                        break;
                    }
                    case EventCode.GroupCode.GROUP_HR_HIRE_FINISH: {
                        //招聘结束 显示日常
                        mDetailFragment = new HrNormalFragment();
                        mView.updateDetailFragment(mDetailFragment);
                        application.traingingStatusManager.consumeStatus(event);
                        break;
                    }

                }
            }


        } else if (application.mRole == Role.ROLE_STU_FINANCE) {

        } else if (application.mRole == Role.ROLE_STU_MARKET) {

        }

    }


    @Override
    public void requestData(Object... o) {
        super.requestData(o);



    }

    /**
     * 更新学生信息
     */
    public void updateStudentInfo(){

        apiService.getStudentInfo(Integer.parseInt(application.uid))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<Student>, Observable<Student>>() {
                    @Override
                    public Observable<Student> call(Response<Student> studentResponse) {
                        return RetrofitUtils.flatResponse(studentResponse);
                    }
                })
                .subscribe(new Subscriber<Student>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Student student) {

                        //刷新学生
                        mView.updateStudentInfo(student);
                    }
                });

    }

    @Override
    public void destroy() {
        super.destroy();

        EventBus.getDefault().unregister(this);

        application.traingingStatusManager.stopScheduling();
    }
}
