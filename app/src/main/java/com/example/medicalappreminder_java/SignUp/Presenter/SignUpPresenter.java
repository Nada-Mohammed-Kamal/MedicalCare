package com.example.medicalappreminder_java.SignUp.Presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.SignUp.View.SignUpViewInterface;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class SignUpPresenter implements SignUpPresenterInterafce {

    String email , password;
    SignUpViewInterface signUpView ;
    Context context ;

    private FirebaseAuth mAuth;
    private  FirebaseUser user ;

    private static final String TAG = "GoogleActivity";
    public static final int RC_SIGN_IN = 1000;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInOptions googleSignInOptions;


    public SignUpPresenter(SignUpViewInterface signUpView , Context context){
        this.signUpView = signUpView ;
        this.context = context ;
        mAuth = FirebaseAuth.getInstance();
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build() ;
        googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions) ;
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

        mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                signUpView.setProgressbarGone();
                if (task.isSuccessful()){
                    Toast.makeText(context, "User Registered successfully", Toast.LENGTH_SHORT).show();
                    user = mAuth.getCurrentUser() ;
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, "Verification is sent to your Email ", Toast.LENGTH_SHORT).show();
                        }
                    }); 
                    signUpView.gotoInfoRegistrationScreen();
                }else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(context, "This Email Is Already Registered", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void logIn() {

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
        mAuth.signInWithEmailAndPassword(email , password) .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                signUpView.setProgressbarGone();
                if (task.isSuccessful()){
                    // go to specific screen
                    // shared preferences
                    Toast.makeText(context, "Login successfully", Toast.LENGTH_SHORT).show();
                    signUpView.gotoHomeScreen();
                }else {
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void logOut() {
        mAuth.signOut();
        Toast.makeText(context, "You Have Logged out ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void signUpWithGoogle() {
//        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build() ;
//        googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions) ;

    }


    @Override
    public void googleOnActivityResult(int requestCode , Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.e(TAG, "onActivityResult: Google Sign In was successful");
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                signUpView.gotoHomeScreen();
                //firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e(TAG, "Google sign in failed", e);
            }
        }
    }

    @Override
    public GoogleSignInClient getClient() {
        return googleSignInClient;
    }

}
