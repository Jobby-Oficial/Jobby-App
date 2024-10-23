/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 21:04
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Network;

import static com.example.jobby_oficial.Network.JobStatusRetroInstance.API_KEY_JOB_STATUS;

import com.example.jobby_oficial.Model.JobStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JobStatusAPI {

    @GET("jobs-status?access-token=" + API_KEY_JOB_STATUS)
    Call<List<JobStatus>> getJobStatusList();
}
