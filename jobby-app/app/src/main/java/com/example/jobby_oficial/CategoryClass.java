/*
 * Created by Guilherme Cruz
 * Last modified: 04/12/21, 12:35
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial;

import android.media.Image;

public class CategoryClass {
    private int imageCategory;
    private String nameCategory, descriptionCategory;

    public CategoryClass(int imageCategory, String nameCategory, String descriptionCategory) {
        this.imageCategory = imageCategory;
        this.nameCategory = nameCategory;
        this.descriptionCategory = descriptionCategory;
    }

    public int getImageCategory() {
        return imageCategory;
    }

    public void setImageCategory(int imageCategory) {
        this.imageCategory = imageCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getDescriptionCategory() {
        return descriptionCategory;
    }

    public void setDescriptionCategory(String descriptionCategory) {
        this.descriptionCategory = descriptionCategory;
    }
}
