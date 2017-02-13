package com.real.simhotel.config;

/**
 * Created by liudan on 2016/12/7.
 */
public class Role {
    public static final int ROLE_STU_CEO = 1;
    public static final int ROLE_STU_HR = 2;
    public static final int ROLE_STU_FINANCE = 3;
    public static final int ROLE_STU_MARKET = 4;
    public static final int ROLE_STU_MANAGER = 5;

    public static String getRoleString(int role){
        switch (role){
            case ROLE_STU_CEO:
                return "CEO";
            case ROLE_STU_HR:
                return "HR";
            case ROLE_STU_FINANCE:
                return "财务总监";
            case ROLE_STU_MARKET:
                return "市场总监";
            case ROLE_STU_MANAGER:
                return "客房总监";


        }
        return "不知道干嘛的";

    }


    //老师
    public static final int ROLE_TEACHER = -1;
}
