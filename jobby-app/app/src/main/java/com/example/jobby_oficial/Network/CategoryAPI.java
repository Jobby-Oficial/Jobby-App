/*
 * Created by Guilherme Cruz
 * Last modified: 25/12/21, 17:06
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.Network;

import com.example.jobby_oficial.Model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryAPI {

    @GET("categories")
    Call<List<Category>> getCategoryList();
}
