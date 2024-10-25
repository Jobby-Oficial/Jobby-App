/*
 * Created by Guilherme Cruz
 * Last modified: 24/01/22, 20:32
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

import com.example.jobby_oficial.Model.Favorite;
import com.example.jobby_oficial.Model.ServicesGallery;

import java.util.List;

@Dao
public interface ServicesGalleryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertServicesGallery(List<ServicesGallery> galleryList);

    @Delete
    void deleteServicesGalleryList(List<ServicesGallery> galleryList);

    @Query("SELECT * FROM gallery_table ORDER BY id DESC")
    LiveData<List<ServicesGallery>> getAllServicesGallerys();

    @Query("DELETE FROM gallery_table")
    void deleteAll();
}
