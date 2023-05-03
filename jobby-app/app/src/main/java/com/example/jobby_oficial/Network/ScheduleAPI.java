/*
 * Created by Guilherme Cruz
 * Last modified: 19/01/22, 03:01
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Network;

import static com.example.jobby_oficial.Network.ScheduleRetroInstance.API_KEY_SCHEDULE;

import com.example.jobby_oficial.Model.Schedule;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ScheduleAPI {

    @POST("schedules/client?access-token=" + API_KEY_SCHEDULE)
    Call<List<Schedule>> getSchedulesList(@Body JsonObject schedules);

    @POST("schedules?access-token=" + API_KEY_SCHEDULE)
    Call<Schedule> createSchedule(@Body JsonObject favorites);
}
