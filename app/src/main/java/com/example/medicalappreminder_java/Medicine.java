package com.example.medicalappreminder_java;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Medicine {
    //when setting the uuid we use
    //uuid = UUID.randomUUID();
    private UUID uuid;
    private String name;
    private Form form;
    private Strength strength;
    private int StrengthAmount;
    private double numberOfPillsLeft;//(auto dec ma3 kol mara na5od al 7abaya w initially hatab2a bt equal al total),
    private int image;
    private boolean isEveryday;
    private String condition;
    private String Dose_howOften;
    private Date startDate;
    private Date endDate;
    private int totalNumOfPills;
    private int howManyTimesWillItBeTakenInADay;
    private String instructions;
    private String state;
    private List<Date> doseTimes;
    private boolean hasRefillReminder;
    private Date timeToShowTheReminderIn;
    private String rxNumber;

    public Medicine() {
    }

    public Medicine(UUID id, String name, Form form, Strength strength, int strengthAmount, double numberOfPillsLeft, int image, boolean isEveryday, String condition, String dose_howOften, Date startDate, Date endDate, int totalNumOfPills, int howManyTimesWillItBeTakenInADay, String instructions, String state, List<Date> doseTimes, boolean hasRefillReminder, Date timeToShowTheReminderIn, String rxNumber) {
        this.uuid = id;
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
        this.totalNumOfPills = totalNumOfPills;
        this.howManyTimesWillItBeTakenInADay = howManyTimesWillItBeTakenInADay;
        this.instructions = instructions;
        this.state = state;
        this.doseTimes = doseTimes;
        this.hasRefillReminder = hasRefillReminder;
        this.timeToShowTheReminderIn = timeToShowTheReminderIn;
        this.rxNumber = rxNumber;
    }

    public Medicine(String name, Form form, Strength strength, int strengthAmount, double numberOfPillsLeft , int image) {
        this.name = name;
        this.form = form;
        this.strength = strength;
        StrengthAmount = strengthAmount;
        this.numberOfPillsLeft = numberOfPillsLeft;
        this.image = image;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID id) {
        this.uuid = id;
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

    public String getDose_howOften() {
        return Dose_howOften;
    }

    public void setDose_howOften(String dose_howOften) {
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

    public List<Date> getDoseTimes() {
        return doseTimes;
    }

    public void setDoseTimes(List<Date> doseTimes) {
        this.doseTimes = doseTimes;
    }

    public boolean isHasRefillReminder() {
        return hasRefillReminder;
    }

    public void setHasRefillReminder(boolean hasRefillReminder) {
        this.hasRefillReminder = hasRefillReminder;
    }

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
