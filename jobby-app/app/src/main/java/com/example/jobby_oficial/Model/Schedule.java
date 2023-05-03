/*
 * Created by Guilherme Cruz
 * Last modified: 19/01/22, 03:03
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "schedule_table")
public class Schedule {

    @PrimaryKey()
    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("service_date")
    @ColumnInfo(name = "service_date")
    private String service_date;

    @SerializedName("service_time")
    @ColumnInfo(name = "service_time")
    private String service_time;

    @SerializedName("note")
    @ColumnInfo(name = "note")
    private String note;

    @SerializedName("payment")
    @ColumnInfo(name = "payment")
    private int payment;

    @SerializedName("schedule_status")
    @ColumnInfo(name = "schedule_status")
    private String schedule_status;

    @SerializedName("schedule_status_note")
    @ColumnInfo(name = "schedule_status_note")
    private String schedule_status_note;

    @SerializedName("price")
    @ColumnInfo(name = "price")
    private String price;

    @SerializedName("job_status_id")
    @ColumnInfo(name = "job_status_id")
    private int job_status_id;

    @SerializedName("service_id")
    @ColumnInfo(name = "service_id")
    private int service_id;

    @SerializedName("professional_id")
    @ColumnInfo(name = "professional_id")
    private int professional_id;

    @SerializedName("client_id")
    @ColumnInfo(name = "client_id")
    private int client_id;

    @SerializedName("username")
    @ColumnInfo(name = "username")
    private int username;

    public Schedule(int id, String service_date, String service_time, String note, int payment, String schedule_status, String schedule_status_note, String price, int job_status_id, int service_id, int professional_id, int client_id, int username) {
        this.id = id;
        this.service_date = service_date;
        this.service_time = service_time;
        this.note = note;
        this.payment = payment;
        this.schedule_status = schedule_status;
        this.schedule_status_note = schedule_status_note;
        this.price = price;
        this.job_status_id = job_status_id;
        this.service_id = service_id;
        this.professional_id = professional_id;
        this.client_id = client_id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getService_date() {
        return service_date;
    }

    public void setService_date(String service_date) {
        this.service_date = service_date;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getSchedule_status() {
        return schedule_status;
    }

    public void setSchedule_status(String schedule_status) {
        this.schedule_status = schedule_status;
    }

    public String getSchedule_status_note() {
        return schedule_status_note;
    }

    public void setSchedule_status_note(String schedule_status_note) {
        this.schedule_status_note = schedule_status_note;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getJob_status_id() {
        return job_status_id;
    }

    public void setJob_status_id(int job_status_id) {
        this.job_status_id = job_status_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getProfessional_id() {
        return professional_id;
    }

    public void setProfessional_id(int professional_id) {
        this.professional_id = professional_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getUsername() {
        return username;
    }

    public void setUsername(int username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", service_date='" + service_date + '\'' +
                ", service_time='" + service_time + '\'' +
                ", note='" + note + '\'' +
                ", payment=" + payment +
                ", schedule_status='" + schedule_status + '\'' +
                ", schedule_status_note='" + schedule_status_note + '\'' +
                ", price='" + price + '\'' +
                ", job_status_id=" + job_status_id +
                ", service_id=" + service_id +
                ", professional_id=" + professional_id +
                ", client_id=" + client_id +
                ", username=" + username +
                '}';
    }
}
