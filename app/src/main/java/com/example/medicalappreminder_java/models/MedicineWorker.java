package com.example.medicalappreminder_java.models;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MedicineWorker extends Worker {
    public static int cnt = 0;
    public MedicineWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        cnt++;
    }

    @Override
    public Result doWork() {
        Data data = getInputData();
        Medicine


        displayNotification(String.valueOf(cnt), "ProjectDemoTask", "A5eran l bta3a de esht9lt <3 !!", data.getInt("IMG", 700129));

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");



        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, 1);

        Date nxtDate = cal.getTime();
        Date endDate = null;
        try {
            endDate = sdf.parse(data.getString("END_DATE"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.i("M3lsh", "doWork: condition " + nxtDate.before(endDate));
        if (nxtDate.before(endDate)) {
            Log.i("M3lsh", "doWork: ");
            OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(ProjectWorker.class)
                    .setInitialDelay(1, TimeUnit.HOURS)
                    .setInputData(getInputData())
                    .build();

            WorkManager.getInstance().enqueue(request);
        }


        return Result.success();
    }
    private void displayNotification (String id, String task, String  desc, int imgID){
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(id, "Mohammad Amr", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "Mohammad Amr")
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(imgID);

        manager.notify( Integer.valueOf(id), builder.build());

    }
}
