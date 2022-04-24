package com.example.medicalappreminder_java.HomeScreen.Presenter.HomeScreen;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.medicalappreminder_java.HomeScreen.View.HomeFragment.HomeScreenInterfaceActivity;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.User;

import java.util.List;

public class Presenter implements PresenterInterface{


    HomeScreenInterfaceActivity homScreen;
    Context context;
    String userEmail;
    String userName;
    public Presenter(HomeScreenInterfaceActivity homescreen, Context context){
        this.homScreen = homescreen;
        this.context = context;
        SharedPreferences preferences = context.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
        userName = preferences.getString("userNameKey" , "user name") ;
        userEmail = preferences.getString("emailKey" , "user email") ;
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
    public String getUserName() {
        return userName;
    }
}
