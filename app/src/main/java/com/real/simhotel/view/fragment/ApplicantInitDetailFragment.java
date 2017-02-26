package com.real.simhotel.view.fragment;

import android.view.View;

import com.real.simhotel.R;
import com.real.simhotel.model.Applicant;

/**
 * Created by liudan on 2017/1/11.
 */
public class ApplicantInitDetailFragment extends BaseDetailFragment<Applicant> {

    @Override
    public void initView() {
        super.initView();
        normLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void renderView(Applicant model) {

        if (model == null) {
            showEmptyView(getString(R.string.applicant_empty));
            return;
        }

        refreshView();

        tvLine1.setText(getString(R.string.applicant_name,model.getLevelStr()));
        tvLine2.setText(getString(R.string.applicant_level,model.getLevel()));
        tvLine3.setText(getString(R.string.applicant_expect_income,model.getExpectMonthIncome()));
//        tvLine4.setText("教师id:" + model.getTeacherId());
//        tvLine5.setText("参数5:" + model.getCurrentCycle());
        tvLine6.setText(getString(R.string.applicant_expeerience,model.getExpectWorkPlace()));



        confirmBtn.setText(getString(R.string.delete));

    }


}
