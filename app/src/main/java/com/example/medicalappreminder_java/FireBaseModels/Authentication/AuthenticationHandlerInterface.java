package com.example.medicalappreminder_java.FireBaseModels.Authentication;

public interface AuthenticationHandlerInterface {

    public void createUserWithEmailAndPassword(String email , String password) ;
    public void signInWithEmailAndPassword(String email , String password) ;
    public void signOut() ;
    public void signInWithGoogle() ;
}
