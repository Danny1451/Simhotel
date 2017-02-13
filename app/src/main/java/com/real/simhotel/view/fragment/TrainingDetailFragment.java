package com.real.simhotel.view.fragment;

import android.view.View;

import com.real.simhotel.MainApplication;
import com.real.simhotel.config.Role;
import com.real.simhotel.model.GroupDetailVo;
import com.real.simhotel.model.GroupRoleDetailVo;
import com.real.simhotel.model.Training;

/**
 * Created by liudan on 2017/1/11.
 */
public class TrainingDetailFragment extends BaseDetailFragment<Training> {

    @Override
    public void initView() {
        super.initView();
        normLayout.setVisibility(View.VISIBLE);
    }
    @Override
    public void renderView(Training model) {
        super.renderView(model);

        tvLine1.setText(model.getTrainingName());
        tvLine2.setText("创建时间:" + model.getInsertTime());
        tvLine3.setText("更新时间:" + model.getUpdateTime());
        tvLine4.setText("教师id:" + model.getTeacherId());

        tvLine6.setVisibility(View.GONE);
//        tvLine5.setText("参数5:" + model.getCurrentCycle());
//        tvLine6.setText("参数6:" + model.getEquipDepreCycle());

        MainApplication application =
                (MainApplication) getActivity().getApplication();

        if(application.mRole != Role.ROLE_TEACHER){

            //是学生
            if (model.getGroupDetailVos() == null || model.getGroupDetailVos().size() == 0){
                //没加入小组
                tvLine5.setText("小组信息:尚未加入该实例");

                confirmBtn.setText("加入实例");

            }else {

                GroupDetailVo group = model.getGroupDetailVos().get(0);

                tvLine5.setText("小组信息:" + group.getGroupName());
                //显示组员信息
                tvLine6.setText(group.getGroupRoleDetailsString());
                tvLine6.setVisibility(View.VISIBLE);

                confirmBtn.setText("回到实例");

            }


        }else {
            //是老师
            String groupDes = "";

            if (model.getGroupDetailVos() != null && model.getGroupDetailVos().size() != 0) {

                for (GroupDetailVo temp : model.getGroupDetailVos()) {

                    int groupRole = temp.getGroupRoleDetailVos() == null ? 0 : temp.getGroupRoleDetailVos().size();
                    groupDes = groupDes + " 组名:" + temp.getGroupName() + " 人数:" + groupRole + "\n";

                }
            }else {
                groupDes = "暂时没有小组";
            }


            tvLine5.setText(groupDes);

            confirmBtn.setText("进入实例");
        }




    }
}
