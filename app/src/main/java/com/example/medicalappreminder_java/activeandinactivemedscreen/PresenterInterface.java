package com.example.medicalappreminder_java.activeandinactivemedscreen;

import com.example.medicalappreminder_java.models.Medicine;

import java.util.List;

public interface PresenterInterface {
    List<Medicine> getCurrentUserMeds();
}
