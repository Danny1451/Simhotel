package com.real.simhotel.data;

import com.real.simhotel.config.Constants;
import com.real.simhotel.events.StatusEvent;
import com.real.simhotel.model.Applicant;
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


    public static final String PARAMS_TRAINING_ID = "training_id";

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
    Observable<Response<List<Hotel>>> getHotelList(@Query(PARAMS_TRAINING_ID) int trainId);

    @GET(Constants.API_URL_GROUP_LIST)
    Observable<Response<List<Group>>> getGroupList(@Query(PARAMS_TRAINING_ID) int trainId);

    @GET(Constants.API_URL_CREATE_GROUP)
    Observable<Response<String>> createGroup(@Query(PARAMS_TRAINING_ID) int trainId,
                                             @Query("group_name") String groupname,
                                             @Query("group_des") String des);

    @GET(Constants.API_URL_CREATE_HOTEL)
    Observable<Response<String>> createHotel(@Query(PARAMS_TRAINING_ID) int trainId,
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
    Observable<Response<List<HotelTemplate>>> getHotelTemplateList(@Query(PARAMS_TRAINING_ID) int trainId);


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


    /**
     * 创建实例对象
     * @param teacherId
     * @param trainingName
     * @param trainingCycle
     * @param initialCapital
     * @param recruitCycle
     * @param customerCycle
     * @param equipDeprePer
     * @param equipDepreCycle
     * @return
     */
    @GET(Constants.API_URL_CREATE_TRAINING)
    Observable<Response<String>> createTraining(@Query("teacher_id") String teacherId,
                                                @Query("training_name") String trainingName,
                                                @Query("training_cycle") int trainingCycle,
                                                @Query("initial_capital") int initialCapital,
                                                @Query("recruit_cycle") int recruitCycle,
                                                @Query("customer_cycle") int customerCycle,
                                                @Query("equip_depre_per") Double equipDeprePer,
                                                @Query("equip_depre_cycle") int equipDepreCycle
                                                );

    /**
     * 获取实例状态
     * @param trainId
     * @return
     */
    @GET(Constants.API_URL_TRAINING_STATUS)
    Observable<Response<StatusEvent>> getTrainingStatus(@Query(PARAMS_TRAINING_ID) int trainId);


    /**
     * 获取雇员列表
     * @param trainId
     * @return
     */
    @GET(Constants.API_URL_TRAINING_EMPLOU_TEMPLATE)
    Observable<Response<List<Applicant>>> getEmployTemplate(@Query(PARAMS_TRAINING_ID) int trainId);


    /**
     * 建立招聘者
     * @param trainId
     * @param level
     * @param expectIncome
     * @param expectWorkPlace
     * @return
     */
    @GET(Constants.API_URL_HR_CREATE_APPLICANT)
    Observable<Response<Integer>> createEmploy(@Query(PARAMS_TRAINING_ID) int trainId,
                                               @Query("level") int level,
                                               @Query("expect_month_income") int expectIncome,
                                               @Query("expect_work_place") int expectWorkPlace
                                               );

    /**
     * 竞拍招聘者
     * @param groupId
     * @param employId
     * @param bidTime
     * @param bidPrice
     * @return
     */
    @GET(Constants.API_URL_BID_EMPLOY)
    Observable<Response<String>> bidEmploy(@Query("group_id") int groupId,
                                           @Query("employ_id") int employId,
                                           @Query("bidding_time") int bidTime,
                                           @Query("bidding_price") int bidPrice);


    /**
     * 删除招聘者
     * @param groupId
     * @param employId
     * @return
     */
    @GET(Constants.API_URL_BID_EMPLOY)
    Observable<Response<String>> deleteEmploy(@Query(PARAMS_TRAINING_ID) int groupId,
                                              @Query("employ_id") int employId);


    /**
     * 招聘结束
     * @param trainingId
     * @return
     */
    Observable<Response<String>> finishEmploy(@Query(PARAMS_TRAINING_ID) int trainingId);



}
