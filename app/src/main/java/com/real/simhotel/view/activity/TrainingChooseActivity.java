package com.real.simhotel.view.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.real.simhotel.R;
import com.real.simhotel.config.Constants;
import com.real.simhotel.model.Group;
import com.real.simhotel.model.Training;
import com.real.simhotel.presenter.TrainingChoosePresenter;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.adapter.DynamicListAdapter;
import com.real.simhotel.view.adapter.DynamicListDecoration;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.base.AppActivity;
import com.real.simhotel.view.fragment.TrainingDetailFragment;
import com.real.simhotel.view.iview.ITrainingView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liudan on 2017/1/5.
 * 选择实例界面
 */
public class TrainingChooseActivity extends AppActivity implements ITrainingView{


    @Inject
    DynamicListAdapter mAdapter;

    @Bind(R.id.training_rv)
    RecyclerView mList;

    @Bind(R.id.add_training_btn)
    Button mAddTraining;

    //增加实例的对话框
    DialogPlus mDialog;

    //角色类型
    private int mUserType;

    private TrainingChoosePresenter mPresenter;

    private TrainingDetailFragment mTrainingDetail;

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);

        //判断是 老师 还是学生的登录界面
        mUserType = intent.getIntExtra("user_type", Constants.USER_TYPE_STUDENT);

    }

    @Override
    protected void initData() {

        mPresenter = new TrainingChoosePresenter(this);

        mPresenter.requestTrainingList(mUserType);

    }

    @Override
    protected void initView() {

        //学生的话不显示加号
        if (mUserType == Constants.USER_TYPE_STUDENT)
            mAddTraining.setVisibility(View.GONE);

        mAdapter = new DynamicListAdapter(this);


        //绑定触摸相应
        mAdapter.setRowInterface((pos, model)-> {

             //点击了对应的cell
             if ( mUserType == Constants.USER_TYPE_STUDENT){
                 //学生的话 去请求实例信息



             }else {
                 //老师的话 刷新
                 //ext 中会 放入原来的数据模型
                 renderTrainingDetail((Training) model.ext);
             }

        });

        mList.setAdapter(mAdapter);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.addItemDecoration(new DynamicListDecoration(this,DynamicListDecoration.VERTICAL_LIST));


        mTrainingDetail = new TrainingDetailFragment();
        mTrainingDetail.setConfirmListener((view)->{

            //点击了
            KLog.d("点击了 进入实例");

        });





    }

    @OnClick({R.id.add_training_btn})
    public void onClick(View view){

        if (mDialog == null) {
            KLog.d("点击了 创建实例");
            mDialog = DialogPlus.newDialog(this)
                    .setContentHolder(new ViewHolder(R.layout.create_training_layout))
                    .setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(DialogPlus dialog, View view) {
                            KLog.d(TAG, "CLICK");

                            View content = dialog.getHolderView();

                            String name = ((EditText) content.findViewById(R.id.training_name_tv)).getText().toString();
                            String time = ((EditText) content.findViewById(R.id.training_time_tv)).getText().toString();
                            String hiretime = ((EditText) content.findViewById(R.id.training_hire_time_tv)).getText().toString();
                            String equiptime = ((EditText) content.findViewById(R.id.training_equip_time_tv)).getText().toString();

                            switch (view.getId()) {
                                case R.id.training_create_confirm: {

                                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(time) || TextUtils.isEmpty(hiretime) || TextUtils.isEmpty(equiptime)) {
                                        Toast.makeText(mContext, "请完整填写实例信息", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    mPresenter.createTraining(name,time,hiretime,equiptime);


                                    break;
                                }
                                case R.id.training_create_cancel: {

                                    dialog.dismiss();
                                    break;
                                }
                            }

                        }
                    })
                    .setExpanded(false)
                    .setGravity(Gravity.CENTER)
                    .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .create();
            mDialog.show();

        }else {

            mDialog.show();
        }
    }

    @Override
    public void selectTrainingRow(int pos) {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_training_choose;
    }

    /**
     * 基础左边模块
     * @param trainingsList
     */
    @Override
    public void renderTrainingsList(List<DynamicListModel> trainingsList) {

        this.getSupportFragmentManager().beginTransaction().replace(R.id.detail_frame,mTrainingDetail).commitAllowingStateLoss();

        //更新
        mAdapter.setDataList(trainingsList);

        mTrainingDetail.updateInfo((Training) trainingsList.get(0).ext);

    }

    /**
     *
     * @param groupsList
     */
    @Override
    public void renderGroupsList(List<DynamicListModel> groupsList) {

    }

    @Override
    public void renderTrainingDetail(Training training) {

        mTrainingDetail.updateInfo(training);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmptyView(String msg) {

    }

    @Override
    public void refreshView() {

        if (mDialog !=  null)
            mDialog.dismiss();
    }

    @Override
    public void showError(String msg) {

    }


}
