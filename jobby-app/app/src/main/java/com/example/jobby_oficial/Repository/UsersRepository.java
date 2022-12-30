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
    private UsersDao usersDao;
    private LiveData<List<Users>> allUsers;

    public UsersRepository(Application application){
        SingletonRoomDatabase database = SingletonRoomDatabase.getInstance(application);
        usersDao = database.usersDao();
        allUsers = usersDao.getAllUsers();
    }

    public void insert(Users users){
        new InsertAsyncTask(usersDao).execute(users);
    }

    public void update(Users users){
        new UpdateAsyncTask(usersDao).execute(users);
    }

    public void delete(Users users){
        new DeleteAsyncTask(usersDao).execute(users);
    }

    public LiveData<List<Users>> getAllUsers() {
        return allUsers;
    }

    public static class InsertAsyncTask extends AsyncTask<Users, Void, Void> {
        private UsersDao usersDao;

        private InsertAsyncTask(UsersDao usersDao){
            this.usersDao = usersDao;
        }

        @Override
        protected Void doInBackground(Users... users) {
            usersDao.insertUser(users[0]);
            return null;
        }
    }

    public static class UpdateAsyncTask extends AsyncTask<Users, Void, Void> {
        private UsersDao usersDao;

        private UpdateAsyncTask(UsersDao usersDao){
            this.usersDao = usersDao;
        }

        @Override
        protected Void doInBackground(Users... users) {
            usersDao.updateUser(users[0]);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<Users, Void, Void> {
        private UsersDao usersDao;

        private DeleteAsyncTask(UsersDao usersDao){
            this.usersDao = usersDao;
        }

        @Override
        protected Void doInBackground(Users... users) {
            usersDao.deleteUser(users[0]);
            return null;
        }
    }
}
