/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.jobby_oficial.Database.SessionManager;
import com.example.jobby_oficial.Model.User;
import com.example.jobby_oficial.R;
import com.example.jobby_oficial.ViewModel.UsersViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private UsersViewModel usersViewModel;
    private static Context mContextLogin;
    LottieAnimationView lavBack, lavTop, lavBottom;
    Button btnGoToRegister, btnSignin;
    TextInputLayout edUsername, edPassword;
    static TextView tvInvalidLogin;
    List<User> list_users;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContextLogin = this;

        //Inicializa Controlos
        InitControls();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lavBack.playAnimation();
                lavTop.playAnimation();
                lavBottom.playAnimation();
            }
        }, 1000);

        list_users = new ArrayList<>();

        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        usersViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> usersList) {
                list_users = usersList;
                System.out.println("Lista: " + list_users);
            }
        });

        lavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Variaveis Locais
                boolean bValidation = true;
                bValidation = Validation(bValidation);
                if (bValidation == true) {
                    if (!isConnected(LoginActivity.this)) {
                        showInternetDialog();
                    } else {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("username", edUsername.getEditText().getText().toString());
                        jsonObject.addProperty("password", edPassword.getEditText().getText().toString());
                        tvInvalidLogin.setVisibility(View.INVISIBLE);
                        usersViewModel.makeApiCallUsers(jsonObject);
                    }
                }
            }
        });

        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this, RegisterStepOneActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean isConnected(LoginActivity login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_internet, findViewById(R.id.internet_dialog));

        view.findViewById(R.id.btn_connect_internet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                alertDialog.dismiss();
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

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        //finish();
                    }
                }).show();
    }

    public void initSessionLogin(boolean bLogin){
        if (bLogin == true){
            Intent intent = new Intent(mContextLogin, MainActivity.class);
            mContextLogin.startActivity(intent);
        }
        else {
            tvInvalidLogin.setVisibility(View.VISIBLE);
            tvInvalidLogin.setText("Invalid Username or Password");
        }
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
        /*else if (!edPassword.getEditText().getText().toString().matches("^(?=.*[A-Z]).+$")){
            validation = false;
            edPassword.setError("Password need at least 1 upper case letter!");
        }*/
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
        lavTop = findViewById(R.id.lav_top_login);
        lavBottom = findViewById(R.id.lav_bottom_login);
        btnGoToRegister = findViewById(R.id.btn_go_to_register);
        btnSignin = findViewById(R.id.btn_sign_in);
        edUsername = findViewById(R.id.username_login);
        edPassword = findViewById(R.id.password_login);
        tvInvalidLogin = findViewById(R.id.tv_invalid_login);
    }
}