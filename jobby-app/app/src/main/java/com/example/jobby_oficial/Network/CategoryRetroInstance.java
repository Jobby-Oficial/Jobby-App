/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryRetroInstance {

    public  static String BASE_URL = "https://619787935953f10017d23dce.mockapi.io/api/v1/";

    public static Retrofit retrofit;

    public static Retrofit getRetrofitCategory(){
        if (retrofit == null){
            retrofit = new  Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
