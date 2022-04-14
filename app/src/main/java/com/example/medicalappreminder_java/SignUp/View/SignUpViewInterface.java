package com.example.medicalappreminder_java.SignUp.View;

import android.widget.EditText;
import android.widget.ProgressBar;

public interface SignUpViewInterface {

    public String getEmail() ;
    public String getPassword() ;
    public void setEmailEditTextError(String error) ;
    public void setPasswordEditTextError(String error) ;
    public void setProgressbarVisible() ;
    public void setProgressbarGone();
    public void gotoHomeScreen();
    public void gotoInfoRegistrationScreen() ;

}
