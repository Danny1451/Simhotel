package com.real.simhotel.view.activity.teacher;

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
import com.real.simhotel.presenter.TeacherMainPresenter;
import com.real.simhotel.utils.log.KLog;
import com.real.simhotel.view.base.AppActivity;
import com.real.simhotel.view.fragment.teacher.GroupListFragment;
import com.real.simhotel.view.fragment.teacher.HotelTemplateFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liudan on 2016/12/7.
 */
public class TeacherMainActivity extends AppActivity {

    @Bind(R.id.initbtn)
    Button mInitTrainBtn;
    @Bind(R.id.hotelcreatebtn)
    Button mInitHotelBtn;
    @Bind(R.id.groupcreatebtn)
    Button mInitGroupBtn;
    @Bind(R.id.hotellistbtn)
    Button mHotelListBtn;
    @Bind(R.id.grouplistbtn)
    Button mGroupListBtn;

    TeacherMainPresenter mainPresenter;

    HotelTemplateFragment hotelListFragment;

    GroupListFragment groupListFragment;

    DialogPlus mDialog;


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        hotelListFragment = new HotelTemplateFragment();

        groupListFragment = new GroupListFragment();

        mainPresenter = new TeacherMainPresenter(this,hotelListFragment,groupListFragment);


        getSupportFragmentManager().beginTransaction().replace(R.id.hotel_fragment,hotelListFragment).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().replace(R.id.group_fragment,groupListFragment).commitAllowingStateLoss();
    }

    @OnClick({R.id.initbtn,R.id.hotelcreatebtn,R.id.groupcreatebtn,R.id.hotellistbtn,R.id.grouplistbtn})
    public void onClick(View view){

        switch (view.getId()){
            case R.id.initbtn:{

                break;
            }
            case R.id.hotelcreatebtn:{

                break;
            }
            case R.id.groupcreatebtn:{

                mDialog = DialogPlus.newDialog(this)
                        .setContentHolder(new ViewHolder(R.layout.create_group_layout))
                        .setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(DialogPlus dialog, View view) {
                                KLog.d(TAG,"CLICK");

                                View content = dialog.getHolderView();

                                String name = ((EditText)content.findViewById(R.id.group_create_et_name)).getText().toString();
                                String des = ((EditText)content.findViewById(R.id.group_create_et_des)).getText().toString();
                                switch (view.getId()){
                                    case R.id.group_create_btn_confirm:{

                                        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(des)){
                                            Toast.makeText(mContext,"小组描述和名字不能为空",Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        mainPresenter.createGroup(name,des);

                                        break;
                                    }
                                    case R.id.group_create_btn_cancel:{

                                        dialog.dismiss();
                                        break;
                                    }
                                }

                            }
                        })
                        .setExpanded(true)
                        .setMargin(20,0,20,0)
                        .setGravity(Gravity.CENTER)
                        .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .create();
                mDialog.show();

                break;
            }
            case R.id.hotellistbtn:{

                hotelListFragment.reloadData();
                break;
            }
            case R.id.grouplistbtn:{

                groupListFragment.reloadData();
                break;
            }
        }


    }

    @Override
    protected int getContentViewId() {

        return R.layout.teacher_mainlayout;
    }

    public void removeDialog(){
        if (mDialog !=  null)
            mDialog.dismiss();
    }

}
