package com.example.medicalappreminder_java.SignUp.Presenter;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public interface SignUpPresenterInterafce {

    public void registerUser() ;
    public void logIn() ;
    public void signUpWithGoogle() ;
    public void logOut() ;
    public void googleOnActivityResult(int requestCode , Intent data ) ;
    public GoogleSignInClient getClient() ;
}
