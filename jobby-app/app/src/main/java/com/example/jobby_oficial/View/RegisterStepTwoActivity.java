/*
 * Created by Guilherme Cruz
 * Last modified: 31/12/21, 00:24
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.jobby_oficial.R;

import java.util.Calendar;

public class RegisterStepTwoActivity extends AppCompatActivity {

    LottieAnimationView lavBack;
    Button btnGoToLogin, btnNext, btnBack;
    RadioGroup rgGender;
    TextView tvGenderError, tvAgeError;
    DatePicker dpAge;
    String sName, sUsername, sEmail, sPassword, sDate;
    int iDay, iMonth, iYear;
    char cGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_two);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Inicializa Controlos
        InitControls();

        //Get Intent Data
        GetIntentData();

        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepTwoActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        lavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepTwoActivity.this, AuthenticationMenu.class);
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
                    Intent intent = new Intent(RegisterStepTwoActivity.this, RegisterStepThreeActivity.class);
                    intent = IntentData(intent); //Send Intent Data
                    startActivity(intent);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepTwoActivity.this, RegisterStepOneActivity.class);
                intent = IntentDataBack(intent); //Send Intent Data
                startActivity(intent);
            }
        });
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

        if (cGender == 'm')
            rgGender.check(R.id.rb_male_register);
        else if (cGender == 'f')
            rgGender.check(R.id.rb_female_register);
        else if (cGender == 'o')
            rgGender.check(R.id.rb_other_register);

        if (sDate != null)
            dpAge.updateDate(iYear, iMonth, iDay);

        System.out.println("Step2: " + sName + " + " + sUsername + " + " + sEmail + " + " + sPassword + " + " + cGender + " + " + sDate);
    }

    private Intent IntentData(Intent intent) {
        switch (rgGender.getCheckedRadioButtonId()) {

            case R.id.rb_male_register:
                cGender = 'm';
                break;

            case R.id.rb_female_register:
                cGender = 'f';
                break;

            default:
                cGender = 'o';
        }

        iDay = dpAge.getDayOfMonth();
        iMonth = dpAge.getMonth();
        iYear =  dpAge.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(iYear, iMonth, iDay);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sDate = sdf.format(calendar.getTime());

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

        return intent;
    }

    private Intent IntentDataBack(Intent intent) {
        //Put Extra
        intent.putExtra("Name", sName);
        intent.putExtra("Username", sUsername);
        intent.putExtra("Email", sEmail);
        intent.putExtra("Password", sPassword);

        return intent;
    }

    private boolean Validation(boolean validation){
        switch (rgGender.getCheckedRadioButtonId()) {

            case R.id.rb_male_register:
                tvGenderError.setVisibility(View.INVISIBLE);
                break;

            case R.id.rb_female_register:
                tvGenderError.setVisibility(View.INVISIBLE);
                break;

            case R.id.rb_other_register:
                tvGenderError.setVisibility(View.INVISIBLE);
                break;
            default:
                tvGenderError.setVisibility(View.VISIBLE);
                tvGenderError.setText("Select you Gender!");
                validation = false;
        }

        int YEAR = Calendar.getInstance().get(Calendar.YEAR);
        int MONTH = Calendar.getInstance().get(Calendar.MONTH)+1;
        int DAY = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        int uYear = dpAge.getYear();
        int uMonth = dpAge.getMonth()+1;
        int uDay = dpAge.getDayOfMonth();

        int isYearValid = YEAR - uYear;
        int isMonthValid = MONTH - uMonth;
        int isDayValid = DAY - uDay;

        if (isYearValid < 16){
            tvAgeError.setVisibility(View.VISIBLE);
            tvAgeError.setText("Invalid Age! (Min 16 Years)");
            validation = false;
        }
        else if (isYearValid == 16 && (isMonthValid > 0 || isDayValid > 0)){
            tvAgeError.setVisibility(View.VISIBLE);
            tvAgeError.setText("Invalid Age! (Min 16 Years)");
            validation = false;
        }
        else
            tvAgeError.setVisibility(View.INVISIBLE);

        return validation;
    }

    private void InitControls() {
        lavBack = findViewById(R.id.lav_back_register);
        btnGoToLogin = findViewById(R.id.btn_go_to_login);
        btnNext = findViewById(R.id.btn_next_step2);
        btnBack = findViewById(R.id.btn_back_step2);
        rgGender = findViewById(R.id.rg_gender_register);
        tvGenderError = findViewById(R.id.tv_gender_register);
        tvAgeError = findViewById(R.id.tv_age_register);
        dpAge = findViewById(R.id.dp_age_register);
    }
}