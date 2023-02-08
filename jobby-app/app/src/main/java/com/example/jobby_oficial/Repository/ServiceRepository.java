/*
 * Created by Guilherme Cruz
 * Last modified: 25/12/21, 15:54
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.jobby_oficial.Dao.ServiceDao;
import com.example.jobby_oficial.Database.SingletonRoomDatabase;
import com.example.jobby_oficial.Model.Service;

import java.util.List;

public class ServiceRepository {

    public SingletonRoomDatabase database;
    private LiveData<List<Service>> getAllServices;

    public ServiceRepository(Application application){
        database = SingletonRoomDatabase.getInstance(application);
        getAllServices = database.serviceDao().getAllServices();
    }

    public void insert(List<Service> serviceList){
        new ServiceRepository.InsertAsyncTask(database).execute(serviceList);
    }

    public LiveData<List<Service>> getAllServices(){
        return getAllServices;
    }

    public static class InsertAsyncTask extends AsyncTask<List<Service>, Void, Void> {
        private ServiceDao serviceDao;

        private InsertAsyncTask(SingletonRoomDatabase singletonRoomDatabase){
            this.serviceDao = singletonRoomDatabase.serviceDao();
        }

        @Override
        protected Void doInBackground(List<Service>... lists) {
            serviceDao.deleteAll();
            serviceDao.insertService(lists[0]);
            return null;
        }
    }
}
