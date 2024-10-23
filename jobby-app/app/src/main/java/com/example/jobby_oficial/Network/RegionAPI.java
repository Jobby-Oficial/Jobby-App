/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Network;

import com.example.jobby_oficial.Model.City;
import com.example.jobby_oficial.Model.CityData;
import com.example.jobby_oficial.Model.CountryData;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RegionAPI {

    @GET("countries")
    Call<CountryData> getCountrysList();

    @POST("countries/cities")
    Call<CityData> getCitysList(@Body JsonObject citys);
}
