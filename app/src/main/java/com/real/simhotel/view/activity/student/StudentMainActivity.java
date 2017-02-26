package com.real.simhotel.view.activity.student;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.real.simhotel.MainApplication;
import com.real.simhotel.R;
import com.real.simhotel.config.Role;
import com.real.simhotel.events.EventCode;
import com.real.simhotel.model.GroupDetailVo;
import com.real.simhotel.model.Student;
import com.real.simhotel.presenter.StudentMainPresenter;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.base.AppActivity;
import com.real.simhotel.view.base.BaseFragment;
import com.real.simhotel.view.fragment.student.LoadingFragment;
import com.real.simhotel.view.iview.IStudentMainView;

import butterknife.Bind;

/**
 * Created by liudan on 2016/12/7.
 */
public class StudentMainActivity extends AppActivity implements IStudentMainView{


    private BaseFragment mFragment;


    StudentMainPresenter mPresenter;


    @Bind(R.id.tv_student_name)
    TextView nameTv;

    @Bind(R.id.tv_student_id)
    TextView idTv;

    @Bind(R.id.tv_role)
    TextView roleTv;

    @Bind(R.id.tv_group_status)
    TextView hotelEmptyTv;

    @Bind(R.id.group_detail_layout)
    RelativeLayout groupDetail;


    @Bind(R.id.tv_group_name)
    TextView groupName;

    @Bind(R.id.tv_group_id)
    TextView groupId;

    @Bind(R.id.tv_group_roles)
    TextView groupRoles;





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
        //更新学生详情
        mPresenter.updateStudentInfo();

    }

    @Override
    protected void initView() {

        MainApplication application = (MainApplication)getApplication();


        //禁止返回
        setBanBack(true);

        //更新界面实训详情
        if (application.training.getTrainingStatus() >= EventCode.TraingingCode.TRAINING_BUILDED){

            updateTrainingInfo(application.group);
        }

        //获取基础的信息
        mFragment = new LoadingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_role,mFragment).commitAllowingStateLoss();

    }

    /**
     * 更新实训详情界面
     * @param group
     */
    public void updateTrainingInfo(GroupDetailVo group){

        hotelEmptyTv.setVisibility(View.INVISIBLE);
        groupDetail.setVisibility(View.VISIBLE);

        groupName.setText("小组名:"+group.getGroupName());
        groupId.setText("小组ID:"+group.getId());
        groupRoles.setText(group.getGroupRoleDetailsString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.student_bar_menu,menu);





        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void updateDetailFragment(BaseFragment fragment) {

        if (fragment.getClass() == mFragment.getClass()){
            KLog.d(TAG,"已经是该界面了,请勿重复切换");
            return;
        }
        mFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.content_role,fragment).commitAllowingStateLoss();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_update:{

                //手动触发一次刷新
                MainApplication application = (MainApplication)getApplication();
                application.traingingStatusManager.manualUpdate();
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




    }

    @Override
    public void updateStudentInfo(Student student) {

        nameTv.setText("姓名:" + student.getStudentName());
        idTv.setText("学号:" + student.getStudentNumber());
        roleTv.setText("角色:" + Role.getRoleString(getMainApplication().mRole));
    }

    @Override
    public BaseFragment getCurrentDetailFragment() {
        return mFragment;
    }
}
