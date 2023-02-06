/*
 * Created by Guilherme Cruz
 * Last modified: 04/12/21, 12:32
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
import androidx.room.Update;

import com.example.jobby_oficial.Model.Category;
import com.example.jobby_oficial.Model.Users;

import java.util.List;

@Dao
public interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(List<Users> usersList);

    @Delete
    void deleteUsers(List<Users> usersList);

    @Query("SELECT * FROM users_table ORDER BY id ASC")
    LiveData<List<Users>> getAllUsers();

    @Query("DELETE FROM users_table")
    void deleteAll();
}
