/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.jobby_oficial.Dao.UserDao;
import com.example.jobby_oficial.Dao.UsernameDao;
import com.example.jobby_oficial.Database.SingletonRoomDatabase;
import com.example.jobby_oficial.Model.User;
import com.example.jobby_oficial.Model.Username;

import java.util.List;

public class UsersRepository {

    public SingletonRoomDatabase database;
    private LiveData<List<User>> getAllUsers;
    private LiveData<List<Username>> getAllUsernames;

    public UsersRepository(Application application){
        database = SingletonRoomDatabase.getInstance(application);
        getAllUsers = database.usersDao().getAllUsers();
        getAllUsernames = database.usernameDao().getAllUsernames();
    }

    public void insert(User user){
        new UsersRepository.InsertAsyncTask(database).execute(user);
    }

    public void insertUsername(List<Username> username){
        new UsersRepository.InsertUsernameAsyncTask(database).execute(username);
    }

    public void delete(){
        new UsersRepository.DeleteAsyncTask(database).execute();
    }

    public LiveData<List<User>> getAllUsers(){
        return getAllUsers;
    }

    public LiveData<List<Username>> getAllUsernames(){
        return getAllUsernames;
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

    public static class InsertUsernameAsyncTask extends AsyncTask<List<Username>, Void, Void> {
        private UsernameDao usernamesDao;

        private InsertUsernameAsyncTask(SingletonRoomDatabase singletonRoomDatabase){
            this.usernamesDao = singletonRoomDatabase.usernameDao();
        }

        @Override
        protected Void doInBackground(List<Username>... usernames) {
            usernamesDao.deleteAll();
            usernamesDao.insertUsername(usernames[0]);
            return null;
        }
    }
}
