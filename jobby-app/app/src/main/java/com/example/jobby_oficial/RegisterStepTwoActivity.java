package com.example.jobby_oficial;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

public class RegisterStepTwoActivity extends AppCompatActivity {

    LottieAnimationView lavBack;
    Button btnGoToLogin, btnNext, btnBack;
    RadioGroup rgGender;
    TextView tvGenderError, tvAgeError;
    DatePicker dpAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_two);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        InitControls();

        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepTwoActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        lavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepTwoActivity.this,AuthenticationMenu.class);
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
                    startActivity(intent);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterStepTwoActivity.this,RegisterStepOneActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean Validation(boolean validation){

        /*if(rgGender.getCheckedRadioButtonId()<=0){//Grp is your radio group object
            //rgGender.setError("Select Item");//Set error to last Radio button
        }*/

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

        if (isYearValid < 14){
            tvAgeError.setVisibility(View.VISIBLE);
            tvAgeError.setText("Invalid Age! (Min 14 Years)");
            validation = false;
        }
        else if (isYearValid == 14 && (isMonthValid > 0 || isDayValid > 0)){
            tvAgeError.setVisibility(View.VISIBLE);
            tvAgeError.setText("Invalid Age! (Min 14 Years)");
            validation = false;
        }
        else
            tvAgeError.setVisibility(View.INVISIBLE);

        //System.out.println(isYearValid);
        //System.out.println(isMonthValid);
        //System.out.println(isDayValid);

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