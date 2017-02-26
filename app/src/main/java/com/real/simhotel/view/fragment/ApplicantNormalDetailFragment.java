package com.real.simhotel.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.real.simhotel.R;
import com.real.simhotel.model.Applicant;

/**
 * Created by liudan on 2017/2/17.
 */
public class ApplicantNormalDetailFragment extends BaseDetailFragment<Applicant>{


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        normLayout.setVisibility(View.VISIBLE);

        confirmBtn.setText(getString(R.string.fire));
        tvLine4.setVisibility(View.INVISIBLE);
        tvLine5.setVisibility(View.INVISIBLE);
        tvLine6.setVisibility(View.INVISIBLE);
    }

    @Override
    public void renderView(Applicant model) {
        super.renderView(model);

        refreshView();

        tvLine1.setText(getString(R.string.applicant_name,model.getLevelStr()));
        tvLine2.setText(getString(R.string.applicant_level,model.getLevel()));
        tvLine3.setText(getString(R.string.applicant_income,model.getBiddingPrice()));



        if (model.hasFired)
            confirmBtn.setEnabled(false);


    }
}
