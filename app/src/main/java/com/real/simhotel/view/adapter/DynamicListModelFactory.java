package com.real.simhotel.view.adapter;

import com.real.simhotel.model.HotelTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudan on 2016/12/28.
 */
public class DynamicListModelFactory {

    public static List<DynamicListModel> parseFromHotelTemplate(List<HotelTemplate> hotelTemplateList){

        List<DynamicListModel> result = new ArrayList<>();

        //第一个是酒店类型
        List<String> items = new ArrayList<>();
        for (HotelTemplate template : hotelTemplateList){
            items.add(template.getLocationName());
        }

        DynamicListModel modelHotelType = new DynamicListModel(DynamicListModel.TYPE_CHOOSE);
        modelHotelType.title = "酒店位置";
        modelHotelType.mChooseItems = items;

        //第二个是酒店房间数量

        DynamicListModel modelHotelNum = new DynamicListModel(DynamicListModel.TYPE_SEEK);
        modelHotelNum.title = "房间数量";
        modelHotelNum.minus = hotelTemplateList.get(0).getRoomLeastNum();
        modelHotelNum.unit = "间";
        modelHotelNum.max = 100;



        result.add(modelHotelType);
        result.add(modelHotelNum);

        return result;

    }
}
