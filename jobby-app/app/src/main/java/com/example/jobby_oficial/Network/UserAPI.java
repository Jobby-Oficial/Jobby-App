/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Network;

import static com.example.jobby_oficial.Network.AvaliationRetroInstance.API_KEY_AVALIATION;
import static com.example.jobby_oficial.Network.UsersRetroInstance.API_KEY_USER;

import com.example.jobby_oficial.Model.Avaliation;
import com.example.jobby_oficial.Model.User;
import com.example.jobby_oficial.Model.Username;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {

    @POST("guests/login")
    Call<User> getUserList(@Body JsonObject users);

    @POST("guests/register")
    Call<User> createUser(@Body JsonObject users);

    @GET("users?access-token=" + API_KEY_USER)
    Call<List<Username>> getUsernameList();

    @GET("users/{id}?access-token=" + API_KEY_USER)
    Call<User> getUser(@Path("id") int id);

    @PUT("users/{id}/update-user?access-token=" + API_KEY_USER)
    Call<User> updateUser(@Path("id") int id, @Body JsonObject users);

    /*@POST("guests/register")
    Call<User> postUserList(@Body JsonObject user);*/

    //@Headers({"Accept:application/json", "Content-Type:application/json"})
    /*@POST("users/login?access-token=" + API_KEY)
    Call<List<User>> getUserList(@Body JsonObject users);*/
}
