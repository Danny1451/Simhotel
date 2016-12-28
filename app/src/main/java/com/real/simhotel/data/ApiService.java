package com.real.simhotel.data;

import com.real.simhotel.config.Constants;
import com.real.simhotel.model.Group;
import com.real.simhotel.model.Hotel;
import com.real.simhotel.model.HotelTemplate;


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

    @GET(Constants.API_URL_Login)
    Observable<Response<String>> login(@Query("name") String name, @Query("pwd") String pwd);

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

    @GET(Constants.API_URL_HOTEL_TEMPLATE_LIST)
    Observable<Response<List<HotelTemplate>>> getHotelTemplateList(@Query("training_id") int trainId);

}
