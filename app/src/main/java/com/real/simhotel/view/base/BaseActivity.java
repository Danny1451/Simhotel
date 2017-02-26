package com.real.simhotel.view.base;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.real.simhotel.MainApplication;
import com.real.simhotel.internal.dl.modules.ActivityModule;
import com.real.simhotel.view.navigation.Navigator;

import javax.inject.Inject;

/**
 * Created by liudan on 2016/12/7.
 */
public abstract class BaseActivity extends AppCompatActivity {
    //使用Dagger2注入的全局导航类
    @Inject
    public Navigator navigator;
    //动态获取类名 打印日志使用
    protected String TAG = this.getClass().getSimpleName();

    //布局文件ID
    protected abstract int getContentViewId();


    @Inject
    protected MainApplication application;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        MainApplication.getComponent().inject(this);
    }

    //返回键返回事件的处理
    //如果FragmentStack中只有1个fragment 关闭当前activity
    // 如果FragmentStack中还有>1数量fragment则可以removeFragment()将fragment出栈 此部分交给子类实现
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //配合Dagger2使用 返回当前Activity的ActivityModule对象
    // ActivityModule生命周期与activity是绑定的
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
