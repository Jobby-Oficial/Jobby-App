/*
 * Created by Guilherme Cruz
 * Last modified: 25/12/21, 16:09
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.jobby_oficial.Model.Service;

import java.util.List;

@Dao
public interface ServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertService(List<Service> serviceList);

    @Delete
    void deleteServiceList(List<Service> serviceList);

    @Query("SELECT * FROM service_table ORDER BY id ASC")
    LiveData<List<Service>> getAllServices();

    @Query("DELETE FROM service_table")
    void deleteAll();
}
