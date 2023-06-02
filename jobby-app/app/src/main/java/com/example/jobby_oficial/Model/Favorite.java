/*
 * Created by Guilherme Cruz
 * Last modified: 14/01/22, 08:57
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "favorite_table")
public class Favorite {

    @PrimaryKey()
    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("service_id")
    @ColumnInfo(name = "service_id")
    private int service_id;

    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    private int user_id;

    public Favorite(int id, int service_id, int user_id) {
        this.id = id;
        this.service_id = service_id;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", service_id=" + service_id +
                ", user_id=" + user_id +
                '}';
    }
}
