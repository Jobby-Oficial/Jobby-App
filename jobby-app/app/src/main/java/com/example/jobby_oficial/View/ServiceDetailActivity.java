/*
 * Created by Guilherme Cruz
 * Last modified: 20/01/22, 15:07
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.View;

import static com.example.jobby_oficial.View.MainActivity.avaliationViewModel;
import static com.example.jobby_oficial.View.MainActivity.id_User;
import static com.example.jobby_oficial.View.MainActivity.scheduleViewModel;
import static com.example.jobby_oficial.View.MainActivity.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Html;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.jobby_oficial.Model.Avaliation;
import com.example.jobby_oficial.R;
import com.example.jobby_oficial.Repository.ScheduleRepository;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ServiceDetailActivity extends AppCompatActivity {

    AlertDialog alertDialog;
    LottieAnimationView lavBack, lavSchedule;
    ImageSlider imageSlider;
    TextView tvNameDetail, tvCategoryDetail, tvProfissionalDetail, tvPriceDetail, tvDescriptionDetail, tvDateError, tvTimeError;
    TextInputLayout edNote;
    DatePicker dpDate;
    NumberPicker npHour, npMinute;
    RatingBar ratingBar;
    String category, name, description, price, profissional;
    int service_id, profissional_id, avaliation_id;
    double dRating;
    boolean bCancel = false;
    List<Avaliation> list_avaliation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Inicializa Controlos
        InitControls();

        GetIntentData();

        avaliationViewModel.getAllAvaliations().observe(this, new Observer<List<Avaliation>>() {
            @Override
            public void onChanged(List<Avaliation> avaliationList) {
                list_avaliation = avaliationList;
                if (id_User != null) {
                    for (Avaliation iAvaliation : list_avaliation) {
                        int iUser = list_avaliation.get(list_avaliation.indexOf(iAvaliation)).getUser_id();
                        if (iUser == Integer.parseInt(id_User)) {
                            int iServ = list_avaliation.get(list_avaliation.indexOf(iAvaliation)).getService_id();
                            if (iServ == service_id)
                                avaliation_id = list_avaliation.get(list_avaliation.indexOf(iAvaliation)).getId();
                        }
                    }
                }
                System.out.println("Lista Avaliation/Service Detail: " + list_avaliation);
            }
        });
        JsonObject joAvaliation = new JsonObject();
        joAvaliation.addProperty("user_id", id_User);
        avaliationViewModel.makeApiCallAvaliations(joAvaliation);

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.jobby_v1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.jobby_v2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.jobby_v1, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels);

        /*if (dRating == 0)
            bCancel = true;
        else
            bCancel = false;*/

        tvNameDetail.setText(name);
        tvCategoryDetail.setText(category);
        tvProfissionalDetail.setText(profissional);
        tvPriceDetail.setText(price + "€");
        String sourceString = "<b>" + "Description: " + "</b> " + description;
        tvDescriptionDetail.setText(Html.fromHtml(sourceString));
        ratingBar.setRating((float) dRating);

        //tvDescriptionDetail.setText("Description: " + description + "dhsududsaccdsyucdscdbudcbcdybydcsbdycydybdussdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsfhhewdaniela");

        lavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lavSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null)
                    showScheduleDialog();
                else
                    showSessionDialog();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                //Toast.makeText(getApplicationContext(), "Rating: " + rating, Toast.LENGTH_SHORT).show();

                if (bCancel == false) {
                    if (id_User != null) {
                        if (rating == 0)
                            showAvaliationRemoveDialog(rating);
                        else
                            showAvaliationInsertDialog(rating);
                    } else
                        showSessionDialog();
                }
                else
                    bCancel = false;
            }
        });
    }

    private void showAvaliationInsertDialog(float rating) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ServiceDetailActivity.this);
        builder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_avaliation_insert, findViewById(R.id.avaliation_insert_dialog));
        bCancel = false;
        TextView tvTitle = view.findViewById(R.id.tv_title_avaliation_insert);
        tvTitle.setText("Service Avaliation: " + rating);

        view.findViewById(R.id.btn_insert_avaliation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Rating: " + dRating + " | " + rating);
                JsonObject joAvaliation = new JsonObject();
                joAvaliation.addProperty("avaliation", rating);
                joAvaliation.addProperty("service_id", service_id);
                joAvaliation.addProperty("user_id", id_User);
                if (dRating != 0)
                    avaliationViewModel.makeApiCallUpdateAvaliations(avaliation_id, joAvaliation);
                else
                    avaliationViewModel.makeApiCallCreateAvaliations(joAvaliation);
                dRating = rating;
                alertDialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_cancel_avaliation_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bCancel = true;
                alertDialog.dismiss();
                ratingBar.setRating((float) dRating);
            }
        });
        builder.setView(view);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }

    private void showAvaliationRemoveDialog(float rating) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ServiceDetailActivity.this);
        builder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_avaliation_remove, findViewById(R.id.avaliation_remove_dialog));
        bCancel = false;

        view.findViewById(R.id.btn_remove_avaliation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dRating = rating;
                avaliationViewModel.makeApiCallDeleteAvaliations(avaliation_id);
                alertDialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_cancel_avaliation_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bCancel = true;
                alertDialog.dismiss();
                ratingBar.setRating((float) dRating);
            }
        });
        builder.setView(view);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }

    private void showScheduleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ServiceDetailActivity.this);
        builder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_schedule, findViewById(R.id.schedule_dialog));

        edNote = view.findViewById(R.id.ed_note_schedule);
        dpDate = view.findViewById(R.id.dp_date_schedule);
        npHour = view.findViewById(R.id.np_hour_time_schedule);
        npMinute = view.findViewById(R.id.np_minute_time_schedule);
        tvDateError = view.findViewById(R.id.tv_invalid_date_schedule);
        tvTimeError = view.findViewById(R.id.tv_invalid_time_schedule);

        npHour.setMinValue(0);
        npHour.setMaxValue(23);
        npHour.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });

        npMinute.setMinValue(0);
        npMinute.setMaxValue(59);
        npMinute.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });

        view.findViewById(R.id.btn_insert_schedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Variaveis Locais
                boolean bValidation = true;
                bValidation = Validation(bValidation);
                if (bValidation == true) {
                    SendDataToAPI();
                    alertDialog.dismiss();
                }
            }
        });
        view.findViewById(R.id.tv_cancel_schedule).setOnClickListener(new View.OnClickListener() {
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

    private void showSessionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ServiceDetailActivity.this);
        builder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_session, findViewById(R.id.session_dialog));
        bCancel = false;

        view.findViewById(R.id.btn_sign_in_session).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceDetailActivity.this, AuthenticationMenu.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.tv_cancel_session).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bCancel = true;
                alertDialog.dismiss();
                ratingBar.setRating(0.0F);
            }
        });
        builder.setView(view);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }

    private boolean Validation(boolean validation) {

        if(edNote.getEditText().getText().toString().trim().isEmpty()){
            validation = false;
            edNote.setError("Enter Name, field can not be empty!");
        }
        else
            edNote.setErrorEnabled(false);

        int YEAR = Calendar.getInstance().get(Calendar.YEAR);
        int MONTH = Calendar.getInstance().get(Calendar.MONTH)+1;
        int DAY = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int HOUR = Calendar.getInstance().get(Calendar.HOUR);
        int MINUTE = Calendar.getInstance().get(Calendar.MINUTE);

        int uYear = dpDate.getYear();
        int uMonth = dpDate.getMonth()+1;
        int uDay = dpDate.getDayOfMonth();
        int uHour = npHour.getValue();
        int uMinute = npMinute.getValue();

        int isYearValid = YEAR - uYear;
        int isMonthValid = MONTH - uMonth;
        int isDayValid = DAY - uDay;
        int isHourValid = HOUR - uHour;
        int isMinuteValid = MINUTE - uMinute;

        System.out.println("Date Valid: " + isYearValid + " | " + isMonthValid + " | " + isDayValid + " | " + isHourValid + " | " + isMinuteValid);

        tvTimeError.setVisibility(View.INVISIBLE);

        if (isYearValid > 0){
            tvDateError.setVisibility(View.VISIBLE);
            tvDateError.setText("Invalid Date! (Year)");
            validation = false;
        }
        else if (isYearValid == 0 && isMonthValid > 0) {
            tvDateError.setVisibility(View.VISIBLE);
            tvDateError.setText("Invalid Date! (Month)");
            validation = false;
        }
        else if (isYearValid == 0 && isMonthValid == 0 && isDayValid > 0){
            tvDateError.setVisibility(View.VISIBLE);
            tvDateError.setText("Invalid Date! (Day)");
            validation = false;
        }
        else {
            tvDateError.setVisibility(View.INVISIBLE);

            if (isHourValid > 0){
                tvTimeError.setVisibility(View.VISIBLE);
                tvTimeError.setText("Invalid Time! (Hour)");
                validation = false;
            }
            else if (isHourValid == 0 && isMinuteValid > 0){
                tvTimeError.setVisibility(View.VISIBLE);
                tvTimeError.setText("Invalid Time! (Minute)");
                validation = false;
            }
        }

        return validation;
    }

    private void SendDataToAPI() {
        String sNote = edNote.getEditText().getText().toString();

        int iDay = dpDate.getDayOfMonth();
        int iMonth = dpDate.getMonth();
        int iYear =  dpDate.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(iYear, iMonth, iDay);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = sdf.format(calendar.getTime());

        String sHour = String.format("%02d", npHour.getValue());
        String sMinute = String.format("%02d", npMinute.getValue());

        String sTime = sHour + ":" + sMinute;

        System.out.println("Schedule: " + sDate + " | " + sTime + " | " + sNote + " | " + price + " | " + service_id + " | " + profissional + " | " + id_User);

        JsonObject joSchedule = new JsonObject();
        joSchedule.addProperty("service_date", sDate);
        joSchedule.addProperty("service_time", sTime);
        joSchedule.addProperty("note", sNote);
        joSchedule.addProperty("payment", "0");
        joSchedule.addProperty("price", price);
        joSchedule.addProperty("job_status_id", "1");
        joSchedule.addProperty("service_id", service_id);
        joSchedule.addProperty("professional_id", profissional_id);
        joSchedule.addProperty("client_id", id_User);

        scheduleViewModel.makeApiCallCreateSchedule(joSchedule);
    }

    private void GetIntentData() {
        service_id = getIntent().getIntExtra("Service_id",0);
        category = getIntent().getStringExtra("Category");
        name = getIntent().getStringExtra("Name");
        description = getIntent().getStringExtra("Description");
        price = getIntent().getStringExtra("Price");
        profissional_id = getIntent().getIntExtra("Profissional_id",0);
        profissional = getIntent().getStringExtra("Profissional");
        dRating = getIntent().getDoubleExtra("Rating", 0.0);

        System.out.println("Service Detail: " + service_id + " + " + category + " + " + name + " + " + description + " + " + price + " + " + " + " + profissional_id + " + " + profissional + " + " + dRating);
    }

    private void InitControls() {
        lavBack = findViewById(R.id.lav_back_service_detail);
        lavSchedule = findViewById(R.id.lav_schedule_service_detail);
        imageSlider = findViewById(R.id.slider_service_detail);
        tvNameDetail = findViewById(R.id.tv_name_service_detail);
        tvCategoryDetail = findViewById(R.id.tv_category_service_detail);
        tvProfissionalDetail = findViewById(R.id.tv_professional_service_detail);
        tvPriceDetail = findViewById(R.id.tv_price_service_detail);
        tvDescriptionDetail = findViewById(R.id.tv_description_service_detail);
        ratingBar = findViewById(R.id.rating_service_detail);
    }
}