package com.real.simhotel.model;

import com.real.simhotel.view.adapter.DynamicListModel;

import java.util.List;

/**
 * Created by liudan on 2016/12/23.
 */
public class Applicant {

    public String name;

    public int level;

    public int expectValues;

    public int year;

    public int headRes;

    public int quotePrice;

    //报价
    public List<Quote> quotes;

    public String getLevelStr(){
        switch (level){
            case 0:
                return "初级工人";
            case 1:
                return "中级工人";
            case 2:
                return "高级工人";
            case 3:
                return "特级工人";
            default:
                return "屌丝工人";
        }
    }
}
