package com.example.medicalappreminder_java.AddMedicine.View.AddViewFragments;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.medicalappreminder_java.AddMedicine.Presenter.AddMedicinePresenter;
import com.example.medicalappreminder_java.AddMedicine.Presenter.AddMedicinePresenterInterface;
import com.example.medicalappreminder_java.AddMedicine.View.AddMedicineViewInterface;
import com.example.medicalappreminder_java.CalculateArrayOfDatesAndTimesOfTheMedication;
import com.example.medicalappreminder_java.Constants.Form;
import com.example.medicalappreminder_java.Constants.Status;
import com.example.medicalappreminder_java.Constants.Strength;
import com.example.medicalappreminder_java.HomeScreen.View.HomeFragment.AllMedViewInterface;
import com.example.medicalappreminder_java.HomeScreen.View.HomeFragment.HomeFragment;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;

import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.CustomTime;
import com.example.medicalappreminder_java.models.DataFromSecondAddMedScreen;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;

import java.util.Date;
import java.util.List;


public class ThirdAddMedScreen extends Fragment implements AddMedicineViewInterface {

    ThirdAddMedScreenArgs thirdAddMedScreenArgs;
    AddMedicinePresenterInterface addMedicinePresenterInterface;

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
    double pillLeftNum;
    double pillLeftReminderNum;
    String moreInstraction;
    DataFromSecondAddMedScreen data;
    boolean hasRefillRemind;
    public ThirdAddMedScreen() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //all previous data
        thirdAddMedScreenArgs = ThirdAddMedScreenArgs.fromBundle(getArguments());
        data = thirdAddMedScreenArgs.getAllData();

        choosingTxt = "Before eating";
        pillLeftNum = 0.0;
        pillLeftReminderNum = 0.0;
        hasRefillRemind = false;

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
                    pillLeftNum = Double.parseDouble(pillLeft.getText().toString());
                }
                if(!pillLeftReminder.getText().toString().isEmpty()) {
                    pillLeftReminderNum = Double.parseDouble(pillLeftReminder.getText().toString());
                }
                if(pillLeftReminderNum != 0.0 && pillLeftNum != 0.0)
                {
                    hasRefillRemind = true;
                }
                if(!editTextTextMultiLine.getText().toString().isEmpty()){
                    moreInstraction = editTextTextMultiLine.getText().toString();
                    moreInstraction = moreInstraction + "  " + choosingTxt;
                }

                //add med to room presenter code
                CalculateArrayOfDatesAndTimesOfTheMedication calculate = new CalculateArrayOfDatesAndTimesOfTheMedication(data.getStartDate(),data.getEndDate(),data.getEveryHowManyDaysWilltheMedBeTaken(), data.getCustomTimes());
                Medicine medicine = new Medicine(data.getMedName(), data.getFormMed(), data.getStrength(), data.getStrengthAmount(), pillLeftNum, data.getImg(), data.isEveryDay(),data.getEveryHowManyDaysWilltheMedBeTaken(),
                        data.getStartDate(), data.getEndDate(), calculate.getForHowLongWillTheMedBeTaken(),
                        data.getHowManyTimes(),moreInstraction,"Active", data.getCustomTimes(),
                        hasRefillRemind, pillLeftReminderNum, calculate.getDates());

                addMedicinePresenterInterface = new AddMedicinePresenter(getContext(),ThirdAddMedScreen.this , ThirdAddMedScreen.this);
                addMedicinePresenterInterface.addMedTOCurrentUser(medicine);

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

    @Override
    public void viewThatTheMedIsAddedSuccessfully() {
        Toast.makeText(getActivity(),"Med added",Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }
}