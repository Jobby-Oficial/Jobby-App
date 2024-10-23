/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Network;

import com.example.jobby_oficial.Model.ServicesGallery;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServicesGalleryAPI {

    @GET("guests/get-services-gallery")
    Call<List<ServicesGallery>> getServicesGalleryList();
}
