/*
 * Created by Guilherme Cruz
 * Last modified: 24/01/22, 20:28
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "gallery_table")
public class ServicesGallery {

    @PrimaryKey()
    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("image")
    @ColumnInfo(name = "image")
    private String image;

    @SerializedName("service_id")
    @ColumnInfo(name = "service_id")
    private int service_id;

    public ServicesGallery(int id, String image, int service_id) {
        this.id = id;
        this.image = image;
        this.service_id = service_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    @Override
    public String toString() {
        return "ServicesGallery{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", service_id=" + service_id +
                '}';
    }
}
