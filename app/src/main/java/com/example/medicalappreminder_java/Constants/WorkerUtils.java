package com.example.medicalappreminder_java.Constants;

import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.medicalappreminder_java.models.CustomTime;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.MedicineWorker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class WorkerUtils {
    public static final String TAG = "M3lsh";
    public static final String MED_ID = "MED_ID";
    public static final String MED_DOSE_TIME = "MED_DOSE_TIME";
    public static final String MED_IMG = "MED_IMG";
    public static final String MED_NAME = "MED_NAME";
    public static final String MED_HOW_OFTEN = "MED_HOW_OFTEN";
    public static final String MED_END_DATE = "MED_END_DATE";

    public static ArrayList<String> wmRequestIds = new ArrayList<String>();

    public static long findDifference(String start_date, String end_date) {
        SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String result = "";
        long difference_In_Seconds = 0;
        long difference_In_Minutes = 0;
        long difference_In_Hours = 0;
        long difference_In_Years = 0;
        try {
            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);

            long difference_In_Time= d2.getTime() - d1.getTime();

            difference_In_Seconds = (difference_In_Time / 1000) % 60;


            difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;


            difference_In_Hours = (difference_In_Time / (1000 * 60 * 60))% 24;


            difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));

            System.out.print("Difference " + "between two dates is: ");

            long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
            result = difference_In_Years
                    + " years, "
                    + difference_In_Days
                    + " days, "
                    + difference_In_Hours
                    + " hours, "
                    + difference_In_Minutes
                    + " minutes, "
                    + difference_In_Seconds
                    + " seconds";

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        long res = (difference_In_Hours * 60 * 60) + (difference_In_Minutes * 60)+ difference_In_Seconds;
        return res;
    }

    public static String convertCustomTimeIntoTimeString(CustomTime ct) {
        String hour = String.valueOf(ct.getHour());
        String minute = String.valueOf(ct.getMinute());

        if(hour.length() == 1)
        {
            hour = "0" + hour;
        }
        if (minute.length() == 1)
        {
            minute = "0" + minute;
        }
        return hour + ":" + minute + ":00";
    }

    public static void pushNewRequestID(UUID reqID, String medID, String time) {
        wmRequestIds.add(reqID.toString() + "_" + medID.toString() + "_" + time);
    }

    public static void addRequestsToWorkManager(Medicine med) {
        SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String timeNow = sdf.format(Calendar.getInstance().getTime());
        String endDate = sdf.format(med.getEndDate());

        Log.i(TAG, "Current Date: " + timeNow);
        Log.i(TAG, "End Date: " + endDate);

        for(CustomTime ct : med.getDoseTimes())
        {
            String time = WorkerUtils.convertCustomTimeIntoTimeString(ct);

            Data data = new Data.Builder()
                    .putInt(WorkerUtils.MED_IMG, med.getImage())
                    .putInt(WorkerUtils.MED_HOW_OFTEN, med.getDose_howOften().ordinal())
                    .putString(WorkerUtils.MED_NAME, med.getName())
                    .putString(WorkerUtils.MED_ID, med.getUuid().toString())
                    .putString(WorkerUtils.MED_DOSE_TIME, time)
                    .putString(WorkerUtils.MED_END_DATE, endDate)
                    .build();


            String startDate = new SimpleDateFormat("dd-MM-yyyy").format(med.getStartDate())
                    + " "
                    + time;

            long res = WorkerUtils.findDifference(timeNow, startDate);
            Log.i(TAG, "Start Date: " + startDate);
            Log.i(TAG, "StartDate - Current Date = " + String.valueOf(res));


            OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MedicineWorker.class)
                    .setInitialDelay(res, TimeUnit.SECONDS)
                    .setInputData(data)
                    .build();

            WorkerUtils.pushNewRequestID(request.getId(), med.getUuid(), time);
            WorkManager.getInstance().enqueue(request);
        }
    }
}
