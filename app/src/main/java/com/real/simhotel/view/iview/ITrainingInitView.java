package com.real.simhotel.view.iview;

import com.real.simhotel.view.adapter.DynamicListModel;

import java.util.List;

/**
 * Created by liudan on 2017/1/10.
 */
public interface ITrainingInitView extends IBaseView {

    void renderTemlplateList(List<DynamicListModel> trainingsList);

    void initSuccess();

}
