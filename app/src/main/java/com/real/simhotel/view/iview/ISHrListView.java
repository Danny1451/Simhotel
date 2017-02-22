package com.real.simhotel.view.iview;

import com.real.simhotel.view.adapter.DynamicListModel;
import com.real.simhotel.view.fragment.BaseDetailFragment;

import java.util.List;

/**
 * Created by liudan on 2017/1/17.
 */
public interface ISHrListView extends IBaseView {

    void renderApplicantsList(List<DynamicListModel> applicantsList);

    BaseDetailFragment getDetailFragment();
}
