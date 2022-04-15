package com.example.medicalappreminder_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.RoomDatabase;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.medicalappreminder_java.DependantInfo.DependentInfoFragment;
import com.example.medicalappreminder_java.activandinactivemedscreen.view.MyFirstFragment;
import com.example.medicalappreminder_java.roomdb.AppDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.medicalappreminder_java.SignUp.View.SignUpActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        startActivity(new Intent(MainActivity.this , SignUpActivity.class));

//        getSupportActionBar().hide();
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);


         BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
         BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.home:
                                selectedFragment = new MyFirstFragment();
                                break;
                            case R.id.medication:
                                selectedFragment = new DependentInfoFragment();
                                break;
                            case R.id.more:
                                selectedFragment = new MyFirstFragment();
                                break;
                        }

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,
                                selectedFragment).commit();
                        return true;
                    }
                };
        bottomNav.setOnNavigationItemSelectedListener(navListener);




    }
}