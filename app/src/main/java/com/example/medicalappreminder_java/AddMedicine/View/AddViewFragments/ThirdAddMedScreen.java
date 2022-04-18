package com.example.medicalappreminder_java.AddMedicine.View.AddViewFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.models.DataFromSecondAddMedScreen;


public class ThirdAddMedScreen extends Fragment {

ThirdAddMedScreenArgs thirdAddMedScreenArgs;

    public ThirdAddMedScreen() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thirdAddMedScreenArgs = ThirdAddMedScreenArgs.fromBundle(getArguments());
        DataFromSecondAddMedScreen data = thirdAddMedScreenArgs.getAllData();
         Log.i("info", data.getMedName() + " \n"+data.getFormMed().toString()+"\n"+data.getStrength().toString()+"\n"
                                        + data.getStrengthAmount() +"\n"+ data.getWhatReasonToTake());
                    for (int i=0;i<data.getDateTimes().size();i++) {
                        Log.i("info",data.getDateTimes().get(i).getHour()+"  "+data.getDateTimes().get(i).getMinute());
                    }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third_add_med_screen, container, false);
    }
}