package com.real.simhotel.view.adapter;

import java.util.List;

/**
 * Created by liudan on 2016/12/19.
 */
public class DynamicListModel {

    public static final int TYPE_DEFAULT = 0;
    //
    /**
     * 多个RadioButton选项的 配合mChooseItems [酒店初始化-位置选择]
     */
    public static final int TYPE_RADIO_BUTTONS = 1;
    //纯文本
    public static final int TYPE_PLAINTEXT = 2;
    /**
     * 滑动的Seek [酒店初始化-数量选择]
     */
    public static final int TYPE_SEEK = 3;
    /**
     * 信息 时间 [酒店日常-普通消息推送]
     */
    public static final int TYPE_INFO_TIME = 4;
    /**
     * 普通带两个按钮选择 [酒店日常-决策消息]
     */
    public static final int TYPE_TWO_BUTTONS_CHOOSE = 5;
    /**
     * 标题 和 信息 [竞价模块-候选人列表]
     */
    public static final int TYPE_TITLE_INFO = 6;
    /**
     * 序号 标题 信息 [竞价结果-酒店列表]
     */
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

    public Boolean isSelected = false;

    //是否选择
    public Boolean hasChoose = false;

    //按钮选择之后的信息
    public String butonChooseInfo;

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



}
