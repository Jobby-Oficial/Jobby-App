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

import com.example.jobby_oficial.Dao.AvaliationDao;
import com.example.jobby_oficial.Dao.ScheduleDao;
import com.example.jobby_oficial.Database.SingletonRoomDatabase;
import com.example.jobby_oficial.Model.Avaliation;
import com.example.jobby_oficial.Model.Schedule;

import java.util.List;

public class AvaliationRepository {

    public SingletonRoomDatabase database;
    private LiveData<List<Avaliation>> getAllAvaliations;

    public AvaliationRepository(Application application){
        database = SingletonRoomDatabase.getInstance(application);
        getAllAvaliations = database.avaliationDao().getAllAvaliations();
    }

    public void insert(List<Avaliation> avaliationList){
        new AvaliationRepository.InsertAsyncTask(database).execute(avaliationList);
    }

    public LiveData<List<Avaliation>> getAllAvaliations(){
        return getAllAvaliations;
    }

    public static class InsertAsyncTask extends AsyncTask<List<Avaliation>, Void, Void> {
        private AvaliationDao avaliationDao;

        private InsertAsyncTask(SingletonRoomDatabase singletonRoomDatabase){
            this.avaliationDao = singletonRoomDatabase.avaliationDao();
        }

        @Override
        protected Void doInBackground(List<Avaliation>... lists) {
            avaliationDao.deleteAll();
            avaliationDao.insertAvaliation(lists[0]);
            return null;
        }
    }
}
