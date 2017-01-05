package com.real.simhotel.data;

import com.real.simhotel.config.Constants;
import com.real.simhotel.model.Group;
import com.real.simhotel.model.Hotel;
import com.real.simhotel.model.HotelTemplate;
import com.real.simhotel.model.Training;


import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liudan on 2016/12/7.
 */
public interface ApiService {

    /**
     * 登录接口
     * @param type 类型 老师或者学生
     * @param account 账号
     * @param pwd 密码
     * @return 返回ID
     */
    @GET(Constants.API_URL_LOGIN)
    Observable<Response<Integer>> login(@Query("user_type") int type,
                                        @Query("login_account") String account,
                                        @Query("password") String pwd);


    @GET(Constants.API_URL_HOTEL_LIST)
    Observable<Response<List<Hotel>>> getHotelList(@Query("training_id") int trainId);

    @GET(Constants.API_URL_GROUP_LIST)
    Observable<Response<List<Group>>> getGroupList(@Query("training_id") int trainId);

    @GET(Constants.API_URL_CREATE_GROUP)
    Observable<Response<String>> createGroup(@Query("training_id") int trainId,
                                             @Query("group_name") String groupname,
                                             @Query("group_des") String des);

    @GET(Constants.API_URL_CREATE_HOTEL)
    Observable<Response<String>> createHotel(@Query("training_id") int trainId,
                                             @Query("location") int location,
                                             @Query("room_least_num") int roomMinNum,
                                             @Query("room_cost") int roomcoast,
                                             @Query("room_income")int income );

    /**
     * 获取酒店模板的接口
     * @param trainId
     * @return
     */
    @GET(Constants.API_URL_HOTEL_TEMPLATE_LIST)
    Observable<Response<List<HotelTemplate>>> getHotelTemplateList(@Query("training_id") int trainId);


    /**
     * 创建酒店的接口
     * @param groupId
     * @param hotelId
     * @param roomNum
     * @return
     */
    @GET(Constants.API_URL_CREATE_HOTEL)
    Observable<Response<String>> createHotel(@Query("group_id") int groupId,
                                             @Query("hotel_id") int hotelId,
                                             @Query("room_num") int roomNum);


    /**
     * 获取给教师的实例列表
     * @param teacherId
     * @return
     */
    @GET(Constants.API_URL_TRAINING_LIST_TEACHER)
    Observable<Response<List<Training>>> getTrainingListForTeacher(@Query("teacher_id") String teacherId);


    /**
     * 获取给学生的实例列表
     * @param teacherId
     * @return
     */
    @GET(Constants.API_URL_TRAINING_LIST_TEACHER)
    Observable<Response<List<Training>>> getTrainingListForStudent(@Query("teacher_id") String teacherId);

}
