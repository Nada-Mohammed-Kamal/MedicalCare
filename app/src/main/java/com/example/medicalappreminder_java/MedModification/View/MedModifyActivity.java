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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.medicalappreminder_java.Constants.EveryHowManyDaysWilltheMedBeTaken;
import com.example.medicalappreminder_java.Constants.Form;
import com.example.medicalappreminder_java.Constants.Keys;
import com.example.medicalappreminder_java.Constants.Strength;
import com.example.medicalappreminder_java.MedModification.Presenter.MedModifyPresenter;
import com.example.medicalappreminder_java.MedModification.Presenter.MedModifyPresenterInterface;
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
import java.util.List;

public class MedModifyActivity extends AppCompatActivity implements MedModifyActivityInterface {

    EditText txtMedName;
    EditText txtNoOfDoses;
    EditText txtStartDate;
    EditText txtEndDate;
    EditText txtCondition;
    EditText txtCurrentlyHave;
    EditText txtRemindMe;
    EditText txtInstructions;
    EditText txtStrength;

    RecyclerView doseTimesRV;
    DosesAdapter adapter;

    ImageView medImg;

    Spinner howOftenSpinner;
    Spinner medStrengthSpinner;
    Spinner medTypeSpinner;

    Switch pillReminderSwitch;

    Button btnDone;

    Medicine medicine;

    MedModifyPresenterInterface presenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_modification);

        Intent intent = getIntent();
        medicine = (Medicine) intent.getSerializableExtra(Keys.USER_MED);

        presenter = new MedModifyPresenter(this, this, medicine);

        Log.i("M3lsh", "onCreate: DosesSize: " + medicine.getDoseTimes().size());
        initUI();
        configureUI();


    }

    private void initTextViews(){
        txtMedName         = findViewById(R.id.txtMedName);
        txtStartDate       = findViewById(R.id.txtStartDate);
        txtEndDate         = findViewById(R.id.txtEndDate);
        txtNoOfDoses       = findViewById(R.id.txtNoOfDoses);
        txtCondition       = findViewById(R.id.txtCondition);
        txtCurrentlyHave   = findViewById(R.id.txtCurrentlyHave);
        txtRemindMe        = findViewById(R.id.txtRemindMe);
        txtInstructions    = findViewById(R.id.txtInstructions);
        txtStrength        = findViewById(R.id.txtEditStrength);
    }

    private void initSpinners() {

        howOftenSpinner    = findViewById(R.id.howOftenSpinner);
        medStrengthSpinner = findViewById(R.id.medStrengthSpinner);
        medTypeSpinner     = findViewById(R.id.medTypeSpinner);

        ArrayAdapter<CharSequence> medTypeSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.MedTypes, android.R.layout.simple_spinner_item);
        medTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        medTypeSpinner.setAdapter(medTypeSpinnerAdapter);

        ArrayAdapter<CharSequence> medStrengthSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.MedStrength, android.R.layout.simple_spinner_item);
        medStrengthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        medStrengthSpinner.setAdapter(medStrengthSpinnerAdapter);

        ArrayAdapter<CharSequence> howOftenSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.ChooseDayesOfDose, android.R.layout.simple_spinner_item);
        howOftenSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        howOftenSpinner.setAdapter(howOftenSpinnerAdapter);
    }

    private void initListeners(){
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
                presenter.editMedicineInDB();
                Toast.makeText(MedModifyActivity.this, "Successfully Edited !!", Toast.LENGTH_LONG).show();
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

    private void initRecyclerViews() {
        doseTimesRV = findViewById(R.id.doseTimesRV);
        doseTimesRV.setVisibility(View.GONE);
    }

    private void initImages() {
        medImg = findViewById(R.id.medImg);
    }

    private void initButtons(){
        btnDone = findViewById(R.id.btnDone);
    }

    private void initSwithces() {
        pillReminderSwitch = findViewById(R.id.pillReminderSwitch);
    }

    private void initUI() {
        initTextViews();
        initSpinners();
        initRecyclerViews();
        initImages();
        initButtons();
        initSwithces();
        initListeners();
    }

    private void configureTextViews(){
        txtMedName.setText(medicine.getName());
        txtNoOfDoses.setText(String.valueOf(medicine.getDoseTimes().size()));

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        txtStartDate.setText(sdf.format(medicine.getStartDate()));
        txtEndDate.setText(sdf.format(medicine.getEndDate()));

        txtCurrentlyHave.setText(String.valueOf(medicine.getNumberOfPillsLeft()));
        txtRemindMe.setText(String.valueOf(medicine.getRemindMeWhenIHaveHowManyPillsLeft()));
        txtCondition.setText(medicine.getCondition());
        txtInstructions.setText(medicine.getInstructions());
        txtStrength.setText(String.valueOf(medicine.getStrengthAmount()));
    }

    private void configureSpinners() {
        medStrengthSpinner.setSelection(medicine.getStrength().ordinal());
        medTypeSpinner.setSelection(medicine.getForm().ordinal());
        howOftenSpinner.setSelection(medicine.getDose_howOften().ordinal());
    }

    private void configureImages() {
        medImg.setImageResource(medicine.getImage());
    }

    private void configureRecyclerViews() {
        doseTimesRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new DosesAdapter(this, Integer.valueOf(txtNoOfDoses.getText().toString()), medicine);
        doseTimesRV.setAdapter(adapter);
    }

    private void configureSwitches(){
        //pillReminderSwitch.setChecked(medicine.getHasRefillReminder());
    }

    private void configureUI(){
        configureTextViews();
        configureSpinners();
        configureImages();
        configureSwitches();
        configureRecyclerViews();
    }

    public Medicine fillMedicineWithFormData(Medicine medicine) {
        medicine.setName(txtMedName.getText().toString());
        medicine.setForm(Form.valueOf(medTypeSpinner.getSelectedItem().toString()));

        medicine.setStrength(Strength.valueOf(medStrengthSpinner.getSelectedItem().toString()));
        medicine.setStrengthAmount(Integer.valueOf(txtStrength.getText().toString()));

        medicine.setImage(getMedTypeImage(Form.valueOf(medTypeSpinner.getSelectedItem().toString())));
        medicine.setCondition(txtCondition.getText().toString());

        //Editing Date
        SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
        try {
            medicine.setStartDate(sdf.parse(txtStartDate.getText().toString()));
            medicine.setEndDate(sdf.parse(txtEndDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        medicine.setRemindMeWhenIHaveHowManyPillsLeft(Double.valueOf(txtRemindMe.getText().toString()));
        medicine.setNumberOfPillsLeft(Double.valueOf(txtCurrentlyHave.getText().toString()));
        StringBuilder howOften = new StringBuilder(howOftenSpinner.getSelectedItem().toString());
        for (int i=0 ; i<howOften.length() ; i++) {
            if(howOften.charAt(i) == ' ')
                howOften.setCharAt(i,'_');
        }
        medicine.setDose_howOften(EveryHowManyDaysWilltheMedBeTaken.valueOf(howOften.toString()));

        medicine.setHowManyTimesWillItBeTakenInADay(Integer.valueOf(txtNoOfDoses.getText().toString()));
        medicine.setInstructions(txtInstructions.getText().toString());

        medicine.setState("Active");
        medicine.setDoseTimes(adapter.getDateList());

        return medicine;
    }

    private int getMedTypeImage(Form medType) {

        int returnedImgID = R.mipmap.ic_launcher;
        switch(medType.ordinal()) {
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