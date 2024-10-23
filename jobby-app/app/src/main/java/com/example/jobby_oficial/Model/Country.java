/*
 * Created by Guilherme Cruz
 * Last modified: 23/01/22, 15:29
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Country {

    @SerializedName("country")
    private String country;

    public Country(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Country{" +
                "country='" + country + '\'' +
                '}';
    }
}
