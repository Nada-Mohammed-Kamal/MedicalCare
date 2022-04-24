package com.example.medicalappreminder_java.dependantInfo.presenter;

import android.content.Context;
import android.util.Log;

import com.example.medicalappreminder_java.NotificationDialog.OnlineUsers;
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

import java.util.ArrayList;
import java.util.List;

public class DepInfoPresenter implements PresenterInterface, OnlineUsers {
    ViewInterface viewInterface;
    RepoInterface repoInterface;
    List<User> fetchedUsersListFromFireStore ;
    String userEmail;
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

    RepoClass repoClass;
    List<User> dependents;
    @Override
    public void addDependantToTheCurrentUser(User user , Context context , String userEmail) {
        this.userEmail= userEmail;
        dependents = new ArrayList<>();
        dependents.add(user);
        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);
        //for room
        //make sure that the get method works because it might be sending a null list
        User currentUser = repoClass.findUserByEmail(userEmail);
        currentUser.setListOfDependant(dependents);
        repoClass.updateUser(currentUser);
        //for fireStore
        if (NetworkChangeReceiver.isThereInternetConnection == true){
            //////////////////// change to working method ////////////////////
            repoClass.getUsersFromFireStore(this);
            //////////////////// change to working method ////////////////////
        }
    }

    @Override
    public void setUserListFromFireStore(List<User> convertedUserList) {
        fetchedUsersListFromFireStore = convertedUserList ;
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
                    fireStoreCurrentUser = fireStoreUser;
                    fireStoreCurrentUser.setUuid(fireStoreUser.getUuid());
                }
            }

        }

        fireStoreCurrentUser.setListOfDependant(dependents);
        repoClass.updateUserFromFireStore(oldFireStoreUser , fireStoreCurrentUser);

        viewInterface.setUsers(userList);

        viewInterface.viewToastAddedDependantSuccessfully();
    }

    @Override
    public void onFailure(String error) {
        viewInterface.responseError(error);

    }
}