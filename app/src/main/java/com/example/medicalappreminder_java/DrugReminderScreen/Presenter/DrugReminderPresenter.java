package com.example.medicalappreminder_java.DrugReminderScreen.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.medicalappreminder_java.Constants.OnRespondToMethod;
import com.example.medicalappreminder_java.DrugReminderScreen.View.DrugReminderActivity;
import com.example.medicalappreminder_java.DrugReminderScreen.View.DrugReminderViewInterface;
import com.example.medicalappreminder_java.NotificationDialog.OnlineUsers;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;
import com.example.medicalappreminder_java.networkConnectivity.NetworkChangeReceiver;

import java.util.Iterator;
import java.util.List;

public class DrugReminderPresenter implements DrugReminderPresenterInterface, OnlineUsers {

    Context context;
    DrugReminderViewInterface drugReminderView;
    String userEmail;
    Medicine medicine;
    RepoClass repoClass;


    public DrugReminderPresenter(Context context, DrugReminderViewInterface drugReminderView) {
        this.context = context;
        this.drugReminderView = drugReminderView;
        medicine = drugReminderView.getMedicineFromIntent();

    }


    @Override
    public void suspendMedicine() {
        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        repoClass = RepoClass.getInstance(remoteSourceInterface, localSourceInterface, context);

        SharedPreferences preferences = context.getSharedPreferences("preferencesFile", Context.MODE_PRIVATE);
        userEmail = preferences.getString("emailKey", "user email");
        User currentUser = repoClass.findUserByEmail(userEmail);


        List<Medicine> listOfMedications = currentUser.getListOfMedications();
        Iterator<Medicine> iterator = listOfMedications.iterator();
        while (iterator.hasNext()) {
            Medicine med = iterator.next();
            if (med.getUuid().equals(medicine.getUuid()))
                listOfMedications.remove(med);
                medicine.setState("Inactive");
                repoClass.updateMedicine(medicine);
        }

        listOfMedications.add(medicine);
        currentUser.setListOfMedications(listOfMedications);
        repoClass.updateUser(currentUser);

        //for room
        if (NetworkChangeReceiver.isThereInternetConnection == true) {
            repoClass.getUsersFromFireStore(this , OnRespondToMethod.suspend);
        }
    }

    /*
    RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        repoClass = RepoClass.getInstance(remoteSourceInterface, localSourceInterface, context);

        SharedPreferences preferences = context.getSharedPreferences("preferencesFile", Context.MODE_PRIVATE);
        userEmail = preferences.getString("emailKey", "user email");
        User currentUser = repoClass.findUserByEmail(userEmail);
        List<Medicine> listOfMedications = currentUser.getListOfMedications();

        Iterator<Medicine> iterator = listOfMedications.iterator();
        while (iterator.hasNext()) {
            Medicine med = iterator.next();
            if (med.getUuid().equals(medicine.getUuid()))
                listOfMedications.remove(med);
                medicine.setState("Inactive");
                repoClass.updateMedicine(medicine);
        }

        listOfMedications.add(medicine);
        currentUser.setListOfMedications(listOfMedications);
        repoClass.updateUser(currentUser);

        //for room
        if (NetworkChangeReceiver.isThereInternetConnection == true) {
            repoClass.getUsersFromFireStore(this);
        }
     */


    @Override
    public void deleteMedicine() {
        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        repoClass = RepoClass.getInstance(remoteSourceInterface, localSourceInterface, context);

        SharedPreferences preferences = context.getSharedPreferences("preferencesFile", Context.MODE_PRIVATE);
        userEmail = preferences.getString("emailKey", "user email");
        User currentUser = repoClass.findUserByEmail(userEmail);


        List<Medicine> listOfMedications = currentUser.getListOfMedications();
        //ConcurrentModificationException while remove medicine
        for (Iterator<Medicine> med = listOfMedications.iterator(); med.hasNext(); ) {
            Medicine removedMed = med.next();
            if (removedMed.getUuid().equals(medicine.getUuid())) {
                listOfMedications.remove(removedMed);
                repoClass.deleteMedicine(medicine);
                break;
            }
        }
        currentUser.setListOfMedications(listOfMedications);
        repoClass.updateUser(currentUser);

        if (NetworkChangeReceiver.isThereInternetConnection == true) {
            repoClass.getUsersFromFireStore(this , OnRespondToMethod.delete);
        }

    }

    @Override
    public void onResponse(List<User> userList , OnRespondToMethod method) {
        User fireStoreCurrentUser = new User();
        User oldFireStoreUser = new User();

        Medicine oldMedicine = new Medicine();
        Medicine newMedicine = new Medicine();


        Log.e("TAG", "onResponse: " + userEmail + userList.size());
        for (User fireStoreUser : userList) {
            if (fireStoreUser.getEmail() == null) {

            } else {
                if (fireStoreUser.getEmail().equals(userEmail)) {
                    oldFireStoreUser = fireStoreUser;
                    oldFireStoreUser.setUuid(fireStoreUser.getUuid());
                    List<Medicine> listOfMedications = oldFireStoreUser.getListOfMedications();
                    for (Medicine med : listOfMedications) {
                        if (med.getUuid().equals(medicine.getUuid())) {
                            oldMedicine = med;
                            oldMedicine.setUuid(medicine.getUuid());
                            newMedicine = oldMedicine;
                            if (method == OnRespondToMethod.suspend) {
                                newMedicine.setState("Inactive");
                            }
                            newMedicine.setUuid(medicine.getUuid());
                        }
                    }

                    listOfMedications.remove(oldMedicine);
                    if (method == OnRespondToMethod.suspend) {
                        listOfMedications.add(newMedicine);
                    }

                    fireStoreCurrentUser = fireStoreUser;
                    fireStoreCurrentUser.setUuid(fireStoreUser.getUuid());
                    fireStoreCurrentUser.setListOfMedications(listOfMedications);
                    repoClass.updateUserFromFireStore(oldFireStoreUser , fireStoreCurrentUser);
                    if (method == OnRespondToMethod.delete){
                        repoClass.deleteMedicineFromFireStore(oldMedicine);
                    }
                    else if (method == OnRespondToMethod.suspend) {
                        repoClass.updateMedicineFromFireStore(oldMedicine, newMedicine);
                    }
                    //repoClass.deleteMedicineFromFireStore(oldMedicine);
                    //repoClass.addMedicineToFireStore(newMedicine);
                }
            }

        }
    }

    @Override
    public void onFailure(String error) {

    }
}
