package com.example.jobby_oficial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;

public class LoginActivity extends AppCompatActivity {

    LottieAnimationView lavBack;

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
    }

    private void InitControls() {
        lavBack = findViewById(R.id.lav_back_login);
    }
}