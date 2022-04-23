package com.example.medicalappreminder_java.HomeScreen.View.HomeFragment;

import com.example.medicalappreminder_java.models.CustomTime;
import com.example.medicalappreminder_java.models.Medicine;

import java.util.List;

public interface AllMedViewInterface {
    public void showData(List<Medicine> moviesList, List<CustomTime> customTimes);
}
