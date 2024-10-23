/*
 * Created by Guilherme Cruz
 * Last modified: 02/01/22, 18:50
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

public class NotFoundFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    LottieAnimationView imgPageNotFound, imgWaiting;
    TextView tvNoResultFound, tvContentPageFound;

    public NotFoundFragment() {
        // Required empty public constructor
    }

    public static NotFoundFragment newInstance(String param1, String param2) {
        NotFoundFragment fragment = new NotFoundFragment();
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
        View view = inflater.inflate(R.layout.fragment_not_found, container, false);
        InitControls(view);
        YoYo.with(Techniques.FadeInUp).duration(3000).repeat(0).playOn(imgPageNotFound);
        YoYo.with(Techniques.FadeInUp).duration(3000).repeat(0).playOn(tvNoResultFound);
        YoYo.with(Techniques.FadeInUp).duration(3000).repeat(0).playOn(tvContentPageFound);
        YoYo.with(Techniques.FadeInUp).duration(3000).repeat(0).playOn(imgWaiting);

        return view;
    }

    private void InitControls(View view) {
        imgPageNotFound = view.findViewById(R.id.lav_page_not_found);
        imgWaiting = view.findViewById(R.id.lav_Waiting_page_found);
        tvNoResultFound = view.findViewById(R.id.tv_no_result_found);
        tvContentPageFound = view.findViewById(R.id.tv_content_page_found);
    }
}