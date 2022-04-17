package com.example.medicalappreminder_java.AddMedicine.View.AddViewFragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.medicalappreminder_java.AddMedicine.View.AdapterForTakeDoseTimes.DoseTimesAdapter;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.activeandinactivemedscreen.view.MyAdapter;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SecondAddMedScreen extends Fragment {
    EditText startDateid;
    EditText endDateid;
    EditText addMedName;
    EditText doseTakenTime;
    SecondAddMedScreenArgs secondAddMedScreenArgs;
    RecyclerView takeTimesforEveryDose;
    Button addMedConfirmButton;
    TextView set_dosing_schedule;
    final Calendar myCalendar= Calendar.getInstance();
    DatePickerDialog.OnDateSetListener selectStartDate;
    DatePickerDialog.OnDateSetListener selectEndDate;
    DoseTimesAdapter myAdapter;
    public SecondAddMedScreen() {
        // Required empty public constructor


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //data from first screen
        secondAddMedScreenArgs = SecondAddMedScreenArgs.fromBundle(getArguments());
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
        startDateid.setText(dateFormat.format(myCalendar.getTime()));
    }
    private void updateEndDateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        endDateid.setText(dateFormat.format(myCalendar.getTime()));
    }

    void init(View view) {

        startDateid = view.findViewById(R.id.startDateid);
        endDateid = view.findViewById(R.id.endDateid);
        addMedName = view.findViewById(R.id.addMedName);
        takeTimesforEveryDose = view.findViewById(R.id.takeTimesforEveryDoseRecyclerview);
        doseTakenTime = view.findViewById(R.id.doseTakenTime);
        addMedConfirmButton = view.findViewById(R.id.addMedConfirmButton);
        set_dosing_schedule = view.findViewById(R.id.set_dosing_schedule);

        set_dosing_schedule.setVisibility(View.GONE);
        takeTimesforEveryDose.setVisibility(View.GONE);

        takeTimesforEveryDose.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        takeTimesforEveryDose.setLayoutManager(layoutManager);


    }
}