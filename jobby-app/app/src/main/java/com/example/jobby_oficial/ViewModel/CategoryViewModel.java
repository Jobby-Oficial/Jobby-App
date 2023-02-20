/*
 * Created by Guilherme Cruz
 * Last modified: 04/12/21, 12:32
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jobby_oficial.Model.Category;
import com.example.jobby_oficial.Network.CategoryAPI;
import com.example.jobby_oficial.Network.CategoryRetroInstance;
import com.example.jobby_oficial.Repository.CategoryRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository categoryRepository;
    private LiveData<List<Category>> getAllCategorys;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
        getAllCategorys = categoryRepository.getAllCategorys();
    }

    public void insert(List<Category> list){
        categoryRepository.insert(list);
    }

    public LiveData<List<Category>> getAllCategorys(){
        return getAllCategorys;
    }

    public void makeApiCallCategory(){
        CategoryAPI apiServiceCategory = CategoryRetroInstance.getRetrofitCategory().create(CategoryAPI.class);
        Call<List<Category>> call = apiServiceCategory.getCategoryList();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful()){
                    categoryRepository.insert(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("Error ", "Category API: " + t);
                System.out.println("Error Category API: " + t);
            }
        });
    }
}
