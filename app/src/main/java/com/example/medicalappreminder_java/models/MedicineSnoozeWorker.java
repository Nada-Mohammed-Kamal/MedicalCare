package com.example.medicalappreminder_java.models;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.medicalappreminder_java.Constants.WorkerUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MedicineSnoozeWorker extends Worker {
    public static int cnt = Integer.MAX_VALUE;
    public MedicineSnoozeWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        cnt--;
    }

    @Override
    public Result doWork() {

        Data data = getInputData();

        int img = data.getInt(WorkerUtils.MED_IMG, 0);
        String name = data.getString(WorkerUtils.MED_NAME);

        displayNotification(String.valueOf(cnt), name, "Snooze, You need to take your " + name + " now !!", img);

        WorkerUtils.deleteRequestFromWorkManagerByReq(getId());

        return Result.success();
    }

    private void displayNotification (String id, String task, String  desc, int imgID){

        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = channelName;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(imgID)
                .setContentTitle(task)
                .setContentText(desc)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(Integer.valueOf(id), builder.build());

        Log.i("M3lsh", "displaying notification" );

    }

}
