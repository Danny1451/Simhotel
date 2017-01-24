package com.real.simhotel.config;

/**
 * Created by liudan on 2016/12/7.
 */
public class Constants {
    public static final String API_BASE_URL = "http://192.168.243.218:8080";
    public static final String API_URL_LOGIN = "/api/erp/edu/hotel/login.json";
    public static final String API_URL_HOTEL_LIST = "/api/erp/edu/hotel/list_hotel.json";
    public static final String API_URL_HOTEL_TEMPLATE_LIST = "/api/erp/edu/hotel/list_hotel_template.json";
    public static final String API_URL_GROUP_LIST = "/api/erp/edu/hotel/list_group.json";

    public static final String API_URL_CREATE_GROUP = "/api/erp/edu/hotel/init_group.json";
    public static final String API_URL_CREATE_HOTEL = "/api/erp/edu/hotel/create_group.json";
    public static final String API_URL_CREATE_TRAINING = "/api/erp/edu/hotel/init_training.json";

    public static final String API_URL_TRAINING_STATUS = "/api/erp/edu/hotel/training_status.json";

    public static final String API_URL_TRAINING_EMPLOU_TEMPLATE = "/api/erp/edu/hotel/list_employ_template.json";

    public static final String API_URL_HR_CREATE_APPLICANT = "/api/erp/edu/hotel/init_employ_template.json";

    public static final String API_URL_TRAINING_LIST_TEACHER = "/api/erp/edu/hotel/list_training_for_teacher.json";

    public static final String API_URL_BID_EMPLOY = "/api/erp/edu/hotel/bid_employ.json";

    public static final String API_URL_HOTEL_TEMPLATE_CHOOSE = "/api/erp/edu/hotel/choose_hotel.json";

    public static final String API_URL_GET_USER_DETAILS = "user_";
    public static final String USER_ID = "USER_ID";




    public static final String OK = "0";



    public static int USER_TYPE_TEACHER = 0;
    public static int USER_TYPE_STUDENT = 1;


    //系统的事件广播
    public static int BORADRCAST_TYPE = 1;
}
