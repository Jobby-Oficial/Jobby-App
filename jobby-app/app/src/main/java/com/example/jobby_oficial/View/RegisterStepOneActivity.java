/*
 * Created by Guilherme Cruz
 * Last modified: 24/12/21, 03:05
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.jobby_oficial.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterStepOneActivity extends AppCompatActivity {

    LottieAnimationView lavBack;
    Button btnGoToLogin, btnNext;
    TextInputLayout edName, edUsername, edEmail, edPassword;
    String sName, sUsername, sEmail, sPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_one);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Inicializa Controlos
        InitControls();

        //Get Intent Data
        GetIntentData();

        /*Spannable wordtoSpan = new SpannableString("Already have an account? Login");
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 25, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        btnGoToLogin.setText(wordtoSpan);*/

        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepOneActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        lavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepOneActivity.this, AuthenticationMenu.class);
                startActivity(intent);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Variaveis Locais
                boolean bValidation = true;
                bValidation = Validation(bValidation);
                if (bValidation == true){
                    Intent intent= new Intent(RegisterStepOneActivity.this, RegisterStepTwoActivity.class);
                    intent = IntentData(intent); //Send Intent Data
                    startActivity(intent);
                }
            }
        });

        /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_layout_register, new StepOneFragment());
        fragmentTransaction.commit();*/
    }

    private void GetIntentData() {
        sName = getIntent().getStringExtra("Name");
        sUsername = getIntent().getStringExtra("Username");
        sEmail = getIntent().getStringExtra("Email");
        sPassword = getIntent().getStringExtra("Password");

        edName.getEditText().setText(sName);
        edUsername.getEditText().setText(sUsername);
        edEmail.getEditText().setText(sEmail);
        edPassword.getEditText().setText(sPassword);

        System.out.println("Step1: " + sName + " + " + sUsername + " + " + sEmail + " + " + sPassword);
    }

    private Intent IntentData(Intent intent) {
        //Variaveis Locais
        sName = edName.getEditText().getText().toString();
        sUsername = edUsername.getEditText().getText().toString();
        sEmail = edEmail.getEditText().getText().toString();
        sPassword = edPassword.getEditText().getText().toString();

        //Put Extra
        intent.putExtra("Name", sName);
        intent.putExtra("Username", sUsername);
        intent.putExtra("Email", sEmail);
        intent.putExtra("Password", sPassword);

        return intent;
    }

    private boolean Validation(boolean validation) {
        //Variaveis Locais
        int iPos = 0;
        String checkPassword = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if(edName.getEditText().getText().toString().trim().isEmpty()){
            validation = false;
            edName.setError("Enter Name, field can not be empty!");
            iPos++;
        }
        else
            edName.setErrorEnabled(false);

        if (edUsername.getEditText().getText().toString().isEmpty()){
            validation = false;
            edUsername.setError("Enter Username, field can not be empty!");
            iPos++;
        }
        else if (!edUsername.getEditText().getText().toString().matches("\\A\\w{1,20}\\z")){
            validation = false;
            edUsername.setError("No spaces are allowed!");
            iPos++;
        }
        else if (edUsername.getEditText().getText().toString().length() < 3){
            validation = false;
            edUsername.setError("Username is too small! (Min 3 characters)");
            iPos++;
        }
        else if (edUsername.getEditText().getText().toString().length() > 20){
            validation = false;
            edUsername.setError("Username is too large! (Max 20 characters)");
            iPos++;
        }
        else
            edUsername.setErrorEnabled(false);

        if (edEmail.getEditText().getText().toString().trim().isEmpty()){
            validation = false;
            edEmail.setError("Enter Email, field can not be empty!");
            iPos++;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(edEmail.getEditText().getText().toString()).matches()){
            validation = false;
            edEmail.setError("Invalid Email");
            iPos++;
        }
        else
            edEmail.setErrorEnabled(false);

        if (edPassword.getEditText().getText().toString().isEmpty()){
            validation = false;
            edPassword.setError("Enter Password, field can not be empty!");
            iPos++;
        }
        else if (!edPassword.getEditText().getText().toString().matches("\\A\\w{1,20}\\z")){
            validation = false;
            edPassword.setError("No spaces are allowed!");
            iPos++;
        }
        else if (edPassword.getEditText().getText().length() < 8){
            validation = false;
            edPassword.setError("Password is too short! (Min 8 characters)");
            iPos++;
        }
        else if (!edPassword.getEditText().getText().toString().matches("^(?=.*[A-Z]).+$")){
            validation = false;
            edPassword.setError("Password need at least 1 upper case letter!");
            iPos++;
        }
        else if (!edPassword.getEditText().getText().toString().matches("^(?=.*[a-z]).+$")){
            validation = false;
            edPassword.setError("Password need at least 1 lower case letter!");
            iPos++;
        }
        else if (!edPassword.getEditText().getText().toString().matches("^(?=.*[0-9]).+$")){
            validation = false;
            edPassword.setError("Password need at least 1 digit!");
            iPos++;
        }
        else edPassword.setErrorEnabled(false);

        FormLayout(iPos);

        return validation;
    }

    private void FormLayout(int position){
        int iTop = 158;
        if (position == 1)
            iTop = 120;
        else if (position == 2)
            iTop = 90;
        else if (position == 3)
            iTop = 50;
        else if (position == 4)
            iTop = 10;
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll_form_step1_register);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)ll.getLayoutParams();
        lp.setMargins(0, iTop, 0, 0);
        ll.setLayoutParams(lp);
    }

    private void InitControls() {
        lavBack = findViewById(R.id.lav_back_register);
        btnGoToLogin = findViewById(R.id.btn_go_to_login);
        btnNext = findViewById(R.id.btn_next_step1);
        edName = findViewById(R.id.full_name_register);
        edUsername = findViewById(R.id.username_register);
        edEmail = findViewById(R.id.email_register);
        edPassword = findViewById(R.id.password_register);
    }
}