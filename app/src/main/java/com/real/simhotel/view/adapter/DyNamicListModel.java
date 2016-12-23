package com.real.simhotel.view.adapter;

import java.util.List;

/**
 * Created by liudan on 2016/12/19.
 */
public class DynamicListModel {

    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_CHOOSE = 1;
    public static final int TYPE_PLAINTEXT = 2;
    public static final int TYPE_SEEK = 3;
    public static final int TYPE_NORMAL_INFO = 4;
    public static final int TYPE_NORMAL_CHOOSE = 5;

    public static final int TYPE_TITLE_INFO = 6;

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

    public String time;

    public String info;

    //是否选择
    public Boolean hasChoose = false;

    //选择之后的信息
    public String chooseInfo;

    public DynamicListModel(){
        super();
    }
    public DynamicListModel(int type){
        super();
        this.itemType = type;
    }
}
