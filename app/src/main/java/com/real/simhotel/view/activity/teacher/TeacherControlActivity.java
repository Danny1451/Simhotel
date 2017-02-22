package com.real.simhotel.view.activity.teacher;

import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.real.simhotel.MainApplication;
import com.real.simhotel.R;
import com.real.simhotel.events.EventCode;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.base.AppActivity;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liudan on 2017/1/10.
 */
public class TeacherControlActivity extends AppActivity {

    @Bind(R.id.cardBtn1)
    CardView hotelTemplateManage;

    @Bind(R.id.cardBtn2)
    CardView hrManage;
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {


    }

    @OnClick({R.id.cardBtn1,R.id.cardBtn2,R.id.cardBtn3})
    public void onClick(View view){

        switch (view.getId()){
            case R.id.cardBtn1:

                if (getMainApplication().training.getTrainingStatus() < EventCode.TraingingCode.TRAINING_BUILDED) {
                    //跳转到酒店管理界面
                    navigator.toTrainingInitActivity(this);
                }else {
                    showToast("已经初始化过了");
                }

                break;
            case R.id.cardBtn2:

                //跳转到人员界面

                navigator.toHRManagerActivity(this);

                break;
            case R.id.cardBtn3:

                //跳转到客源推送界面
                showLoading();

                Observable.timer(3,TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aLong -> {

                            disMissLoading();

                        });

                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_hr_bit:{

                //获取小组状态
                item.setTitle("更新中。。。");

                Observable.timer(3, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aLong -> {

                    item.setTitle("小组: 8/10");

                });

                return true;
            }
            case R.id.action_end_month:{

                //结束本周

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
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
