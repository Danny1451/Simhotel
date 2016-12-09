package com.real.simhotel.view.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.real.simhotel.MainApplication;

import butterknife.ButterKnife;

/**
 * Created by liudan on 2016/12/7.
 */
public abstract class AppActivity extends BaseActivity  {


    /**
     * 处理Intent
     *
     * @param intent
     */
    protected void handleIntent(Intent intent) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ButterKnife.bind(this);

        MainApplication.getComponent().inject(this);

        if (null != getIntent()) {
            handleIntent(getIntent());
        }

        initView();
        initData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * 初始化data
     */
    protected abstract void initData();

    /**
     * 初始化view
     */
    protected abstract void initView();


    protected void showToast(String content){
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }
}
