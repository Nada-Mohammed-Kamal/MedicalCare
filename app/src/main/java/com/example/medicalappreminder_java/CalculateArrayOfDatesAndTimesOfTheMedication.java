package com.example.medicalappreminder_java;


import android.util.Log;

import com.example.medicalappreminder_java.Constants.EveryHowManyDaysWilltheMedBeTaken;
import com.example.medicalappreminder_java.Constants.Status;
import com.example.medicalappreminder_java.models.CustomTime;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class CalculateArrayOfDatesAndTimesOfTheMedication {
    private List<CustomTime> doseTimes;
    private List<Date> dates;
    private Date startDate , endDate;
    private int numberOfTimesInADay;
    private String TAG = "OUTPUT";
    private int forHowLongWillTheMedBeTaken;
    private EveryHowManyDaysWilltheMedBeTaken everyHowManyDaysWillTheMedBeTaken;

    public CalculateArrayOfDatesAndTimesOfTheMedication(int forHowLongWillTheMedBeTaken ,List<CustomTime> doseTimes, List<Date> dates, Date startDate, Date endDate, int numberOfTimesInADay , EveryHowManyDaysWilltheMedBeTaken everyHowManyDaysWillTheMedBeTaken) {
        this.doseTimes = doseTimes;
        this.dates = dates;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfTimesInADay = numberOfTimesInADay;
        this.everyHowManyDaysWillTheMedBeTaken = everyHowManyDaysWillTheMedBeTaken;
        this.forHowLongWillTheMedBeTaken = forHowLongWillTheMedBeTaken;


        //remove those and call them when needed or call the vars directly.
        //to get the list of dates
        dates = getDaysBetweenDates(startDate, endDate);
        forHowLongWillTheMedBeTaken = dates.size();
        dates = getOnlyTheDaysToTakeTheMedInFromTheInterval(dates, everyHowManyDaysWillTheMedBeTaken);

        //times
        //doseTimes = getTheNumberOfTimesAndTheExactTimesInADay(3);
    }

    public List<Date> getDates() {
        return dates;
    }

    public int getForHowLongWillTheMedBeTaken() {
        return forHowLongWillTheMedBeTaken;
    }

    public CalculateArrayOfDatesAndTimesOfTheMedication(Date startDate, Date endDate, EveryHowManyDaysWilltheMedBeTaken everyHowManyDaysWillTheMedBeTaken , List<CustomTime> times) {
            startDate = startDate;
            this.endDate = endDate;
            this.everyHowManyDaysWillTheMedBeTaken = everyHowManyDaysWillTheMedBeTaken;


            Calendar calendar = new GregorianCalendar();
            dates = getDaysBetweenDates(startDate, endDate);
            forHowLongWillTheMedBeTaken = dates.size();
            dates = getOnlyTheDaysToTakeTheMedInFromTheInterval(dates,everyHowManyDaysWillTheMedBeTaken);

//        List<CustomTime> timeStatusHashMap = new ArrayList<>();
//        for(int i = 0 ; i < times.size() ; i++){
//            timeStatusHashMap.put(times.get(i) , Status.notItsTimeYet);
//        }
//        doseTimes = timeStatusHashMap;
        }

    public List<CustomTime> getDoseTimes() {
        return doseTimes;
    }

    public List<Date> getDaysBetweenDates (Date startdate, Date enddate)
        {
            List<Date> dates = new ArrayList<>();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(startdate);

            while (calendar.getTime().before(enddate)) {
                Date result = calendar.getTime();
                dates.add(result);
                calendar.add(Calendar.DATE, 1);
            }
            return dates;
        }


    public List<Date> getOnlyTheDaysToTakeTheMedInFromTheInterval(List<Date> list, EveryHowManyDaysWilltheMedBeTaken everyHowManyDaysWillTheMedBeTaken){
        List<Date> datesFiltered = new ArrayList<>();
        switch (everyHowManyDaysWillTheMedBeTaken){
            case Every_day:
                return list;
            case Every_two_days:
                for (int i =0 ; i< list.size() ; i++) {
                    if(i%2 == 0){
                        datesFiltered.add(list.get(i));
                    }
                }
                return datesFiltered;
            case Every_three_days:
                for (int i =0 ; i< list.size() ; i++) {
//                    if(i == 0){
//                        datesFiltered.add(list.get(i));
//                    }
                    if(i % 3 == 0){
                        datesFiltered.add(list.get(i));
                    }
                }
                return datesFiltered;
            case Every_four_days:
                for (int i =0 ; i< list.size() ; i++) {
//                    if(i == 0){
//                        datesFiltered.add(list.get(i));
//                    }
                    if(i % 4 == 0){
                        datesFiltered.add(list.get(i));
                    }
                }
                return datesFiltered;
            case Every_five_days:
                for (int i =0 ; i< list.size() ; i++) {
//                    if(i == 0){
//                        datesFiltered.add(list.get(i));
//                    }
                    if(i % 5 == 0){
                        datesFiltered.add(list.get(i));
                    }
                }
                return datesFiltered;
            case Every_six_days:
                for (int i =0 ; i< list.size() ; i++) {

                    if(i % 6 == 0){
                        datesFiltered.add(list.get(i));
                    }
                }
                return datesFiltered;
        }
        return datesFiltered;
        //de keda list bal 2ayam bta3et al dawa da
    }


    //de btgeeb al mawa3eed balzabt fal yoom
    public HashMap<CustomTime, Status> getTheNumberOfTimesAndTheExactTimesInADay(int numOfTimesAsInt){
        HashMap<CustomTime , Status> timesAndStatus = new HashMap<>();
        for (int i = 0; i < numOfTimesAsInt; i++) {
            //bna5ol number of clocks bal da5el
            CustomTime time = new CustomTime(3,40);
            timesAndStatus.put(time , Status.notItsTimeYet);
        }
        return timesAndStatus;
    }

}
