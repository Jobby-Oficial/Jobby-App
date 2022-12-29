/*
 * Copyright (c) $year. Guilherme Cruz
 */

package com.example.jobby_oficial.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.jobby_oficial.Model.Users;

import java.util.List;

@Dao
public interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(Users users);

    @Update
    void updateUser(Users users);

    @Delete
    void deleteUser(Users users);

    @Query("SELECT * FROM users_table ORDER BY id DESC")
    LiveData<List<Users>> getAllUsers();
}
