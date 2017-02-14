package com.real.simhotel.presenter;

import com.real.simhotel.config.Role;
import com.real.simhotel.events.BaseStatus;
import com.real.simhotel.events.EventCode;
import com.real.simhotel.events.StatusManager;
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

/**
 * Created by liudan on 2016/12/9.
 */
public class StudentMainPresenter extends BasePresenter {




    IStudentMainView mView;

    BaseFragment mDetailFragment;


    public StudentMainPresenter(IStudentMainView view){ mView = view;}

    public void startUpdateStatus(){

        //获取轮询管理 开始轮询
        application.traingingStatusManager.startScheduling();

        EventBus.getDefault().register(this);


    }



    /**
     * 注册接收广播事件 用来切换界面
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseStatus event) {

        KLog.d("receive event " + event.getStatus() + " " + event.getDes());

        int status = event.getStatus();

        //根据角色 接收不同的广播事件
        if (application.mRole == Role.ROLE_STU_CEO){

            //角色为CEO

            switch (status){
                case EventCode.TraingingCode.TRAINING_BUILDING:

                    break;
                case EventCode.TraingingCode.TRAINING_BUILDED: {



                    //老师实训完成
                    if (application.group.getGroupStatus() == 0 ) {
                        //改变小组 状态 触发界面切换
                        application.traingingStatusManager.changeGroupStatus(
                                EventCode.GroupCode.GROUP_CEO_HOTEL_INITING,
                                new StatusManager.StatusChangeListener() {
                                    @Override
                                    public void OnChangedSuccess() {
                                        //实例建完之后 开始初始化
                                        mDetailFragment = new CeoInitFragment();
                                        mView.updateDetailFragment(mDetailFragment);
                                    }

                                    @Override
                                    public void OnChangedFailed(String erro) {

                                    }
                                });
                    }else if (application.group.getGroupStatus() == EventCode.GroupCode.GROUP_CEO_HOTEL_INITING){

                        //状态是小组状态 初始化 当前界面是等待界面的话 进入到初始化
                        if (mView.getCurrentDetailFragment().getClass() != CeoInitFragment.class) {

                            mDetailFragment = new CeoInitFragment();
                            mView.updateDetailFragment(mDetailFragment);
                        }
                    }else if (application.group.getGroupStatus() == EventCode.GroupCode.GROUP_CEO_HOTEL_INITTED){
                        //正常状态
                        if (mView.getCurrentDetailFragment().getClass() != CeoNormalFragment.class) {

                            mDetailFragment = new CeoNormalFragment();
                            mView.updateDetailFragment(mDetailFragment);
                        }
                    }

                    break;
                }

//                case EventCode.GroupCode.GROUP_CEO_HOTEL_INITING:{
//
//                    //状态是小组状态 初始化 当前界面是等待界面的话 进入到初始化
//                    if (mView.getCurrentDetailFragment().getClass() == LoadingFragment.class){
//
//                        mDetailFragment = new CeoInitFragment();
//                        mView.updateDetailFragment(mDetailFragment);
//                    }
//
//                }
//                    break;
//                case EventCode.GroupCode.GROUP_CEO_HOTEL_INITTED:{
//
//                    //状态是小组状态 初始化 当前界面是等待界面的话 进入到初始化
//                    if (mView.getCurrentDetailFragment().getClass() != CeoNormalFragment.class){
//
//                        mDetailFragment = new CeoNormalFragment();
//                        mView.updateDetailFragment(mDetailFragment);
//                    }
//                }
//                    break;

                default:{
                    //其他状态默认回到 日常状态
                    if (mView.getCurrentDetailFragment().getClass() == LoadingFragment.class &&
                            application.training.getTrainingStatus() >= EventCode.TraingingCode.TRAINING_BUILDED){

                        mDetailFragment = new CeoNormalFragment();
                        mView.updateDetailFragment(mDetailFragment);
                    }

                }
            }


        }else if (application.mRole == Role.ROLE_STU_HR){

            switch (status){
                case EventCode.TraingingCode.TRAINING_BUILDED:{



                    break;
                }
                case EventCode.GroupCode.GROUP_CEO_HIRE_CONFIRM:
                case EventCode.GroupCode.GROUP_HR_HIRE_BIDDING :{

                    if (application.group.getGroupStatus() != EventCode.GroupCode.GROUP_HR_HIRE_BIDDING) {
                        //CEO 决定招聘
                        application.traingingStatusManager.changeGroupStatus(
                                EventCode.GroupCode.GROUP_HR_HIRE_BIDDING,
                                new StatusManager.StatusChangeListener() {
                                    @Override
                                    public void OnChangedSuccess() {
                                        mDetailFragment = new BidInitFragment();
                                        mView.updateDetailFragment(mDetailFragment);
                                    }

                                    @Override
                                    public void OnChangedFailed(String erro) {

                                    }
                                });
                    }else {
                        if (mView.getCurrentDetailFragment().getClass() != BidInitFragment.class){

                            mDetailFragment = new BidInitFragment();
                            mView.updateDetailFragment(mDetailFragment);
                        }
                    }

                    break;
                }


                case EventCode.TraingingCode.TRAINING_HIRE_PUSH_RESULT:{

                    if (application.group.getGroupStatus() != EventCode.GroupCode.GROUP_HR_HIRE_RESULT_SHOW) {
                        //正在展示结果
                        application.traingingStatusManager.changeGroupStatus(
                                EventCode.GroupCode.GROUP_HR_HIRE_RESULT_SHOW,
                                new StatusManager.StatusChangeListener() {
                                    @Override
                                    public void OnChangedSuccess() {

                                        mDetailFragment = new BidResultFragment();
                                        mView.updateDetailFragment(mDetailFragment);
                                    }

                                    @Override
                                    public void OnChangedFailed(String erro) {

                                    }
                                }
                        );
                    }else {
                        if (mView.getCurrentDetailFragment().getClass() == LoadingFragment.class){

                            mDetailFragment = new BidResultFragment();
                            mView.updateDetailFragment(mDetailFragment);
                        }
                    }

                }

                case EventCode.TraingingCode.TRAINING_HIRE_FINISHED:{
                    if (mView.getCurrentDetailFragment().getClass() != HrNormalFragment.class){

                        mDetailFragment = new HrNormalFragment();
                        mView.updateDetailFragment(mDetailFragment);
                    }
                }
                    break;
            }



        }else if (application.mRole == Role.ROLE_STU_FINANCE){

        }else if (application.mRole == Role.ROLE_STU_MARKET){

        }

    }


    @Override
    public void requestData(Object... o) {
        super.requestData(o);



    }

    @Override
    public void destroy() {
        super.destroy();

        EventBus.getDefault().unregister(this);

        application.traingingStatusManager.stopScheduling();
    }
}
