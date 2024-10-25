/*
 * Created by Guilherme Cruz
 * Last modified: 23/01/22, 02:15
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.View;

import static com.example.jobby_oficial.View.RegisterStepThreeActivity.list_country;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.jobby_oficial.Model.City;
import com.example.jobby_oficial.Model.CityData;
import com.example.jobby_oficial.Model.Country;
import com.example.jobby_oficial.Model.CountryData;
import com.example.jobby_oficial.Network.RegionRetroInstance;
import com.example.jobby_oficial.R;
import com.example.jobby_oficial.ViewModel.UsersViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterStepFourActivity extends AppCompatActivity {

    private UsersViewModel usersViewModel;
    private static Context mContextRegister;
    AlertDialog alertDialog;
    LottieAnimationView lavBack;
    Button btnGoToLogin, btnNext, btnBack;
    AutoCompleteTextView acCountry, acCity;
    TextInputLayout edCountry, edCity, edAddress;
    String sName, sUsername, sEmail, sPassword, sDate, sPhone, sCountry, sCity, sAddress;
    int iDay, iMonth, iYear;
    char cGender;
    List<String> list_city;
    ArrayAdapter<Country> arrayCountryAdapter;
    ArrayAdapter<City> arrayCityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_four);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mContextRegister = this;

        //Inicializa Controlos
        InitControls();

        //Get Intent CountryData
        GetIntentData();

        list_city = new ArrayList<>();
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);

        arrayCountryAdapter = new ArrayAdapter(getApplicationContext(), R.layout.list_item_dropdown, list_country);
        acCountry.setAdapter(arrayCountryAdapter);
        acCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Testeeeeee: " + list_country.get(i));
                JsonObject joCity = new JsonObject();
                joCity.addProperty("country", list_country.get(i));
                makeApiCallCitys(joCity);
                arrayCityAdapter = new ArrayAdapter(getApplicationContext(), R.layout.list_item_dropdown, list_city);
                acCity.clearListSelection();
                acCity.setAdapter(arrayCityAdapter);
            }
        });

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
                        SendDataToAPI();
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

    public void makeApiCallCitys(JsonObject jsonObject){
        Call<CityData> call = RegionRetroInstance.getRegions().getCitysList(jsonObject);
        call.enqueue(new Callback<CityData>() {
            @Override
            public void onResponse(Call<CityData> call, Response<CityData> response) {
                System.out.println("resp: " + response);
                if (response.isSuccessful()){
                    System.out.println("Cityyyy: " + response.body().getCityList());
                    list_city.clear();
                    for (int iPos = 0; iPos < response.body().getCityList().size(); iPos++) {
                        list_city.add(response.body().getCityList().get(iPos));
                        //System.out.println("Country List: " + response.body().getCountryList().get(iPos).getCountry());
                    }
                }
            }

            @Override
            public void onFailure(Call<CityData> call, Throwable t) {
                Log.e("Error ", "City API: " + t);
                System.out.println("Error City API: " + t);
                call.cancel();
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
        sCountry = acCountry.getText().toString();
        sCity = acCity.getText().toString();
        sAddress = edAddress.getEditText().getText().toString();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("image", "/assets/img/user-profile.svg");
        jsonObject.addProperty("username", sUsername);
        jsonObject.addProperty("email", sEmail);
        jsonObject.addProperty("name", sName);
        jsonObject.addProperty("phone", sPhone);
        jsonObject.addProperty("genre", cGender);
        jsonObject.addProperty("birth", sDate);
        jsonObject.addProperty("country", sCountry);
        jsonObject.addProperty("city", sCity);
        jsonObject.addProperty("morada", sAddress);
        jsonObject.addProperty("biography", "Biografia Texto");
        jsonObject.addProperty("password", sPassword);
        jsonObject.addProperty("typeUser", "consumer");
        usersViewModel.makeApiCallRegister(jsonObject);

        System.out.println("Send API: " + sName + " + " + sUsername + " + " + sEmail + " + " + sPassword + " + " + cGender + " + " + sDate + " + " + sPhone + " + " + sCountry + " + " + sCity + " + " + sAddress);
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

    public void initSessionRegister(boolean bRegister){
        if (bRegister == true){
            Intent intent = new Intent(mContextRegister, LoginActivity.class);
            mContextRegister.startActivity(intent);
        }
        else {
            //Open a dialog
        }
    }

    private boolean Validation(boolean validation) {

        if (acCountry.getText().toString().isEmpty()){
            validation = false;
            edCountry.setError("Enter Country, field can not be empty!");
        }
        else
            edCountry.setErrorEnabled(false);

        if (acCity.getText().toString().isEmpty()){
            validation = false;
            edCity.setError("Enter City, field can not be empty!");
        }
        else
            edCity.setErrorEnabled(false);

        if (edAddress.getEditText().getText().toString().trim().isEmpty()) {
            validation = false;
            edAddress.setError("Enter Address, field can not be empty!");
        } else
            edAddress.setErrorEnabled(false);

        return validation;
    }

    private void InitControls() {
        lavBack = findViewById(R.id.lav_back_register);
        btnGoToLogin = findViewById(R.id.btn_go_to_login);
        btnNext = findViewById(R.id.btn_next_step4);
        btnBack = findViewById(R.id.btn_back_step4);
        acCountry = findViewById(R.id.country_register);
        acCity = findViewById(R.id.city_register);
        edCountry = findViewById(R.id.til_country_register);
        edCity = findViewById(R.id.til_city_register);
        edAddress = findViewById(R.id.address_register);
    }
}