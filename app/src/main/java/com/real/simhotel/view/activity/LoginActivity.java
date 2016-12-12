package com.real.simhotel.view.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.real.simhotel.R;
import com.real.simhotel.presenter.LoginPresenter;
import com.real.simhotel.view.base.AppActivity;
import com.real.simhotel.view.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liudan on 2016/12/7.
 */
public class LoginActivity extends AppActivity implements LoginView{

    @Bind(R.id.loginbtn)
    Button mLoginBtn;

    @Bind(R.id.nametf)
    EditText mNameTf;

    @Bind(R.id.pwdtf)
    EditText mPwdTf;

    private LoginPresenter mLoginPresenter;
    @Override
    protected int getContentViewId() {
        return R.layout.login_layout;
    }


    @OnClick({R.id.loginbtn})
    public void onClick(View view){

        String name = mNameTf.getText().toString();
        String pwd = mPwdTf.getText().toString();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)){
            Toast.makeText(this,"请输入用户名和密码",Toast.LENGTH_LONG).show();
            return;
        }

        mLoginPresenter.login(name,pwd);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != mLoginPresenter)
            mLoginPresenter.destroy();
    }

    @Override
    protected void initData() {

        mLoginPresenter = new LoginPresenter(this);

    }

    @Override
    protected void initView() {


    }


    @Override
    public void loginFaied(String reason) {

        showToast("登录失败");



    }

    @Override
    public void loginStudentSuccess(int role) {
        //跳转到学生界面
        showToast("登录成功");
        navigator.toStudentMainActivity(this,role);
    }

    @Override
    public void loginTeacherSuccess(int role) {

        //跳转到老师界面
        showToast("登录成功");
        navigator.toTeacherMainActivity(this);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void showEmptyView(String msg) {

    }

    @Override
    public void refreshView() {

    }

    @Override
    public void showError(String msg) {

    }
}
