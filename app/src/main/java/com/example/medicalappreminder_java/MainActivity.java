package com.example.medicalappreminder_java;

import androidx.appcompat.app.AppCompatActivity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;


import com.example.medicalappreminder_java.Login.LoginView.LoginActivity;


import android.view.View;

import com.example.medicalappreminder_java.AddMedicine.View.AddMedicine;

import com.example.medicalappreminder_java.networkConnectivity.NetworkChangeReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.example.medicalappreminder_java.SignUp.View.SignUpActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.medicalappreminder_java.HomeScreen.View.HomeScreenActivity.HomeScreen;

public class MainActivity extends AppCompatActivity {

    String emailFromPref , passwordFromPref ;
    BroadcastReceiver broadcastReceiver;
    String TAG ="TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            //startActivity(new Intent(MainActivity.this  , LoginActivity.class));


        //startActivity(new Intent(MainActivity.this  , LoginActivity.class));



            //startActivity(new Intent(MainActivity.this  , NotificationDialogActivity.class));


            //startActivity(new Intent(MainActivity.this  , HomeScreen.class));

            //startActivity(new Intent(MainActivity.this  , DrugReminderActivity.class));




            SharedPreferences preferences = getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
            emailFromPref = preferences.getString("emailKey","N/A");
            passwordFromPref = preferences.getString("passwordKey" , "N/A");

            if (emailFromPref.equals("N/A") ) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }else {
                startActivity(new Intent(MainActivity.this, HomeScreen.class));
            }


//
//         SharedPreferences preferences = getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
//        emailFromPref = preferences.getString("emailKey","N/A");
//        passwordFromPref = preferences.getString("passwordKey" , "N/A");
//
//        if (emailFromPref.equals("N/A") ) {
//            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//        }else {
//            startActivity(new Intent(MainActivity.this, HomeScreen.class));
//        }


    //        getSupportActionBar().hide();
    //        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
    //        setSupportActionBar(myToolbar);


           /*  BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
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
            bottomNav.setOnNavigationItemSelectedListener(navListener);*/


    }


    protected void registerNetworkBroadCastReceiver(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            registerReceiver(broadcastReceiver , new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            registerReceiver(broadcastReceiver , new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetwork(){
        try {
            unregisterReceiver(broadcastReceiver);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            Log.i(TAG, "unregisterNetwork in Main Activity: produced an errrorrr -----------------");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetwork();
    }
}
