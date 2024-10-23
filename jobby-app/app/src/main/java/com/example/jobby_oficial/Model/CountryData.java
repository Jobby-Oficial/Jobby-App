/*
 * Created by Guilherme Cruz
 * Last modified: 23/01/22, 21:18
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryData {

    @SerializedName("data")
    @Expose
    private List<Country> countryList = null;

    public CountryData(List<Country> countryList) {
        this.countryList = countryList;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }
}
