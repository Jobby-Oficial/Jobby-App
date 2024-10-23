/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jobby_oficial.Model.Service;
import com.example.jobby_oficial.Network.ServiceAPI;
import com.example.jobby_oficial.Network.ServiceRetroInstance;
import com.example.jobby_oficial.Repository.ServiceRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceViewModel extends AndroidViewModel {
    private ServiceRepository serviceRepository;
    private LiveData<List<Service>> getAllServices;

    public ServiceViewModel(@NonNull Application application) {
        super(application);
        serviceRepository = new ServiceRepository(application);
        getAllServices = serviceRepository.getAllServices();
    }

    public void insert(List<Service> list){
        serviceRepository.insert(list);
    }

    public LiveData<List<Service>> getAllServices(){
        return getAllServices;
    }

    public void makeApiCallServices(){
        ServiceAPI apiService = ServiceRetroInstance.getRetrofitService().create(ServiceAPI.class);
        Call<List<Service>> call = apiService.getServiceList();
        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful()){
                    serviceRepository.insert(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                Log.e("Error ", "Service API: " + t);
                System.out.println("Error Service API: " + t);
            }
        });
    }
}
