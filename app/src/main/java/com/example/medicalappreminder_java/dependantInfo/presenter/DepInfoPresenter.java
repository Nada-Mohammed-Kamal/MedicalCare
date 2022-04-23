package com.example.medicalappreminder_java.dependantInfo.presenter;

import android.content.Context;

import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.RepoInterface;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.dependantInfo.PresenterInterface;
import com.example.medicalappreminder_java.dependantInfo.ViewInterface;
import com.example.medicalappreminder_java.models.User;
import com.example.medicalappreminder_java.networkConnectivity.NetworkChangeReceiver;
import com.example.medicalappreminder_java.roomdb.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class DepInfoPresenter implements PresenterInterface {
    ViewInterface viewInterface;
    RepoInterface repoInterface;
    public DepInfoPresenter(RepoInterface repoInterface, ViewInterface viewInterface) {
        this.repoInterface =  repoInterface;
        this.viewInterface = viewInterface;
    }

    @Override
    public void addDependant(User user) {
        repoInterface.insertUser(user);
        //add to fireStore too if there is internet
        //hereeeeeeeeeeeeeeeeeeeeeeeeeeee
        if (NetworkChangeReceiver.isThereInternetConnection == true){
            FireStoreHandler fireStoreHandler = new FireStoreHandler();
            fireStoreHandler.addUserToFireStore(user);
        }
        viewInterface.viewToastAddedDependantSuccessfully();
    }

    @Override
    public void addDependantToTheCurrentUser(User user , Context context , String userEmail) {
        User fireStoreCurrentUser = new User();
        User oldFireStoreUser = new User();
        List<User> dependents = new ArrayList<>();
        dependents.add(user);
        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);
        //for room
        //make sure that the get method works because it might be sending a null list
        User currentUser = repoClass.findUserByEmail(userEmail);
        currentUser.setListOfDependant(dependents);
        repoClass.updateUser(currentUser);
        //for fireStore
        if (NetworkChangeReceiver.isThereInternetConnection == true){
            //////////////////// change to working method ////////////////////
            repoClass.getUsersFromFireStore();
            List<User> usersList = repoClass.getUsersList();
            //////////////////// change to working method ////////////////////
            for (User fireStoreUser : usersList){
                if(fireStoreUser.getEmail() == userEmail){
                    oldFireStoreUser = fireStoreUser;
                    fireStoreCurrentUser = fireStoreUser;
                }
            }
            fireStoreCurrentUser.setListOfDependant(dependents);
            repoClass.updateUserFromFireStore(oldFireStoreUser , fireStoreCurrentUser);
        }
    }


}
