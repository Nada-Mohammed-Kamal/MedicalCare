package com.example.medicalappreminder_java.Login.LoginView;

public interface LogInViewInterface {

    public String getEmail() ;
    public String getPassword() ;
    public void setEmailEditTextError(String error) ;
    public void setPasswordEditTextError(String error) ;
    public void makeToast(String message) ;
    public void setProgressbarVisible() ;
    public void setProgressbarGone();
    public void gotoHomeScreen();

}
