/*
 * Created by Guilherme Cruz
 * Last modified: 04/12/21, 12:32
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "users_table")
public class Users {

    @PrimaryKey(/*autoGenerate = true*/)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("username")
    @ColumnInfo(name = "username")
    private String username;

    @SerializedName("password_hash")
    @ColumnInfo(name = "password")
    private String password;

    @SerializedName("picture")
    @ColumnInfo(name = "picture")
    private String picture;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("email")
    @ColumnInfo(name = "email")
    private String email;

    @SerializedName("phone")
    @ColumnInfo(name = "phone")
    private String phone;

    @SerializedName("genre")
    @ColumnInfo(name = "genre")
    private String genre;

    @SerializedName("birth")
    @ColumnInfo(name = "birth")
    private String birth;

    public Users(int id, String username, String password, String picture, String name, String email, String phone, String genre, String birth) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.picture = picture;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.genre = genre;
        this.birth = birth;
    }

    public Users() {

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", picture='" + picture + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", genre='" + genre + '\'' +
                ", birth='" + birth + '\'' +
                '}';
    }
}
