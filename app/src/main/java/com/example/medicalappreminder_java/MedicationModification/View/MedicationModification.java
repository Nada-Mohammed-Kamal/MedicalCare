package com.example.medicalappreminder_java.MedicationModification.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.medicalappreminder_java.Constants.Keys;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.models.Medicine;

public class MedicationModification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_modification);
        Intent intent = getIntent();
        Medicine medicine = (Medicine) intent.getSerializableExtra(Keys.USER_MED);
    }
}