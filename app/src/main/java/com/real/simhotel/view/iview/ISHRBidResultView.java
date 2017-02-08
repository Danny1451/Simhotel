package com.real.simhotel.view.iview;

import com.real.simhotel.view.adapter.DynamicListModel;

import java.util.List;

/**
 * Created by liudan on 2017/2/8.
 */
public interface ISHRBidResultView extends IBaseView {
    void renderApplicantsList(List<DynamicListModel> applicantsList);

    void renderQuotesList(List<DynamicListModel> quoteList);
}
