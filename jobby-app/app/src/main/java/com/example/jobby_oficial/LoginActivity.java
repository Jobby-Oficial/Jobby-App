/*
 * Created by Guilherme Cruz
 * Last modified: 04/12/21, 12:32
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    LottieAnimationView lavBack;
    Button btnGoToRegister, btnSignin;
    TextInputLayout edUsername, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        InitControls();

        lavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this,AuthenticationMenu.class);
                startActivity(intent);
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Variaveis Locais
                boolean bValidation = true;
                bValidation = Validation(bValidation);
                if (bValidation == true){
                    Intent intent= new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this,RegisterStepOneActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean Validation(boolean validation) {

        if (edUsername.getEditText().getText().toString().isEmpty()){
            validation = false;
            edUsername.setError("Enter Username, field can not be empty!");
        }
        else if (!edUsername.getEditText().getText().toString().matches("\\A\\w{1,20}\\z")){
            validation = false;
            edUsername.setError("No spaces are allowed!");
        }
        else if (edUsername.getEditText().getText().toString().length() < 3){
            validation = false;
            edUsername.setError("Username is too small! (Min 3 characters)");
        }
        else if (edUsername.getEditText().getText().toString().length() > 20){
            validation = false;
            edUsername.setError("Username is too large! (Max 20 characters)");
        }
        else
            edUsername.setErrorEnabled(false);

        if (edPassword.getEditText().getText().toString().isEmpty()){
            validation = false;
            edPassword.setError("Enter Password, field can not be empty!");
        }
        else if (!edPassword.getEditText().getText().toString().matches("\\A\\w{1,20}\\z")){
            validation = false;
            edPassword.setError("No spaces are allowed!");
        }
        else if (edPassword.getEditText().getText().length() < 8){
            validation = false;
            edPassword.setError("Password is too short! (Min 8 characters)");
        }
        else if (!edPassword.getEditText().getText().toString().matches("^(?=.*[A-Z]).+$")){
            validation = false;
            edPassword.setError("Password need at least 1 upper case letter!");
        }
        else if (!edPassword.getEditText().getText().toString().matches("^(?=.*[a-z]).+$")){
            validation = false;
            edPassword.setError("Password need at least 1 lower case letter!");
        }
        else if (!edPassword.getEditText().getText().toString().matches("^(?=.*[0-9]).+$")){
            validation = false;
            edPassword.setError("Password need at least 1 digit!");
        }
        else edPassword.setErrorEnabled(false);

        return validation;
    }

    private void InitControls() {
        lavBack = findViewById(R.id.lav_back_login);
        btnGoToRegister = findViewById(R.id.btn_go_to_register);
        btnSignin = findViewById(R.id.btn_sign_in);
        edUsername = findViewById(R.id.username_login);
        edPassword = findViewById(R.id.password_login);
    }
}