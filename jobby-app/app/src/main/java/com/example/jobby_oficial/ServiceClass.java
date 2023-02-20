/*
 * Created by Guilherme Cruz
 * Last modified: 04/12/21, 12:35
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial;

public class ServiceClass {
    private int imageService;
    private String nameService, categoryService;

    public ServiceClass(int imageService, String nameService, String categoryService) {
        this.imageService = imageService;
        this.nameService = nameService;
        this.categoryService = categoryService;
    }

    public int getImageService() {
        return imageService;
    }

    public void setImageService(int imageService) {
        this.imageService = imageService;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public String getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(String categoryService) {
        this.categoryService = categoryService;
    }
}
