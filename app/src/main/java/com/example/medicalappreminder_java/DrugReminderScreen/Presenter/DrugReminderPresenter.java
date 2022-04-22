package com.example.medicalappreminder_java.DrugReminderScreen.Presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.medicalappreminder_java.DrugReminderScreen.View.DrugReminderActivity;
import com.example.medicalappreminder_java.DrugReminderScreen.View.DrugReminderViewInterface;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;

import java.util.Iterator;
import java.util.List;

public class DrugReminderPresenter implements DrugReminderPresenterInterface {

    Context context ;
    DrugReminderViewInterface drugReminderView ;

    Medicine medicine ;


    public DrugReminderPresenter(Context context, DrugReminderViewInterface drugReminderView) {
        this.context = context;
        this.drugReminderView = drugReminderView;
        medicine = drugReminderView.getMedicineFromIntent() ;
        
    }



    @Override
    public void suspendMedicine() {
        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);

        SharedPreferences preferences = context.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
        String userEmail = preferences.getString("emailKey" , "user email") ;
        User currentUser = repoClass.findUserByEmail(userEmail);
        List<Medicine> listOfMedications = currentUser.getListOfMedications();
        for (Medicine med:listOfMedications) {
            if(med.getUuid().equals(medicine.getUuid())) {
                listOfMedications.remove(med);
                medicine.setState("Inactive");
                repoClass.updateMedicine(medicine);
            }
        }
        listOfMedications.add(medicine);
        currentUser.setListOfMedications(listOfMedications);
        repoClass.updateUser(currentUser);
    }

    @Override
    public void deleteMedicine() {
        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);

        SharedPreferences preferences = context.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
        String userEmail = preferences.getString("emailKey" , "user email") ;
        User currentUser = repoClass.findUserByEmail(userEmail);
        List<Medicine> listOfMedications = currentUser.getListOfMedications();

        //ConcurrentModificationException while remove medicine
        for(Iterator<Medicine> med = listOfMedications.iterator(); med.hasNext();){
            Medicine removedMed = med.next();
            if(removedMed.getUuid().equals(medicine.getUuid())) {
                listOfMedications.remove(removedMed);
                repoClass.deleteMedicine(medicine);

            }
        }
        currentUser.setListOfMedications(listOfMedications);
        repoClass.updateUser(currentUser);
    }
}
