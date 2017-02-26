package com.real.simhotel.view.fragment;

import android.view.View;

import com.real.simhotel.R;
import com.real.simhotel.model.HotelTemplate;


/**
 * Created by liudan on 2017/1/11.
 */
public class HotelTemplateDetailFragment extends BaseDetailFragment<HotelTemplate> {

    @Override
    public void initView() {
        super.initView();
        normLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void renderView(HotelTemplate model) {
        super.renderView(model);

        tvLine1.setText(getString(R.string.trainging_detail_name, model.getLocationName()));
        tvLine2.setText(getString(R.string.trainging_detail_name, model.getInsertTime()));
        tvLine3.setText(getString(R.string.trainging_detail_update_time,model.getUpdateTime()));
//        tvLine4.setText("教师id:" + model.getTeacherId());
//        tvLine5.setText("参数5:" + model.getCurrentCycle());
        tvLine6.setText("参数6:" + model.getEquipDepreCycle());


        confirmBtn.setText(getString(R.string.delete));
    }

}
