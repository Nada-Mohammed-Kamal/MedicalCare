package com.example.medicalappreminder_java.MedModification.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.medicalappreminder_java.Constants.EveryHowManyDaysWilltheMedBeTaken;
import com.example.medicalappreminder_java.Constants.Form;
import com.example.medicalappreminder_java.Constants.Keys;
import com.example.medicalappreminder_java.Constants.Strength;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MedModifyActivity extends AppCompatActivity {
    ImageView medImg;
    EditText txtMedName;
    RecyclerView doseTimesRV;

    EditText txtNoOfDoses;

    EditText txtStartDate;
    EditText txtEndDate;
    Spinner howOftenSpinner;

    DosesAdapter adapter;


    EditText txtCondition;
    EditText txtPrescription;
    EditText txtInstructions;
    EditText txtStrength;
    Spinner medStrengthSpinner;

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

        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);
        howOftenSpinner = findViewById(R.id.howOftenSpinner);

        txtCondition = findViewById(R.id.txtCondition);
        txtPrescription = findViewById(R.id.txtPrescription);
        txtInstructions = findViewById(R.id.txtInstructions);

        txtStrength = findViewById(R.id.txtEditStrength);
        medStrengthSpinner = findViewById(R.id.medStrengthSpinner);

        btnDone = findViewById(R.id.btnDone);
        medTypeSpinner = findViewById(R.id.medTypeSpinner);

        doseTimesRV.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> medTypeSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.MedTypes, android.R.layout.simple_spinner_item);
        medTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        medTypeSpinner.setAdapter(medTypeSpinnerAdapter);

        ArrayAdapter<CharSequence> medStrengthSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.MedStrength, android.R.layout.simple_spinner_item);
        medStrengthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        medStrengthSpinner.setAdapter(medStrengthSpinnerAdapter);

        ArrayAdapter<CharSequence> howOftenSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.ChooseDayesOfDose, android.R.layout.simple_spinner_item);
        howOftenSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        howOftenSpinner.setAdapter(howOftenSpinnerAdapter);

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
                        adapter = new DosesAdapter(MedModifyActivity.this, number, medicine);
                        doseTimesRV.setAdapter(adapter);
                        adapter.configureList();
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

                RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
                LocalSourceInterface localSourceInterface = new ConcreteLocalSource(MedModifyActivity.this);
                RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,MedModifyActivity.this);
                repoClass.updateMedicine(medicine);
                SharedPreferences preferences = MedModifyActivity.this.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
                String userEmail = preferences.getString("emailKey" , "user email") ;
                User currentUser = repoClass.findUserByEmail(userEmail);
                List<Medicine> listOfMedications = currentUser.getListOfMedications();
                for (Medicine oldMed:listOfMedications) {
                    if(oldMed.getUuid().equals(medicine.getUuid())){
                        listOfMedications.remove(oldMed);
                        break;
                    }
                }
                fillMedicineWithFormData();
                listOfMedications.add(medicine);
                currentUser.setListOfMedications(listOfMedications);
                repoClass.updateUser(currentUser);

                Toast.makeText(MedModifyActivity.this, "Successfully Added !!", Toast.LENGTH_LONG).show();

                finish();
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

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        txtStartDate.setText(sdf.format(medicine.getStartDate()));
        txtEndDate.setText(sdf.format(medicine.getEndDate()));


        txtPrescription.setText("I currently have " + medicine.getTotalNumOfPills() + " pills, remind me when I have " + medicine.getNumberOfPillsLeft() + " pills remaining.");

        txtCondition.setText(medicine.getCondition());
        txtInstructions.setText(medicine.getInstructions());
        txtStrength.setText(String.valueOf(medicine.getStrengthAmount()));

        medStrengthSpinner.setSelection(medicine.getStrength().ordinal());
        medTypeSpinner.setSelection(medicine.getForm().ordinal());
        howOftenSpinner.setSelection(medicine.getDose_howOften().ordinal());
    }

    private void fillMedicineWithFormData() {
        medicine.setName(txtMedName.getText().toString());
        medicine.setForm(Form.valueOf(medTypeSpinner.getSelectedItem().toString()));

        medicine.setStrength(Strength.valueOf(medStrengthSpinner.getSelectedItem().toString()));
        medicine.setStrengthAmount(Integer.valueOf(txtStrength.getText().toString()));

        medicine.setImage(getMedTypeImage(Form.valueOf(medTypeSpinner.getSelectedItem().toString())));
        medicine.setCondition(txtCondition.getText().toString());

        //Editing Date

        StringBuilder howOften = new StringBuilder(howOftenSpinner.getSelectedItem().toString());
        for (int i=0 ; i<howOften.length() ; i++)
        {
            if(howOften.charAt(i) == ' ')
                howOften.setCharAt(i,'_');
        }
        medicine.setDose_howOften(EveryHowManyDaysWilltheMedBeTaken.valueOf(howOften.toString()));

        medicine.setHowManyTimesWillItBeTakenInADay(Integer.valueOf(txtNoOfDoses.getText().toString()));
        medicine.setInstructions(txtInstructions.getText().toString());

        medicine.setDoseTimes(adapter.getDateList());
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