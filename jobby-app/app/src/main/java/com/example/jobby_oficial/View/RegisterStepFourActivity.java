/*
 * Created by Guilherme Cruz
 * Last modified: 23/01/22, 02:15
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.jobby_oficial.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterStepFourActivity extends AppCompatActivity {

    LottieAnimationView lavBack;
    Button btnGoToLogin, btnNext, btnBack;
    String sName, sUsername, sEmail, sPassword, sDate, sPhone;
    int iDay, iMonth, iYear;
    char cGender;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_four);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Inicializa Controlos
        InitControls();

        //Get Intent Data
        GetIntentData();

        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepFourActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        lavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepFourActivity.this, AuthenticationMenu.class);
                startActivity(intent);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Variaveis Locais
                boolean bValidation = true;
                bValidation = Validation(bValidation);
                if (bValidation == true) {
                        if (!isConnected(RegisterStepFourActivity.this)) {
                        showInternetDialog();
                    } else {
                        Intent intent = new Intent(RegisterStepFourActivity.this, MainActivity.class);
                        SendDataToAPI();
                        startActivity(intent);
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepFourActivity.this, RegisterStepThreeActivity.class);
                intent = IntentDataBack(intent);
                startActivity(intent);
            }
        });
    }

    private boolean isConnected(RegisterStepFourActivity register) {
        ConnectivityManager connectivityManager = (ConnectivityManager) register.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())){
            return true;
        }
        else {
            return false;
        }
    }

    private void showInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterStepFourActivity.this);
        builder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_internet, findViewById(R.id.internet_dialog));

        view.findViewById(R.id.btn_connect_internet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
        view.findViewById(R.id.tv_cancel_internet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        builder.setView(view);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }

    private void SendDataToAPI() {
        System.out.println("Send API: " + sName + " + " + sUsername + " + " + sEmail + " + " + sPassword + " + " + cGender + " + " + sDate + " + " + sPhone);
    }

    private void GetIntentData() {
        sName = getIntent().getStringExtra("Name");
        sUsername = getIntent().getStringExtra("Username");
        sEmail = getIntent().getStringExtra("Email");
        sPassword = getIntent().getStringExtra("Password");
        cGender = getIntent().getCharExtra("Gender", cGender);
        sDate =  getIntent().getStringExtra("Date");
        iDay = getIntent().getIntExtra("Day", iDay);
        iMonth = getIntent().getIntExtra("Month", iMonth);
        iYear = getIntent().getIntExtra("Year", iYear);
        sPhone = getIntent().getStringExtra("Phone");

        System.out.println("Step4: " + sName + " + " + sUsername + " + " + sEmail + " + " + sPassword + " + " + cGender + " + " + sDate + " + " + sPhone);
        System.out.println("date int s4: " + iDay + iMonth + iYear);
    }

    private Intent IntentDataBack(Intent intent) {
        //Put Extra
        intent.putExtra("Name", sName);
        intent.putExtra("Username", sUsername);
        intent.putExtra("Email", sEmail);
        intent.putExtra("Password", sPassword);
        intent.putExtra("Gender", cGender);
        intent.putExtra("Date", sDate);
        intent.putExtra("Day", iDay);
        intent.putExtra("Month", iMonth);
        intent.putExtra("Year", iYear);
        intent.putExtra("Phone", sPhone);

        return intent;
    }

    private boolean Validation(boolean validation) {

        return validation;
    }

    private void InitControls() {
        lavBack = findViewById(R.id.lav_back_register);
        btnGoToLogin = findViewById(R.id.btn_go_to_login);
        btnNext = findViewById(R.id.btn_next_step4);
        btnBack = findViewById(R.id.btn_back_step4);
    }
}