package com.real.simhotel.view.iview;

import com.real.simhotel.model.Group;
import com.real.simhotel.model.Training;
import com.real.simhotel.view.adapter.DynamicListModel;

import java.util.List;

/**
 * Created by liudan on 2017/1/5.
 */
public interface ITrainingView extends IBaseView {

    void renderTrainingsList(List<DynamicListModel> trainingsList);

    void renderGroupsList(List<DynamicListModel> groupsList);

    void renderTrainingDetail(Training training);

    void selectTrainingRow(int pos);

    void closeDialog();

    void reloadTrainingList();


    void enterTrainingForTeacher(Training training);
}
