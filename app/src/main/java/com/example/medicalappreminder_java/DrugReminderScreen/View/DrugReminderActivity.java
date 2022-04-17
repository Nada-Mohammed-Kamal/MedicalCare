package com.example.medicalappreminder_java.DrugReminderScreen.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicalappreminder_java.DrugReminderScreen.Presenter.DrugReminderPresenter;
import com.example.medicalappreminder_java.DrugReminderScreen.Presenter.DrugReminderPresenterInterface;
import com.example.medicalappreminder_java.Form;
import com.example.medicalappreminder_java.Medicine;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.Strength;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DrugReminderActivity extends AppCompatActivity implements DrugReminderViewInterface {

    Button backButton , deleteButton , editButton , suspendButton , refillButton ;
    ImageView drugIconImageView ;
    TextView drugNameTextView , drugStrengthTextView , howOftenTextView
            , conditionTextView , refillTextView;

    RecyclerView drugReminderRecyclerView ;
    DrugReminderAdapter drugReminderAdapter ;
    Medicine medicine = new Medicine() ;

    List<Date> dates = new ArrayList<>();
    Date StartDate = new Date(2022 , 4 , 12);
    Date endDate = endDate = new Date(2022 , 4 , 20 );


    DrugReminderPresenterInterface drugReminderPresenter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_reminder);

        settingIds();
        drugReminderPresenter = new DrugReminderPresenter(this , this) ;
        settingRecyclerView();

        dates.add(StartDate) ;
        dates.add(endDate) ;
        setMedicineDetails();
        // replace number with remind me when i have
        refillTextView.setText(medicine.getNumberOfPillsLeft()+" Pills left \nRX number: " + medicine.getRxNumber()
        +"\nRefill Reminder: when i have " + "33" + " Pills Left"  );



    }

    public void settingIds (){
        backButton = findViewById(R.id.backButton) ;
        deleteButton = findViewById(R.id.deleteDrugButton) ;
        editButton = findViewById(R.id.editDrugButton) ;
        suspendButton = findViewById(R.id.suspendButton) ;
        refillButton = findViewById(R.id.refillButton) ;
        drugIconImageView = findViewById(R.id.reminderDrugIconImageView) ;
        drugNameTextView = findViewById(R.id.drugNameTextV) ;
        drugStrengthTextView = findViewById(R.id.drugStrenghtTextView) ;
        howOftenTextView = findViewById(R.id.howOftenTextView) ;
        conditionTextView = findViewById(R.id.conditionTextView) ;
        refillTextView = findViewById(R.id.drugRefillTextView) ;

    }

    public void setMedicineDetails(){

        medicine.setDose_howOften("every 2 days ");
        medicine.setName("panadol");
        medicine.setHowManyTimesWillItBeTakenInADay(2);
        medicine.setCondition("headache");
        medicine.setInstructions("instructions");
        medicine.setState("state");
        medicine.setRxNumber("20");
        medicine.setNumberOfPillsLeft(4.0);
        medicine.setStrength(Strength.g);
        medicine.setForm(Form.Pill);
        medicine.setHasRefillReminder(true);
        medicine.setTotalNumOfPills(20);
        medicine.setImage(R.drawable.inhaler);
        medicine.setDoseTimes(dates);
        medicine.setDose_howOften("twice daily");
        medicine.setEveryday(true);
        if (medicine.isEveryday() == true) {
//            howOftenTextView.setText("Every day ");
        }else {
            howOftenTextView.setText(medicine.getDose_howOften());
            //howOftenTextView.append();
        }

    }
    public void settingRecyclerView(){

        //Log.e("list11", "settingRecyclerView: " + listOdMedicines.get(2).getName());
        drugReminderRecyclerView = findViewById(R.id.drugReminderRecycylerView) ;
        drugReminderRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DrugReminderActivity.this) ;
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        drugReminderRecyclerView.setLayoutManager(layoutManager);
        drugReminderAdapter = new DrugReminderAdapter(DrugReminderActivity.this , medicine) ;
        drugReminderRecyclerView.setAdapter(drugReminderAdapter);
    }


    @Override
    public void setDrugNameTextView() {

    }

    @Override
    public void setDrugStrengthTextView() {

    }

    @Override
    public void setHowOftenTextView() {

    }

    @Override
    public void setConditionTextView() {

    }

    @Override
    public void setPrescriptionTextView() {

    }
}