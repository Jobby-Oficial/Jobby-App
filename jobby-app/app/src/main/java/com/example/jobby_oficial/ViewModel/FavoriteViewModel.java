/*
 * Created by Guilherme Cruz
 * Last modified: 14/01/22, 09:13
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jobby_oficial.Model.Favorite;
import com.example.jobby_oficial.Model.Service;
import com.example.jobby_oficial.Model.User;
import com.example.jobby_oficial.Network.FavoriteAPI;
import com.example.jobby_oficial.Network.FavoriteRetroInstance;
import com.example.jobby_oficial.Network.ServiceAPI;
import com.example.jobby_oficial.Network.ServiceRetroInstance;
import com.example.jobby_oficial.Network.UsersRetroInstance;
import com.example.jobby_oficial.Repository.FavoriteRepository;
import com.example.jobby_oficial.Repository.ServiceRepository;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteRepository favoriteRepository;
    private LiveData<List<Favorite>> getAllFavorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        favoriteRepository = new FavoriteRepository(application);
        getAllFavorites = favoriteRepository.getAllFavorites();
    }

    public void insert(List<Favorite> list){
        favoriteRepository.insert(list);
    }

    public LiveData<List<Favorite>> getAllFavorites(){
        return getAllFavorites;
    }

    public void makeApiCallFavorites(JsonObject jsonObject){
        //FavoriteAPI apiFavorite = FavoriteRetroInstance.getRetrofitFavorites().create(FavoriteAPI.class);
        Call<List<Favorite>> call = FavoriteRetroInstance.getFavorites().getFavoritesList(jsonObject);
        call.enqueue(new Callback<List<Favorite>>() {
            @Override
            public void onResponse(Call<List<Favorite>> call, Response<List<Favorite>> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful()){
                    favoriteRepository.insert(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Favorite>> call, Throwable t) {
                Log.e("Error ", "Service API: " + t);
                System.out.println("Error Service API: " + t);
            }
        });
    }
}
