package com.example.medicalappreminder_java.HomeScreen.Presenter.HomeScreen;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.medicalappreminder_java.DataStorage.SharedPrefrencesModel;
import com.example.medicalappreminder_java.HomeScreen.View.HomeScreenActivity.HomeScreenInterfaceActivity;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;

import java.util.List;

public class Presenter implements PresenterInterface{


    HomeScreenInterfaceActivity homScreen;
    Context context;
    String userEmail;
    String userName;
    SharedPrefrencesModel sharedPrefrencesModel;

    public Presenter(HomeScreenInterfaceActivity homescreen, Context context){
        this.homScreen = homescreen;
        this.context = context;
        SharedPreferences preferences = context.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
        userName = preferences.getString("userNameKey" , "user name") ;
        userEmail = preferences.getString("emailKey" , "user email") ;
        sharedPrefrencesModel = SharedPrefrencesModel.getInstance(context);
    }
    @Override
    public void getDependent() {
        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);
        User currentUser = repoClass.findUserByEmail(userEmail);
        List<User> listOfDependant = currentUser.getListOfDependant();
        for (int i = 0; i < listOfDependant.size(); i++) {
            homScreen.addNewDependentToDrawer(listOfDependant.get(i).getFirstName() + " " + listOfDependant.get(i).getLastName());
        }
    }

    @Override
    public void getMedFriend() {
        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);
        User currentUser = repoClass.findUserByEmail(userEmail);
        List<User> listOfMedFriends = currentUser.getListOfInvitations();
        for (int i = 0; i < listOfMedFriends.size(); i++) {
            String friendName = listOfMedFriends.get(i).getFirstName() + " " + listOfMedFriends.get(i).getLastName();
            sharedPrefrencesModel.writeInlistOfMedFriends(friendName,listOfMedFriends.get(i).getEmail());
            homScreen.addNewDependentToDrawer(friendName);
        }
    }

    @Override
    public String getUserName() {
        return userName;
    }
}
