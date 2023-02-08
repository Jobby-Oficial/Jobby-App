/*
 * Created by Guilherme Cruz
 * Last modified: 04/12/21, 12:32
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.jobby_oficial.Dao.UsersDao;
import com.example.jobby_oficial.Database.SingletonRoomDatabase;
import com.example.jobby_oficial.Model.Users;

import java.util.List;

public class UsersRepository {
    public SingletonRoomDatabase database;
    private LiveData<List<Users>> getAllUsers;

    public UsersRepository(Application application){
        database = SingletonRoomDatabase.getInstance(application);
        getAllUsers = database.usersDao().getAllUsers();
    }

    public void insert(List<Users> usersList){
        new UsersRepository.InsertAsyncTask(database).execute(usersList);
    }

    public void delete(){
        new UsersRepository.DeleteAsyncTask(database).execute();
    }

    public LiveData<List<Users>> getAllUsers(){
        return getAllUsers;
    }

    public static class InsertAsyncTask extends AsyncTask<List<Users>, Void, Void> {
        private UsersDao usersDao;

        private InsertAsyncTask(SingletonRoomDatabase singletonRoomDatabase){
            this.usersDao = singletonRoomDatabase.usersDao();
        }

        @Override
        protected Void doInBackground(List<Users>... lists) {
            usersDao.deleteAll();
            usersDao.insertUsers(lists[0]);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<List<Users>, Void, Void> {
        private UsersDao usersDao;

        private DeleteAsyncTask(SingletonRoomDatabase singletonRoomDatabase){
            this.usersDao = singletonRoomDatabase.usersDao();
        }

        @Override
        protected Void doInBackground(List<Users>... lists) {
            usersDao.deleteAll();
            return null;
        }
    }
}
