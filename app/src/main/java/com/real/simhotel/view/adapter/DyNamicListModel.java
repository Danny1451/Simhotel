package com.real.simhotel.view.adapter;

import java.util.List;

/**
 * Created by liudan on 2016/12/19.
 */
public class DynamicListModel {

    public static final int TYPE_DEFAULT = 0;
    //带选项的
    public static final int TYPE_CHOOSE = 1;
    //纯文本
    public static final int TYPE_PLAINTEXT = 2;
    //滑动的Seek
    public static final int TYPE_SEEK = 3;
    //普通带信息的
    public static final int TYPE_NORMAL_INFO = 4;
    //普通带选择的
    public static final int TYPE_NORMAL_CHOOSE = 5;
    //左右标题 和 信息 选择列表
    public static final int TYPE_TITLE_INFO = 6;

    //带序号的 标题 和 信息
    public static final int TYPE_NUMBER_INFO = 7;

    //类型
    public int itemType;

    //详细类型
    public int detailType;
    //最后选择的值
    public int selectedValue;

    //选择类型
    public List<String> mChooseItems;

    public String title;

    public String unit;

    public int max;

    public int minus;

    public String time;

    public String info;

    public int number;

    //是否选择
    public Boolean hasChoose = false;

    //选择之后的信息
    public String chooseInfo;


    //额外信息
    public Object ext;

    public DynamicListModel(){
        super();
    }
    public DynamicListModel(int type){
        super();
        this.itemType = type;
        this.selectedValue = -1;
    }


    public static DynamicListModel modelWithNumberTitleInfo(int number,String title, String info){
        DynamicListModel model = new DynamicListModel(TYPE_NUMBER_INFO);
        model.number = number;
        model.title = title;
        model.info = info;
        return model;
    }
}
