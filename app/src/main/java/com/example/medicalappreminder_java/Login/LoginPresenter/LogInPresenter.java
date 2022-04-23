package com.example.medicalappreminder_java.Login.LoginPresenter;

import android.content.Context;
import android.content.Intent;
import android.util.Patterns;

import androidx.lifecycle.LifecycleOwner;

import com.example.medicalappreminder_java.DataStorage.SharedPrefrencesModel;
import com.example.medicalappreminder_java.FireBaseModels.Authentication.AuthenticationHandler;
import com.example.medicalappreminder_java.Login.LoginView.LogInViewInterface;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class LogInPresenter implements LogInPresenterInterface{

    String email , password;
    LogInViewInterface logInView ;
    Context context ;
    AuthenticationHandler authenticationHandler ;
    LifecycleOwner lifecycleOwner ;
    //SharedPrefrencesModel sharedPrefrencesModel ;



    public LogInPresenter(LogInViewInterface logInView , Context context , LifecycleOwner lifecycleOwner){

        this.lifecycleOwner = lifecycleOwner ;
        this.logInView = logInView ;
        this.context = context ;
        authenticationHandler = new AuthenticationHandler(context , logInView , lifecycleOwner) ;
        //sharedPrefrencesModel = SharedPrefrencesModel.getInstance(context) ;
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
        //sharedPrefrencesModel.writeInSharedPreferences(email,password);
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

}
