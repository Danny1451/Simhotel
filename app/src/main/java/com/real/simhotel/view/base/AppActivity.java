package com.real.simhotel.view.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.real.simhotel.MainApplication;

import butterknife.ButterKnife;

/**
 * Created by liudan on 2016/12/7.
 */
public abstract class AppActivity extends BaseActivity  {



    protected Context mContext;
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

        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(getContentViewId());

        mContext = this;
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


    public void showToast(String content){
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }


    public void showLoading() {

    }

}
