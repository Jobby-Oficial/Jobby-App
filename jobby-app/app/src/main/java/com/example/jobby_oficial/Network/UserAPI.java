/*
 * Created by Guilherme Cruz
 * Last modified: 25/12/21, 17:06
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.Network;

import static com.example.jobby_oficial.Network.UsersRetroInstance.API_KEY_USER;

import com.example.jobby_oficial.Model.User;
import com.example.jobby_oficial.Model.Username;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserAPI {

    @POST("guests/login")
    Call<User> getUserList(@Body JsonObject users);

    @GET("users?access-token=" + API_KEY_USER)
    Call<List<Username>> getUsernameList();

    /*@POST("guests/register")
    Call<User> postUserList(@Body JsonObject user);*/

    //@Headers({"Accept:application/json", "Content-Type:application/json"})
    /*@POST("users/login?access-token=" + API_KEY)
    Call<List<User>> getUserList(@Body JsonObject users);*/
}
