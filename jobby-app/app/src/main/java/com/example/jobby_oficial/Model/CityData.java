/*
 * Created by Guilherme Cruz
 * Last modified: 23/01/22, 23:47
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityData {

    @SerializedName("data")
    @Expose
    private List<String> cityList = null;

    public CityData(List<String> cityList) {
        this.cityList = cityList;
    }

    public List<String> getCityList() {
        return cityList;
    }

    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
    }
}
