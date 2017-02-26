package com.real.simhotel.view.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.real.simhotel.R;
import com.real.simhotel.config.Constants;
import com.real.simhotel.presenter.LoginPresenter;
import com.real.simhotel.utils.PreferenceUtils;
import com.real.simhotel.view.iview.ILoginView;
import com.real.simhotel.view.base.AppActivity;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;

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


    @OnClick({R.id.loginbtn,R.id.login_reset_btn})
    public void onClick(View view){

        switch (view.getId()){
            case R.id.loginbtn:
            {
                String name = mNameTf.getText().toString();
                String pwd = mPwdTf.getText().toString();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)){
                    Toast.makeText(this,this.getString(R.string.login_toast),Toast.LENGTH_LONG).show();
                    return;
                }

                int role = !mToggleBtn.isChecked() ? Constants.USER_TYPE_TEACHER : Constants.USER_TYPE_STUDENT;

                //登录
                mLoginPresenter.login(role,name,pwd);

            }
                break;
            case R.id.login_reset_btn:
            {

                PreferenceUtils.clean(this);

                Toast.makeText(this,"重置完成,即将重启",Toast.LENGTH_SHORT).show();
                Observable.timer(3, TimeUnit.SECONDS)
                        .subscribe( call ->{

                            android.os.Process.killProcess(android.os.Process.myPid());  //获取PID
                            System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出

                        });
            }
                break;
        }

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

        mToggleBtn.setChecked(PreferenceUtils.getIsTeacher(mContext));
        if (!TextUtils.isEmpty(lastName))
            mNameTf.setText(lastName);

        if (!TextUtils.isEmpty(lastPwd))
            mPwdTf.setText(lastPwd);
    }


    @Override
    public void loginFaied(String reason) {

        showToast(getString(R.string.login_toast_failed) + reason);

    }

    @Override
    public void loginStudentSuccess(int uid) {



        //跳转到学生界面
        showToast(getString(R.string.login_toast_success));
        navigator.toStudentTrainingDetailActivity(this);

        //记住密码
        saveNameAndPwd();

        finish();
    }

    @Override
    public void loginTeacherSuccess(int uid) {

        //跳转到老师界面
        showToast(getString(R.string.login_toast_success));
        navigator.toTeacherTrainingDetailActivity(this);

        //记住密码
        saveNameAndPwd();

        finish();
    }

    private void saveNameAndPwd(){
        PreferenceUtils.setLastPwd(mContext,mPwdTf.getText().toString());
        PreferenceUtils.setLastUser(mContext,mNameTf.getText().toString());
        PreferenceUtils.setIsTeacher(mContext,mToggleBtn.isChecked());
    }


}
