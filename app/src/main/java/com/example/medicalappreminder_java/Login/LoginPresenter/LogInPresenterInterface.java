package com.example.medicalappreminder_java.Login.LoginPresenter;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public interface LogInPresenterInterface {

    public void logIn() ;
    public void logOut() ;
    public void googleOnActivityResult(int requestCode , Intent data ) ;
    public GoogleSignInClient getGoogleClient() ;
}
