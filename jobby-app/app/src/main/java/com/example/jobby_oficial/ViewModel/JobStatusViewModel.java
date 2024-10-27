/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:45
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jobby_oficial.Model.JobStatus;
import com.example.jobby_oficial.Network.JobStatusRetroInstance;
import com.example.jobby_oficial.Repository.JobStatusRepository;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobStatusViewModel extends AndroidViewModel {
    private JobStatusRepository jobStatusRepository;
    private LiveData<List<JobStatus>> getAllJobStatus;

    public JobStatusViewModel(@NonNull Application application) {
        super(application);
        jobStatusRepository = new JobStatusRepository(application);
        getAllJobStatus = jobStatusRepository.getAllJobStatus();
    }

    public void insert(List<JobStatus> list){
        jobStatusRepository.insert(list);
    }

    public LiveData<List<JobStatus>> getAllJobStatus(){
        return getAllJobStatus;
    }

    public void makeApiCalJobStatus(){
        Call<List<JobStatus>> call = JobStatusRetroInstance.getJobStatus().getJobStatusList();
        call.enqueue(new Callback<List<JobStatus>>() {
            @Override
            public void onResponse(Call<List<JobStatus>> call, Response<List<JobStatus>> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful()){
                    jobStatusRepository.insert(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<JobStatus>> call, Throwable t) {
                Log.e("Error ", "JobStatus API: " + t);
                System.out.println("Error JobStatus API: " + t);
                call.cancel();
            }
        });
    }
}
