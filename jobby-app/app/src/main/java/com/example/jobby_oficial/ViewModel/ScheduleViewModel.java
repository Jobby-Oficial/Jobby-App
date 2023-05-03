/*
 * Created by Guilherme Cruz
 * Last modified: 19/01/22, 03:02
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jobby_oficial.Model.Schedule;
import com.example.jobby_oficial.Network.ScheduleRetroInstance;
import com.example.jobby_oficial.Repository.ScheduleRepository;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleViewModel extends AndroidViewModel {
    private ScheduleRepository scheduleRepository;
    private LiveData<List<Schedule>> getAllSchedules;

    public ScheduleViewModel(@NonNull Application application) {
        super(application);
        scheduleRepository = new ScheduleRepository(application);
        getAllSchedules = scheduleRepository.getAllSchedules();
    }

    public void insert(List<Schedule> list){
        scheduleRepository.insert(list);
    }

    public LiveData<List<Schedule>> getAllSchedules(){
        return getAllSchedules;
    }

    public void makeApiCallSchedules(JsonObject jsonObject){
        Call<List<Schedule>> call = ScheduleRetroInstance.getSchedules().getSchedulesList(jsonObject);
        call.enqueue(new Callback<List<Schedule>>() {
            @Override
            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful()){
                    scheduleRepository.insert(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Schedule>> call, Throwable t) {
                Log.e("Error ", "Schedule API: " + t);
                System.out.println("Error Schedule API: " + t);
                call.cancel();
            }
        });
    }
}
