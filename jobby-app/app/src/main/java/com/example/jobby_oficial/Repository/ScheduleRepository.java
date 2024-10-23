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

import com.example.jobby_oficial.Dao.FavoriteDao;
import com.example.jobby_oficial.Dao.ScheduleDao;
import com.example.jobby_oficial.Database.SingletonRoomDatabase;
import com.example.jobby_oficial.Model.Favorite;
import com.example.jobby_oficial.Model.Schedule;

import java.util.List;

public class ScheduleRepository {

    public SingletonRoomDatabase database;
    private LiveData<List<Schedule>> getAllSchedules;

    public ScheduleRepository(Application application){
        database = SingletonRoomDatabase.getInstance(application);
        getAllSchedules = database.scheduleDao().getAllSchedules();
    }

    public void insert(List<Schedule> scheduleList){
        new ScheduleRepository.InsertAsyncTask(database).execute(scheduleList);
    }

    public LiveData<List<Schedule>> getAllSchedules(){
        return getAllSchedules;
    }

    public static class InsertAsyncTask extends AsyncTask<List<Schedule>, Void, Void> {
        private ScheduleDao scheduleDao;

        private InsertAsyncTask(SingletonRoomDatabase singletonRoomDatabase){
            this.scheduleDao = singletonRoomDatabase.scheduleDao();
        }

        @Override
        protected Void doInBackground(List<Schedule>... lists) {
            scheduleDao.deleteAll();
            scheduleDao.insertSchedule(lists[0]);
            return null;
        }
    }
}
