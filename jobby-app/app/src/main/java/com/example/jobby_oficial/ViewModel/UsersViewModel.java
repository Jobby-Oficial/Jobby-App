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
import com.example.jobby_oficial.Model.User;
import com.example.jobby_oficial.Model.Username;
import com.example.jobby_oficial.Network.AvaliationRetroInstance;
import com.example.jobby_oficial.View.LoginActivity;
import com.example.jobby_oficial.Network.UsersRetroInstance;
import com.example.jobby_oficial.Repository.UsersRepository;
import com.example.jobby_oficial.View.ProfileEditActivity;
import com.example.jobby_oficial.View.RegisterStepFourActivity;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersViewModel extends AndroidViewModel {
    private UsersRepository usersRepository;
    private LiveData<List<User>> getAllUsers;
    private LiveData<List<Username>> getAllUsernames;
    private MutableLiveData<User> userLiveData;
    private LoginActivity loginActivity = new LoginActivity();
    private RegisterStepFourActivity registerActivity = new RegisterStepFourActivity();
    private ProfileEditActivity profileEditActivity = new ProfileEditActivity();

    public UsersViewModel(@NonNull Application application) {
        super(application);
        usersRepository = new UsersRepository(application);
        getAllUsers = usersRepository.getAllUsers();
        getAllUsernames = usersRepository.getAllUsernames();
        userLiveData = new MutableLiveData<>();
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
                if (response.isSuccessful() && response.code() != 404) {
                    usersRepository.insert(response.body());
                    loginActivity.initSessionLogin(true);
                } else {
                    loginActivity.initSessionLogin(false);
                }
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

    public void makeApiCallRegister (JsonObject jsonObject){
        Call<User> call = UsersRetroInstance.getUsers().createUser(jsonObject);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful() && response.code() != 404) {
                    userLiveData.postValue(response.body());
                    registerActivity.initSessionRegister(true);
                } else {
                    registerActivity.initSessionRegister(false);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error ", "User API: " + t);
                System.out.println("Error User API: " + t);
                call.cancel();
            }
        });
    }

    public void makeApiCallUpdateProfile (int id, JsonObject jsonObject){
        Call<User> call = UsersRetroInstance.getUsers().updateUser(id,jsonObject);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful()) {
                    userLiveData.postValue(response.body());
                    makeApiCallUserID(id);
                    System.out.println("User Update API: " + response.body());
                } else
                    System.out.println("Error User Update API");
            }

            public void makeApiCallUserID (int id){
                Call<User> call = UsersRetroInstance.getUsers().getUser(id);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        System.out.println("resp: " + response);
                        if (response.isSuccessful()) {
                            usersRepository.insert(response.body());
                            profileEditActivity.initProfile(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("Error ", "User API: " + t);
                        System.out.println("Error User API: " + t);
                        call.cancel();
                    }
                });
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error ", "User Update API: " + t);
                System.out.println("Error User Update API: " + t);
                call.cancel();
            }
        });
    }

    public void makeDeleteUsers (){
        usersRepository.delete();
    }
}
