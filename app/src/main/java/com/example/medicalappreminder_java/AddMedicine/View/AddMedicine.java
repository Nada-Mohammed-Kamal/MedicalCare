package com.example.medicalappreminder_java.AddMedicine.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.medicalappreminder_java.R;

public class AddMedicine extends AppCompatActivity {
    RadioGroup form;
    RadioGroup strength;
    RadioButton radioButton;
    EditText addMedName;
    EditText strengthAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        init();
        addLiListenerToradioGroups();

    }
    void init()
    {
        form = findViewById(R.id.form);
        strength = findViewById(R.id.Strength);
        addMedName = findViewById(R.id.addMedName);
        strengthAmount = findViewById(R.id.StrengthAmount);
    }
    void addLiListenerToradioGroups()
    {
        form.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                radioButton = findViewById(checkedId);
                Toast.makeText(AddMedicine.this,radioButton.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        strength.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                radioButton = findViewById(checkedId);
                Toast.makeText(AddMedicine.this,radioButton.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToNextScreenToCompleteAddMed(View view) {

    }
}