/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:45
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.View;

import static com.example.jobby_oficial.View.SplashScreen.FILE_SETTINGS;
import static com.example.jobby_oficial.View.SplashScreen.sDayNight;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.jobby_oficial.Fragment.AvaliationFragment;
import com.example.jobby_oficial.Database.SessionManager;
import com.example.jobby_oficial.Fragment.FavoriteFragment;
import com.example.jobby_oficial.Fragment.CategoryFragment;
import com.example.jobby_oficial.Fragment.Page404Fragment;
import com.example.jobby_oficial.Model.Avaliation;
import com.example.jobby_oficial.Model.Favorite;
import com.example.jobby_oficial.Model.Schedule;
import com.example.jobby_oficial.Model.Service;
import com.example.jobby_oficial.Model.ServicesGallery;
import com.example.jobby_oficial.Model.User;
import com.example.jobby_oficial.Model.Username;
import com.example.jobby_oficial.R;
import com.example.jobby_oficial.Fragment.ServiceFragment;
import com.example.jobby_oficial.Fragment.ScheduleFragment;
import com.example.jobby_oficial.ViewModel.AvaliationViewModel;
import com.example.jobby_oficial.ViewModel.FavoriteViewModel;
import com.example.jobby_oficial.ViewModel.JobStatusViewModel;
import com.example.jobby_oficial.ViewModel.ScheduleViewModel;
import com.example.jobby_oficial.ViewModel.ServiceViewModel;
import com.example.jobby_oficial.ViewModel.ServicesGalleryViewModel;
import com.example.jobby_oficial.ViewModel.UsersViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
import com.like.LikeButton;
import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    SessionManager sessionManager;
    public static String user, id_User;
    public static int iNotFound = 0;
    private UsersViewModel usersViewModel;
    private ServiceViewModel serviceViewModel;
    public static FavoriteViewModel favoriteViewModel;
    public static ScheduleViewModel scheduleViewModel;
    public static AvaliationViewModel avaliationViewModel;
    private ServicesGalleryViewModel servicesGalleryViewModel;
    public static JobStatusViewModel jobStatusViewModel;
    public MeowBottomNavigation meowBottomNavigationView;
    FloatingActionButton fabExtended, fabLogout, fabProfile, fabSettings;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false, bWarnning = false;
    Fragment selectedFragment = new CategoryFragment();
    NestedScrollView scrollview;
    LottieAnimationView imgSparklesCategory;
    AlertDialog alertDialog;
    List<User> list_user;
    List<Service> list_service;
    List<Favorite> list_favorite;
    List<Schedule> list_schedule;
    List<Avaliation> list_avaliation;
    List<Username> list_username;
    List<ServicesGallery> list_gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inicializa Controlos
        InitControls();
        AddMenuItems();
        Warnning();

        //Animations
        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        sessionManager = new SessionManager(MainActivity.this);
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        serviceViewModel = new ViewModelProvider(this).get(ServiceViewModel.class);
        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        avaliationViewModel = new ViewModelProvider(this).get(AvaliationViewModel.class);
        servicesGalleryViewModel = new ViewModelProvider(this).get(ServicesGalleryViewModel.class);
        jobStatusViewModel = new ViewModelProvider(this).get(JobStatusViewModel.class);


        usersViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                list_user = users;
                Log.d(TAG, "run: " + list_user.toString());

                if (list_user.size() != 0) {
                    String id = String.valueOf(list_user.get(0).getId());
                    String username = list_user.get(0).getUsername();
                    sessionManager.createLoginSession(id, username);
                    HashMap<String, String> userDetails = sessionManager.getUserDetailFromSession();
                    id_User = userDetails.get(sessionManager.KEY_ID);
                    user = userDetails.get(sessionManager.KEY_USERNAME);
                    JsonObject joFavorite = new JsonObject();
                    joFavorite.addProperty("user_id", id_User);
                    favoriteViewModel.makeApiCallFavorites(joFavorite);
                    JsonObject joSchedule = new JsonObject();
                    joSchedule.addProperty("client_id", id_User);
                    scheduleViewModel.makeApiCallSchedules(joSchedule);
                    JsonObject joAvaliation = new JsonObject();
                    joAvaliation.addProperty("user_id", id_User);
                    avaliationViewModel.makeApiCallAvaliations(joAvaliation);
                    serviceViewModel.makeApiCallServices();
                    usersViewModel.makeApiCallUsernames();
                    servicesGalleryViewModel.makeApiCallServicesGallerys();
                    jobStatusViewModel.makeApiCalJobStatus();
                }
                else
                    sessionManager.createLoginSession(null, null);

                if (user != null)
                    fabLogout.setImageResource(R.drawable.ic_logout);
                else
                    fabLogout.setImageResource(R.drawable.ic_menu);
            }
        });

        serviceViewModel.getAllServices().observe(this, new Observer<List<Service>>() {
            @Override
            public void onChanged(List<Service> serviceList) {
                list_service = serviceList;
                System.out.println("Lista Username/Main: " + list_username);
            }
        });

        favoriteViewModel.getAllFavorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favoriteList) {
                list_favorite = favoriteList;
                System.out.println("Lista Favoriros/Main: " + list_favorite);
            }
        });

        scheduleViewModel.getAllSchedules().observe(this, new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> scheduleList) {
                list_schedule = scheduleList;
                System.out.println("Lista Schedules/Main: " + list_schedule);
            }
        });

        avaliationViewModel.getAllAvaliations().observe(this, new Observer<List<Avaliation>>() {
            @Override
            public void onChanged(List<Avaliation> avaliationList) {
                list_avaliation = avaliationList;
                System.out.println("Lista Avaliations/Main: " + list_avaliation);
            }
        });

        usersViewModel.getAllUsernames().observe(this, new Observer<List<Username>>() {
            @Override
            public void onChanged(List<Username> usernameList) {
                list_username = usernameList;
                System.out.println("Lista Username/Main: " + list_username);
            }
        });

        servicesGalleryViewModel.getAllServicesGallerys().observe(this, new Observer<List<ServicesGallery>>() {
            @Override
            public void onChanged(List<ServicesGallery> galleryList) {
                list_gallery = galleryList;
                System.out.println("Lista Services Gallery/Main: " + list_gallery);
            }
        });

        meowBottomNavigationView.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                YoYo.with(Techniques.FadeInUp).duration(3000).repeat(0).playOn(imgSparklesCategory);
            }
        });

        meowBottomNavigationView.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                getSupportFragmentManager().beginTransaction().remove(selectedFragment).commit();
                selectedFragment = null;
                LockScrollview(false);

                switch (item.getId()) {
                    case 1:
                        selectedFragment = new CategoryFragment();
                        //Toast.makeText(getApplicationContext(),"Category",Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        iNotFound = 1;
                        selectedFragment = new ServiceFragment();
                        //Toast.makeText(getApplicationContext(),"Service",Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        iNotFound = 2;
                        if (user != null)
                            selectedFragment = new FavoriteFragment();
                        else {
                            selectedFragment = new Page404Fragment();
                            LockScrollview(true);
                        }
                        //Toast.makeText(getApplicationContext(),"Favorite",Toast.LENGTH_SHORT).show();
                        break;

                    case 4:
                        iNotFound = 3;
                        if (user != null)
                            selectedFragment = new AvaliationFragment();
                        else {
                            selectedFragment = new Page404Fragment();
                            LockScrollview(true);
                        }
                        //Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_SHORT).show();
                        break;

                    case 5:
                        iNotFound = 4;
                        if (user != null)
                            selectedFragment = new ScheduleFragment();
                        else {
                            selectedFragment = new Page404Fragment();
                            LockScrollview(true);
                        }
                        //Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_SHORT).show();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, selectedFragment).detach(selectedFragment).attach(selectedFragment).commit();
                Warnning();
            }
        });

        meowBottomNavigationView.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //Reselect Item Code
            }
        });

        //meowBottomNavigationView.setCount(1,"10");
        meowBottomNavigationView.show(1, true);

        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null) {
                    id_User = null;
                    user = null;
                    sessionManager.logoutUserFromSession();
                }
                Intent intent = new Intent(MainActivity.this, AuthenticationMenu.class);
                startActivity(intent);
                finish();
            }
        });

        fabProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null) {
                    if (!isConnected(MainActivity.this)) {
                        showInternetDialog();
                    } else {
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    }
                }
                else
                    showSessionDialog();
            }
        });

        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSettingsDialog();
            }
        });

        fabExtended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabAnimation();
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new CategoryFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private boolean isConnected(MainActivity connected) {
        ConnectivityManager connectivityManager = (ConnectivityManager) connected.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())){
            return true;
        }
        else {
            return false;
        }
    }

    private void Warnning(){
        if (!isConnected(MainActivity.this)) {
            if (bWarnning == false)
                showWarnningDialog();
        } else
            bWarnning = false;
    }

    private void showInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_internet, findViewById(R.id.internet_dialog));

        view.findViewById(R.id.btn_connect_internet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                alertDialog.dismiss();
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

    private void showWarnningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_warnning, findViewById(R.id.warnning_dialog));
        bWarnning = true;

        view.findViewById(R.id.btn_connect_warnning).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                alertDialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_cancel_warnning).setOnClickListener(new View.OnClickListener() {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_session, findViewById(R.id.session_dialog));

        view.findViewById(R.id.btn_sign_in_session).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AuthenticationMenu.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.tv_cancel_session).setOnClickListener(new View.OnClickListener() {
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

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_settings, findViewById(R.id.settings_dialog));

        DayNightSwitch dayNightSwitch = view.findViewById(R.id.switch_day_night_settings);

        if (sDayNight.equals("night"))
            dayNightSwitch.setIsNight(true, false);
        else
            dayNightSwitch.setIsNight(false, false);

        view.findViewById(R.id.btn_save_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayNightSwitch.isNight()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sDayNight = "night";
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sDayNight = "day";
                }
                SaveSettings();

                alertDialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_cancel_settings).setOnClickListener(new View.OnClickListener() {
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

    private void SaveSettings() {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_SETTINGS, MODE_PRIVATE);
            fos.write(sDayNight.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void LockScrollview (boolean bLock) {
        if (bLock)
            scrollview.setNestedScrollingEnabled(false);
        else
            scrollview.setNestedScrollingEnabled(true);
    }

    private void AddMenuItems() {
        meowBottomNavigationView.add(new MeowBottomNavigation.Model(1, R.drawable.ic_topic));
        meowBottomNavigationView.add(new MeowBottomNavigation.Model(2, R.drawable.ic_business));
        meowBottomNavigationView.add(new MeowBottomNavigation.Model(3, R.drawable.ic_favorite));
        meowBottomNavigationView.add(new MeowBottomNavigation.Model(4, R.drawable.ic_star));
        meowBottomNavigationView.add(new MeowBottomNavigation.Model(5, R.drawable.ic_calendar));
    }

    private void fabAnimation(){
        if (isOpen) {
            fabExtended.startAnimation(rotateBackward);
            fabLogout.startAnimation(fabClose);
            fabProfile.startAnimation(fabClose);
            fabSettings.startAnimation(fabClose);
            fabLogout.setClickable(false);
            fabProfile.setClickable(false);
            fabSettings.setClickable(false);
            isOpen = false;
        }
        else {
            fabExtended.startAnimation(rotateForward);
            fabLogout.startAnimation(fabOpen);
            fabProfile.startAnimation(fabOpen);
            fabSettings.startAnimation(fabOpen);
            fabLogout.setClickable(true);
            fabProfile.setClickable(true);
            fabSettings.setClickable(true);
            isOpen = true;
        }
    }

    private void InitControls() {
        scrollview = findViewById(R.id.nestedScrollView);
        meowBottomNavigationView = findViewById(R.id.bottom_navegation);
        fabExtended = findViewById(R.id.fab_Extended);
        fabLogout = findViewById(R.id.fab_Avaliation);
        fabProfile = findViewById(R.id.fab_Profile);
        fabSettings = findViewById(R.id.fab_Settings);
        imgSparklesCategory = findViewById(R.id.lav_sparkles);
    }
}