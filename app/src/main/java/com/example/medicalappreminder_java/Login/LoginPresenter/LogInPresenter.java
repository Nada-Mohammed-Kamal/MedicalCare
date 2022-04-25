package com.example.medicalappreminder_java.Login.LoginPresenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LifecycleOwner;

import com.example.medicalappreminder_java.Constants.OnRespondToMethod;
import com.example.medicalappreminder_java.DataStorage.SharedPrefrencesModel;
import com.example.medicalappreminder_java.FireBaseModels.Authentication.AuthenticationHandler;
import com.example.medicalappreminder_java.Login.LoginView.LogInViewInterface;
import com.example.medicalappreminder_java.NotificationDialog.OnlineUsers;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.RepoInterface;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.User;
import com.example.medicalappreminder_java.networkConnectivity.NetworkChangeReceiver;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.util.List;

public class LogInPresenter implements LogInPresenterInterface , OnlineUsers {

    String email , password;
    LogInViewInterface logInView ;
    Context context ;
    AuthenticationHandler authenticationHandler ;
    LifecycleOwner lifecycleOwner ;
    SharedPrefrencesModel sharedPrefrencesModel ;
    RepoInterface repoClass;
    String userEmail;
    User user;


    public LogInPresenter(LogInViewInterface logInView , Context context , LifecycleOwner lifecycleOwner){

        this.lifecycleOwner = lifecycleOwner ;
        this.logInView = logInView ;
        this.context = context ;




        authenticationHandler = new AuthenticationHandler(context , logInView , lifecycleOwner) ;
        sharedPrefrencesModel = SharedPrefrencesModel.getInstance(context) ;


    }

    public void getEmailAndPassword(){
        email = logInView.getEmail() ;
        password = logInView.getPassword() ;

    }


    @Override
    public void logIn() {

        getEmailAndPassword();

        if (email.isEmpty()){
            logInView.setEmailEditTextError("Email is required");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            logInView.setEmailEditTextError("Wrong Email");
            return;
        }

        if (password.isEmpty()){
            logInView.setPasswordEditTextError("Password is required");
            return;
        }

        if (password.length() < 6){
            logInView.setPasswordEditTextError("Minimum length of password should be 6");
            return;
        }

        logInView.setProgressbarVisible();
        authenticationHandler.signInWithEmailAndPassword(email,password);


        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler(context);
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);

        getCurrentUserFromFireStore();
        if(!(userEmail.equals("user email"))){
            repoClass.listenToInvitation(user);
        }
    }

    @Override
    public void logOut() {
        authenticationHandler.signOut();
    }

    @Override
    public void googleOnActivityResult(int requestCode , Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        authenticationHandler.signInWithGoogle(requestCode , data);
    }

    @Override
    public GoogleSignInClient getGoogleClient() {
        return authenticationHandler.getGoogleSignInClient();
    }

    void getCurrentUserFromFireStore(){

        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler(context);
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
         repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);

       // userEmail = sharedPrefrencesModel.getEmailFromPref();
        SharedPreferences preferences = context.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
        userEmail = preferences.getString("emailKey" , "user email") ;


        if (NetworkChangeReceiver.isThereInternetConnection == true && !(userEmail.equals("user email"))){
            //////////////////// change to working method ////////////////////
            repoClass.getUsersFromFireStore(this , OnRespondToMethod.addDep);
            //////////////////// change to working method ////////////////////
        }


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
