/*
 * Created by Guilherme Cruz
 * Last modified: 21/01/22, 19:15
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial;

import static com.example.jobby_oficial.View.MainActivity.id_User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.jobby_oficial.Adapter.ServiceAdapter;
import com.example.jobby_oficial.Fragment.CategoryFragment;
import com.example.jobby_oficial.Fragment.NotFoundFragment;
import com.example.jobby_oficial.Model.Avaliation;
import com.example.jobby_oficial.Model.Schedule;
import com.example.jobby_oficial.Model.Service;
import com.example.jobby_oficial.View.MainActivity;
import com.example.jobby_oficial.ViewModel.AvaliationViewModel;
import com.example.jobby_oficial.ViewModel.ScheduleViewModel;
import com.example.jobby_oficial.ViewModel.ServiceViewModel;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class AvaliationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliation);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout_avaliation, new AvaliationFragment()).commit();
    }
}