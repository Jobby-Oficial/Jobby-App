/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.jobby_oficial.Model.User;
import com.example.jobby_oficial.R;
import com.example.jobby_oficial.ViewModel.UsersViewModel;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    LottieAnimationView lavBack, lavEdit;
    TextView tvUsername, tvName, tvEmail, tvPhone, tvGenre, tvCountry, tvCity;
    private UsersViewModel usersViewModel;
    List<User> list_user;
    String sName, sUsername, sEmail, sPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Inicializa Controlos
        InitControls();

        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        usersViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> userList) {
                list_user = userList;
                if (list_user.size() != 0) {
                    System.out.println("Username: " + list_user.get(0).getUsername());
                    tvUsername.setText(list_user.get(0).getUsername());
                    tvName.setText(list_user.get(0).getName());
                    tvEmail.setText(list_user.get(0).getEmail());
                    tvPhone.setText(list_user.get(0).getPhone());
                    if (list_user.get(0).getGenre().equals("m"))
                        tvGenre.setText("Male");
                    else if (list_user.get(0).getGenre().equals("f"))
                        tvGenre.setText("Female");
                    else if (list_user.get(0).getGenre().equals("o"))
                        tvGenre.setText("Other");
                    else
                        tvGenre.setText("Error");
                    tvCountry.setText(list_user.get(0).getCountry());
                    tvCity.setText(list_user.get(0).getCity());
                }
            }
        });

        lavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        lavEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ProfileActivity.this, ProfileEditActivity.class);
                intent = IntentData(intent); //Send Intent
                startActivity(intent);
            }
        });
    }

    private Intent IntentData(Intent intent) {
        //Variaveis Locais
        sName = tvName.getText().toString();
        sUsername = tvUsername.getText().toString();
        sEmail = tvEmail.getText().toString();
        sPhone = tvPhone.getText().toString();

        //Put Extra
        intent.putExtra("Name", sName);
        intent.putExtra("Username", sUsername);
        intent.putExtra("Email", sEmail);
        intent.putExtra("Phone", sPhone);

        return intent;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void InitControls() {
        lavBack = findViewById(R.id.lav_back_profile);
        lavEdit = findViewById(R.id.lav_edit_profile);
        tvUsername = findViewById(R.id.tv_username_profile);
        tvName = findViewById(R.id.tv_name_profile);
        tvEmail = findViewById(R.id.tv_email_profile);
        tvPhone = findViewById(R.id.tv_phone_profile);
        tvGenre = findViewById(R.id.tv_genre_profile);
        tvCountry = findViewById(R.id.tv_country_profile);
        tvCity = findViewById(R.id.tv_city_profile);
    }
}