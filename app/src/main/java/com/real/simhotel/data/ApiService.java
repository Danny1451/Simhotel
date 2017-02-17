package com.real.simhotel.data;

import com.real.simhotel.config.Constants;
import com.real.simhotel.events.GroupStatus;
import com.real.simhotel.events.TrainStatus;
import com.real.simhotel.model.Applicant;
import com.real.simhotel.model.Group;
import com.real.simhotel.model.GroupDetailVo;
import com.real.simhotel.model.Hotel;
import com.real.simhotel.model.HotelTemplate;
import com.real.simhotel.model.Quote;
import com.real.simhotel.model.Student;
import com.real.simhotel.model.Training;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liudan on 2016/12/7.
 */
public interface ApiService {


    String PARAMS_TRAINING_ID = "training_id";
    String PARAMS_GROUP_ID = "group_id";

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


    /**
     * 获取学生详情
     * @param id
     * @return
     */
    @GET(Constants.API_URL_STUDENT_DETAIL)
    Observable<Response<Student>> getStudentInfo(@Query("student_id") int id);


    @GET(Constants.API_URL_HOTEL_LIST)
    Observable<Response<List<Hotel>>> getHotelList(@Query(PARAMS_TRAINING_ID) int trainId);

    @GET(Constants.API_URL_GROUP_LIST)
    Observable<Response<List<Group>>> getGroupList(@Query(PARAMS_TRAINING_ID) int trainId);

    @GET(Constants.API_URL_CREATE_GROUP)
    Observable<Response<String>> createGroup(@Query(PARAMS_TRAINING_ID) int trainId,
                                             @Query("group_name") String groupname,
                                             @Query("group_des") String des);



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
    Observable<Response<String>> createHotel(@Query(PARAMS_GROUP_ID) int groupId,
                                             @Query("hotel_id") int hotelId,
                                             @Query("room_num") int roomNum);


    /**
     * 获取给教师的实例列表
     * @param teacherId
     * @return
     */
    @GET(Constants.API_URL_TRAINING_LIST)
    Observable<Response<List<Training>>> getTrainingListForTeacher(@Query("teacher_id") String teacherId);


    /**
     * 获取给学生的实例列表
     * @param studentId
     * @return
     */
    @GET(Constants.API_URL_TRAINING_LIST)
    Observable<Response<List<Training>>> getTrainingListForStudent(@Query("student_id") String studentId,
                                                                   @Query("device_number") String deviceNumber);


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
                                                @Query("equip_depre_cycle") int equipDepreCycle,
                                                @Query("group_num") int groupNum

                                                );


    /**
     * 选择小组角色
     * @param trainingId
     * @param deviceNumber
     * @param studentId
     * @return
     */
    @GET(Constants.API_URL_CHOOSE_GROUP_ROLE)
    Observable<Response<GroupDetailVo>> chooseGroupRole(@Query(PARAMS_TRAINING_ID) String trainingId,
                                                        @Query("device_number") String deviceNumber,
                                                        @Query("student_id") String studentId);

    /**
     * 获取实例状态
     * @param trainId
     * @return
     */
    @GET(Constants.API_URL_TRAINING_STATUS)
    Observable<Response<TrainStatus>> getTrainingStatus(@Query(PARAMS_TRAINING_ID) int trainId);


    /**
     * 更新小组状态
     * @param groupId
     * @return
     */
    @GET(Constants.API_URL_GROUP_STATUS)
    Observable<Response<GroupStatus>> getGroupStatus(@Query(PARAMS_GROUP_ID) int groupId);


    /**
     * 更新小组状态
     * @param groupId
     * @param status
     * @return
     */
    @GET(Constants.API_URL_UPDATE_GROUP_STATUS)
    Observable<Response<String>> updateGroupStatus(@Query(PARAMS_GROUP_ID) int groupId,
                                                   @Query("group_status") int status);

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
     * @param bidPrice
     * @return
     */
    @GET(Constants.API_URL_BID_EMPLOY)
    Observable<Response<String>> bidEmploy(@Query("group_id") int groupId,
                                           @Query("employ_id") int employId,
                                           @Query("bidding_price") int bidPrice);


    /**
     * 删除招聘者
     * @param groupId
     * @param employId
     * @return
     */
    @GET(Constants.API_URL_DELETE_EMPLOY)
    Observable<Response<String>> deleteEmploy(@Query(PARAMS_TRAINING_ID) int groupId,
                                              @Query("employ_id") int employId);


    /**
     * 更新实训状态
     * @param trainingId
     * @param trainingStatus
     * @return
     */
    @GET(Constants.API_URL_UPDATE_TRAINING_STATUS)
    Observable<Response<String>> updateTrainingStatus(@Query(PARAMS_TRAINING_ID) int trainingId,
                                                      @Query("training_status") int trainingStatus);


    /**
     * 获取员工的竞拍结果
     * @param employId
     * @return
     */
    @GET(Constants.API_URL_EMPLOY_RESULT_LIST)
    Observable<Response<List<Quote>>> getEmployQuotes(@Query("employ_id") int employId);


    /**
     * 重新竞拍
     * @param trainId
     * @return
     */
    @GET(Constants.API_URL_RESTART_BID)
    Observable<Response<String>> restartBidEmploy(@Query(PARAMS_TRAINING_ID) int trainId);


    /**
     * 已经招聘的员工
     * @param groupId
     * @return
     */
    @GET(Constants.API_URL_EMPLOYED_LIST)
    Observable<Response<List<Applicant>>> getEmployedList(@Query(PARAMS_GROUP_ID) int groupId);

}
