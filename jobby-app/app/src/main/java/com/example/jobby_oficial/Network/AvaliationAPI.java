/*
 * Created by Guilherme Cruz
 * Last modified: 21/01/22, 17:54
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Network;

import static com.example.jobby_oficial.Network.AvaliationRetroInstance.API_KEY_AVALIATION;

import com.example.jobby_oficial.Model.Avaliation;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AvaliationAPI {

    @POST("avaliations/client?access-token=" + API_KEY_AVALIATION)
    Call<List<Avaliation>> getAvaliationsList(@Body JsonObject avaliations);
}
