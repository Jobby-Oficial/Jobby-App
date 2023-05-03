/*
 * Created by Guilherme Cruz
 * Last modified: 25/12/21, 17:12
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.jobby_oficial.Dao.FavoriteDao;
import com.example.jobby_oficial.Dao.UserDao;
import com.example.jobby_oficial.Database.SingletonRoomDatabase;
import com.example.jobby_oficial.Model.User;

import java.util.List;

public class UsersRepository {

    public SingletonRoomDatabase database;
    private LiveData<List<User>> getAllUsers;

    public UsersRepository(Application application){
        database = SingletonRoomDatabase.getInstance(application);
        getAllUsers = database.usersDao().getAllUsers();
    }

    public void insert(User user){
        new UsersRepository.InsertAsyncTask(database).execute(user);
    }

    public void delete(){
        new UsersRepository.DeleteAsyncTask(database).execute();
    }

    public LiveData<List<User>> getAllUsers(){
        return getAllUsers;
    }

    public static class InsertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao usersDao;

        private InsertAsyncTask(SingletonRoomDatabase singletonRoomDatabase){
            this.usersDao = singletonRoomDatabase.usersDao();
        }

        @Override
        protected Void doInBackground(User... users) {
            usersDao.deleteAll();
            usersDao.insertUser(users[0]);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao usersDao;

        private DeleteAsyncTask(SingletonRoomDatabase singletonRoomDatabase){
            this.usersDao = singletonRoomDatabase.usersDao();
        }

        @Override
        protected Void doInBackground(User... lists) {
            usersDao.deleteAll();
            return null;
        }
    }
}
