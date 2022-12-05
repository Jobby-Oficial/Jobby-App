package com.example.jobby_oficial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import soup.neumorphism.NeumorphCardView;

public class AuthenticationMenu extends AppCompatActivity {

    NeumorphCardView ncContinue;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_menu);
        InitControls();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AuthenticationMenu.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ncContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AuthenticationMenu.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void InitControls() {
        ncContinue = findViewById(R.id.nc_Continue);
        btnLogin = findViewById(R.id.btn_login_authentication);
        btnRegister = findViewById(R.id.btn_regster_authentication);
    }
}