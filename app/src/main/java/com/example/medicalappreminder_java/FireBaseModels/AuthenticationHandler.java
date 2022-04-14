package com.example.medicalappreminder_java.FireBaseModels;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationHandler implements AuthenticationHandlerInterface{

    public static AuthenticationHandler authenticationHandler = null ;
    private Context context ;
    private FirebaseAuth mAuth;
    private FirebaseUser user ;


    private AuthenticationHandler(Context context){
        this.context = context ;
        mAuth = FirebaseAuth.getInstance();
    }
    public static AuthenticationHandler getInstance(Context context){
        if(authenticationHandler == null) {
            authenticationHandler = new AuthenticationHandler(context);
        }
        return authenticationHandler ;
    }

    @Override
    public void createUserWithEmailAndPassword(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //signUpView.setProgressbarGone();
                if (task.isSuccessful()){
                    Toast.makeText(context, "User Registered successfully", Toast.LENGTH_SHORT).show();
                    user = mAuth.getCurrentUser() ;
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, "Verification is sent to your Email ", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //signUpView.gotoInfoRegistrationScreen();
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
    public void signInWithEmailAndPassword(String email, String password) {

    }

    @Override
    public void signOut() {

    }

    @Override
    public void signInWithGoogle() {

    }
}
