/*
 * Created by Guilherme Cruz
 * Last modified: 25/12/21, 17:06
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersRetroInstance {

    public static final String BASE_URL = "http://10.0.2.2:21080/v1/";
    public static final String API_KEY_USER = "-5wg3Yy8DwgLORe0hQn-ZHW4AO8-wuB8";
    //http://jobbyapp.epizy.com/admin/v1/users?access-token=-5wg3Yy8DwgLORe0hQn-ZHW4AO8-wuB8
    //http://10.0.2.2:21080/v1/users/login?access-token=-5wg3Yy8DwgLORe0hQn-ZHW4AO8-wuB8

    public static Retrofit retrofit;

    public static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit getRetrofitUsers(){

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

    public static UserAPI getUsers(){
        UserAPI users = getRetrofitUsers().create(UserAPI.class);
        return users;
    }
}
