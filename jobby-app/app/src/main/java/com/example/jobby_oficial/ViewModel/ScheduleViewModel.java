/*
 * Created by Guilherme Cruz
 * Last modified: 19/01/22, 03:02
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
import com.example.jobby_oficial.Model.Schedule;
import com.example.jobby_oficial.Network.FavoriteRetroInstance;
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
    private MutableLiveData<Schedule> scheduleLiveData;

    public ScheduleViewModel(@NonNull Application application) {
        super(application);
        scheduleRepository = new ScheduleRepository(application);
        getAllSchedules = scheduleRepository.getAllSchedules();
        scheduleLiveData = new MutableLiveData<>();
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

    public void makeApiCallCreateSchedule (JsonObject jsonObject){
        Call<Schedule> call = ScheduleRetroInstance.getSchedules().createSchedule(jsonObject);
        call.enqueue(new Callback<Schedule>() {
            @Override
            public void onResponse(Call<Schedule> call, Response<Schedule> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful()){
                    scheduleLiveData.postValue(response.body());
                    JsonObject joSchedule = new JsonObject();
                    joSchedule.addProperty("client_id", id_User);
                    makeApiCallSchedules(joSchedule);
                    System.out.println("Schedule Create API: " + response.body());
                }
                else
                    System.out.println("Error Schedule Create API");
            }

            @Override
            public void onFailure(Call<Schedule> call, Throwable t) {
                Log.e("Error ", "Schedule Create API: " + t);
                System.out.println("Error Schedule Create API: " + t);
                call.cancel();
            }
        });
    }
}
