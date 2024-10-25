/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.jobby_oficial.Model.JobStatus;
import com.example.jobby_oficial.Model.Schedule;

import java.util.List;

@Dao
public interface JobStatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJobStatus(List<JobStatus> jobStatusList);

    @Delete
    void deleteJobStatusList(List<JobStatus> jobStatusList);

    @Query("SELECT * FROM job_status_table ORDER BY id ASC")
    LiveData<List<JobStatus>> getAllJobStatus();

    @Query("DELETE FROM job_status_table")
    void deleteAll();
}
