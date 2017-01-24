package com.real.simhotel.view.activity.teacher;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.real.simhotel.R;
import com.real.simhotel.model.Applicant;
import com.real.simhotel.presenter.TeacherHRManagerPresenter;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.adapter.DynamicListAdapter;
import com.real.simhotel.view.adapter.DynamicListDecoration;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.base.AppActivity;
import com.real.simhotel.view.fragment.ApplicantDetailFragment;
import com.real.simhotel.view.fragment.ApplicantDetailListFragment;
import com.real.simhotel.view.fragment.BaseDetailFragment;
import com.real.simhotel.view.iview.ITHRManagerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liudan on 2017/1/11.
 * 教师的人力资源管理界面
 */
public class TeacherHRManagerActivity extends AppActivity implements ITHRManagerView {

    @Inject
    DynamicListAdapter mAdapter;

    @Bind(R.id.training_rv)
    RecyclerView mList;

    @Bind(R.id.add_training_btn)
    Button mAddApplicant;

    //增加实例的对话框
    DialogPlus mDialog;

    TeacherHRManagerPresenter mPresenter;

    //详情页
    BaseDetailFragment mDetailFragment;

    //上方菜单按钮
    MenuItem mGroupStatus;
    MenuItem mConfirm;

    @Override
    public void updateGroupStatus(String value) {

        if (mGroupStatus != null)
            mGroupStatus.setTitle(value);

    }

    @Override
    public void updateConfirmStatus(String value) {
        if (mConfirm != null)
            mConfirm.setTitle(value);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.teacher_hr_manger_bar_menu,menu);

        mGroupStatus = menu.findItem(R.id.action_state);

        mConfirm = menu.findItem(R.id.action_confirm);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_state:{


                //获取小组状态
                item.setTitle("更新中。。。");


                //刷新界面
                mPresenter.updateResult();



                return true;
            }
            case R.id.action_confirm:{



                if (mConfirm.getTitle().toString().startsWith("推送")){

                    //确认推送人员信息
                    mPresenter.pushApplicants();

                }else if (mConfirm.getTitle().toString().contains("二次")){


                    showToast("此次招聘结束结束");

                }else {
                    //推送结果  包括第一轮 和 第二轮

                    mPresenter.pushResult();
                }



                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void initData() {

        mPresenter = new TeacherHRManagerPresenter(this);
        mPresenter.requestData();
    }

    @Override
    protected void initView() {

        mAddApplicant.setText("增加候选人");
        mAdapter = new DynamicListAdapter(this)
                .setRowInterface((pos, model)-> {

                //刷新人员模板详细
                 mDetailFragment.updateInfo(model.ext);

                });




        mList.setAdapter(mAdapter);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.addItemDecoration(new DynamicListDecoration(this,DynamicListDecoration.VERTICAL_LIST));



    }

    @OnClick({R.id.add_training_btn})
    public void onClick(View view){

        if (mDialog == null) {
            KLog.d("点击了 创建人员模板");
            mDialog = DialogPlus.newDialog(this)
                    .setContentHolder(new ViewHolder(R.layout.dialog_applicant_layout))
                    .setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(DialogPlus dialog, View view) {
                            KLog.d(TAG, "CLICK");

                            View content = dialog.getHolderView();

                            String name = ((EditText) content.findViewById(R.id.applicant_name_tv)).getText().toString();
                            String level = ((EditText) content.findViewById(R.id.applicant_level_tv)).getText().toString();
                            String price = ((EditText) content.findViewById(R.id.applicant_price_tv)).getText().toString();


                            switch (view.getId()) {
                                case R.id.training_create_confirm: {
                                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(level) || TextUtils.isEmpty(name) ) {

                                        Toast.makeText(mContext, "请完整填写人员信息", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    //TODO
                                    Applicant template = new Applicant();
//                                    template.setEmployNum(1);
                                    template.setExpectMonthIncome(Integer.parseInt(price));
                                    template.setLevel(Integer.parseInt(level));
                                    template.setExpectWorkPlace(Integer.parseInt(level));
                                    template.quotes = new ArrayList<>();
                                    mPresenter.createApplicant(template);



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
    protected int getContentViewId() {
        return R.layout.activity_list_detail;
    }


    @Override
    public void renderApplicantsList(List<DynamicListModel> applicantsList) {

        //替换 fragment
        this.getSupportFragmentManager().beginTransaction().replace(R.id.detail_frame,mDetailFragment).commitAllowingStateLoss();

        //清空之前的选中状态
        for (int i = 0; i < applicantsList.size(); i++) {
            applicantsList.get(i).isSelected = false;
        }

        //若第一个有数据的话 刷新这个人的界面
        if (applicantsList.size() == 0){
            mAdapter.setDataList(applicantsList);
            return;
        }
        //选中第一个
        applicantsList.get(0).isSelected = true;
        //更新
        mAdapter.setDataList(applicantsList);

        //默认选中第一个
        mDetailFragment.updateInfo(applicantsList.get(0).ext);
    }

    /**
     * 切换至详情显示
     */
    @Override
    public void transToDetailFragment(){
        mDetailFragment = new ApplicantDetailFragment();
        mDetailFragment.setConfirmListener(view -> {

            //点击删除指定位置
            mPresenter.removeApplicant(mAdapter.getSelectPos());

        });
        mAddApplicant.setVisibility(View.VISIBLE);
    }

    /**
     * 切换至列表显示
     */
    @Override
    public void transToInitListFragment(){

        mDetailFragment = new ApplicantDetailListFragment();
        mAddApplicant.setVisibility(View.GONE);
    }

    @Override
    public void removeAddApplicantDialog() {
        if (mDialog != null)
            mDialog.dismiss();
    }
}
