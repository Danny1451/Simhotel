package com.real.simhotel.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.real.simhotel.R;
import com.real.simhotel.utils.PreferenceUtils;
import com.real.simhotel.view.activity.student.StudentMainActivity;
import com.real.simhotel.view.activity.teacher.TeacherControlActivity;
import com.real.simhotel.view.activity.teacher.TeacherMainActivity;
import com.real.simhotel.view.base.AppActivity;
import com.real.simhotel.view.base.BaseActivity;
import com.real.simhotel.view.base.BaseFragment;

/**
 * Created by liudan on 2016/12/8.
 */
public class SplashActivity extends AppActivity {

    private static final int sleepTime = 1000;
    private Context mContext;

    @Override
    protected void initData() {

        //check login
        mContext = this;


        //check 是否初始化过小组和角色

        if (TextUtils.isEmpty(PreferenceUtils.getCharacter(mContext))){
            //没有初始化过
            startActivity(new Intent(SplashActivity.this, SeatInitActivity.class));
            finish();
            return;
        }


        new Thread(new Runnable() {
            public void run() {
                String lastUser = PreferenceUtils.getLastUser(mContext);
                lastUser = "";
                if (!TextUtils.isEmpty(lastUser)) {
                    // auto login mode, make sure all group and conversation is loaed before enter the main screen
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }

                    if (lastUser.startsWith("")){
                        startActivity(new Intent(SplashActivity.this, StudentMainActivity                                                                                                                                                            .class));
                    }else {
                        startActivity(new Intent(SplashActivity.this, TeacherMainActivity.class));
                    }
                    finish();
                }else {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }).start();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.splash_layout;
    }

}
