package com.example.medicalappreminder_java.AddMedicine.View.AddViewFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;

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

import com.example.medicalappreminder_java.CalculateArrayOfDatesAndTimesOfTheMedication;
import com.example.medicalappreminder_java.Constants.Form;
import com.example.medicalappreminder_java.Constants.Status;
import com.example.medicalappreminder_java.Constants.Strength;
import com.example.medicalappreminder_java.Constants.WorkerUtils;
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
import com.example.medicalappreminder_java.models.MedicineWorker;
import com.example.medicalappreminder_java.models.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


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

                RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
                LocalSourceInterface localSourceInterface = new ConcreteLocalSource(getContext());
                RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,getContext());
                repoClass.insertMedicine(medicine);
                SharedPreferences preferences = getActivity().getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
                String userEmail = preferences.getString("emailKey" , "user email") ;
                User currentUser = repoClass.findUserByEmail(userEmail);
                List<Medicine> listOfMedications = currentUser.getListOfMedications();
                listOfMedications.add(medicine);
                currentUser.setListOfMedications(listOfMedications);
                repoClass.updateUser(currentUser);
               
                Toast.makeText(getActivity(),"Med added",Toast.LENGTH_SHORT).show();


                WorkerUtils.addRequestsToWorkManager(medicine);

                Toast.makeText(getActivity().getApplicationContext(), "WorkManager Request Added !!", Toast.LENGTH_LONG).show();
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