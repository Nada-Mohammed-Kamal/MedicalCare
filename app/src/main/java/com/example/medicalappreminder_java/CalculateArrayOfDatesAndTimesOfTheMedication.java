package com.example.medicalappreminder_java;

import android.text.format.Time;
import android.util.Log;

import com.example.medicalappreminder_java.Constants.Status;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class CalculateArrayOfDatesAndTimesOfTheMedication {
    HashMap<Time , Status> doseTimes;
    List<Date> dates;

    Date StartDate , endDate;

    int numberOfTimesInADay;

    String TAG = "OUTPUT";

    public CalculateArrayOfDatesAndTimesOfTheMedication(HashMap<Time, Status> doseTimes, List<Date> dates, Date startDate, Date endDate, int numberOfTimesInADay) {
        this.doseTimes = doseTimes;
        this.dates = dates;
        StartDate = startDate = new Date(2-12-2020);
        this.endDate = endDate = new Date(7-12-2020);
        this.numberOfTimesInADay = numberOfTimesInADay;

        Calendar calendar = new GregorianCalendar();
        dates = getDaysBetweenDates(startDate , endDate);

        for (Date date:dates) {
            Log.i(TAG, "CalculateArrayOfDatesAndTimesOfTheMedication: " + date);
        }
    }

    public CalculateArrayOfDatesAndTimesOfTheMedication(Date startDate, Date endDate) {
        StartDate = startDate;
        this.endDate = endDate;
        Calendar calendar = new GregorianCalendar();
        dates = getDaysBetweenDates(startDate , endDate);
    }

    public List<Date> getDaysBetweenDates(Date startdate, Date enddate)
    {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate))
        {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }
}
