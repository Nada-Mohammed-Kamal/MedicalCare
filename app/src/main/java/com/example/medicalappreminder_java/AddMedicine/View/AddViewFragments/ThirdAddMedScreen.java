package com.example.medicalappreminder_java.AddMedicine.View.AddViewFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.medicalappreminder_java.Constants.Form;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.models.DataFromSecondAddMedScreen;


public class ThirdAddMedScreen extends Fragment {

    ThirdAddMedScreenArgs thirdAddMedScreenArgs;

    CardView cardView;
    CardView cardViewAddInstraction;

    LinearLayout linearLayoutHideview;
    ConstraintLayout hiddenView;

    ImageButton arrow;
    ImageButton arrowForInstraction;
    Button goToHomeFinishAddingMed;

    EditText pillLeft;
    EditText pillLeftReminder;
    EditText editTextTextMultiLine;

    RadioGroup chooseEatingRadioGroup;
    RadioButton radioButton;

    String choosingTxt;
    int pillLeftNum;
    int pillLeftReminderNum;
    String moreInstraction;
    public ThirdAddMedScreen() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //all previous data
        thirdAddMedScreenArgs = ThirdAddMedScreenArgs.fromBundle(getArguments());
        DataFromSecondAddMedScreen data = thirdAddMedScreenArgs.getAllData();

        choosingTxt = "Before eating";


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third_add_med_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        addListeners(view);
    }

    private void addListeners(View view) {

        chooseEatingRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                radioButton = view.findViewById(checkedId);
                choosingTxt = radioButton.getText().toString();
                Toast.makeText(getActivity(),choosingTxt,Toast.LENGTH_LONG).show();
            }
        });

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hiddenView.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(cardView,
                            new AutoTransition());
                    hiddenView.setVisibility(View.GONE);
                    arrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }
                else {
                    TransitionManager.beginDelayedTransition(cardView,
                            new AutoTransition());
                    hiddenView.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });

        arrowForInstraction .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayoutHideview.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(cardViewAddInstraction ,
                            new AutoTransition());
                    linearLayoutHideview.setVisibility(View.GONE);
                    arrowForInstraction.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }
                else {
                    TransitionManager.beginDelayedTransition(cardViewAddInstraction ,
                            new AutoTransition());
                    linearLayoutHideview.setVisibility(View.VISIBLE);
                    arrowForInstraction.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });
        goToHomeFinishAddingMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pillLeft.getText().toString().isEmpty()) {
                    pillLeftNum = Integer.parseInt(pillLeft.getText().toString());
                }
                if(!pillLeftReminder.getText().toString().isEmpty()) {
                    pillLeftReminderNum = Integer.parseInt(pillLeftReminder.getText().toString());
                }
                if(!editTextTextMultiLine.getText().toString().isEmpty()){
                    moreInstraction = editTextTextMultiLine.getText().toString();
                }
                //add med to room
                Toast.makeText(getActivity(),"Med added",Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

    }

    private void init(View view) {
        pillLeft = view.findViewById(R.id.pill_left);
        pillLeftReminder = view.findViewById(R.id.pill_leftReminderMe);
        editTextTextMultiLine = view.findViewById(R.id.editTextTextMultiLine);

        cardView = view.findViewById(R.id.cardView);
        cardViewAddInstraction = view.findViewById(R.id.cardViewAddInstraction);

        hiddenView = view.findViewById(R.id.hideView);
        linearLayoutHideview = view.findViewById(R.id.LinearLayoutHideview);

        arrow = view.findViewById(R.id.arrow);
        arrowForInstraction = view.findViewById(R.id.arrowForInstraction);
        goToHomeFinishAddingMed = view.findViewById(R.id.goToHomeFinishAddingMed);

        chooseEatingRadioGroup = view.findViewById(R.id.chooseEatingRadioGroup);

    }
}