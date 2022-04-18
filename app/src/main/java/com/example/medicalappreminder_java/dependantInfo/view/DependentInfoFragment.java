package com.example.medicalappreminder_java.dependantInfo.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.dependantInfo.ViewInterface;
import com.example.medicalappreminder_java.dependantInfo.presenter.DepInfoPresenter;
import com.example.medicalappreminder_java.models.User;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


public class DependentInfoFragment extends Fragment implements ViewInterface {
    TextView firstName;
    TextView lastName;
    TextView birthdate;
    RadioButton female;
    RadioButton male;
    View view;
    RadioGroup radioGroup;
    final Calendar myCalendar= Calendar.getInstance();
    int selectedId;
    Button addDepBtn;
    User user = new User();
    ViewInterface depViewInterfaceReference;
    Date date_selected;
    Context context;

    public DependentInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        birthdate= view.findViewById(R.id.dateId);
        firstName = view.findViewById(R.id.firstNameDepid);
        lastName = view.findViewById(R.id.lastNameDepId);
         female = view.findViewById(R.id.FEmaleRadioBtniD);
         male = view.findViewById(R.id.maleRadioBtniD);
         radioGroup = view.findViewById(R.id.radioGroup);
         addDepBtn = view.findViewById(R.id.addDepBtn);
         context = getActivity();
        //ll fragment navigation
         /*
                anady 3ala get selected gender lama agy a save al object 3ashan a retrive al selected
          */


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                date_selected = new Date(year , month , day);
                updateLabel();
            }
        };


        addDepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DepInfoPresenter depInfoPresenter = new DepInfoPresenter(getContext() , DependentInfoFragment.this);
                user.setFirstName(firstName.getText().toString());
                user.setLastName(lastName.getText().toString());
                user.setBirthdate( date_selected);
                String selectedGender = getSelectedGender();
                user.setGender(selectedGender);
                user.setUuid(UUID.randomUUID());
                depInfoPresenter.addDependant(user);
            }
        });


        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    private String getSelectedGender(){
        // get selected radio doNotHaveAccButton from radioGroup
         selectedId = radioGroup.getCheckedRadioButtonId();
         return checkSelectedGender();
    }

    private String checkSelectedGender(){
        //lw al condition de mashta3'alatsh mmkn ashoof tare2et al comparing between al objects
        if(view.findViewById(R.id.FEmaleRadioBtniD) == view.findViewById(selectedId)){
            //female
            return "female";
        } else if(view.findViewById(R.id.maleRadioBtniD) == view.findViewById(selectedId)){
            //male
            return "male";
        }
        return "noGender";
    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        birthdate.setText(dateFormat.format(myCalendar.getTime()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dependent_info, container, false);
        ExtendedFloatingActionButton e = getActivity().findViewById(R.id.ExtendedFloatingActionButtonAddMed);
        if(e != null)
            e.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void viewToastAddedDependantSuccessfully() {
        Toast.makeText(this.getContext(), "Dependant added successfully" , Toast.LENGTH_SHORT).show();
    }
}