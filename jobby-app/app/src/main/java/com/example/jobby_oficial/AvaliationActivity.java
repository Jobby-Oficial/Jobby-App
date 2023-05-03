/*
 * Created by Guilherme Cruz
 * Last modified: 21/01/22, 19:15
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.jobby_oficial.Fragment.AvaliationFragment;

public class AvaliationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliation);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout_avaliation, new AvaliationFragment()).commit();
    }
}