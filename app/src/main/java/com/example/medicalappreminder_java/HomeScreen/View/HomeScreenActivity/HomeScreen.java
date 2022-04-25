package com.example.medicalappreminder_java.HomeScreen.View.HomeScreenActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.medicalappreminder_java.AddMedicine.View.AddMedicine;
import com.example.medicalappreminder_java.DataStorage.SharedPrefrencesModel;
import com.example.medicalappreminder_java.HomeScreen.Presenter.HomeScreen.Presenter;
import com.example.medicalappreminder_java.HomeScreen.Presenter.HomeScreen.PresenterInterface;
import com.example.medicalappreminder_java.Login.LoginView.LoginActivity;
import com.example.medicalappreminder_java.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;;
import com.google.android.material.navigation.NavigationView;

public class HomeScreen extends AppCompatActivity implements HomeScreenInterfaceActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    MenuItem menuItem;
    String userName;
    PresenterInterface presenterInterface;
    boolean dataOfDependentChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initComponents();
        dataOfDependentChanged = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            }
        }
    }

    private void initComponents(){


        presenterInterface = new Presenter(this,this);

        userName = presenterInterface.getUserName();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(userName);
        setSupportActionBar(toolbar);
        setMenu();
        setListeners();
        BottomNavigationView navView = findViewById(R.id.bottomnavigation);
        NavController navController2 = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(navView, navController2);
        findViewById(R.id.ExtendedFloatingActionButtonAddMed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this , AddMedicine.class));
            }
        });
        Menu mMenu = navigationView.getMenu();

        MenuItem user_name_drwable = mMenu.findItem(R.id.home);
        user_name_drwable.setTitle(userName);
        View getHeaderView = navigationView.getHeaderView(0);
        TextView t = getHeaderView.findViewById(R.id.namenav);
        t.setText(userName);

        presenterInterface.getDependent();
    }
    private  void  setListeners(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                toolbar.setTitle(item.getTitle());
                switch (item.getItemId())
                {
                    case R.id.add_dependent:
                        dataOfDependentChanged = true;
                        NavController navController = Navigation.findNavController(HomeScreen.this, R.id.nav_host_fragment_activity_main);
                        navController.navigateUp();
                        navController.navigate(R.id.dependentInfoFragment2);

                        break;
                    case R.id.logOutButton:
                        SharedPrefrencesModel sharedPrefrencesModel = SharedPrefrencesModel.getInstance(HomeScreen.this);
                        sharedPrefrencesModel.writeInSharedPreferences(null,null , null);
                        startActivity(new Intent(HomeScreen.this  , LoginActivity.class));
                        break;
                }
                item.setChecked(true);
                menuItem = item;
                drawerLayout.closeDrawers();
                return true;
            }
        });
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                Log.e("HomeScreen", ": onDrawerSlide");
                if(dataOfDependentChanged) {
                    presenterInterface.getDependent();
                }

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                Log.e("HomeScreen", ": onDrawerOpened");
                dataOfDependentChanged = false;
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                Log.e("HomeScreen", ": closed");
                if(menuItem != null)
                    menuItem.setChecked(false);

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                Log.e("HomeScreen", ": onDrawerState");
                if(dataOfDependentChanged) {
                    presenterInterface.getDependent();
                }

            }
        });
    }
    private  void  setMenu(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Toast.makeText(MainActivity.this,"Hi",Toast.LENGTH_SHORT).show();

        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    public void addNewDependentToDrawer(String dependentName) {
        // add new dependent to drawer

        Menu mMenu = navigationView.getMenu();

        MenuItem m = mMenu.findItem(R.id.Profiles);
        Menu menuSun = m.getSubMenu();
        menuSun.removeItem(3);
        menuSun.add(R.id.G1,3,500,dependentName);


    }
}
