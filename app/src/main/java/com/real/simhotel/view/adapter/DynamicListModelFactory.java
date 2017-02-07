package com.real.simhotel.view.adapter;

import com.real.simhotel.model.Applicant;
import com.real.simhotel.model.HotelTemplate;
import com.real.simhotel.model.Training;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by liudan on 2016/12/28.
 *
 * ViewModel 的工厂类
 *
 */
public class DynamicListModelFactory {

    /**
     * 把酒店列表数据转换成显示列表
     *
     * @param hotelTemplateList
     * @return
     */
    public static List<DynamicListModel> parseFromHotelTemplate(List<HotelTemplate> hotelTemplateList){

        List<DynamicListModel> result = new ArrayList<>();

        //第一个是酒店类型
        List<String> items = new ArrayList<>();
        for (HotelTemplate template : hotelTemplateList){
            items.add(template.getLocationName());
        }

        DynamicListModel modelHotelType = new DynamicListModel(DynamicListModel.TYPE_RADIO_BUTTONS);
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

    public static List<DynamicListModel> parseFromHotelTemplateForTeacher(List<HotelTemplate> hotelTemplateList){

        List<DynamicListModel> result = new ArrayList<>();

        for (HotelTemplate temp: hotelTemplateList){

            DynamicListModel model = new DynamicListModel(DynamicListModel.TYPE_TITLE_INFO);
            model.title = temp.getLocationName();
            model.info = temp.getId() + "";
            model.ext = temp;
            result.add(model);

        }

        return result;

    }

    /**
     * 转换成展示用的列表
     * @param trainingsList
     * @return
     */
    public static List<DynamicListModel> parseFromTraining(List<Training> trainingsList){

        List<DynamicListModel> result = new ArrayList<>();



        for (Training temp : trainingsList){

            DynamicListModel model = new DynamicListModel(DynamicListModel.TYPE_TITLE_INFO);
            model.title = temp.getTrainingName();
            model.info = temp.getUpdateTime();
            model.ext = temp;
            result.add(model);
        }

        return result;
    }


    /**
     * 候选人列表专为 ViewModel
     * @param applicantList
     * @return
     */
    public static List<DynamicListModel> parseFromApplicants(List<Applicant> applicantList){

        List<DynamicListModel> result = new ArrayList<>();


        for (Applicant temp: applicantList){

            DynamicListModel model1 = new DynamicListModel(DynamicListModel.TYPE_TITLE_INFO);
            model1.title = temp.getLevelStr();
            model1.info = "未报价";
            model1.ext = temp;
            result.add(model1);
        }

        return result;
    }

    public static DynamicListModel modelForCeoNormalMessage(String info, String time){
        DynamicListModel model = new DynamicListModel(DynamicListModel.TYPE_INFO_TIME);
        model.info = info;
        model.time = time;
        return model;
    }


    public static DynamicListModel modelForCeoDecisionMessage(String info, String time, Object ext){
        DynamicListModel model = new DynamicListModel(DynamicListModel.TYPE_TWO_BUTTONS_CHOOSE);
        model.info = info;
        model.time = time;
        model.ext = ext;
        return model;
    }

    public static DynamicListModel modelForApplicantsBidResult(int number,String title, String info){
        DynamicListModel model = new DynamicListModel(DynamicListModel.TYPE_NUMBER_INFO);
        model.number = number;
        model.title = title;
        model.info = info;
        return model;
    }
}
