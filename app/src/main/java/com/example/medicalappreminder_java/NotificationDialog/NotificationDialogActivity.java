package com.example.medicalappreminder_java.NotificationDialog;

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

import com.example.medicalappreminder_java.FireBaseModels.FireStore.FireStoreHandler;
import com.example.medicalappreminder_java.Form;
import com.example.medicalappreminder_java.Medicine;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.Strength;
import com.example.medicalappreminder_java.User;


public class NotificationDialogActivity extends AppCompatActivity {

    Button openDialogeButton  , notifyButton;
    Dialog dialog ;
    private final String CHANNEL_ID = "ID";
    NotificationManager notificationManager;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_dialog);

        notifyButton = findViewById(R.id.notifyButton) ;
        openDialogeButton = findViewById(R.id.openDialogeButton) ;
        dialog = new Dialog(this) ;

        openDialogeButton.setOnClickListener(view -> {
            Log.e("xxx", "onClick: ");
            openDialoge() ;

        });

        notifyButton.setOnClickListener(view -> {notifyMe();});

    }



    private void openDialoge() {

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

