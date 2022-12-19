package com.example.jobby_oficial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

public class RegisterStepOneActivity extends AppCompatActivity {

    LottieAnimationView lavBack;
    Button btnGoToLogin, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_one);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        InitControls();

        /*Spannable wordtoSpan = new SpannableString("Already have an account? Login");
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 25, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        btnGoToLogin.setText(wordtoSpan);*/

        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepOneActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        lavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepOneActivity.this,AuthenticationMenu.class);
                startActivity(intent);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepOneActivity.this,RegisterStepTwoActivity.class);
                startActivity(intent);
            }
        });

        /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_layout_register, new StepOneFragment());
        fragmentTransaction.commit();*/
    }

    private void InitControls() {
        lavBack = findViewById(R.id.lav_back_register);
        btnGoToLogin = findViewById(R.id.btn_go_to_login);
        btnNext = findViewById(R.id.btn_next_step1);
    }
}