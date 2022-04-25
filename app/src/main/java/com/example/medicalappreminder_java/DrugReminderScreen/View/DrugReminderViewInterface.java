package com.example.medicalappreminder_java.DrugReminderScreen.View;

import com.example.medicalappreminder_java.models.Medicine;

public interface DrugReminderViewInterface {


    public Medicine getMedicineFromIntent();
    public void setDrugNameTextView() ;
    public void setDrugStrengthTextView() ;
    public void setHowOftenTextView() ;
    public void setConditionTextView() ;
    public void setPrescriptionTextView() ;


}
