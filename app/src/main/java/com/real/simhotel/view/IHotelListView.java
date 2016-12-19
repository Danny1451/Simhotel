package com.real.simhotel.view;

import com.real.simhotel.model.Hotel;

import java.util.List;

/**
 * Created by liudan on 2016/12/12.
 */
public interface IHotelListView extends IBaseView {

    void renderHotelList(List<Hotel> hotels);

    void viewHotel(Hotel hotelModel);

}
