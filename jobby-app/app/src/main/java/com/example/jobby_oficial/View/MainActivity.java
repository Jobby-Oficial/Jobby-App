/*
 * Created by Guilherme Cruz
 * Last modified: 30/12/21, 02:24
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.jobby_oficial.FavoriteFragment;
import com.example.jobby_oficial.Fragment.CategoryFragment;
import com.example.jobby_oficial.Model.User;
import com.example.jobby_oficial.ProfileFragment;
import com.example.jobby_oficial.R;
import com.example.jobby_oficial.Fragment.ServiceFragment;
import com.example.jobby_oficial.ViewModel.CategoryViewModel;
import com.example.jobby_oficial.ViewModel.UsersViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private UsersViewModel usersViewModel;
    private CategoryViewModel categoryViewModel;
    public MeowBottomNavigation meowBottomNavigationView;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fabExtended, fabValuations, fabProfile;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isCheck = false, isOpen = false;
    int iNavBarId = 1;
    Fragment selectedFragment = new CategoryFragment();
    Bundle bundle;
    NestedScrollView scrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Inicializa Controlos
        InitControls();
        AddMenuItems();

        //Animations
        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        usersViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                //update recyclerview
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Log.d(TAG, "run: " + users.toString());
                    }
                });
                thread.start();
            }
        });

        /*categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategorys().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {

            }
        });*/

        //insert();
        //getAll();

        meowBottomNavigationView.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                // your codes
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
                        iNavBarId = 1;
                        selectedFragment = new CategoryFragment();
                        //Toast.makeText(getApplicationContext(),"Category",Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        iNavBarId = 2;
                        selectedFragment = new ServiceFragment();
                        //Toast.makeText(getApplicationContext(),"Service",Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        iNavBarId = 3;
                        selectedFragment = new FavoriteFragment();
                        //Toast.makeText(getApplicationContext(),"Favorite",Toast.LENGTH_SHORT).show();
                        break;

                    case 4:
                        iNavBarId = 4;
                        selectedFragment = new ProfileFragment();
                        //Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_SHORT).show();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, selectedFragment).detach(selectedFragment).attach(selectedFragment).commit();
            }
        });

        meowBottomNavigationView.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                // your codes
            }
        });

        //meowBottomNavigationView.setCount(1,"10");
        meowBottomNavigationView.show(1, true);

        fabValuations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fabProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        /*bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.category:
                    selectedFragment = new CategoryFragment();
                    Toast.makeText(getApplicationContext(),"Category",Toast.LENGTH_SHORT).show();
                    break;

                case R.id.service:
                    selectedFragment = new ServiceFragment();
                    Toast.makeText(getApplicationContext(),"Service",Toast.LENGTH_SHORT).show();

                    break;

                case R.id.favorite:
                    selectedFragment = new FavoriteFragment();
                    Toast.makeText(getApplicationContext(),"Favorite",Toast.LENGTH_SHORT).show();
                    break;

                case R.id.profile:
                    selectedFragment = new ProfileFragment();
                    Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_SHORT).show();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,selectedFragment).commit();

            return true;
        });*/

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new CategoryFragment()).commit();

        fabExtended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabAnimation();
                //Toast.makeText(getApplicationContext(),"Adicionar",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void insert() {
        User users = new User(
                "guilherme",
                "123",
                "img.jpg",
                "Guilherme Cruz",
                "guilhermecruz@gmail.com",
                "912345678",
                "Masculino",
                "28-01-2000");
        InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
        insertAsyncTask.execute(users);
    }*/

    private void getAll() {
        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> usersList = SingletonRoomDatabase.getInstance(getApplicationContext())
                        .usersDao().getAllUsers();
                Log.d(TAG, "run: " + usersList.toString());
            }
        });
        thread.start();*/
    }

    /*class InsertAsyncTask extends AsyncTask<User, Void, Void>{

        @Override
        protected Void doInBackground(User... users) {
            SingletonRoomDatabase.getInstance(getApplicationContext())
                    .usersDao()
                    .insertUser(users[0]);
            return null;
        }
    }*/

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
        meowBottomNavigationView.add(new MeowBottomNavigation.Model(4, R.drawable.ic_calendar));
    }

    private void fabAnimation(){
        if (isOpen) {
            fabExtended.startAnimation(rotateBackward);
            fabValuations.startAnimation(fabClose);
            fabProfile.startAnimation(fabClose);
            fabValuations.setClickable(false);
            fabProfile.setClickable(false);
            isOpen = false;
        }
        else {
            fabExtended.startAnimation(rotateForward);
            fabValuations.startAnimation(fabOpen);
            fabProfile.startAnimation(fabOpen);
            fabValuations.setClickable(true);
            fabProfile.setClickable(true);
            isOpen = true;
        }
    }

    private void InitControls() {
        //bottomNavigationView = findViewById(R.id.bottomNavigationView);
        scrollview = findViewById(R.id.nestedScrollView);
        meowBottomNavigationView = findViewById(R.id.bottom_navegation);
        fabExtended = findViewById(R.id.fab_Extended);
        fabValuations = findViewById(R.id.fab_Valuations);
        fabProfile = findViewById(R.id.fab_Profile);
    }
}