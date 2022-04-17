package com.example.medicalappreminder_java.dependantInfo;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.medicalappreminder_java.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.DataInput;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class DependentInfoFragment extends Fragment {
    TextView firstName;
    TextView lastName;
    TextView birthdate;
    RadioButton female;
    RadioButton male;
    View view;
    RadioGroup radioGroup;
    final Calendar myCalendar= Calendar.getInstance();
    int selectedId;




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
        firstName = view.findViewById(R.id.firstNameDepId);
        lastName = view.findViewById(R.id.lastNameDepId);
         female = view.findViewById(R.id.FEmaleRadioBtniD);
         male = view.findViewById(R.id.maleRadioBtniD);
         radioGroup = view.findViewById(R.id.radioGroup);
        //ll fragment navigation
         /*
                anady 3ala get selected gender lama agy a save al object 3ashan a retrive al selected
          */


        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };



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
}