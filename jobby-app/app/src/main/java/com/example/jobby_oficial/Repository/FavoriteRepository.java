/*
 * Created by Guilherme Cruz
 * Last modified: 14/01/22, 09:16
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.jobby_oficial.Dao.FavoriteDao;
import com.example.jobby_oficial.Dao.ServiceDao;
import com.example.jobby_oficial.Database.SingletonRoomDatabase;
import com.example.jobby_oficial.Model.Favorite;
import com.example.jobby_oficial.Model.Service;

import java.util.List;

public class FavoriteRepository {

    public SingletonRoomDatabase database;
    private LiveData<List<Favorite>> getAllFavorites;

    public FavoriteRepository(Application application){
        database = SingletonRoomDatabase.getInstance(application);
        getAllFavorites = database.favoriteDao().getAllFavorites();
    }

    public void insert(List<Favorite> favoriteList){
        new FavoriteRepository.InsertAsyncTask(database).execute(favoriteList);
    }

    public LiveData<List<Favorite>> getAllFavorites(){
        return getAllFavorites;
    }

    public static class InsertAsyncTask extends AsyncTask<List<Favorite>, Void, Void> {
        private FavoriteDao favoriteDao;

        private InsertAsyncTask(SingletonRoomDatabase singletonRoomDatabase){
            this.favoriteDao = singletonRoomDatabase.favoriteDao();
        }

        @Override
        protected Void doInBackground(List<Favorite>... lists) {
            favoriteDao.deleteAll();
            favoriteDao.insertFavorite(lists[0]);
            return null;
        }
    }
}
