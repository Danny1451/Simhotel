package com.real.simhotel.view.base;

import android.content.Intent;
import android.os.Bundle;

import com.real.simhotel.MainApplication;

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

        MainApplication.getComponent().inject(this);

        if (null != getIntent()) {
            handleIntent(getIntent());
        }

        initView();
        initData();

    }

    /**
     * 初始化data
     */
    protected abstract void initData();

    /**
     * 初始化view
     */
    protected abstract void initView();
}
