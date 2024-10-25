/*
 * Created by Guilherme Cruz
 * Last modified: 24/01/22, 20:19
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

public class ServicesGalleryRetroInstance {

    public static final String BASE_URL = "http://10.0.2.2:21080/v1/";

    public static Retrofit retrofit;

    public static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit getRetrofitServicesGallery(){

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

    public static ServicesGalleryAPI getServicesGallery(){
        ServicesGalleryAPI servicesGallery = getRetrofitServicesGallery().create(ServicesGalleryAPI.class);
        return servicesGallery;
    }
}
