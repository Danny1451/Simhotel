package com.real.simhotel.view.iview;

import com.real.simhotel.view.adapter.DynamicListModel;

import java.util.List;

/**
 * Created by liudan on 2016/12/28.
 */
public interface ICeoInitView extends IBaseView{

    void renderHotelInitItems(List<DynamicListModel> data);

}
