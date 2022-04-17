package com.example.medicalappreminder_java.SignUp.Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Patterns;

import com.example.medicalappreminder_java.DataStorage.SharedPrefrencesModel;
import com.example.medicalappreminder_java.FireBaseModels.AuthenticationHandler;
import com.example.medicalappreminder_java.Login.LoginView.LogInViewInterface;
import com.example.medicalappreminder_java.SignUp.View.SignUpViewInterface;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;


public class SignUpPresenter implements SignUpPresenterInterafce {

    String email , password;
    SignUpViewInterface signUpView ;
    LogInViewInterface logInView ;
    Context context ;
    AuthenticationHandler authenticationHandler ;
    SharedPrefrencesModel sharedPrefrencesModel ;


    public SignUpPresenter(SignUpViewInterface signUpView , Context context ){

        this.signUpView = signUpView ;
        this.context = context ;
        this.logInView = logInView ;
        authenticationHandler = new AuthenticationHandler(context , signUpView ) ;
        sharedPrefrencesModel = SharedPrefrencesModel.getInstance(context) ;
    }

    public void getEmailAndPassword(){
        email = signUpView.getEmail() ;
        password = signUpView.getPassword() ;

    }

    @Override
    public void registerUser(){

        getEmailAndPassword();

        if (email.isEmpty()){
            signUpView.setEmailEditTextError("Email is required");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signUpView.setEmailEditTextError("Wrong Email");
            return;
        }

        if (password.isEmpty()){
            signUpView.setPasswordEditTextError("Password is required");
            return;
        }

        if (password.length() < 6){
            signUpView.setPasswordEditTextError("Minimum length of password should be 6");
            return;
        }

        signUpView.setProgressbarVisible();
        authenticationHandler.createUserWithEmailAndPassword(email,password);
    }

}
