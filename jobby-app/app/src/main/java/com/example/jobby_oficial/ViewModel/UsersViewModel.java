/*
 * Created by Guilherme Cruz
 * Last modified: 25/12/21, 17:06
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jobby_oficial.Model.User;
import com.example.jobby_oficial.Model.Username;
import com.example.jobby_oficial.View.LoginActivity;
import com.example.jobby_oficial.Network.UsersRetroInstance;
import com.example.jobby_oficial.Repository.UsersRepository;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersViewModel extends AndroidViewModel {
    private UsersRepository usersRepository;
    private LiveData<List<User>> getAllUsers;
    private LiveData<List<Username>> getAllUsernames;
    private LoginActivity loginActivity = new LoginActivity();

    public UsersViewModel(@NonNull Application application) {
        super(application);
        usersRepository = new UsersRepository(application);
        getAllUsers = usersRepository.getAllUsers();
        getAllUsernames = usersRepository.getAllUsernames();
    }

    public void insert(User user){
        usersRepository.insert(user);
    }

    public void insertUsername(List<Username> username){
        usersRepository.insertUsername(username);
    }

    public LiveData<List<User>> getAllUsers(){
        return getAllUsers;
    }

    public LiveData<List<Username>> getAllUsernames(){
        return getAllUsernames;
    }

    public void makeApiCallUsers (JsonObject jsonObject){
        Call<User> call = UsersRetroInstance.getUsers().getUserList(jsonObject);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful() && response.code() != 404){
                    usersRepository.insert(response.body());
                    loginActivity.initSession(true);
                }
                else
                    loginActivity.initSession(false);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error ", "User API: " + t);
                System.out.println("Error User API: " + t);
                call.cancel();
            }
        });
    }

    public void makeApiCallUsernames (){
        Call<List<Username>> call = UsersRetroInstance.getUsers().getUsernameList();
        call.enqueue(new Callback<List<Username>>() {
            @Override
            public void onResponse(Call<List<Username>> call, Response<List<Username>> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful()){
                    usersRepository.insertUsername(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Username>> call, Throwable t) {
                Log.e("Error ", "Username API: " + t);
                System.out.println("Error Username API: " + t);
                call.cancel();
            }
        });
    }

    public void makeDeleteUsers (){
        usersRepository.delete();
    }
}
