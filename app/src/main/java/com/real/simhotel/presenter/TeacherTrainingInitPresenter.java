package com.real.simhotel.presenter;

import com.real.simhotel.model.HotelTemplate;
import com.real.simhotel.presenter.base.BasePresenter;
import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.base.BaseFragment;
import com.real.simhotel.view.iview.ITrainingInitView;
import com.real.simhotel.view.iview.ITrainingView;

/**
 * Created by liudan on 2017/1/10.
 */
public class TeacherTrainingInitPresenter extends BasePresenter {

    ITrainingInitView mView;

    public TeacherTrainingInitPresenter(ITrainingInitView view){
        mView = view;
    }

    @Override
    public void requestData(Object... o) {
        super.requestData(o);
    }


    /**
     * 删除指定模板
     * @param pos
     */
    public void removeTemplate(int pos){

    }

    /**
     * 增加模板
     * @param template
     */
    public void createHotelTemplate(HotelTemplate template){

    }

    /**
     * 初始化酒店 开始酒店
     */
    public void initHotel(){

    }
}

