package com.example.medicalappreminder_java.models;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.medicalappreminder_java.Constants.WorkerUtils;
import com.example.medicalappreminder_java.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
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

        int img = data.getInt(WorkerUtils.MED_IMG, 0);
        int howOften = data.getInt(WorkerUtils.MED_HOW_OFTEN, 0) + 1;
        String name = data.getString(WorkerUtils.MED_NAME);
        String medId = data.getString(WorkerUtils.MED_ID);
        String doseTime = data.getString(WorkerUtils.MED_DOSE_TIME);
        String endDate = data.getString(WorkerUtils.MED_END_DATE);

        displayNotification(String.valueOf(cnt), name, "You need to take your " + name + " now !!", img);


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, howOften);

        Date nxtDate = cal.getTime();
        Date _endDate = null;
        try {
            _endDate = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("M3lsh", "doWork: nxtDate: " + nxtDate.toString());
        Log.i("M3lsh", "doWork: condition " + nxtDate.before(_endDate));

        WorkerUtils.deleteRequestFromWorkManagerByReq(getId());

        if (nxtDate.before(_endDate)) {

            Log.i("M3lsh", "doWork: ");
            OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MedicineWorker.class)
                    .setInitialDelay(howOften, TimeUnit.DAYS)
                    .setInputData(getInputData())
                    .build();

            WorkerUtils.pushNewRequestID(request.getId(), String.valueOf(UUID.fromString(medId)), doseTime);
            WorkManager.getInstance().enqueue(request);
        }

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
