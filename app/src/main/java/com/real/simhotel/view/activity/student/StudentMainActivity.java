package com.real.simhotel.view.activity.student;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.real.simhotel.MainApplication;
import com.real.simhotel.R;
import com.real.simhotel.events.StatusEvent;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.base.AppActivity;
import com.real.simhotel.view.base.BaseFragment;
import com.real.simhotel.view.fragment.student.BidInitFragment;
import com.real.simhotel.view.fragment.student.BidResultFragment;
import com.real.simhotel.view.fragment.student.CeoInitFragment;
import com.real.simhotel.view.fragment.student.CeoNormalFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by liudan on 2016/12/7.
 */
public class StudentMainActivity extends AppActivity{


    private int mRole;
    private BaseFragment mFragment;


    MenuItem mTrainingStatus;
    @Override
    protected void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {

        KLog.d(TAG,"receive event " + event.getTrainingStatus() + " " + event.getStatusDes());

        if (mTrainingStatus != null)
            mTrainingStatus.setTitle(event.getStatusDes());
    }

    @Override
    protected void initData() {



    }

    @Override
    protected void initView() {

        //获取基础的信息
        mFragment = new CeoInitFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_role,mFragment).commitAllowingStateLoss();

        
        //开始轮询
        MainApplication app = (MainApplication) getApplication();
        //获取轮询管理
        app.broadCastManager.startScheduling();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.student_bar_menu,menu);


        mTrainingStatus = menu.findItem(R.id.trainging_status);
        mTrainingStatus.setCheckable(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_ceo_init:
            {
                BaseFragment fragment = new CeoInitFragment();

                getSupportFragmentManager().beginTransaction().replace(R.id.content_role,fragment).commitAllowingStateLoss();
                return true;
            }

            case R.id.action_ceo_normal:{
                BaseFragment fragment = new CeoNormalFragment();

                getSupportFragmentManager().beginTransaction().replace(R.id.content_role,fragment).commitAllowingStateLoss();
                return true;
            }

            case R.id.action_hr_bit:{
                BaseFragment fragment = new BidInitFragment();

                getSupportFragmentManager().beginTransaction().replace(R.id.content_role,fragment).commitAllowingStateLoss();

                return true;
            }

            case R.id.action_hr_normal:{

                BaseFragment fragment = new BidResultFragment();

                getSupportFragmentManager().beginTransaction().replace(R.id.content_role,fragment).commitAllowingStateLoss();

                return true;
            }

            case R.id.action_update:{

                navigator.toTeacherMainActivity(this);
                return true;
            }
            case R.id.action_exit:

                android.os.Process.killProcess(android.os.Process.myPid());  //获取PID
                System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected int getContentViewId() {
        return R.layout.student_mainlayout;
    }


    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);

        //更具role 初始化 fragment
    }
}
