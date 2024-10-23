/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.View;

import static com.example.jobby_oficial.View.SplashScreen.sDayNight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.jobby_oficial.Database.SessionManager;
import com.example.jobby_oficial.R;
import com.example.jobby_oficial.ViewModel.UsersViewModel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import soup.neumorphism.NeumorphCardView;

public class AuthenticationMenu extends AppCompatActivity {
    private UsersViewModel usersViewModel;
    ImageView imgLogo;
    NeumorphCardView ncContinue;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_menu);
        //Inicializa Controlos
        InitControls();

        if (sDayNight.equals("night"))
            imgLogo.setImageResource(R.drawable.jobby_v3);
        else
            imgLogo.setImageResource(R.drawable.ic_jobby2_oficial);

        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);

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
                SessionManager sessionManager = new SessionManager(AuthenticationMenu.this);
                sessionManager.logoutUserFromSession();
                usersViewModel.makeDeleteUsers();
                Intent intent = new Intent(AuthenticationMenu.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void InitControls() {
        imgLogo = findViewById(R.id.img_logotipo);
        ncContinue = findViewById(R.id.nc_Continue);
        btnLogin = findViewById(R.id.btn_login_authentication);
        btnRegister = findViewById(R.id.btn_regster_authentication);
    }
}