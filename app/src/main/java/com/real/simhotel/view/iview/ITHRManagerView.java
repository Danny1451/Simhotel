package com.real.simhotel.view.iview;

import com.real.simhotel.view.adapter.DynamicListModel;

import java.util.List;

/**
 * Created by liudan on 2017/1/11.
 */
public interface ITHRManagerView extends IBaseView{

    void renderApplicantsList(List<DynamicListModel> applicantsList);

    void transToDetailFragment();

    void transToInitListFragment();

    void updateGroupStatus(String value);

    void updateConfirmStatus(String value);

    void removeAddApplicantDialog();
}
