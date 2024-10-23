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

import com.example.jobby_oficial.Model.Avaliation;
import com.example.jobby_oficial.Model.Favorite;
import com.example.jobby_oficial.Network.AvaliationRetroInstance;
import com.example.jobby_oficial.Network.FavoriteRetroInstance;
import com.example.jobby_oficial.Repository.AvaliationRepository;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvaliationViewModel extends AndroidViewModel {
    private AvaliationRepository avaliationRepository;
    private LiveData<List<Avaliation>> getAllAvaliations;
    private MutableLiveData<Avaliation> avaliationLiveData;

    public AvaliationViewModel(@NonNull Application application) {
        super(application);
        avaliationRepository = new AvaliationRepository(application);
        getAllAvaliations = avaliationRepository.getAllAvaliations();
        avaliationLiveData = new MutableLiveData<>();
    }

    public void insert(List<Avaliation> list){
        avaliationRepository.insert(list);
    }

    public LiveData<List<Avaliation>> getAllAvaliations(){
        return getAllAvaliations;
    }

    public void makeApiCallAvaliations(JsonObject jsonObject){
        Call<List<Avaliation>> call = AvaliationRetroInstance.getAvaliations().getAvaliationsList(jsonObject);
        call.enqueue(new Callback<List<Avaliation>>() {
            @Override
            public void onResponse(Call<List<Avaliation>> call, Response<List<Avaliation>> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful()){
                    avaliationRepository.insert(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Avaliation>> call, Throwable t) {
                Log.e("Error ", "Avaliation API: " + t);
                System.out.println("Error Avaliation API: " + t);
                call.cancel();
            }
        });
    }

    public void makeApiCallCreateAvaliations (JsonObject jsonObject){
        Call<Avaliation> call = AvaliationRetroInstance.getAvaliations().createAvaliation(jsonObject);
        call.enqueue(new Callback<Avaliation>() {
            @Override
            public void onResponse(Call<Avaliation> call, Response<Avaliation> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful()){
                    avaliationLiveData.postValue(response.body());
                    JsonObject joAvaliation = new JsonObject();
                    joAvaliation.addProperty("user_id", id_User);
                    makeApiCallAvaliations(joAvaliation);
                    System.out.println("Avaliation Create API: " + response.body());
                }
                else
                    System.out.println("Error Avaliation Create API");
            }

            @Override
            public void onFailure(Call<Avaliation> call, Throwable t) {
                Log.e("Error ", "Avaliation Create API: " + t);
                System.out.println("Error Avaliation Create API: " + t);
                call.cancel();
            }
        });
    }

    public void makeApiCallDeleteAvaliations (int id){
        Call<Avaliation> call = AvaliationRetroInstance.getAvaliations().deleteAvaliation(id);
        call.enqueue(new Callback<Avaliation>() {
            @Override
            public void onResponse(Call<Avaliation> call, Response<Avaliation> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful()){
                    avaliationLiveData.postValue(response.body());
                    JsonObject joAvaliation = new JsonObject();
                    joAvaliation.addProperty("user_id", id_User);
                    makeApiCallAvaliations(joAvaliation);
                    System.out.println("Avaliation Delete API: " + response.body());
                }
                else
                    System.out.println("Error Avaliation Delete API");
            }

            @Override
            public void onFailure(Call<Avaliation> call, Throwable t) {
                Log.e("Error ", "Avaliation Delete API: " + t);
                System.out.println("Error Avaliation Delete API: " + t);
                call.cancel();
            }
        });
    }

    public void makeApiCallUpdateAvaliations (int id, JsonObject jsonObject){
        Call<Avaliation> call = AvaliationRetroInstance.getAvaliations().updateAvaliation(id,jsonObject);
        call.enqueue(new Callback<Avaliation>() {
            @Override
            public void onResponse(Call<Avaliation> call, Response<Avaliation> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful()){
                    avaliationLiveData.postValue(response.body());
                    JsonObject joAvaliation = new JsonObject();
                    joAvaliation.addProperty("user_id", id_User);
                    makeApiCallAvaliations(joAvaliation);
                    System.out.println("Avaliation Update API: " + response.body());
                }
                else
                    System.out.println("Error Avaliation Update API");
            }

            @Override
            public void onFailure(Call<Avaliation> call, Throwable t) {
                Log.e("Error ", "Avaliation Update API: " + t);
                System.out.println("Error Avaliation Update API: " + t);
                call.cancel();
            }
        });
    }
}
