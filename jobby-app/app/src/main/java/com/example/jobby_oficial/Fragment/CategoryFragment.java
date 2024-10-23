/*
 * Created by Guilherme Cruz
 * Last modified: 31/12/21, 22:14
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.jobby_oficial.Adapter.CategoryAdapter;
import com.example.jobby_oficial.Model.Category;
import com.example.jobby_oficial.R;
import com.example.jobby_oficial.View.MainActivity;
import com.example.jobby_oficial.ViewModel.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class CategoryFragment extends Fragment implements CategoryAdapter.OnCategoryListener {

    private static final String ARG_PARAM1 = "pCategoryName";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private CategoryViewModel categoryViewModel;
    RecyclerView rvCategory;
    CategoryAdapter adapter;
    List<Category> list_category;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance(String sCategoryName) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, sCategoryName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        rvCategory = view.findViewById(R.id.recyclerView_category);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategory.setHasFixedSize(true);
        list_category = new ArrayList<>();

        adapter = new CategoryAdapter(getContext(), list_category,this);
        //rvCategory.setAdapter(adapter);

        //Animations
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapter);
        alphaInAnimationAdapter.setDuration(1000);//[1 Sec]
        alphaInAnimationAdapter.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaInAnimationAdapter.setFirstOnly(false);
        rvCategory.setAdapter(alphaInAnimationAdapter);


        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategorys().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categoryList) {
                list_category = categoryList;
                adapter.getAllCategorys(categoryList);
                adapter.notifyDataSetChanged();
            }
        });
        categoryViewModel.makeApiCallCategory();

        return view;
    }

    @Override
    public void onCategoryClick(int position) {
        //Toast.makeText(getContext(),"Category Position: " + position,Toast.LENGTH_SHORT).show();
        Category category = list_category.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("nameCategory", category.getName());
        ServiceFragment fragment = new ServiceFragment();
        fragment.setArguments(bundle);
        System.out.println("Bondleeeeeeeeeee:" + bundle);
        ((MainActivity)getActivity()).meowBottomNavigationView.show(2, true);
        getFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).detach(fragment).attach(fragment).commit();
    }
}