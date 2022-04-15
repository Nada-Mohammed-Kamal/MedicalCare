package com.example.medicalappreminder_java.Model;

public class HomeScreenRecyclerViewDTO {
    //roomDB.getInstance(Context).medDAO.getAllMed
    int medIcon;
    String doseTime;
    String medName;
    String medDescription;

    public int getMedIcon() {
        return medIcon;
    }

    public void setMedIcon(int medIcon) {
        this.medIcon = medIcon;
    }

    public String getDoseTime() {
        return doseTime;
    }

    public void setDoseTime(String doseTime) {
        this.doseTime = doseTime;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedDescription() {
        return medDescription;
    }

    public void setMedDescription(String medDescription) {
        this.medDescription = medDescription;
    }

    public HomeScreenRecyclerViewDTO(int medIcon, String doseTime, String medName, String medDescription) {
        this.medIcon = medIcon;
        this.doseTime = doseTime;
        this.medName = medName;
        this.medDescription = medDescription;
    }
}
