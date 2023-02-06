/*
 * Created by Guilherme Cruz
 * Last modified: 04/12/21, 12:35
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.jobby_oficial.R;

import soup.neumorphism.NeumorphCardView;

public class AuthenticationMenu extends AppCompatActivity {

    NeumorphCardView ncContinue;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_menu);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        InitControls();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AuthenticationMenu.this, LoginActivity.class);
                Pair[] pair = new Pair[1];
                pair[0] = new Pair(findViewById(R.id.btn_login_authentication),"tn_login");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AuthenticationMenu.this, pair);
                startActivity(intent, options.toBundle());
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AuthenticationMenu.this, RegisterStepOneActivity.class);
                Pair[] pair = new Pair[1];
                pair[0] = new Pair(findViewById(R.id.btn_regster_authentication),"tn_register");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AuthenticationMenu.this, pair);
                startActivity(intent, options.toBundle());
            }
        });

        ncContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AuthenticationMenu.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void InitControls() {
        ncContinue = findViewById(R.id.nc_Continue);
        btnLogin = findViewById(R.id.btn_login_authentication);
        btnRegister = findViewById(R.id.btn_regster_authentication);
    }
}