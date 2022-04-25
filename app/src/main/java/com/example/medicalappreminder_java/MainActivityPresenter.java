package com.example.medicalappreminder_java;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.medicalappreminder_java.Constants.OnRespondToMethod;
import com.example.medicalappreminder_java.NotificationDialog.OnlineUsers;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.RepoInterface;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.User;
import com.example.medicalappreminder_java.networkConnectivity.NetworkChangeReceiver;

import java.util.List;

public class MainActivityPresenter implements OnlineUsers {
    RepoInterface repoClass;
    String userEmail;
    Context context;
    MainActivityPresenter mainActivityPresenter;
    User user;
    public MainActivityPresenter(Context context) {
        this.context = context;

        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler(context);
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);

        getCurrentUserFromFireStore();
        if(!(userEmail.equals("user email"))){
            repoClass.listenToInvitation(user);
        }
    }

    void getCurrentUserFromFireStore(){

        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler(context);
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);

        // userEmail = sharedPrefrencesModel.getEmailFromPref();
        SharedPreferences preferences = context.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
        userEmail = preferences.getString("emailKey" , "user email") ;


        //if (NetworkChangeReceiver.isThereInternetConnection == true && !(userEmail.equals("user email"))){
            //////////////////// change to working method ////////////////////
            repoClass.getUsersFromFireStore(this , OnRespondToMethod.addDep);
            //////////////////// change to working method ////////////////////
       // }
    }


    @Override
    public void onResponse(List<User> userList , OnRespondToMethod method) {
//        User fireStoreCurrentUser = new User();
//        User oldFireStoreUser = new User();
        Log.e("TAG", "onResponse: " +userEmail+ userList.size());
        for (User fireStoreUser : userList){
            if(fireStoreUser.getEmail() == null){

            }else {
                if(fireStoreUser.getEmail().equals(userEmail)){
//                    oldFireStoreUser = fireStoreUser;
//                    oldFireStoreUser.setUuid(fireStoreUser.getUuid());
//                    fireStoreCurrentUser = fireStoreUser;
//                    fireStoreCurrentUser.setUuid(fireStoreUser.getUuid());
                    user = fireStoreUser;
                }

                repoClass.listenToInvitation(user);
            }

        }



        //viewInterface.setUsers(userList);

        //viewInterface.viewToastAddedDependantSuccessfully();
    }

    @Override
    public void onFailure(String error) {
        //viewInterface.responseError(error);

    }
}
