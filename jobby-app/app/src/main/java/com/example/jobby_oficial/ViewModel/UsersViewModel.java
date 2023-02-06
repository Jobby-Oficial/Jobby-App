/*
 * Created by Guilherme Cruz
 * Last modified: 04/12/21, 12:32
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jobby_oficial.View.LoginActivity;
import com.example.jobby_oficial.Model.Users;
import com.example.jobby_oficial.Network.UsersRetroInstance;
import com.example.jobby_oficial.Repository.UsersRepository;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersViewModel extends AndroidViewModel {
    private UsersRepository usersRepository;
    private LiveData<List<Users>> getAllUsers;
    private LoginActivity loginActivity = new LoginActivity();

    public UsersViewModel(@NonNull Application application) {
        super(application);
        usersRepository = new UsersRepository(application);
        getAllUsers = usersRepository.getAllUsers();
    }

    public void insert(List<Users> list){
        usersRepository.insert(list);
    }

    public LiveData<List<Users>> getAllUsers(){
        return getAllUsers;
    }

    public void makeApiCallUsers (JsonObject jsonObject){
        Call<List<Users>> call = UsersRetroInstance.getUsersService().getUsersList(jsonObject);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful() && response.code() != 404){
                    usersRepository.insert(response.body());
                    loginActivity.initSession(true);
                }
                else
                    loginActivity.initSession(false);
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Log.e("Error ", "User API: " + t);
                System.out.println("Error User API: " + t);
                call.cancel();
            }
        });
    }
}
