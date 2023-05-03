/*
 * Created by Guilherme Cruz
 * Last modified: 20/01/22, 15:07
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.jobby_oficial.R;

import java.util.ArrayList;
import java.util.List;

public class ServiceDetailActivity extends AppCompatActivity {

    LottieAnimationView lavBack, lavSchedule;
    ImageSlider imageSlider;
    TextView tvNameDetail, tvCategoryDetail, tvProfissionalDetail, tvPriceDetail, tvDescriptionDetail;
    RatingBar ratingBar;
    String id, category, name, description, price, user_id, profissional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Inicializa Controlos
        InitControls();

        GetIntentData();

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.jobby_v1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.jobby_v2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.jobby_v1, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels);

        id =  getIntent().getStringExtra("Id");
        category = getIntent().getStringExtra("Category");
        name = getIntent().getStringExtra("Name");
        description = getIntent().getStringExtra("Description");
        price = getIntent().getStringExtra("Price");
        user_id = getIntent().getStringExtra("User_id");
        profissional = getIntent().getStringExtra("Profissional");
        System.out.println("Detail: " + id + " " + category + " " + name + " " + description + " " + price + " " + user_id);

        tvNameDetail.setText(name);
        tvCategoryDetail.setText(category);
        tvProfissionalDetail.setText(profissional);
        tvPriceDetail.setText(price + "€");
        String sourceString = "<b>" + "Description: " + "</b> " + description;
        tvDescriptionDetail.setText(Html.fromHtml(sourceString));
        //tvDescriptionDetail.setText("Description: " + description + "dhsududsaccdsyucdscdbudcbcdybydcsbdycydybdussdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsfhhewdaniela");

        lavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                Toast.makeText(getApplicationContext(),"Rating: " + rating,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetIntentData() {
        id =  getIntent().getStringExtra("Id");
        category = getIntent().getStringExtra("Category");
        name = getIntent().getStringExtra("Name");
        description = getIntent().getStringExtra("Description");
        price = getIntent().getStringExtra("Price");
        user_id = getIntent().getStringExtra("User_id");
        profissional = getIntent().getStringExtra("Profissional");

        System.out.println("Service Detail: " + id + " + " + category + " + " + name + " + " + description + " + " + price + " + " + user_id + profissional);
    }

    private void InitControls() {
        lavBack = findViewById(R.id.lav_back_service_detail);
        lavSchedule = findViewById(R.id.lav_schedule_service_detail);
        imageSlider = findViewById(R.id.slider_service_detail);
        tvNameDetail = findViewById(R.id.tv_name_service_detail);
        tvCategoryDetail = findViewById(R.id.tv_category_service_detail);
        tvProfissionalDetail = findViewById(R.id.tv_professional_service_detail);
        tvPriceDetail = findViewById(R.id.tv_price_service_detail);
        tvDescriptionDetail = findViewById(R.id.tv_description_service_detail);
        ratingBar = findViewById(R.id.rating_service_detail);
    }
}