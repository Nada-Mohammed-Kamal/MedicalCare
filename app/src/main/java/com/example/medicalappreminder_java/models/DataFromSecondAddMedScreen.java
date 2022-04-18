package com.example.medicalappreminder_java.models;

import com.example.medicalappreminder_java.Constants.Form;
import com.example.medicalappreminder_java.Constants.Strength;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class DataFromSecondAddMedScreen implements Serializable {
    String whatReasonToTake;
    Date startDate;
    Date endDate;
    int howManyTimes;
    String selected_val;
    ArrayList<DateTime> dateTimes;

    String medName;
    Form formMed;
    Strength strength;
    int strengthAmount;

    public String getWhatReasonToTake() {
        return whatReasonToTake;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getHowManyTimes() {
        return howManyTimes;
    }

    public String getSelected_val() {
        return selected_val;
    }

    public ArrayList<DateTime> getDateTimes() {
        return dateTimes;
    }

    public String getMedName() {
        return medName;
    }

    public Form getFormMed() {
        return formMed;
    }

    public Strength getStrength() {
        return strength;
    }

    public int getStrengthAmount() {
        return strengthAmount;
    }

    public DataFromSecondAddMedScreen(String whatReasonToTake, Date startDate, Date endDate, int howManyTimes, String selected_val, ArrayList<DateTime> dateTimes, String medName, Form formMed, Strength strength, int strengthAmount) {
        super();
        this.whatReasonToTake = whatReasonToTake;
        this.startDate = startDate;
        this.endDate = endDate;
        this.howManyTimes = howManyTimes;
        this.selected_val = selected_val;
        this.dateTimes = dateTimes;
        this.medName = medName;
        this.formMed = formMed;
        this.strength = strength;
        this.strengthAmount = strengthAmount;
    }
}
