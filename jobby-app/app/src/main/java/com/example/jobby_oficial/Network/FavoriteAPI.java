/*
 * Created by Guilherme Cruz
 * Last modified: 14/01/22, 08:51
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Network;

import static com.example.jobby_oficial.Network.FavoriteRetroInstance.API_KEY_FAVORITE;

import androidx.room.Delete;

import com.example.jobby_oficial.Model.Favorite;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FavoriteAPI {

    @POST("favorites/detail?access-token=" + API_KEY_FAVORITE)
    Call<List<Favorite>> getFavoritesList(@Body JsonObject favorites);

    @POST("favorites?access-token=" + API_KEY_FAVORITE)
    Call<Favorite> createFavorite(@Body JsonObject favorites);

    @DELETE("favorites/{id}?access-token=" + API_KEY_FAVORITE)
    Call<Favorite> deleteFavorite(@Path ("id") int id);
}
