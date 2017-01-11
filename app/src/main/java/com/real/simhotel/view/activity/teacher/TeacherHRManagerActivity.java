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
import com.real.simhotel.model.HotelTemplate;
import com.real.simhotel.presenter.TeacherHRManagerPresenter;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.adapter.DynamicListAdapter;
import com.real.simhotel.view.adapter.DynamicListDecoration;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.base.AppActivity;
import com.real.simhotel.view.fragment.ApplicantDetailFragment;
import com.real.simhotel.view.iview.IHRManagerView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liudan on 2017/1/11.
 * 教师的人力资源管理界面
 */
public class TeacherHRManagerActivity extends AppActivity implements IHRManagerView{


    @Inject
    DynamicListAdapter mAdapter;

    @Bind(R.id.training_rv)
    RecyclerView mList;

    @Bind(R.id.add_training_btn)
    Button mAddApplicant;

    //增加实例的对话框
    DialogPlus mDialog;

    TeacherHRManagerPresenter mPresenter;

    ApplicantDetailFragment mDetailFragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.teacher_hr_manger_bar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_state:{

                //获取小组状态

                //获取小组状态
                item.setTitle("更新中。。。");

                Observable.timer(3, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aLong -> {

                            item.setTitle("小组: 8/10");
                        });


                return true;
            }
            case R.id.action_confirm:{

                //确认推送



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
        mAdapter = new DynamicListAdapter(this);


        //绑定触摸相应
        mAdapter.setRowInterface((pos, model)-> {

            //刷新人员模板详细
            mDetailFragment.updateInfo((Applicant) model.ext);

        });

        mList.setAdapter(mAdapter);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.addItemDecoration(new DynamicListDecoration(this,DynamicListDecoration.VERTICAL_LIST));


        mDetailFragment = new ApplicantDetailFragment();
        mDetailFragment.setConfirmListener(view -> {

            //点击删除指定位置
            mPresenter.removeApplicant(mAdapter.getSelectPos());

        });


    }

    @OnClick({R.id.add_training_btn})
    public void onClick(View view){

        if (mDialog == null) {
            KLog.d("点击了 创建人员模板");
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
                                        Toast.makeText(mContext, "请完整填写人员信息", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    Applicant template = new Applicant();

                                    template.name = name;
                                    template.expectValues = 10000;

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

        this.getSupportFragmentManager().beginTransaction().replace(R.id.detail_frame,mDetailFragment).commitAllowingStateLoss();

        //清空之前的选中状态
        for (int i = 0; i < applicantsList.size(); i++) {
            applicantsList.get(i).isSelected = false;
        }

        if (applicantsList.size() == 0){
            mAdapter.setDataList(applicantsList);
            return;
        }
        //选中第一个
        applicantsList.get(0).isSelected = true;
        //更新
        mAdapter.setDataList(applicantsList);

        //默认选中第一个
        mDetailFragment.updateInfo((Applicant) applicantsList.get(0).ext);
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
