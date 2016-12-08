package com.real.simhotel.data;

import com.real.simhotel.config.Constants;


import retrofit2.http.Field;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by liudan on 2016/12/7.
 */
public interface ApiService {


    @GET(Constants.API_URL_Login)
    Observable<Response<String>> login(@Field("name") String name, @Field("pwd") String pwd);
}
