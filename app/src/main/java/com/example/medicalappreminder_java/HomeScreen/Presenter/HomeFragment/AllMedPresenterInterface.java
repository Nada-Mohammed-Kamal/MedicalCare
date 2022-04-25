package com.example.medicalappreminder_java.HomeScreen.Presenter.HomeFragment;

import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;

import java.util.Date;

public interface AllMedPresenterInterface {
     void getMeds(Date date);
     User getCurrentUser();
     void updateMedicine(Medicine medicine);
     void updateUserWithNewMedData(Medicine medicine);
     User getFriend(String name);

}
