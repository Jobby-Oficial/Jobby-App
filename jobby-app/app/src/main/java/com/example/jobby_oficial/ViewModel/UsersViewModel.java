/*
 * Created by Guilherme Cruz
 * Last modified: 04/12/21, 12:32
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jobby_oficial.Model.Users;
import com.example.jobby_oficial.Repository.UsersRepository;

import java.util.List;

public class UsersViewModel extends AndroidViewModel {
    private UsersRepository repository;
    private LiveData<List<Users>> allUsers;

    public UsersViewModel(@NonNull Application application) {
        super(application);
        repository = new UsersRepository(application);
        allUsers = repository.getAllUsers();
    }

    public void insert(Users users){
        repository.insert(users);
    }

    public void update(Users users){
        repository.update(users);
    }

    public void delete(Users users){
        repository.delete(users);
    }

    public LiveData<List<Users>> getAllUsers() {
        return allUsers;
    }
}
