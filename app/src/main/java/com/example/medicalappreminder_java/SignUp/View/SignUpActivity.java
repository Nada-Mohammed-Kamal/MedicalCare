package com.example.medicalappreminder_java.SignUp.View;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.medicalappreminder_java.Login.LoginView.LogInViewInterface;
import com.example.medicalappreminder_java.Login.LoginView.LoginActivity;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.SignUp.Presenter.SignUpPresenter;
import com.example.medicalappreminder_java.SignUp.Presenter.SignUpPresenterInterafce;


public class SignUpActivity extends AppCompatActivity implements SignUpViewInterface {


    EditText emailEditText , passwordEditText , userNameEditText;
    Button signUpButton ;
    ProgressBar progressBar ;
    String email , password , userName ;
    static String userNameKey = "userNameKey" ;
    SignUpPresenterInterafce signUpPresenter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        gettingIds();
        signUpPresenter = new SignUpPresenter(this , getApplicationContext())  ;
        signUpButton.setOnClickListener(vieww -> signUpPresenter.registerUser());

    }

    public void gettingIds(){
        emailEditText = findViewById(R.id.emailEditText) ;
        passwordEditText = findViewById(R.id.passwordEditText) ;
        userNameEditText = findViewById(R.id.registerUserNameEditText) ;
        signUpButton = findViewById(R.id.signUpButton) ;
        progressBar = findViewById(R.id.signUpProgressbar) ;
    }

    @Override
    public String getEmail() {
        email = emailEditText.getText().toString();
        return email ;
    }

    @Override
    public String getPassword() {
        password = passwordEditText.getText().toString() ;
        return password ;
    }

    @Override
    public String getUserName() {
        userName = userNameEditText.getText().toString() ;
        return userName;
    }

    @Override
    public void setEmailEditTextError(String error) {
        emailEditText.setError(error);
        emailEditText.requestFocus() ;
    }

    @Override
    public void setPasswordEditTextError(String error) {
        passwordEditText.setError(error);
        passwordEditText.requestFocus();
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setProgressbarVisible() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setProgressbarGone() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void gotoLoginScreen() {

        Intent intent = new Intent(this, LoginActivity.class) ;
        // ******* el user name aho ya Esraaaaaa *******
        intent.putExtra(userNameKey ,userName ) ;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
        startActivity(intent);

    }
}