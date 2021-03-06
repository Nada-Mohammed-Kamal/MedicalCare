package com.example.medicalappreminder_java.models;

import android.text.format.Time;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.example.medicalappreminder_java.Constants.EveryHowManyDaysWilltheMedBeTaken;
import com.example.medicalappreminder_java.Constants.Form;
import com.example.medicalappreminder_java.Constants.Status;
import com.example.medicalappreminder_java.Constants.Strength;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.Exclude;
import com.google.gson.Gson;


import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import kotlin.jvm.JvmStatic;

@Entity
public class Medicine implements Serializable {
    @ColumnInfo(name = "id")
    @PrimaryKey
    @NonNull
    @Exclude

    private String uuid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "form")
    private Form form;

    @ColumnInfo(name = "strength")
    private Strength strength;

    @ColumnInfo(name = "StrengthAmount")
    private int StrengthAmount;

    @ColumnInfo(name = "numberOfPillsLeft")
    private double numberOfPillsLeft;//(auto dec ma3 kol mara na5od al 7abaya w initially hatab2a bt equal al total),

    @ColumnInfo(name = "image")
    private int image;

    @ColumnInfo(name = "isEveryday")
    private boolean isEveryday;

    @ColumnInfo(name = "condition")
    private String condition;

    @ColumnInfo(name = "Dose_howOften")
    private EveryHowManyDaysWilltheMedBeTaken Dose_howOften;

    @ColumnInfo(name = "startDate")
    private Date startDate;

    @ColumnInfo(name = "endDate")
    private Date endDate;

    @ColumnInfo(name = "fowHowManyDaysIsTheMedicineGoingToBeTaken")
    //msh h3mlha
    private int fowHowManyDaysIsTheMedicineGoingToBeTaken;

    @ColumnInfo(name = "totalNumOfPills")
    //not used
    private int totalNumOfPills;

    @ColumnInfo(name = "howManyTimesWillItBeTakenInADay")
    private int howManyTimesWillItBeTakenInADay;

    @ColumnInfo(name = "instructions")
    private String instructions;

    @ColumnInfo(name = "state")
    private String state;//active , inactive

    @ColumnInfo(name = "doseTimes")
    private List<CustomTime> doseTimes;

    @ColumnInfo(name = "hasRefillReminder")
    private boolean hasRefillReminder;

    @ColumnInfo(name = "timeToShowTheReminderIn")
    private Date timeToShowTheReminderIn;

    @ColumnInfo(name = "rxNumber")
    //not used
    private String rxNumber;

    @ColumnInfo(name = "remindMeWhenIHaveHowManyPillsLeft")
    private double remindMeWhenIHaveHowManyPillsLeft;

    @ColumnInfo(name = "fireStoreId")
    private String fireStoreId;

    public String getFireStoreId() {
        return fireStoreId;
    }

    public void setFireStoreId(String fireStoreId) {
        this.fireStoreId = fireStoreId;
    }

    //modified
    @ColumnInfo(name = "daysThatTheMedWillBeTakenIn")
    private List<Date> daysThatTheMedWillBeTakenIn;

    @ColumnInfo(name = "numberOfPillsInOneTime")
    private int numberOfPillsInOneTime;

    public Medicine() {
    }

    public int getFowHowManyDaysIsTheMedicineGoingToBeTaken() {
        return fowHowManyDaysIsTheMedicineGoingToBeTaken;
    }

    public void setFowHowManyDaysIsTheMedicineGoingToBeTaken(int fowHowManyDaysIsTheMedicineGoingToBeTaken) {
        this.fowHowManyDaysIsTheMedicineGoingToBeTaken = fowHowManyDaysIsTheMedicineGoingToBeTaken;
    }

    public double getRemindMeWhenIHaveHowManyPillsLeft() {
        return remindMeWhenIHaveHowManyPillsLeft;
    }

    public void setRemindMeWhenIHaveHowManyPillsLeft(double remindMeWhenIHaveHowManyPillsLeft) {
        this.remindMeWhenIHaveHowManyPillsLeft = remindMeWhenIHaveHowManyPillsLeft;
    }

    public Medicine(@NonNull String uuid, String name, Form form, Strength strength, int strengthAmount, double numberOfPillsLeft, int image, boolean isEveryday, String condition, EveryHowManyDaysWilltheMedBeTaken dose_howOften, Date startDate, Date endDate, int fowHowManyDaysIsTheMedicineGoingToBeTaken, int totalNumOfPills, int howManyTimesWillItBeTakenInADay, String instructions, String state, List<CustomTime> doseTimes, boolean hasRefillReminder, Date timeToShowTheReminderIn, String rxNumber, double remindMeWhenIHaveHowManyPillsLeft, List<Date> daysThatTheMedWillBeTakenIn, int numberOfPillsInOneTime) {
        this.uuid = uuid;
        this.name = name;
        this.form = form;
        this.strength = strength;
        StrengthAmount = strengthAmount;
        this.numberOfPillsLeft = numberOfPillsLeft;
        this.image = image;
        this.isEveryday = isEveryday;
        this.condition = condition;
        Dose_howOften = dose_howOften;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fowHowManyDaysIsTheMedicineGoingToBeTaken = fowHowManyDaysIsTheMedicineGoingToBeTaken;
        this.totalNumOfPills = totalNumOfPills;
        this.howManyTimesWillItBeTakenInADay = howManyTimesWillItBeTakenInADay;
        this.instructions = instructions;
        this.state = state;
        this.doseTimes = doseTimes;
        this.hasRefillReminder = hasRefillReminder;
        this.timeToShowTheReminderIn = timeToShowTheReminderIn;
        this.rxNumber = rxNumber;
        this.remindMeWhenIHaveHowManyPillsLeft = remindMeWhenIHaveHowManyPillsLeft;
        this.daysThatTheMedWillBeTakenIn = daysThatTheMedWillBeTakenIn;
        this.numberOfPillsInOneTime = numberOfPillsInOneTime;
    }

    public int getNumberOfPillsInOneTime() {
        return numberOfPillsInOneTime;
    }

    public void setNumberOfPillsInOneTime(int numberOfPillsInOneTime) {
        this.numberOfPillsInOneTime = numberOfPillsInOneTime;
    }

    public List<Date> getDaysThatTheMedWillBeTakenIn() {
        return daysThatTheMedWillBeTakenIn;
    }

    public void setDaysThatTheMedWillBeTakenIn(List<Date> daysThatTheMedWillBeTakenIn) {
        this.daysThatTheMedWillBeTakenIn = daysThatTheMedWillBeTakenIn;
    }

    public Medicine(String uuid , String name, Form form, Strength strength, int strengthAmount, double numberOfPillsLeft, int image, boolean isEveryday, String condition, EveryHowManyDaysWilltheMedBeTaken dose_howOften, Date startDate, Date endDate, int totalNumOfPills, int howManyTimesWillItBeTakenInADay, String instructions, String state, List<CustomTime> doseTimes, boolean hasRefillReminder, Date timeToShowTheReminderIn, String rxNumber ,List<Date> daysThatTheMedWillBeTakenIn , int numberOfPillsInOneTime) {
        this.daysThatTheMedWillBeTakenIn = daysThatTheMedWillBeTakenIn;
        this.uuid = uuid;
        this.name = name;
        this.form = form;
        this.numberOfPillsInOneTime = numberOfPillsInOneTime;
        this.strength = strength;
        StrengthAmount = strengthAmount;
        this.numberOfPillsLeft = numberOfPillsLeft;
        this.image = image;
        this.isEveryday = isEveryday;
        this.condition = condition;
        Dose_howOften = dose_howOften;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalNumOfPills = totalNumOfPills;
        this.howManyTimesWillItBeTakenInADay = howManyTimesWillItBeTakenInADay;
        this.instructions = instructions;
        this.state = state;
        this.doseTimes = doseTimes;
        this.hasRefillReminder = hasRefillReminder;
        this.timeToShowTheReminderIn = timeToShowTheReminderIn;
        this.rxNumber = rxNumber;
    }

    public Medicine(String uuid , String name, Form form, Strength strength, int strengthAmount, double numberOfPillsLeft , int image) {
        this.uuid = uuid;
        this.name = name;
        this.form = form;
        this.strength = strength;
        StrengthAmount = strengthAmount;
        this.numberOfPillsLeft = numberOfPillsLeft;
        this.image = image;
    }

    public Medicine(String name, Form form, Strength strength, int strengthAmount, double numberOfPillsLeft, int image, boolean isEveryday, EveryHowManyDaysWilltheMedBeTaken dose_howOften, Date startDate, Date endDate, int fowHowManyDaysIsTheMedicineGoingToBeTaken, int howManyTimesWillItBeTakenInADay, String instructions, String state, List<CustomTime> doseTimes, boolean hasRefillReminder, double remindMeWhenIHaveHowManyPillsLeft, List<Date> daysThatTheMedWillBeTakenIn) {
        super();
        uuid = UUID.randomUUID().toString();
        this.name = name;
        this.form = form;
        this.strength = strength;
        StrengthAmount = strengthAmount;
        this.numberOfPillsLeft = numberOfPillsLeft;
        this.image = image;
        this.isEveryday = isEveryday;
        Dose_howOften = dose_howOften;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fowHowManyDaysIsTheMedicineGoingToBeTaken = fowHowManyDaysIsTheMedicineGoingToBeTaken;
        this.howManyTimesWillItBeTakenInADay = howManyTimesWillItBeTakenInADay;
        this.instructions = instructions;
        this.state = state;
        this.doseTimes = doseTimes;
        this.hasRefillReminder = hasRefillReminder;
        this.remindMeWhenIHaveHowManyPillsLeft = remindMeWhenIHaveHowManyPillsLeft;
        this.daysThatTheMedWillBeTakenIn = daysThatTheMedWillBeTakenIn;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isEveryday() {
        return isEveryday;
    }

    public void setEveryday(boolean everyday) {
        isEveryday = everyday;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public EveryHowManyDaysWilltheMedBeTaken getDose_howOften() {
        return Dose_howOften;
    }

    public void setDose_howOften(EveryHowManyDaysWilltheMedBeTaken dose_howOften) {
        Dose_howOften = dose_howOften;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getTotalNumOfPills() {
        return totalNumOfPills;
    }

    public void setTotalNumOfPills(int totalNumOfPills) {
        this.totalNumOfPills = totalNumOfPills;
    }

    public int getHowManyTimesWillItBeTakenInADay() {
        return howManyTimesWillItBeTakenInADay;
    }

    public void setHowManyTimesWillItBeTakenInADay(int howManyTimesWillItBeTakenInADay) {
        this.howManyTimesWillItBeTakenInADay = howManyTimesWillItBeTakenInADay;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<CustomTime> getDoseTimes() {
        return doseTimes;
    }

    public void setDoseTimes(List<CustomTime> doseTimes) {
        this.doseTimes = doseTimes;
    }

    public boolean isHasRefillReminder() {
        return hasRefillReminder;
    }

    public void setHasRefillReminder(boolean hasRefillReminder) {
        this.hasRefillReminder = hasRefillReminder;
    }

    /*
    public boolean getHasRefillReminder(){
        return this.hasRefillReminder;
    }
    */

    public Date getTimeToShowTheReminderIn() {
        return timeToShowTheReminderIn;
    }

    public void setTimeToShowTheReminderIn(Date timeToShowTheReminderIn) {
        this.timeToShowTheReminderIn = timeToShowTheReminderIn;
    }

    public String getRxNumber() {
        return rxNumber;
    }

    public void setRxNumber(String rxNumber) {
        this.rxNumber = rxNumber;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Strength getStrength() {
        return strength;
    }

    public void setStrength(Strength strength) {
        this.strength = strength;
    }

    public int getStrengthAmount() {
        return StrengthAmount;
    }

    public void setStrengthAmount(int strengthAmount) {
        StrengthAmount = strengthAmount;
    }

    public double getNumberOfPillsLeft() {
        return numberOfPillsLeft;
    }

    public void setNumberOfPillsLeft(double numberOfPillsLeft) {
        this.numberOfPillsLeft = numberOfPillsLeft;
    }
}
