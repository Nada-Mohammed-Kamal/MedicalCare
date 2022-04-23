package com.example.medicalappreminder_java.MedModification.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.medicalappreminder_java.AddMedicine.View.AdapterForTakeDoseTimes.DoseTimesAdapter;
import com.example.medicalappreminder_java.Constants.Form;
import com.example.medicalappreminder_java.Constants.Keys;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.models.CustomTime;
import com.example.medicalappreminder_java.models.Medicine;

import java.util.List;

public class MedModifyActivity extends AppCompatActivity {
    ImageView medImg;
    EditText txtMedName;
    RecyclerView doseTimesRV;

    EditText txtNoOfDoses;
    DosesAdapter adapter;

    EditText txtCondition;
    EditText txtPrescription;
    EditText txtInstructions;
    EditText txtStrength;

    Button btnDone;

    Spinner medTypeSpinner;

    Medicine medicine;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_modification);
        Intent intent = getIntent();
        medicine = (Medicine) intent.getSerializableExtra(Keys.USER_MED);

        initUI();
        configureUI();


        doseTimesRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new DosesAdapter(this, Integer.valueOf(txtNoOfDoses.getText().toString()), medicine);
        doseTimesRV.setAdapter(adapter);

    }

    private void initUI() {
        medImg = findViewById(R.id.medImg);
        txtMedName = findViewById(R.id.txtMedName);
        doseTimesRV = findViewById(R.id.doseTimesRV);
        txtNoOfDoses = findViewById(R.id.txtNoOfDoses);
        txtCondition = findViewById(R.id.txtCondition);
        txtPrescription = findViewById(R.id.txtPrescription);
        txtInstructions = findViewById(R.id.txtInstructions);
        txtStrength = findViewById(R.id.txtStrength);
        btnDone = findViewById(R.id.btnDone);
        medTypeSpinner = findViewById(R.id.medTypeSpinner);

        doseTimesRV.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> spinnerAdapter=ArrayAdapter.createFromResource(getApplicationContext(), R.array.MedTypes, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        medTypeSpinner.setAdapter(spinnerAdapter);
        medTypeSpinner.setSelection(medicine.getForm().ordinal());

        txtNoOfDoses.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String countNum = charSequence.toString();
                if(!countNum.isEmpty()) {
                    int number = Integer.parseInt(countNum);
                    if(number > 6) {
                        txtNoOfDoses.setError("Max dose 6");

                    }
                    else {
                        doseTimesRV.setVisibility(View.VISIBLE);
                        adapter = new DosesAdapter(getApplicationContext(), number, medicine);
                        doseTimesRV.setAdapter(adapter);
                        adapter.clearList();
                        adapter.notifyDataSetChanged();
                    }
                }
                else {
                    doseTimesRV.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<CustomTime> currentTimes = adapter.getNewDateList();
                System.out.println("New List Size: " + currentTimes.size());
                for (CustomTime time : currentTimes)
                {
                    System.out.println(time.getHour() + ":" + time.getMinute());
                }
            }
        });

        medTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                medicine.setForm(Form.valueOf(medTypeSpinner.getSelectedItem().toString()));
                medImg.setImageResource(getMedTypeImage(medicine.getForm()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void configureUI(){
        medImg.setImageResource(medicine.getImage());
        txtMedName.setText(medicine.getName());
        txtNoOfDoses.setText(String.valueOf(medicine.getDoseTimes().size()));

        txtPrescription.setText(medicine.getNumberOfPillsInOneTime() + " " + medicine.getNumberOfPillsLeft() + " " + medicine.getTotalNumOfPills() + "");
        //txtPrescription.setText("I currently have " + medicine.getTotalNumOfPills() + " pills, remind me when I have " + medicine.getNumberOfPillsLeft() + " pills remaining.");

        txtCondition.setText(medicine.getCondition());
        txtInstructions.setText(medicine.getInstructions());
        txtStrength.setText(medicine.getStrengthAmount() + medicine.getStrength().toString());

    }

    private int getMedTypeImage(Form medType) {

        int returnedImgID = R.mipmap.ic_launcher;
        switch(medType.ordinal())
        {
            case 0:
                returnedImgID = R.drawable.pill;
                break;

            case 1:
                returnedImgID = R.drawable.solution;
                break;

            case 2:
                returnedImgID = R.drawable.injection;
                break;

            case 3:
                returnedImgID = R.drawable.powder;
                break;

            case 4:
                returnedImgID = R.drawable.drops;
                break;

            case 5:
                returnedImgID = R.drawable.inhaler;
                break;

            default:
                returnedImgID = R.drawable.other_form;
                break;
        }
        return returnedImgID;
    }
}