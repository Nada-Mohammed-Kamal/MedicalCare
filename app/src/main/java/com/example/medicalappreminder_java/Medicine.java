package com.example.medicalappreminder_java;

public class Medicine {
    private String name;
    private Form form;
    private Strength strength;
    private int StrengthAmount;
    private double numberOfPillsLeft;
    private int image;

    public Medicine(String name, Form form, Strength strength, int strengthAmount, double numberOfPillsLeft , int image) {
        this.name = name;
        this.form = form;
        this.strength = strength;
        StrengthAmount = strengthAmount;
        this.numberOfPillsLeft = numberOfPillsLeft;
        this.image = image;
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
