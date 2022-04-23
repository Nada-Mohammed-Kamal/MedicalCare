package com.example.medicalappreminder_java.roomdb;

import com.example.medicalappreminder_java.models.CustomTime;
import com.example.medicalappreminder_java.models.Medicine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserData {
    public static List<Medicine> getTodayMedicineswithTimeSorted(List<Medicine> userMedicines, Date todayDate)
    {
        List<Medicine> toDayMedicines = new ArrayList<>();
        for (Medicine currentUserMed:userMedicines) {
            List<Date> daysThatTheMedWillBeTakenIn = currentUserMed.getDaysThatTheMedWillBeTakenIn();
            for (Date medicineDayes:daysThatTheMedWillBeTakenIn) {
                if(medicineDayes.equals(todayDate)){
                    Collections.sort(currentUserMed.getDoseTimes());
                    toDayMedicines.add(currentUserMed);
                    break;
                }
            }
        }
        return toDayMedicines;
    }
    public static List<CustomTime>getTodayesTimesOfDoses(List<Medicine> todayMedicines) {
        Set<CustomTime> todayesTimes = new HashSet<>();
        for (Medicine medicine:todayMedicines) {
            List<CustomTime> doseTimes = medicine.getDoseTimes();
            for (CustomTime time:doseTimes) {
                todayesTimes.add(time);
            }
        }
        List<CustomTime> arr = new ArrayList<>(todayesTimes);
        Collections.sort(arr);
        return arr;
    }
}
