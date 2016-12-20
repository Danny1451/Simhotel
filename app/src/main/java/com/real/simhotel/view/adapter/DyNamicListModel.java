package com.real.simhotel.view.adapter;

import java.util.List;

/**
 * Created by liudan on 2016/12/19.
 */
public class DyNamicListModel {

    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_CHOOSE = 1;
    public static final int TYPE_PLAINTEXT = 2;
    public static final int TYPE_SEEK = 3;

    //类型
    public int itemType;

    //最后选择的值
    public int selectedValue;

    //选择类型
    public List<String> mChooseItems;

    public String title;

    public String unit;

    public int max;
}
