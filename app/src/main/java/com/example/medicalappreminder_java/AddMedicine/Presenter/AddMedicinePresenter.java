package com.example.medicalappreminder_java.AddMedicine.Presenter;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.medicalappreminder_java.AddMedicine.View.AddMedicineViewInterface;
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

public class AddMedicinePresenter implements AddMedicinePresenterInterface {
    RepoInterface repoClass;
    Context context;
    AddMedicineViewInterface addMedicineViewInterface;
    LifecycleOwner lifecycleOwner;


    public AddMedicinePresenter(Context context, AddMedicineViewInterface addMedicineViewInterface, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.addMedicineViewInterface = addMedicineViewInterface;
        this.lifecycleOwner = lifecycleOwner;
    }

    @Override
    public void addMedTOCurrentUser(Medicine medicine) {
        SharedPreferences preferences = context.getSharedPreferences("preferencesFile", Context.MODE_PRIVATE);
        String userEmail = preferences.getString("emailKey", "user email");
        User currentUser = repoClass.findUserByEmail(userEmail);

        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        RepoInterface repoClass = RepoClass.getInstance(remoteSourceInterface, localSourceInterface, context);
        repoClass.insertMedicine(medicine);
        addMedicineToFireStore(medicine);


        if (NetworkChangeReceiver.isThereInternetConnection == true) {
            //////////////////// change to working method ////////////////////
//            LiveData<List<User>> userLiveData = repoClass.getUserLiveData().observe(lifecycleOwner, new Observer<List<User>>() {
//                @Override
//                public void onChanged(List<User> users) {
//                    User fireStoreCurrentUser = new User();
//                    User oldFireStoreUser = new User();
//                    List<Medicine> medicines = new ArrayList<>();
//                    for (User fireStoreUser : users) {
//                        if (fireStoreUser.getEmail() == userEmail) {
//                            oldFireStoreUser = fireStoreUser;
//                            medicines = oldFireStoreUser.getListOfMedications();
//                            medicines.add(medicine);
//                            fireStoreCurrentUser = fireStoreUser;
//                            fireStoreCurrentUser.setListOfMedications(medicines);
//                        }
//                    }
//                    repoClass.updateUserFromFireStore(oldFireStoreUser, fireStoreCurrentUser);
//                }
//            });


            User fireStoreCurrentUser = new User();
            User oldFireStoreUser = new User();
            List<Medicine> medicines = new ArrayList<>();
            repoClass.getUserLiveData();
//            List<User> usersList = repoClass.getUsersList();
//            for (User fireStoreUser : usersList) {
//                if (fireStoreUser.getEmail() == userEmail) {
//                    oldFireStoreUser = fireStoreUser;
//                    medicines = oldFireStoreUser.getListOfMedications();
//                    medicines.add(medicine);
//                    fireStoreCurrentUser = fireStoreUser;
//                    fireStoreCurrentUser.setListOfMedications(medicines);
//                }
//            }
//            repoClass.updateUserFromFireStore(oldFireStoreUser, fireStoreCurrentUser);
            //////////////////// change to working method ////////////////////


            // al rabta fal room
            List<Medicine> listOfMedications = currentUser.getListOfMedications();
            listOfMedications.add(medicine);
            currentUser.setListOfMedications(listOfMedications);
            repoClass.updateUser(currentUser);
            addMedicineViewInterface.viewThatTheMedIsAddedSuccessfully();
        }
    }

    public void addMedicineToFireStore(Medicine medicine) {
        //add to fireStore too if there is internet
        //hereeeeeeeeeeeeeeeeeeeeeeeeeeee
        if (NetworkChangeReceiver.isThereInternetConnection == true) {
            repoClass.addMedicineToFireStore(medicine);
        }
        addMedicineViewInterface.viewThatTheMedIsAddedSuccessfully();
    }

}
