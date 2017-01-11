package com.real.simhotel.view.fragment;

import com.real.simhotel.model.HotelTemplate;


/**
 * Created by liudan on 2017/1/11.
 */
public class HotelTemplateDetailFragment extends DetailFragment<HotelTemplate> {

    @Override
    public void renderView(HotelTemplate model) {
        super.renderView(model);

        tvLine1.setText("模板名称:"+ model.getLocationName());
        tvLine2.setText("创建时间:" + model.getInsertTime());
        tvLine3.setText("更新时间:" + model.getUpdateTime());
//        tvLine4.setText("教师id:" + model.getTeacherId());
//        tvLine5.setText("参数5:" + model.getCurrentCycle());
        tvLine6.setText("参数6:" + model.getEquipDepreCycle());


        confirmBtn.setText("删除");
    }

}
