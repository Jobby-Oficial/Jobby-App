/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScheduleRetroInstance {
    public static final String BASE_URL = "http://192.168.1.244/jobby-web/backend/web/v1/";
    public static final String API_KEY_SCHEDULE = "-5wg3Yy8DwgLORe0hQn-ZHW4AO8-wuB8";

    public static Retrofit retrofit;

    public static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit getRetrofitSchedules(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient clients = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        if (retrofit == null){
            retrofit = new  Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(clients)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static ScheduleAPI getSchedules(){
        ScheduleAPI Schedules = getRetrofitSchedules().create(ScheduleAPI.class);
        return Schedules;
    }
}
