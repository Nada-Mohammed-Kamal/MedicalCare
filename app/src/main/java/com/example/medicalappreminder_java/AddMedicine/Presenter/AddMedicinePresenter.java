package com.example.medicalappreminder_java.AddMedicine.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.medicalappreminder_java.AddMedicine.View.AddMedicineViewInterface;
import com.example.medicalappreminder_java.NotificationDialog.OnlineUsers;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.RepoInterface;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;
import com.example.medicalappreminder_java.networkConnectivity.NetworkChangeReceiver;

import java.util.ArrayList;
import java.util.List;

public class AddMedicinePresenter implements AddMedicinePresenterInterface , OnlineUsers {
    RepoInterface repoClass;
    Context context;
    AddMedicineViewInterface addMedicineViewInterface;
    LifecycleOwner lifecycleOwner;
    String userEmail;
    List<Medicine> medicines;
    Medicine medicine;


    public AddMedicinePresenter(Context context, AddMedicineViewInterface addMedicineViewInterface, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.addMedicineViewInterface = addMedicineViewInterface;
        this.lifecycleOwner = lifecycleOwner;
    }

    @Override
    public void addMedTOCurrentUser(Medicine medicine) {
        this.medicine = medicine;

        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        repoClass = RepoClass.getInstance(remoteSourceInterface, localSourceInterface, context);

        SharedPreferences preferences = context.getSharedPreferences("preferencesFile", Context.MODE_PRIVATE);
        userEmail = preferences.getString("emailKey", "user email");
        User currentUser = repoClass.findUserByEmail(userEmail);


        repoClass.insertMedicine(medicine);
        if (NetworkChangeReceiver.isThereInternetConnection == true){
            FireStoreHandler fireStoreHandler = new FireStoreHandler();
            fireStoreHandler.addMedicineToFireStore(medicine);
        }

        medicines = new ArrayList<>();

        //for fireStore
        if (NetworkChangeReceiver.isThereInternetConnection == true){
            //////////////////// change to working method ////////////////////
            repoClass.getUsersFromFireStore(this);
            //////////////////// change to working method ////////////////////
        }

            // al rabta fal room
            List<Medicine> listOfMedications = currentUser.getListOfMedications();
            listOfMedications.add(medicine);
            currentUser.setListOfMedications(listOfMedications);
            repoClass.updateUser(currentUser);
            addMedicineViewInterface.viewThatTheMedIsAddedSuccessfully();

    }


    @Override
    public void onResponse(List<User> userList) {
        User fireStoreCurrentUser = new User();
        User oldFireStoreUser = new User();
        Log.e("TAG", "onResponse: " +userEmail+ userList.size());
        for (User fireStoreUser : userList){
            if(fireStoreUser.getEmail() == null){

            }else {
                if(fireStoreUser.getEmail().equals(userEmail)){
                    oldFireStoreUser = fireStoreUser;
                    oldFireStoreUser.setUuid(fireStoreUser.getUuid());
                    List<Medicine> listOfMedications = oldFireStoreUser.getListOfMedications();
                    listOfMedications.add(medicine);
                    medicines = listOfMedications;
                    fireStoreCurrentUser = fireStoreUser;
                    fireStoreCurrentUser.setUuid(fireStoreUser.getUuid());
                    fireStoreCurrentUser.setListOfMedications(medicines);
                }
            }

        }

        repoClass.updateUserFromFireStore(oldFireStoreUser , fireStoreCurrentUser);
        //viewInterface.setUsers(userList);
        addMedicineViewInterface.viewThatTheMedIsAddedSuccessfully();
    }

    @Override
    public void onFailure(String error) {
        //viewInterface.responseError(error);
    }
}
