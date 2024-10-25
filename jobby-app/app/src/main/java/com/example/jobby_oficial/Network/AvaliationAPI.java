/*
 * Created by Guilherme Cruz
 * Last modified: 21/01/22, 17:54
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Network;

import static com.example.jobby_oficial.Network.AvaliationRetroInstance.API_KEY_AVALIATION;

import com.example.jobby_oficial.Model.Avaliation;
import com.example.jobby_oficial.Model.Favorite;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AvaliationAPI {

    @POST("avaliations/client?access-token=" + API_KEY_AVALIATION)
    Call<List<Avaliation>> getAvaliationsList(@Body JsonObject avaliations);

    @POST("avaliations?access-token=" + API_KEY_AVALIATION)
    Call<Avaliation> createAvaliation(@Body JsonObject avaliations);

    @PUT("avaliations/{id}?access-token=" + API_KEY_AVALIATION)
    Call<Avaliation> updateAvaliation(@Path("id") int id, @Body JsonObject avaliations);

    @DELETE("avaliations/{id}?access-token=" + API_KEY_AVALIATION)
    Call<Avaliation> deleteAvaliation(@Path("id") int id);
}
