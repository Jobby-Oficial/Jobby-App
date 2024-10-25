/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.ViewModel;

import static com.example.jobby_oficial.View.MainActivity.id_User;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
    private MutableLiveData<Favorite> favoriteLiveData;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        favoriteRepository = new FavoriteRepository(application);
        getAllFavorites = favoriteRepository.getAllFavorites();
        favoriteLiveData = new MutableLiveData<>();
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
                Log.e("Error ", "Favorite API: " + t);
                System.out.println("Error Favorite API: " + t);
                call.cancel();
            }
        });
    }

    public void makeApiCallCreateFavorites (JsonObject jsonObject){
        Call<Favorite> call = FavoriteRetroInstance.getFavorites().createFavorite(jsonObject);
        call.enqueue(new Callback<Favorite>() {
            @Override
            public void onResponse(Call<Favorite> call, Response<Favorite> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful()){
                    favoriteLiveData.postValue(response.body());
                    JsonObject jsonObject2 = new JsonObject();
                    jsonObject2.addProperty("user_id", id_User);
                    makeApiCallFavorites(jsonObject2);
                    System.out.println("Favorite Create API: " + response.body());
                }
                else
                    System.out.println("Error Favorite Create API");
                //createNewUserLiveData.postValue(null);
            }

            @Override
            public void onFailure(Call<Favorite> call, Throwable t) {
                Log.e("Error ", "Favorite Create API: " + t);
                System.out.println("Error Favorite Create API: " + t);
                call.cancel();
            }
        });
    }

    public void makeApiCallDeleteFavorites (int id){
        Call<Favorite> call = FavoriteRetroInstance.getFavorites().deleteFavorite(id);
        call.enqueue(new Callback<Favorite>() {
            @Override
            public void onResponse(Call<Favorite> call, Response<Favorite> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful()){
                    favoriteLiveData.postValue(response.body());
                    JsonObject jsonObject2 = new JsonObject();
                    jsonObject2.addProperty("user_id", id_User);
                    makeApiCallFavorites(jsonObject2);
                    System.out.println("Favorite Delete API: " + response.body());
                }
                else
                    System.out.println("Error Favorite Delete API");
                //createNewUserLiveData.postValue(null);
            }

            @Override
            public void onFailure(Call<Favorite> call, Throwable t) {
                Log.e("Error ", "Favorite Delete API: " + t);
                System.out.println("Error Favorite Delete API: " + t);
                call.cancel();
            }
        });
    }
}
