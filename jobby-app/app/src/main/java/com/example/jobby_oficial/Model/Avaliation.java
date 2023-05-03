/*
 * Created by Guilherme Cruz
 * Last modified: 21/01/22, 18:08
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "avaliation_table")
public class Avaliation {

    @PrimaryKey()
    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("avaliation")
    @ColumnInfo(name = "avaliation")
    private double avaliation;

    @SerializedName("service_id")
    @ColumnInfo(name = "service_id")
    private int service_id;

    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    private int user_id;

    public Avaliation(int id, double avaliation, int service_id, int user_id) {
        this.id = id;
        this.avaliation = avaliation;
        this.service_id = service_id;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAvaliation() {
        return avaliation;
    }

    public void setAvaliation(double avaliation) {
        this.avaliation = avaliation;
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
        return "Avaliation{" +
                "id=" + id +
                ", avaliation=" + avaliation +
                ", service_id=" + service_id +
                ", user_id=" + user_id +
                '}';
    }
}
