/*
 * Created by Guilherme Cruz
 * Last modified: 21/12/21, 19:09
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.Network;

import static com.example.jobby_oficial.Network.UsersRetroInstance.API_KEY;

import com.example.jobby_oficial.Model.Users;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIServiceUsers {

    @POST("users/login?access-token=" + API_KEY)
    Call<List<Users>> getUsersList(@Body JsonObject users);


}
