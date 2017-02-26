package com.real.simhotel.view.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.real.simhotel.R;
import com.real.simhotel.utils.PreferenceUtils;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.base.AppActivity;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.observers.Observers;

/**
 * Created by liudan on 2017/1/17.
 */
public class SeatInitActivity extends AppActivity {

    @Bind(R.id.group_spinner)
    Spinner groupSpinner;

    @Bind(R.id.character_spinner)
    Spinner characterSpinner;

    @Bind(R.id.seat_confirm_btn)
    Button mConfirm;

    @Bind(R.id.ip_tv)
    EditText mIpET;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {



    }

    @OnClick({R.id.seat_confirm_btn})
    public void onClick(View view) {

        KLog.d(TAG,"" + groupSpinner.getSelectedItem() + characterSpinner.getSelectedItem() );

        PreferenceUtils.setCharacter(mContext,characterSpinner.getSelectedItem()+"");
        PreferenceUtils.setTeamNum(mContext,groupSpinner.getSelectedItem()+"");

        if (TextUtils.isEmpty(mIpET.getText().toString())){
            showToast("IP 未填写");
            return;
        }
        PreferenceUtils.setIpAdress(mContext,"http://" + mIpET.getText().toString());
        //调整 重新加载
//        navigator.toLoginActivity(mContext);

        Toast.makeText(this,"设置完成,即将重启",Toast.LENGTH_SHORT).show();
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe( call ->{

                    android.os.Process.killProcess(android.os.Process.myPid());  //获取PID
                    System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出

                });


    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_seat_init;
    }
}
