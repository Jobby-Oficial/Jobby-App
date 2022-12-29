package com.example.jobby_oficial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jobby_oficial.Database.SingletonRoomDatabase;
import com.example.jobby_oficial.Model.Category;
import com.example.jobby_oficial.Model.Users;
import com.example.jobby_oficial.ViewModel.CategoryViewModel;
import com.example.jobby_oficial.ViewModel.UsersViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private UsersViewModel usersViewModel;
    private CategoryViewModel categoryViewModel;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fabAdicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inicializa Controlos
        InitControls();

        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        usersViewModel.getAllUsers().observe(this, new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> users) {
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

        bottomNavigationView.setBackground(null);
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
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new CategoryFragment()).commit();

        fabAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Adicionar",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insert() {
        Users users = new Users(
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
    }

    private void getAll() {
        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Users> usersList = SingletonRoomDatabase.getInstance(getApplicationContext())
                        .usersDao().getAllUsers();
                Log.d(TAG, "run: " + usersList.toString());
            }
        });
        thread.start();*/
    }

    class InsertAsyncTask extends AsyncTask<Users, Void, Void>{

        @Override
        protected Void doInBackground(Users... users) {
            SingletonRoomDatabase.getInstance(getApplicationContext())
                    .usersDao()
                    .insertUser(users[0]);
            return null;
        }
    }

    private void InitControls() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fabAdicionar = findViewById(R.id.fabAdicionar);
    }
}