package com.example.medicalappreminder_java.DrugReminderScreen.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicalappreminder_java.Constants.Keys;
import com.example.medicalappreminder_java.Constants.Strength;
import com.example.medicalappreminder_java.Constants.WorkerUtils;

import com.example.medicalappreminder_java.DrugReminderScreen.Presenter.DrugReminderPresenter;
import com.example.medicalappreminder_java.DrugReminderScreen.Presenter.DrugReminderPresenterInterface;

import com.example.medicalappreminder_java.LoginTest;
import com.example.medicalappreminder_java.MedModification.View.MedModifyActivity;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DrugReminderActivity extends AppCompatActivity implements DrugReminderViewInterface {

    ImageButton deleteButton , editButton;
    Button suspendButton;
    ImageView drugIconImageView ;
    TextView drugNameTextView , drugStrengthTextView , howOftenTextView
            , conditionTextView , refillTextView;


    RecyclerView drugReminderRecyclerView ;
    DrugReminderAdapter drugReminderAdapter ;
    Medicine medicine;


    DrugReminderPresenterInterface drugReminderPresenter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_reminder);


        // get medecine from get intent
        getMedicineFromIntent() ;

        settingIds();
        drugReminderPresenter = new DrugReminderPresenter(this , this) ;
        settingRecyclerView();


        // replace number with remind me when i have
        setUI();

        suspendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //***************************************** presenter code **************************
                drugReminderPresenter.suspendMedicine();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to edit screen
               Intent intent = new Intent(DrugReminderActivity.this, MedModifyActivity.class);
               intent.putExtra(Keys.USER_MED,medicine);
               startActivity(intent);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drugReminderPresenter.deleteMedicine();
                Toast.makeText(getApplicationContext(), "deleteeed", Toast.LENGTH_SHORT).show();

                // RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
                // LocalSourceInterface localSourceInterface = new ConcreteLocalSource(DrugReminderActivity.this);
                // RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,DrugReminderActivity.this);

                // SharedPreferences preferences = getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
                // String userEmail = preferences.getString("emailKey" , "user email") ;
                // User currentUser = repoClass.findUserByEmail(userEmail);
                // List<Medicine> listOfMedications = currentUser.getListOfMedications();

                // //ConcurrentModificationException while remove medicine
                // for(Iterator<Medicine> med = listOfMedications.iterator();med.hasNext();){
                //     Medicine removedMed = med.next();
                //     if(removedMed.getUuid().equals(medicine.getUuid())) {
                //         listOfMedications.remove(removedMed);
                //         repoClass.deleteMedicine(medicine);


                // Presenter Line ===============================================================================================================================
                //         WorkerUtils.deleteAllRequestsFromWorkManagerByMed(medicine.getUuid());


                //         break;
                //     }
                // }
                // currentUser.setListOfMedications(listOfMedications);
                // repoClass.updateUser(currentUser);
                 finish();

            }
        });
    }
    public void settingIds (){
        deleteButton = findViewById(R.id.deleteDrugButton) ;
        editButton = findViewById(R.id.editDrugButton) ;
        suspendButton = findViewById(R.id.suspendButton) ;
        drugIconImageView = findViewById(R.id.reminderDrugIconImageView) ;
        drugNameTextView = findViewById(R.id.drugNameTextV) ;
        drugStrengthTextView = findViewById(R.id.drugStrenghtTextView) ;
        howOftenTextView = findViewById(R.id.howOftenTextView) ;
        conditionTextView = findViewById(R.id.conditionTextView) ;
        refillTextView = findViewById(R.id.drugRefillTextView) ;

    }

    public void setUI(){
        refillTextView.setText(medicine.getNumberOfPillsLeft()+" Pills left \nRefill Reminder: when i have " + medicine.getRemindMeWhenIHaveHowManyPillsLeft() + " Pills Left"  );
        drugIconImageView.setImageResource(medicine.getImage());
        drugNameTextView.setText(medicine.getName());
        drugStrengthTextView.setText(medicine.getStrengthAmount()+" "+ medicine.getStrength().toString());
        howOftenTextView.setText(medicine.getDose_howOften().toString());
        conditionTextView.setText(medicine.getCondition());
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
    public Medicine getMedicineFromIntent() {
        Intent intent = getIntent();
        medicine = (Medicine) intent.getSerializableExtra(Keys.USER_MED);
        return medicine;
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
