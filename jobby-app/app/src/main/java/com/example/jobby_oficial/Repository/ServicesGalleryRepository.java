/*
 * Created by Guilherme Cruz
 * Last modified: 24/01/22, 20:36
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.jobby_oficial.Dao.ServicesGalleryDao;
import com.example.jobby_oficial.Database.SingletonRoomDatabase;
import com.example.jobby_oficial.Model.ServicesGallery;

import java.util.List;

public class ServicesGalleryRepository {

    public SingletonRoomDatabase database;
    private LiveData<List<ServicesGallery>> getAllServicesGallerys;

    public ServicesGalleryRepository(Application application){
        database = SingletonRoomDatabase.getInstance(application);
        getAllServicesGallerys = database.servicesGalleryDao().getAllServicesGallerys();
    }

    public void insert(List<ServicesGallery> servicesGalleryList){
        new ServicesGalleryRepository.InsertAsyncTask(database).execute(servicesGalleryList);
    }

    public LiveData<List<ServicesGallery>> getAllServicesGallerys(){
        return getAllServicesGallerys;
    }

    public static class InsertAsyncTask extends AsyncTask<List<ServicesGallery>, Void, Void> {
        private ServicesGalleryDao servicesGalleryDao;

        private InsertAsyncTask(SingletonRoomDatabase singletonRoomDatabase){
            this.servicesGalleryDao = singletonRoomDatabase.servicesGalleryDao();
        }

        @Override
        protected Void doInBackground(List<ServicesGallery>... lists) {
            servicesGalleryDao.deleteAll();
            servicesGalleryDao.insertServicesGallery(lists[0]);
            return null;
        }
    }
}
