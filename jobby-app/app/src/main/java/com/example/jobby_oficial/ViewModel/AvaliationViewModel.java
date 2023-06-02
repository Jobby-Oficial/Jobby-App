/*
 * Created by Guilherme Cruz
 * Last modified: 21/01/22, 18:17
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jobby_oficial.Model.Avaliation;
import com.example.jobby_oficial.Network.AvaliationRetroInstance;
import com.example.jobby_oficial.Repository.AvaliationRepository;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvaliationViewModel extends AndroidViewModel {
    private AvaliationRepository avaliationRepository;
    private LiveData<List<Avaliation>> getAllAvaliations;

    public AvaliationViewModel(@NonNull Application application) {
        super(application);
        avaliationRepository = new AvaliationRepository(application);
        getAllAvaliations = avaliationRepository.getAllAvaliations();
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
}
