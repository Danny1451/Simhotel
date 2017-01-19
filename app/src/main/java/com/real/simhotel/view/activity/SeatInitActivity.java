package com.real.simhotel.view.activity;

import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.real.simhotel.R;
import com.real.simhotel.utils.PreferenceUtils;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.base.AppActivity;

import butterknife.Bind;
import butterknife.OnClick;

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

        //调整
        navigator.toLoginActivity(mContext);
        finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_seat_init;
    }
}
