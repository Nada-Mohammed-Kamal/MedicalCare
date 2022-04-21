package com.example.medicalappreminder_java.activeandinactivemedscreen.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FirestoreManger;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.activeandinactivemedscreen.PresenterInterface;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;

import java.util.List;

public class ActiveInactivePresenter implements PresenterInterface {

    Context context;

    public ActiveInactivePresenter(Context context) {
        this.context = context;
    }

    @Override
    public List<Medicine> getCurrentUserMeds() {
        RemoteSourceInterface remoteSourceInterface = new FirestoreManger();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);
        SharedPreferences preferences = context.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
        String userEmail = preferences.getString("emailKey" , "user email") ;
        User currentUser = repoClass.findUserByEmail(userEmail);
        List<Medicine> listOfMedications = currentUser.getListOfMedications();
        return listOfMedications;
    }
}
