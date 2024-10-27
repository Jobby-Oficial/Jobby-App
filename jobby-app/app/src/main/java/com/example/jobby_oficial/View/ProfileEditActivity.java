/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.View;

import static com.example.jobby_oficial.View.MainActivity.avaliationViewModel;
import static com.example.jobby_oficial.View.MainActivity.id_User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.jobby_oficial.R;
import com.example.jobby_oficial.ViewModel.UsersViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

public class ProfileEditActivity extends AppCompatActivity {

    private UsersViewModel usersViewModel;
    private static Context mContextProfile;
    LottieAnimationView lavBack;
    Button btnUpadte;
    TextInputLayout edName, edUsername, edEmail, edPhone;
    String sName, sUsername, sEmail, sPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        mContextProfile = this;

        //Inicializa Controlos
        InitControls();

        GetIntentData();

        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);

        btnUpadte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Variaveis Locais
                boolean bValidation = true;
                bValidation = Validation(bValidation);
                if (bValidation == true){
                    sName = edName.getEditText().getText().toString();
                    sUsername = edUsername.getEditText().getText().toString();
                    sEmail = edEmail.getEditText().getText().toString();
                    sPhone = edPhone.getEditText().getText().toString();

                    JsonObject joUser = new JsonObject();
                    joUser.addProperty("username", sUsername);
                    joUser.addProperty("email", sEmail);
                    joUser.addProperty("name", sName);
                    joUser.addProperty("phone", sPhone);

                    usersViewModel.makeApiCallUpdateProfile(Integer.parseInt(id_User), joUser);

                }
            }
        });

        lavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initProfile(boolean bProfile){
        if (bProfile == true){
            Intent intent = new Intent(mContextProfile, ProfileActivity.class);
            mContextProfile.startActivity(intent);
        }
        else {
            //Open a dialog
        }
    }

    private void GetIntentData() {
        sName = getIntent().getStringExtra("Name");
        sUsername = getIntent().getStringExtra("Username");
        sEmail = getIntent().getStringExtra("Email");
        sPhone = getIntent().getStringExtra("Phone");

        edName.getEditText().setText(sName);
        edUsername.getEditText().setText(sUsername);
        edEmail.getEditText().setText(sEmail);
        edPhone.getEditText().setText(sPhone);
    }

    private boolean Validation(boolean validation) {
        if(edName.getEditText().getText().toString().trim().isEmpty()){
            validation = false;
            edName.setError("Enter Name, field can not be empty!");
        }
        else
            edName.setErrorEnabled(false);

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

        if (edEmail.getEditText().getText().toString().trim().isEmpty()){
            validation = false;
            edEmail.setError("Enter Email, field can not be empty!");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(edEmail.getEditText().getText().toString()).matches()){
            validation = false;
            edEmail.setError("Invalid Email");
        }
        else
            edEmail.setErrorEnabled(false);

        if(edPhone.getEditText().getText().toString().isEmpty()){
            validation = false;
            edPhone.setError("Enter Phone, field can not be empty!");
        }
        else if (!edPhone.getEditText().getText().toString().matches("\\A\\w{1,20}\\z")){
            validation = false;
            edPhone.setError("No spaces are allowed!");
        }
        else if(edPhone.getEditText().getText().toString().length() != 9){
            validation = false;
            edPhone.setError("Invalid Phone, use only 9 characters!");
        }
        else
            edPhone.setErrorEnabled(false);

        return validation;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void InitControls() {
        lavBack = findViewById(R.id.lav_back_profile_edit);
        btnUpadte = findViewById(R.id.btn_update_profile);
        edName = findViewById(R.id.full_name_profile_edit);
        edUsername = findViewById(R.id.username_profile_edit);
        edEmail = findViewById(R.id.email_profile_edit);
        edPhone = findViewById(R.id.phone_number_profile_edit);
    }
}