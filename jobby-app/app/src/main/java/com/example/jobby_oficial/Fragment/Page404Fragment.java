/*
 * Created by Guilherme Cruz
 * Last modified: 02/01/22, 01:05
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.jobby_oficial.R;

public class Page404Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    LottieAnimationView imgPage404NotFound;
    TextView tvNoResultFound404, tvContentPage404Found;

    public Page404Fragment() {
        // Required empty public constructor
    }

    public static Page404Fragment newInstance(String param1, String param2) {
        Page404Fragment fragment = new Page404Fragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page404, container, false);
        InitControls(view);
        YoYo.with(Techniques.FadeInUp).duration(3000).repeat(0).playOn(imgPage404NotFound);
        YoYo.with(Techniques.FadeInUp).duration(3000).repeat(0).playOn(tvNoResultFound404);
        YoYo.with(Techniques.FadeInUp).duration(3000).repeat(0).playOn(tvContentPage404Found);

        return view;
    }

    private void InitControls(View view) {
        imgPage404NotFound = view.findViewById(R.id.lav_page_404_not_found);
        tvNoResultFound404 = view.findViewById(R.id.tv_page_404_not_found);
        tvContentPage404Found = view.findViewById(R.id.tv_content_page_404);
    }
}