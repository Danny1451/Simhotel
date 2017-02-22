package com.real.simhotel.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.real.simhotel.model.Applicant;

/**
 * Created by liudan on 2017/2/17.
 */
public class ApplicantNormalDetailFragment extends BaseDetailFragment<Applicant>{


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

        confirmBtn.setText("解雇");
        tvLine4.setVisibility(View.INVISIBLE);
        tvLine5.setVisibility(View.INVISIBLE);
        tvLine6.setVisibility(View.INVISIBLE);
    }

    @Override
    public void renderView(Applicant model) {
        super.renderView(model);

        refreshView();

        tvLine1.setText("人员名称:"+ model.getLevelStr());
        tvLine2.setText("等级:" + model.getLevel());
        tvLine3.setText("月薪:" + model.getBiddingPrice());



        if (model.hasFired)
            confirmBtn.setEnabled(false);


    }
}
