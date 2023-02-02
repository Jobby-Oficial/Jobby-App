/*
 * Created by Guilherme Cruz
 * Last modified: 04/12/21, 12:32
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StepTwoFragment extends Fragment {

    Button btnNext, btnBack;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public StepTwoFragment() {
        // Required empty public constructor
    }

    public static StepTwoFragment newInstance(String param1, String param2) {
        StepTwoFragment fragment = new StepTwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        View view = inflater.inflate(R.layout.fragment_step_two, container, false);
        InitControls(view);
        btnNext.setOnClickListener(this::onClick);
        btnBack.setOnClickListener(this::onClick);
        return view;
    }

    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.btn_next_step2:
                fragment = new StepThreeFragment();
                replaceFragment(fragment);
                break;

            case R.id.btn_back_step2:
                fragment = new StepOneFragment();
                replaceFragment(fragment);
                break;
        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //transaction.replace(R.id.fragment_layout_register, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void InitControls(View view) {
        btnNext = view.findViewById(R.id.btn_next_step2);
        btnBack = view.findViewById(R.id.btn_back_step2);
    }
}