package com.real.simhotel.view.activity.teacher;

import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;

import com.real.simhotel.R;
import com.real.simhotel.view.base.AppActivity;

import butterknife.Bind;

/**
 * Created by liudan on 2017/1/10.
 */
public class TeacherControlActivity extends AppActivity {

    @Bind(R.id.cardBtn1)
    CardView hotelTemplateManage;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.teacher_bar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_teacher_control;
    }
}
