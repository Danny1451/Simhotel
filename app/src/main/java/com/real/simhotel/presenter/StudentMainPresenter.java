package com.real.simhotel.presenter;

import com.real.simhotel.R;
import com.real.simhotel.config.Role;
import com.real.simhotel.events.EventCode;
import com.real.simhotel.events.StatusEvent;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.activity.student.StudentMainActivity;
import com.real.simhotel.view.base.BaseFragment;
import com.real.simhotel.view.fragment.student.BidInitFragment;
import com.real.simhotel.view.fragment.student.CeoInitFragment;
import com.real.simhotel.view.fragment.student.CeoNormalFragment;
import com.real.simhotel.view.iview.IStudentMainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import rx.Subscription;

/**
 * Created by liudan on 2016/12/9.
 */
public class StudentMainPresenter extends BasePresenter {




    IStudentMainView mView;

    BaseFragment mDetailFragment;


    public StudentMainPresenter(IStudentMainView view){ mView = view;}

    public void startUpdateStatus(){

        //获取轮询管理 开始轮询
        application.broadCastManager.startScheduling();

        EventBus.getDefault().register(this);


    }



    /**
     * 注册接收广播事件 用来切换界面
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {

        KLog.d("receive event " + event.getTrainingStatus() + " " + event.getStatusDes());

        //根据角色 接收不同的广播事件
        if (application.mRole == Role.ROLE_STU_CEO){

            //角色为CEO

            switch (event.getTrainingStatus()){
                case EventCode.TRAINING_BUILDING:

                    break;
                case EventCode.TRAINING_BUILDED: {
                    //实例建完之后 开始初始化
                    mDetailFragment = new CeoInitFragment();


                    mView.updateDetailFragment(mDetailFragment);
                    break;
                }

                case EventCode.TRAINING_HOTEL_INITED: {

                    //酒店实例化完成了 切换界面
                    mDetailFragment = new CeoNormalFragment();

                    mView.updateDetailFragment(mDetailFragment);


                    break;
                }
            }


        }else if (application.mRole == Role.ROLE_STU_HR){

            switch (event.getTrainingStatus()){
                case EventCode.CEO_CONFIRM_HIRE:{
                    // CEO 触发招聘

                    mDetailFragment = new BidInitFragment();

                    mView.updateDetailFragment(mDetailFragment);
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

        application.broadCastManager.stopScheduling();
    }
}
