package com.real.simhotel.data;

import com.real.simhotel.config.Constants;
import com.real.simhotel.model.Hotel;


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
}
