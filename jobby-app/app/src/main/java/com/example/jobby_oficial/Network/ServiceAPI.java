/*
 * Created by Guilherme Cruz
 * Last modified: 25/12/21, 17:09
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.Network;

import static com.example.jobby_oficial.Network.ServiceRetroInstance.API_KEY_SERVICE;

import com.example.jobby_oficial.Model.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceAPI {

    @GET("guests/get-services")
    Call<List<Service>> getServiceList();


    /*@GET("services?access-token=" + API_KEY)
    Call<List<Service>> getServiceList();*/
}
