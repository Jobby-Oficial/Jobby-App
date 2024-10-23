/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:33
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.jobby_oficial.Dao.JobStatusDao;
import com.example.jobby_oficial.Database.SingletonRoomDatabase;
import com.example.jobby_oficial.Model.JobStatus;

import java.util.List;

public class JobStatusRepository {

    public SingletonRoomDatabase database;
    private LiveData<List<JobStatus>> getAllJobStatus;

    public JobStatusRepository(Application application){
        database = SingletonRoomDatabase.getInstance(application);
        getAllJobStatus = database.jobStatusDao().getAllJobStatus();
    }

    public void insert(List<JobStatus> jobStatusList){
        new JobStatusRepository.InsertAsyncTask(database).execute(jobStatusList);
    }

    public LiveData<List<JobStatus>> getAllJobStatus(){
        return getAllJobStatus;
    }

    public static class InsertAsyncTask extends AsyncTask<List<JobStatus>, Void, Void> {
        private JobStatusDao jobStatusDao;

        private InsertAsyncTask(SingletonRoomDatabase singletonRoomDatabase){
            this.jobStatusDao = singletonRoomDatabase.jobStatusDao();
        }

        @Override
        protected Void doInBackground(List<JobStatus>... lists) {
            jobStatusDao.deleteAll();
            jobStatusDao.insertJobStatus(lists[0]);
            return null;
        }
    }
}
