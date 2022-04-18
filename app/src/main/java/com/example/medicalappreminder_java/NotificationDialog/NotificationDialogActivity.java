package com.example.medicalappreminder_java.NotificationDialog;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;


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

import com.example.medicalappreminder_java.Constants.Form;
import com.example.medicalappreminder_java.Constants.Strength;
import com.example.medicalappreminder_java.FireBaseModels.FireStore.FireStoreHandler;

import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class NotificationDialogActivity extends AppCompatActivity {

    Button openDialogeButton  , notifyButton , storeButton ;
    Dialog dialog ;
    private final String CHANNEL_ID = "ID";
    NotificationManager notificationManager;
    FireStoreHandler fireStoreHandler ;

    User user ;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_dialog);

        notifyButton = findViewById(R.id.notifyButton) ;
        openDialogeButton = findViewById(R.id.openDialogeButton) ;
        storeButton = findViewById(R.id.storeButton) ;
        dialog = new Dialog(this) ;
        fireStoreHandler = new FireStoreHandler() ;

        user = new User();
        user.setFirstName("moe");
        user.setLastName("mostafa");
        user.setGender("male");

        Medicine medicine = new Medicine() ;
        medicine.setDose_howOften("every 2 days ");
        medicine.setName("panadol");
        medicine.setHowManyTimesWillItBeTakenInADay(2);
        medicine.setCondition("headache");
        medicine.setInstructions("instructions");
        medicine.setState("state");
        medicine.setRxNumber("20");
        medicine.setNumberOfPillsLeft(4.0);
        medicine.setStrength(Strength.g);
        medicine.setForm(Form.Pill);
        medicine.setHasRefillReminder(true);
        medicine.setTotalNumOfPills(20);
        medicine.setImage(R.drawable.inhaler);
        //  medicine.setDoseTimes(dates);
        medicine.setDose_howOften("twice daily");
        medicine.setEveryday(true);

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NotificationDialogActivity.this, "stooooore", Toast.LENGTH_SHORT).show();
                fireStoreHandler.addUserToFireStore(user);
                fireStoreHandler.addMedicineToFireStore(medicine);
            }
        });


        openDialogeButton.setOnClickListener(view -> {
            Log.e("xxx", "onClick: ");
            openDialoge() ;
            //fireStoreHandler.addUserToFireStore(user);


        });

        notifyButton.setOnClickListener(view -> {notifyMe();});

    }


    private void openDialoge() {

        //dialog.setContentView(R.layout.drug_notification_dialog);
        dialog.setContentView(R.layout.drug_notification_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button skipButton , takeButton , snoozeButton ;
        TextView timeTextView , drugNameTextView  ,drugDescrTextView ;
        ImageView drugIconImageView ;

        // get ids of dialog
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

}

