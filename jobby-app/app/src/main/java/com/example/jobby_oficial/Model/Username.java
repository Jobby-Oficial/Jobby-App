/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "username_table")
public class Username {

    @PrimaryKey()
    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("username")
    @ColumnInfo(name = "username")
    private String username;

    public Username(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Username{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
