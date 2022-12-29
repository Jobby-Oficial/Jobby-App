/*
 * Copyright (c) $year. Guilherme Cruz
 */

package com.example.jobby_oficial.Network;

import com.example.jobby_oficial.Model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIServiceCategory {

    @GET("categories")
    Call<List<Category>> getCategoryList();
}
