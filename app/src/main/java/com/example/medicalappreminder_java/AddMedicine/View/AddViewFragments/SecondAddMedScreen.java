package com.example.medicalappreminder_java.AddMedicine.View.AddViewFragments;

import static com.example.medicalappreminder_java.Constants.EveryHowManyDaysWilltheMedBeTaken.Every_day;
import static com.example.medicalappreminder_java.Constants.EveryHowManyDaysWilltheMedBeTaken.Every_five_days;
import static com.example.medicalappreminder_java.Constants.EveryHowManyDaysWilltheMedBeTaken.Every_four_days;
import static com.example.medicalappreminder_java.Constants.EveryHowManyDaysWilltheMedBeTaken.Every_six_days;
import static com.example.medicalappreminder_java.Constants.EveryHowManyDaysWilltheMedBeTaken.Every_three_days;
import static com.example.medicalappreminder_java.Constants.EveryHowManyDaysWilltheMedBeTaken.Every_two_days;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicalappreminder_java.AddMedicine.View.AdapterForTakeDoseTimes.DoseTimesAdapter;
import com.example.medicalappreminder_java.Constants.EveryHowManyDaysWilltheMedBeTaken;
import com.example.medicalappreminder_java.Constants.Form;
import com.example.medicalappreminder_java.Constants.Strength;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.models.DataFromSecondAddMedScreen;
import com.example.medicalappreminder_java.models.CustomTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SecondAddMedScreen extends Fragment {
    EditText startDateid;
    EditText endDateid;
    EditText whatYouTakingForEditText;
    EditText doseTakenTime;
    Spinner spinnerHowOftenYouTakeIt;
    SecondAddMedScreenArgs secondAddMedScreenArgs;
    RecyclerView takeTimesforEveryDose;
    Button addMedConfirmButton;
    TextView set_dosing_schedule;
    final Calendar myCalendar= Calendar.getInstance();
    DatePickerDialog.OnDateSetListener selectStartDate;
    DatePickerDialog.OnDateSetListener selectEndDate;
    DoseTimesAdapter myAdapter;

    String WhatReasonToTake;
    Date startDate;
    Date endDate;
    int howManyTimes;
    String selected_val;
    ArrayList<CustomTime> customTimes;
    EveryHowManyDaysWilltheMedBeTaken everyHowManyDaysWilltheMedBeTaken;
    String medName;
    Form formMed;
    Strength strength;
    int strengthAmount;

    public SecondAddMedScreen() {
        // Required empty public constructor


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //data from first screen
        secondAddMedScreenArgs = SecondAddMedScreenArgs.fromBundle(getArguments());
        medName = secondAddMedScreenArgs.getMedNamed();
        formMed = secondAddMedScreenArgs.getFromType();
        strength = secondAddMedScreenArgs.getStrengthSelected();
        strengthAmount = secondAddMedScreenArgs.getStrengthAmount();


        selectStartDate =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateStartDateLabel();
            }
        };

        selectEndDate =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateEndDateLabel();
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second_add_med_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setOnClickListeners();

    }

    void setOnClickListeners(){
        startDateid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), selectStartDate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDateid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 new DatePickerDialog(getContext(), selectEndDate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        addMedConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(startDateid.getText().toString().isEmpty()) {
                    startDateid.setError("Field is required");
                }
                if(endDateid.getText().toString().isEmpty()) {
                    endDateid.setError("Field is required");
                }
                if(doseTakenTime.getText().toString().isEmpty()) {
                    doseTakenTime.setError("Field is required");
                }
                else
                {
                    if(!whatYouTakingForEditText.getText().toString().isEmpty())
                         WhatReasonToTake = whatYouTakingForEditText.getText().toString();
                    else
                        WhatReasonToTake = "";
                    selected_val = spinnerHowOftenYouTakeIt.getSelectedItem().toString();
                    howManyTimes = Integer.parseInt(doseTakenTime.getText().toString());
                    customTimes = myAdapter.getTimes();
                    if(customTimes.size() == 0)
                        Toast.makeText(getContext(),"Please set all doses time",Toast.LENGTH_SHORT).show();

                    else
                    {
                        if(selected_val.equals("Every day")){
                            everyHowManyDaysWilltheMedBeTaken = Every_day;
                        }
                        else if(selected_val.equals("Every two days")) {
                            everyHowManyDaysWilltheMedBeTaken = Every_two_days;
                        }
                        else if(selected_val.equals("Every three days")) {
                            everyHowManyDaysWilltheMedBeTaken = Every_three_days;
                        }
                        else if(selected_val.equals("Every four days")) {
                            everyHowManyDaysWilltheMedBeTaken = Every_four_days;
                        }
                        else if(selected_val.equals("Every five days")) {
                            everyHowManyDaysWilltheMedBeTaken = Every_five_days;
                        }
                        else if(selected_val.equals("Every six days")) {
                            everyHowManyDaysWilltheMedBeTaken = Every_six_days;
                        }
                        DataFromSecondAddMedScreen dataFromSecondAddMedScreen = new DataFromSecondAddMedScreen(WhatReasonToTake,startDate,endDate,
                                howManyTimes,everyHowManyDaysWilltheMedBeTaken, customTimes,medName,formMed,
                                strength,strengthAmount);
                        SecondAddMedScreenDirections.GoToThirdAddMedScreen action = SecondAddMedScreenDirections.goToThirdAddMedScreen(dataFromSecondAddMedScreen);
                        Navigation.findNavController(view).navigate(action);
                    }



                }
            }
        });

        doseTakenTimeLisener();

    }
    private void doseTakenTimeLisener() {
        doseTakenTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String countNum = charSequence.toString();
                if(!countNum.isEmpty()) {
                    int number = Integer.parseInt(countNum);
                    if(number > 6) {
                        doseTakenTime.setError("Max dose 6");

                    }
                    else {
                        takeTimesforEveryDose.setVisibility(View.VISIBLE);
                        set_dosing_schedule.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), charSequence, Toast.LENGTH_SHORT).show();
                        myAdapter = new DoseTimesAdapter(getContext(), number);
                        takeTimesforEveryDose.setAdapter(myAdapter);
                        myAdapter.notifyDataSetChanged();
                    }
                }
                else {
                    takeTimesforEveryDose.setVisibility(View.GONE);
                    set_dosing_schedule.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void updateStartDateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        startDate = myCalendar.getTime();

        startDateid.setText(dateFormat.format(myCalendar.getTime()));

        try {
            startDate = dateFormat.parse(dateFormat.format(myCalendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void updateEndDateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        endDate = myCalendar.getTime();
        endDateid.setText(dateFormat.format(myCalendar.getTime()));

        try {
            endDate = dateFormat.parse(dateFormat.format(myCalendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    void init(View view) {

        startDateid = view.findViewById(R.id.startDateid);
        endDateid = view.findViewById(R.id.endDateid);
        whatYouTakingForEditText = view.findViewById(R.id.WhatYouTakingFor);
        takeTimesforEveryDose = view.findViewById(R.id.takeTimesforEveryDoseRecyclerview);
        doseTakenTime = view.findViewById(R.id.doseTakenTime);
        addMedConfirmButton = view.findViewById(R.id.addMedConfirmButton);
        set_dosing_schedule = view.findViewById(R.id.set_dosing_schedule);
        spinnerHowOftenYouTakeIt = view.findViewById(R.id.spinnerHowOftenYouTakeIt);

        set_dosing_schedule.setVisibility(View.GONE);
        takeTimesforEveryDose.setVisibility(View.GONE);

        takeTimesforEveryDose.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        takeTimesforEveryDose.setLayoutManager(layoutManager);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(), R.array.ChooseDayesOfDose, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerHowOftenYouTakeIt.setAdapter(adapter);
    }
}