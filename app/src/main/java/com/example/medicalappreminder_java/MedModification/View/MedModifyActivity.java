package com.example.medicalappreminder_java.MedModification.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.medicalappreminder_java.R;

public class MedModifyActivity extends AppCompatActivity {
    ImageView medImg;
    EditText txtMedName;
    RecyclerView doseTimesRV;
    
    EditText txtCondition;
    EditText txtPrescription;
    EditText txtInstructions;
    EditText txtStrength;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_modification);
    }

    private void initUI() {
        medImg = findViewById(R.id.medImg);
        txtMedName = findViewById(R.id.txtMedName);
        doseTimesRV = findViewById(R.id.doseTimesRV);
        txtCondition = findViewById(R.id.txtCondition);
        txtPrescription = findViewById(R.id.txtPrescription);
        txtInstructions = findViewById(R.id.txtInstructions);
        txtStrength = findViewById(R.id.txtStrength);
    }

    private void configureUI(){

    }

    private void setMedImg()
    {

    }
}