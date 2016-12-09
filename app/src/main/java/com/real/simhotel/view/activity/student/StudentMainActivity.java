package com.real.simhotel.view.activity.student;

import android.content.Intent;

import com.real.simhotel.R;
import com.real.simhotel.view.base.AppActivity;
import com.real.simhotel.view.base.BaseFragment;

/**
 * Created by liudan on 2016/12/7.
 */
public class StudentMainActivity extends AppActivity{


    private int mRole;
    private BaseFragment mFragment;


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        //获取基础的信息


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
