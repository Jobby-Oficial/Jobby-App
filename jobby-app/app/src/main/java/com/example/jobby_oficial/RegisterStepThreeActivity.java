package com.example.jobby_oficial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterStepThreeActivity extends AppCompatActivity {

    LottieAnimationView lavBack;
    Button btnGoToLogin, btnNext, btnBack;
    TextInputLayout edPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_three);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        InitControls();

        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepThreeActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        lavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepThreeActivity.this,AuthenticationMenu.class);
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
                    Intent intent= new Intent(RegisterStepThreeActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepThreeActivity.this,RegisterStepTwoActivity.class);
                startActivity(intent);
            }
        });
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
        lavBack = findViewById(R.id.lav_back_register);
        btnGoToLogin = findViewById(R.id.btn_go_to_login);
        btnNext = findViewById(R.id.btn_next_step3);
        btnBack = findViewById(R.id.btn_back_step3);
        edPhone = findViewById(R.id.phone_number_register);
    }
}