/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.jobby_oficial.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class SplashScreen extends AppCompatActivity {

    //Variables
    public static final String FILE_SETTINGS = "settings.txt";
    Animation topAnim, bottonAnim;
    ImageView imgJobbyLogotipo;
    LottieAnimationView imgWatchPc;
    TextView tvPowerBy, tvJobby, tvSlogan;
    public static String sDayNight = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //Inicializa Controlos
        InitControls();

        LoadSettings();

        final MediaPlayer introSound = MediaPlayer.create(this, R.raw.sound);
        introSound.start();

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottonAnim = AnimationUtils.loadAnimation(this,R.anim.botton_animation);

        imgJobbyLogotipo.setAnimation(topAnim);
        tvSlogan.setAnimation(topAnim);
        imgWatchPc.setAnimation(bottonAnim);
        //tvPowerBy.setAnimation(bottonAnim);
        //tvJobby.setAnimation(bottonAnim);

        YoYo.with(Techniques.FadeInUp).duration(4000).repeat(0).playOn(tvPowerBy);
        YoYo.with(Techniques.FadeInUp).duration(4000).repeat(0).playOn(tvJobby);

        //YoYo.with(Techniques.Bounce).duration(2000).repeat(0).playOn(imgJobbyLogotipo);
        //YoYo.with(Techniques.Shake).duration(3000).repeat(0).playOn(tvSlogan);
        //YoYo.with(Techniques.Shake).duration(2000).repeat(0).playOn(imgWatchPc);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(SplashScreen.this, AuthenticationMenu.class);
                Pair[] pair = new Pair[3];
                pair[0] = new Pair(imgJobbyLogotipo,"tn_jobby_logo");
                pair[1] = new Pair(tvPowerBy, "tn_slogan");
                pair[2] = new Pair(tvPowerBy, "tn_power_by");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pair);
                startActivity(intent, options.toBundle());
                //finish();
            }
        }, 7000);
    }

    private void LoadSettings(){
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_SETTINGS);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String settings;
            while ((settings = br.readLine()) != null) {
                sb.append(settings);
            }
            sDayNight = sb.toString();
            if (sDayNight.equals("night"))
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void InitControls() {
        imgJobbyLogotipo = findViewById(R.id.img_ss_jobby_logotipo);
        tvSlogan = findViewById(R.id.tv_ss_slogan);
        imgWatchPc = findViewById(R.id.lav_ss_watch_pc);
        tvPowerBy = findViewById(R.id.tv_ss_power_by);
        tvJobby = findViewById(R.id.tv_ss_jobby);
    }
}