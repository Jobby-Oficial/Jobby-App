package com.example.jobby_oficial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SplashScreen extends AppCompatActivity {
    //Variables
    Animation topAnim, bottonAnim;
    ImageView imgJobbyLogotipo;
    LottieAnimationView imgWatchPc;
    TextView tvPowerBy, tvSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        InitControls();

        final MediaPlayer introSound = MediaPlayer.create(this, R.raw.sound);
        introSound.start();

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottonAnim = AnimationUtils.loadAnimation(this,R.anim.botton_animation);

        imgJobbyLogotipo.setAnimation(topAnim);
        tvSlogan.setAnimation(topAnim);
        imgWatchPc.setAnimation(bottonAnim);
        tvPowerBy.setAnimation(bottonAnim);

        //YoYo.with(Techniques.Bounce).duration(2000).repeat(0).playOn(imgJobbyLogotipo);
        //YoYo.with(Techniques.Shake).duration(3000).repeat(0).playOn(tvSlogan);
        //YoYo.with(Techniques.Shake).duration(2000).repeat(0).playOn(imgWatchPc);


        final Intent intent= new Intent(SplashScreen.this,MainActivity.class);
        Thread timer = new Thread(){
            public void run (){
                try{
                    sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }

    private void InitControls() {
        imgJobbyLogotipo = findViewById(R.id.img_jobby_logotipo);
        tvSlogan = findViewById(R.id.tv_slogan);
        imgWatchPc = findViewById(R.id.lav_watch_pc);
        tvPowerBy = findViewById(R.id.tv_power_by);
    }
}