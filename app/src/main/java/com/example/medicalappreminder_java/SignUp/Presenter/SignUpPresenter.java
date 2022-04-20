package com.example.medicalappreminder_java.SignUp.Presenter;

import android.content.Context;
import android.util.Patterns;

import com.example.medicalappreminder_java.DataStorage.SharedPrefrencesModel;
import com.example.medicalappreminder_java.FireBaseModels.Authentication.AuthenticationHandler;
import com.example.medicalappreminder_java.Login.LoginView.LogInViewInterface;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FirestoreManger;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.SignUp.View.SignUpViewInterface;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;

import java.util.ArrayList;


public class SignUpPresenter implements SignUpPresenterInterafce {

    String email , password , userName ;
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

    public void getEmailAndPasswordAndUserName(){
        email = signUpView.getEmail() ;
        password = signUpView.getPassword() ;
        userName = signUpView.getUserName() ;
    }

    @Override
    public void registerUser(){

        getEmailAndPasswordAndUserName();

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
        if (userName.isEmpty()){
            signUpView.setUserNameEditTextError("user name is required");
            return;
        }

        signUpView.setProgressbarVisible();
        authenticationHandler.createUserWithEmailAndPassword(email,password ,userName );
        User user = new User(userName, email, new ArrayList<User>() , new ArrayList<Medicine>());
        RemoteSourceInterface remoteSourceInterface = new FirestoreManger();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);
        repoClass.insertUser(user);
    }

}
