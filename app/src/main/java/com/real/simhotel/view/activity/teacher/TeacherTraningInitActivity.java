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
import com.real.simhotel.model.HotelTemplate;
import com.real.simhotel.presenter.TeacherTrainingInitPresenter;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.adapter.DynamicListAdapter;
import com.real.simhotel.view.adapter.DynamicListDecoration;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.base.AppActivity;
import com.real.simhotel.view.fragment.HotelTemplateDetailFragment;
import com.real.simhotel.view.iview.ITrainingInitView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liudan on 2017/1/10.
 *
 * 教师 初始化实例 界面
 */
public class TeacherTraningInitActivity extends AppActivity implements ITrainingInitView{

    @Inject
    DynamicListAdapter mAdapter;

    @Bind(R.id.training_rv)
    RecyclerView mList;

    @Bind(R.id.add_training_btn)
    Button mAddTraining;


    //增加实例的对话框
    DialogPlus mDialog;

    TeacherTrainingInitPresenter mPresenter;

    HotelTemplateDetailFragment mHotelTemplateDetail;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.teacher_init_training_bar_menu,menu);



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
            case R.id.action_end_init:{

                //结束本周

                mPresenter.initHotel();

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void initData() {

        mPresenter = new TeacherTrainingInitPresenter(this);

        //请求数据
        mPresenter.requestData();
    }

    @Override
    protected void initView() {



        mAddTraining.setText("增加酒店模板");

        mAdapter = new DynamicListAdapter(this)
                .setRowInterface((pos, model)-> {

                    //刷新酒店模板详细
                    mHotelTemplateDetail.updateInfo((HotelTemplate) model.ext);

                });


        mList.setAdapter(mAdapter);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.addItemDecoration(new DynamicListDecoration(this,DynamicListDecoration.VERTICAL_LIST));


        mHotelTemplateDetail = new HotelTemplateDetailFragment();
        mHotelTemplateDetail.setConfirmListener(view -> {

            //点击删除指定位置
            mPresenter.removeTemplate(mAdapter.getSelectPos());

        });


    }


    @OnClick({R.id.add_training_btn})
    public void onClick(View view){

        if (mDialog == null) {
            KLog.d("点击了 创建酒店模板");
            showToast("暂时不支持动态添加,此处为默认");
//            mDialog = DialogPlus.newDialog(this)
//                    .setContentHolder(new ViewHolder(R.layout.create_training_layout))
//                    .setOnClickListener((dialog, view1)-> {
//                            KLog.d(TAG, "CLICK");
//
//                            View content = dialog.getHolderView();
//
//                            String name = ((EditText) content.findViewById(R.id.training_name_tv)).getText().toString();
//                            String time = ((EditText) content.findViewById(R.id.training_time_tv)).getText().toString();
//                            String hiretime = ((EditText) content.findViewById(R.id.training_hire_time_tv)).getText().toString();
//                            String equiptime = ((EditText) content.findViewById(R.id.training_equip_time_tv)).getText().toString();
//
//                            switch (view.getId()) {
//                                case R.id.training_create_confirm: {
//
//                                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(time) || TextUtils.isEmpty(hiretime) || TextUtils.isEmpty(equiptime)) {
//                                        Toast.makeText(mContext, "请完整填写酒店模板信息", Toast.LENGTH_SHORT).show();
//                                        return;
//                                    }
//
//                                    HotelTemplate template = new HotelTemplate();
//
//                                    template.setLocationName(name);
//                                    template.setId(5);
//                                    mPresenter.createHotelTemplate(template);
//
//
//
//                                    break;
//                                }
//                                case R.id.training_create_cancel: {
//
//                                    dialog.dismiss();
//                                    break;
//                                }
//                            }
//
//                        }
//                    )
//                    .setExpanded(false)
//                    .setGravity(Gravity.CENTER)
//                    .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
//                    .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
//                    .create();
//            mDialog.show();

        }else {

            mDialog.show();
        }
    }


    @Override
    public void renderTemlplateList(List<DynamicListModel> trainingsList) {

        this.getSupportFragmentManager().beginTransaction().replace(R.id.detail_frame,mHotelTemplateDetail).commitAllowingStateLoss();

        //清空之前的选中状态
        for (int i = 0; i < trainingsList.size(); i++) {
           trainingsList.get(i).isSelected = false;
        }
        //选中第一个
        trainingsList.get(0).isSelected = true;
        //更新
        mAdapter.setDataList(trainingsList);

        //默认选中第一个
        mHotelTemplateDetail.updateInfo((HotelTemplate) trainingsList.get(0).ext);
    }


    @Override
    public void initSuccess() {
        //初始化成功 直接回到上衣界面
        finish();
        showToast("初始化成功");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_list_detail;
    }



}
