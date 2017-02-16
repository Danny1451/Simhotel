package com.real.simhotel.view.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.real.simhotel.MainApplication;
import com.real.simhotel.view.iview.IBaseView;
import com.real.simhotel.view.loading.LoadingDialog;

import butterknife.ButterKnife;

/**
 * Created by liudan on 2016/12/7.
 */
public abstract class AppActivity extends BaseActivity implements IBaseView {



    protected Context mContext;

    private LoadingDialog mLoadingDialog;


    private Boolean banBack = false;
    /**
     * 处理Intent
     *
     * @param intent
     */
    protected void handleIntent(Intent intent) {
    }


    protected void setBanBack(Boolean value){
        banBack = value;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                if (banBack)
                    return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
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


    @Override
    public void showToast(String content){
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showLoading() {

        if (mLoadingDialog == null){

            mLoadingDialog = new LoadingDialog(this).setMessage("请稍后...");
        }

        mLoadingDialog.show();

    }

    @Override
    public void disMissLoading(){
        if (mLoadingDialog != null)
            mLoadingDialog.dismiss();
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showEmptyView(String msg) {

    }

    @Override
    public void refreshView() {

    }

    public MainApplication getMainApplication(){
        return(MainApplication)getApplication();
    }
}
