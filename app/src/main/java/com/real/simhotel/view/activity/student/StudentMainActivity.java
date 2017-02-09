package com.real.simhotel.view.activity.student;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.real.simhotel.MainApplication;
import com.real.simhotel.R;
import com.real.simhotel.config.Constants;
import com.real.simhotel.config.Role;
import com.real.simhotel.events.StatusEvent;
import com.real.simhotel.presenter.StudentMainPresenter;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.base.AppActivity;
import com.real.simhotel.view.base.BaseFragment;
import com.real.simhotel.view.fragment.student.BidInitFragment;
import com.real.simhotel.view.fragment.student.BidResultFragment;
import com.real.simhotel.view.fragment.student.CeoInitFragment;
import com.real.simhotel.view.fragment.student.CeoNormalFragment;
import com.real.simhotel.view.fragment.student.LoadingFragment;
import com.real.simhotel.view.iview.IStudentMainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;

/**
 * Created by liudan on 2016/12/7.
 */
public class StudentMainActivity extends AppActivity implements IStudentMainView{


    private int mRole;
    private BaseFragment mFragment;


    MenuItem mTrainingStatus;

    StudentMainPresenter mPresenter;


    @Bind(R.id.tv_student_name)
    TextView nameTv;

    @Bind(R.id.tv_student_id)
    TextView idTv;

    @Bind(R.id.tv_role)
    TextView roleTv;


    @Override
    protected void onDestroy() {
        super.onDestroy();


        if (mPresenter != null)
            mPresenter.destroy();
    }



    @Override
    protected void initData() {

        mPresenter = new StudentMainPresenter(this);
        mPresenter.startUpdateStatus();

    }

    @Override
    protected void initView() {

        //获取基础的信息
        mFragment = new LoadingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_role,mFragment).commitAllowingStateLoss();


        MainApplication application = (MainApplication)getApplication();
        nameTv.setText("姓名:" + application.uid);
        idTv.setText("学号:" + application.uid);
        roleTv.setText("角色:" + Role.getRoleString(application.mRole));


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
    public void updateDetailFragment(BaseFragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.content_role,fragment).commitAllowingStateLoss();

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

//                navigator.toTeacherMainActivity(this);
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

        //判断是 老师 还是学生的登录界面
        mRole = intent.getIntExtra("user_role", Role.ROLE_STU_CEO);

    }



}
