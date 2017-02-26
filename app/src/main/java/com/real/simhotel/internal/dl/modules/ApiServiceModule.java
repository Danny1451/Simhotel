package com.real.simhotel.internal.dl.modules;

import com.real.simhotel.config.Constants;
import com.real.simhotel.data.ApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liudan on 2016/12/7.
 */
@Module
public class ApiServiceModule {

    private final String baseUrl;

    public ApiServiceModule(String url){
        baseUrl = url;
    }


    @Provides
    @Singleton
    protected OkHttpClient provideClient() {
        return new OkHttpClient.Builder().connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS).addInterceptor(new HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY)).build();
    }

    @Provides
    @Singleton
    protected RxJavaCallAdapterFactory provideRxJavaCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    @Provides
    @Singleton
    protected GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    protected ApiService provideApiService(RxJavaCallAdapterFactory rxJavaCallAdapterFactory, GsonConverterFactory gsonConverterFactory, OkHttpClient client) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .client(client)
                .baseUrl(baseUrl)
                .build()
                .create(ApiService.class);
    }
}
