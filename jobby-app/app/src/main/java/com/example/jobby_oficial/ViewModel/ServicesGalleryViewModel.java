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
import com.example.jobby_oficial.Model.ServicesGallery;
import com.example.jobby_oficial.Network.ServiceAPI;
import com.example.jobby_oficial.Network.ServiceRetroInstance;
import com.example.jobby_oficial.Network.ServicesGalleryAPI;
import com.example.jobby_oficial.Network.ServicesGalleryRetroInstance;
import com.example.jobby_oficial.Repository.ServicesGalleryRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesGalleryViewModel extends AndroidViewModel {
    private ServicesGalleryRepository servicesGalleryRepository;
    private LiveData<List<ServicesGallery>> getAllServicesGallerys;

    public ServicesGalleryViewModel(@NonNull Application application) {
        super(application);
        servicesGalleryRepository = new ServicesGalleryRepository(application);
        getAllServicesGallerys = servicesGalleryRepository.getAllServicesGallerys();
    }

    public void insert(List<ServicesGallery> list){
        servicesGalleryRepository.insert(list);
    }

    public LiveData<List<ServicesGallery>> getAllServicesGallerys(){
        return getAllServicesGallerys;
    }

    public void makeApiCallServicesGallerys(){
        ServicesGalleryAPI apiServicesGallery = ServicesGalleryRetroInstance.getRetrofitServicesGallery().create(ServicesGalleryAPI.class);
        Call<List<ServicesGallery>> call = apiServicesGallery.getServicesGalleryList();
        call.enqueue(new Callback<List<ServicesGallery>>() {
            @Override
            public void onResponse(Call<List<ServicesGallery>> call, Response<List<ServicesGallery>> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful()){
                    servicesGalleryRepository.insert(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ServicesGallery>> call, Throwable t) {
                Log.e("Error ", "Services Gallery API: " + t);
                System.out.println("Error Services Gallery API: " + t);
            }
        });
    }
}
