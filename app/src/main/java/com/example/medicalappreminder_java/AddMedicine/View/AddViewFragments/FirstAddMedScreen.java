package com.example.medicalappreminder_java.AddMedicine.View.AddViewFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.medicalappreminder_java.R;
import com.google.android.material.navigation.NavigationView;

public class FirstAddMedScreen extends Fragment {
    RadioGroup form;
    RadioGroup strength;
    RadioButton radioButton;
    EditText addMedName;
    EditText strengthAmount;
    Button nextToSecondScreen;
    int strengthAmountInt;
    String formtxt,strengthtxt,addMedNametxt;
    public FirstAddMedScreen() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_add_med_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        addListeners(view);
    }
    void init(View view)
    {
        form = view.findViewById(R.id.form);
        strength = view.findViewById(R.id.Strength);
        addMedName = view.findViewById(R.id.addMedName);
        strengthAmount = view.findViewById(R.id.StrengthAmount);
        nextToSecondScreen = view.findViewById(R.id.nextToSecondScreen);
        formtxt = "Pill";
        strengthtxt = "g";

    }
    void addListeners(View view)
    {
        form.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                radioButton = view.findViewById(checkedId);
                formtxt = radioButton.getText().toString();
                Toast.makeText(getActivity(),formtxt,Toast.LENGTH_SHORT).show();
            }
        });
        strength.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                radioButton = view.findViewById(checkedId);
                strengthtxt = radioButton.getText().toString();
                Toast.makeText(getActivity(),strengthtxt,Toast.LENGTH_SHORT).show();
            }
        });
        nextToSecondScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedNametxt = addMedName.getText().toString();
                if(addMedNametxt.isEmpty())
                    addMedName.setError("Field is required");
                else if(strengthAmount.getText().toString().isEmpty())
                    strengthAmount.setError("Field is required");
                else
                {
                    strengthAmountInt = Integer.parseInt(strengthAmount.getText().toString());
                    FirstAddMedScreenDirections.GoToSecondAddMedScreen action = FirstAddMedScreenDirections.goToSecondAddMedScreen  (addMedNametxt,
                            formtxt,strengthAmountInt,strengthtxt);
                    Navigation.findNavController(view).navigate(action);
                }

            }
        });
    }
}