package com.real.simhotel.view.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.real.simhotel.R;
import com.real.simhotel.config.Constants;
import com.real.simhotel.config.Role;
import com.real.simhotel.presenter.LoginPresenter;
import com.real.simhotel.utils.PreferenceUtils;
import com.real.simhotel.view.iview.ILoginView;
import com.real.simhotel.view.base.AppActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liudan on 2016/12/7.
 */
public class LoginActivity extends AppActivity implements ILoginView {

    @Bind(R.id.loginbtn)
    Button mLoginBtn;

    @Bind(R.id.nametf)
    EditText mNameTf;

    @Bind(R.id.pwdtf)
    EditText mPwdTf;

    @Bind(R.id.roleToggleButton)
    ToggleButton mToggleBtn;



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

        int role = !mToggleBtn.isChecked() ? Constants.USER_TYPE_TEACHER : Constants.USER_TYPE_STUDENT;

        //登录
        mLoginPresenter.login(role,name,pwd);

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

        String lastName = PreferenceUtils.getLastUser(mContext);
        String lastPwd = PreferenceUtils.getLastPwd(mContext);

        if (!TextUtils.isEmpty(lastName))
            mNameTf.setText(lastName);

        if (!TextUtils.isEmpty(lastPwd))
            mPwdTf.setText(lastPwd);
    }


    @Override
    public void loginFaied(String reason) {

        showToast("登录失败 " + reason);



    }

    @Override
    public void loginStudentSuccess(int uid) {



        //跳转到学生界面
        showToast("登录成功");
        navigator.toTrainingDetailActivity(this);

        //记住密码
        saveNameAndPwd();

        finish();
    }

    @Override
    public void loginTeacherSuccess(int uid) {

        //跳转到老师界面
        showToast("登录成功");
        navigator.toTrainingDetailActivity(this);

        //记住密码
        saveNameAndPwd();

        finish();
    }

    private void saveNameAndPwd(){
        PreferenceUtils.setLastPwd(mContext,mPwdTf.getText().toString());
        PreferenceUtils.setLastUser(mContext,mNameTf.getText().toString());
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
