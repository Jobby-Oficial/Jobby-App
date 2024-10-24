/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.View;

import static com.example.jobby_oficial.View.SplashScreen.sDayNight;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.jobby_oficial.Model.CountryData;
import com.example.jobby_oficial.Network.RegionRetroInstance;
import com.example.jobby_oficial.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterStepThreeActivity extends AppCompatActivity {

    GifImageView gifPhone;
    LottieAnimationView lavBack;
    Button btnGoToLogin, btnNext, btnBack;
    TextInputLayout edPhone;
    String sName, sUsername, sEmail, sPassword, sDate, sPhone;
    int iDay, iMonth, iYear;
    char cGender;
    AlertDialog alertDialog;
    public static List<String> list_country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_three);
        //Inicializa Controlos
        InitControls();

        //Get Intent
        GetIntentData();

        if (sDayNight.equals("night"))
            gifPhone.setImageResource(R.drawable.animation_phone_run_dark);
        else
            gifPhone.setImageResource(R.drawable.animation_phone_run);

        list_country = new ArrayList<>();
        makeApiCallCountrys();

        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepThreeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        lavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepThreeActivity.this, AuthenticationMenu.class);
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
                    Intent intent = new Intent(RegisterStepThreeActivity.this, RegisterStepFourActivity.class);
                    intent = IntentData(intent); //Send Intent CountryData
                    startActivity(intent);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepThreeActivity.this, RegisterStepTwoActivity.class);
                intent = IntentDataBack(intent);
                startActivity(intent);
            }
        });
    }

    public void makeApiCallCountrys(){
        Call<CountryData> call = RegionRetroInstance.getRegions().getCountrysList();
        call.enqueue(new Callback<CountryData>() {
            @Override
            public void onResponse(Call<CountryData> call, Response<CountryData> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful()){
                    for (int iPos = 0; iPos < response.body().getCountryList().size(); iPos++) {
                        list_country.add(response.body().getCountryList().get(iPos).getCountry());
                        //System.out.println("Country List: " + response.body().getCountryList().get(iPos).getCountry());
                    }
                    /*ArrayAdapter<Country> adapter = new ArrayAdapter<Country>(getApplicationContext(), R.layout.list_item_dropdown, list_country){
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            parent.setOverScrollMode(View.OVER_SCROLL_NEVER);
                            return super.getView(position, convertView, parent);
                        }
                    };
                    ((AutoCompleteTextView)findViewById(R.id.country_register)).setAdapter(adapter);*/
                }
            }

            @Override
            public void onFailure(Call<CountryData> call, Throwable t) {
                Log.e("Error ", "Country API: " + t);
                System.out.println("Error Country API: " + t);
                call.cancel();
            }
        });
    }

    private Intent IntentData(Intent intent) {
        sPhone = edPhone.getEditText().getText().toString();

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

        if (sPhone != null)
            edPhone.getEditText().setText(sPhone);

        System.out.println("Step3: " + sName + " + " + sUsername + " + " + sEmail + " + " + sPassword + " + " + cGender + " + " + sDate + " + " + sPhone);
        System.out.println("date int s3: " + iDay + iMonth + iYear);
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

        return intent;
    }


    private boolean Validation(boolean validation) {

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

    private void InitControls() {
        gifPhone = findViewById(R.id.lav_phone_run);
        lavBack = findViewById(R.id.lav_back_register);
        btnGoToLogin = findViewById(R.id.btn_go_to_login);
        btnNext = findViewById(R.id.btn_next_step3);
        btnBack = findViewById(R.id.btn_back_step3);
        edPhone = findViewById(R.id.phone_number_register);
    }
}