package com.real.simhotel.view.fragment;

import android.view.View;

import com.real.simhotel.model.Applicant;

/**
 * Created by liudan on 2017/1/11.
 */
public class ApplicantDetailFragment extends BaseDetailFragment<Applicant> {

    @Override
    public void initView() {
        super.initView();
        normLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void renderView(Applicant model) {


        tvLine1.setText("人员名称:"+ model.name);
        tvLine2.setText("等级:" + model.level);
        tvLine3.setText("期望月薪:" + model.expectValues);
//        tvLine4.setText("教师id:" + model.getTeacherId());
//        tvLine5.setText("参数5:" + model.getCurrentCycle());
        tvLine6.setText("工作年限:" + model.year);

        confirmBtn.setText("删除");

    }
}
