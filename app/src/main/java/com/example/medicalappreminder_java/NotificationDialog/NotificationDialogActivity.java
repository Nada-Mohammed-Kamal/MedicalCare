package com.example.medicalappreminder_java.NotificationDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;


import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicalappreminder_java.Constants.EveryHowManyDaysWilltheMedBeTaken;
import com.example.medicalappreminder_java.Constants.Form;
import com.example.medicalappreminder_java.Constants.Strength;
import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;

import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.models.CustomTime;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NotificationDialogActivity extends AppCompatActivity implements Serializable /*, OnlineUsers*/ {

    Button openDialogeButton  , notifyButton , storeButton , getButton , updateButton , deleteButton ;
    TextView userName , medName ;
    Dialog dialog ;

    NotificationManager notificationManager;
    FireStoreHandler fireStoreHandler ;

    User user ;
    Medicine medicine ;
    List<User> usersList ;
    //List<Medicine> medicinesList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_dialog);

        init();
        settingUserDetails();
        settingMedicineDetails();

        //setObservers();

        
        //fireStoreHandler.getUsersFromFireStore();

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NotificationDialogActivity.this, "stooooore", Toast.LENGTH_SHORT).show();
                fireStoreHandler.addUserToFireStore(user);
                fireStoreHandler.addMedicineToFireStore(medicine);
            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "gettttt", Toast.LENGTH_SHORT).show();
//                fireStoreHandler.getUsersFromFireStore();
//                fireStoreHandler.getMedicinesFromFireStore() ;
                if (usersList.size() > 0){
                    userName.setText(usersList.get(0).getFirstName());
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        openDialogeButton.setOnClickListener(view -> {
            Log.e("xxx", "onClick: ");
            openDialoge() ;
        });

        notifyButton.setOnClickListener(view -> {notifyMe();});

    }

//    public void setObservers(){
//        // in fragment only : getLifecycleOwner
//        // removeObservers and pass life cycle owner
//        fireStoreHandler.getUserLiveData().observe(this, new Observer<List<User>>() {
//            @Override
//            public void onChanged(List<User> userList) {
//                // set ui
//                if (userList.size() > 0){
//                    userName.setText(userList.get(0).getFirstName());
//                }
//                //medName.setText(medicinesList.get(0).getName());
//            }
//        });
//
//        fireStoreHandler.getMedicineLiveData().observe(this, new Observer<List<Medicine>>() {
//            @Override
//            public void onChanged(List<Medicine> medicinesList) {
//                if (medicinesList.size() > 0){
//                    medName.setText(medicinesList.get(0).getName());
//                }
//            }
//        });
//    }

    public void init(){
        notifyButton = findViewById(R.id.notifyButton) ;
        openDialogeButton = findViewById(R.id.openDialogeButton) ;
        storeButton = findViewById(R.id.storeButton) ;
        getButton = findViewById(R.id.getButton) ;
        updateButton = findViewById(R.id.updateButton) ;
        deleteButton = findViewById(R.id.deleteButton) ;
        userName = findViewById(R.id.userNameTextViewww) ;
        medName = findViewById(R.id.medicineNameTextViewww) ;
        dialog = new Dialog(this) ;
        fireStoreHandler = new FireStoreHandler(this ) ;
    }

    public void settingUserDetails(){
        user = new User();
        user.setFirstName("samiiir");
        user.setLastName("mostafa");
        user.setGender("male");
        user.setEmail("nada@gmail.com");

    }

    public void settingMedicineDetails(){

        //medicine = new Medicine() ;
        //medicine.setDose_howOften("every 2 days ");


        medicine = new Medicine();
        medicine.setName("adol");

        medicine.setHowManyTimesWillItBeTakenInADay(2);
        medicine.setCondition("headacheeeeeee");
        medicine.setInstructions("instructions");
        medicine.setState("state");
        medicine.setRxNumber("20");
        medicine.setNumberOfPillsLeft(4.0);
        medicine.setStrength(Strength.g);
        medicine.setForm(Form.Pill);
        medicine.setHasRefillReminder(true);
        medicine.setTotalNumOfPills(20);
        medicine.setImage(R.drawable.inhaler);
        List<Date> dates = new ArrayList<>();
        dates.add(new Date(2-4-2020));
        dates.add(new Date(5-4-2020));
        medicine.setDaysThatTheMedWillBeTakenIn(dates);
        List<CustomTime> customTimes = new ArrayList<>();
        customTimes.add(new CustomTime(3,30));
        customTimes.add(new CustomTime(5,30));
        medicine.setDoseTimes(customTimes);
        medicine.setDose_howOften(EveryHowManyDaysWilltheMedBeTaken.Every_day);
        medicine.setEveryday(true);
    }

    private void openDialoge() {

        dialog.setContentView(R.layout.drug_notification_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button skipButton , takeButton , snoozeButton ;
        TextView timeTextView , drugNameTextView  ,drugDescrTextView ;
        ImageView drugIconImageView ;

        skipButton = dialog.findViewById(R.id.dialogSkipButton) ;
        takeButton = dialog.findViewById(R.id.dialogTakeButton);
        snoozeButton = dialog.findViewById(R.id.dialogSnoozeButton) ;
        timeTextView = dialog.findViewById(R.id.dialogTimeTextView) ;
        drugNameTextView = dialog.findViewById(R.id.dialogDrugNameTextView) ;
        drugDescrTextView = dialog.findViewById(R.id.dialogDrugDescrTextView) ;
        drugIconImageView = dialog.findViewById(R.id.dialogDrugIconimageView) ;

        drugNameTextView.setText("Panadol");
        drugDescrTextView.setText("500mg");

        dialog.show();
    }

    private void notifyMe() {

        String notificationMsg = "please take your medicine" ;
        createNotificationChannel("id","medNotification");
        Intent intent = new Intent(this, NotificationDialogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "id")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Medication Reminder")
                .setContentText(notificationMsg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notificationManager.notify(5, builder.build());

    }

    private void createNotificationChannel(String channelID , String channelName) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelID, channelName, importance);
            channel.setDescription("decription");
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

//    @Override
//    public void setOnlineUserList(List<User> convertedUserList) {
//        usersList = convertedUserList ;
//    }
}

