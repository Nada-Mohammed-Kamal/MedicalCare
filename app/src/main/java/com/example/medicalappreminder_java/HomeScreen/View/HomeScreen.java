package com.example.medicalappreminder_java.HomeScreen.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.medicalappreminder_java.AddMedicine.View.AddMedicine;
import com.example.medicalappreminder_java.DataStorage.SharedPrefrencesModel;
import com.example.medicalappreminder_java.Login.LoginView.LoginActivity;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.dependantInfo.view.DependentInfoFragment;
import com.example.medicalappreminder_java.models.User;
import com.example.medicalappreminder_java.roomdb.AppDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class HomeScreen extends AppCompatActivity {
    //AppDatabase db = AppDatabase.getDBInstance(getApplicationContext());
    //List<User> allUsers = db.userDao().getAllUsers();


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    MenuItem menuItem;
    private FragmentManager fragmentManager;
    FragmentTransaction transaction;
    DependentInfoFragment dependentInfoFragment;
    ExtendedFloatingActionButton extendedFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initComponents();
    }
    private void initComponents(){


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Esraa");
        setSupportActionBar(toolbar);
        setMenu();
        setListeners();
        BottomNavigationView navView = findViewById(R.id.bottomnavigation);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.home,R.id.medication,R.id.Refills)
                .build();
        NavController navController2 = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(navView, navController2);
       // NavigationUI.setupWithNavController(navigationView, navController2);

       // extendedFloatingActionButton = findViewById(R.id.ExtendedFloatingActionButtonAddMed);
            // do something with f

        findViewById(R.id.ExtendedFloatingActionButtonAddMed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this , AddMedicine.class));

            }
        });
    }
    private  void  setListeners(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                toolbar.setTitle(item.getTitle());
                switch (item.getItemId())
                {
                    case R.id.add_dependent:
                        Toast.makeText(HomeScreen.this,"logOutButton",Toast.LENGTH_SHORT).show();
                       // Navigation.findNavController(navigationView).navigate(R.id.);
                        fragmentManager = getSupportFragmentManager();
                        dependentInfoFragment = new DependentInfoFragment();
                        transaction = fragmentManager.beginTransaction();
                        transaction.setReorderingAllowed(true);
                        transaction.add(R.id.nav_host_fragment_activity_main,dependentInfoFragment,"dependent");
                        transaction.commit();
                        break;
                    case R.id.logOutButton:
                        SharedPrefrencesModel sharedPrefrencesModel = SharedPrefrencesModel.getInstance(HomeScreen.this);
                        sharedPrefrencesModel.writeInSharedPreferences(null,null);
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

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

                if(menuItem != null)
                    menuItem.setChecked(false);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

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
}
