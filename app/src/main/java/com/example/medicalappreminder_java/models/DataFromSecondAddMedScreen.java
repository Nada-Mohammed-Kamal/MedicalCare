package com.example.medicalappreminder_java.models;

import com.example.medicalappreminder_java.Constants.EveryHowManyDaysWilltheMedBeTaken;
import com.example.medicalappreminder_java.Constants.Form;
import com.example.medicalappreminder_java.Constants.Strength;
import com.example.medicalappreminder_java.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class DataFromSecondAddMedScreen implements Serializable {
    String whatReasonToTake;
    Date startDate;
    Date endDate;
    int howManyTimes;
    EveryHowManyDaysWilltheMedBeTaken everyHowManyDaysWilltheMedBeTaken;
    ArrayList<CustomTime> customTimes;

    String medName;
    Form formMed;
    Strength strength;
    int strengthAmount;
    int img;
    boolean isEveryDay;

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

    public EveryHowManyDaysWilltheMedBeTaken getEveryHowManyDaysWilltheMedBeTaken() {
        return everyHowManyDaysWilltheMedBeTaken;
    }

    public ArrayList<CustomTime> getCustomTimes() {
        return customTimes;
    }

    public ArrayList<CustomTime> getDateTimes() {
        return customTimes;
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

    public int getImg() {
        return img;
    }

    public boolean isEveryDay() {
        return isEveryDay;
    }

    public DataFromSecondAddMedScreen(String whatReasonToTake, Date startDate, Date endDate, int howManyTimes, EveryHowManyDaysWilltheMedBeTaken everyHowManyDaysWilltheMedBeTaken, ArrayList<CustomTime> customTimes, String medName, Form formMed, Strength strength, int strengthAmount) {
        super();
        this.whatReasonToTake = whatReasonToTake;
        this.startDate = startDate;
        this.endDate = endDate;
        this.howManyTimes = howManyTimes;
        this.everyHowManyDaysWilltheMedBeTaken = everyHowManyDaysWilltheMedBeTaken;
        this.customTimes = customTimes;
        this.medName = medName;
        this.formMed = formMed;
        this.strength = strength;
        this.strengthAmount = strengthAmount;

        if (formMed == Form.Pill ) {
                img = R.drawable.pill;
        }
        else if(formMed == Form.Drops){
            img = R.drawable.drops;
        }
        else if(formMed == Form.Inhaler){
            img = R.drawable.inhaler;
        }
        else if(formMed == Form.Powder){
            img = R.drawable.powder;
        }
        else if(formMed == Form.Injection){
            img = R.drawable.injection;
        }
        else if(formMed == Form.Solution){
            img = R.drawable.solution;
        }
        else {
            img = R.drawable.other_form;
        }
        if (everyHowManyDaysWilltheMedBeTaken == EveryHowManyDaysWilltheMedBeTaken.Every_day){
            isEveryDay = true;
        }
        else{
            isEveryDay = false;
        }
    }
}
