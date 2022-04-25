package com.example.medicalappreminder_java.Login.LoginView;

import static com.example.medicalappreminder_java.FireBaseModels.Authentication.AuthenticationHandler.RC_SIGN_IN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.medicalappreminder_java.HomeScreen.View.HomeScreenActivity.HomeScreen;
import com.example.medicalappreminder_java.Login.LoginPresenter.LogInPresenter;
import com.example.medicalappreminder_java.Login.LoginPresenter.LogInPresenterInterface;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.SignUp.View.SignUpActivity;

public class LoginActivity extends AppCompatActivity implements LogInViewInterface {

    EditText emailEditText , passwordEditText;
    Button logInButton , googleButton  , gotoSignUpButton;
    LogInPresenterInterface logInPresenter ;
    ProgressBar progressBar ;
    String email , password , userName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gettingIds();
        getIncomingIntent() ;



        Toast.makeText(LoginActivity.this, "username : " + userName, Toast.LENGTH_SHORT).show();
        logInPresenter = new LogInPresenter (this , this ,this ) ;
        logInButton.setOnClickListener(vieww -> {
            logInPresenter.logIn() ;
            if (userName == null){
                SharedPreferences preferences = getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
                userName = preferences.getString("userNameKey" , "N/A") ;
            }
            Toast.makeText(LoginActivity.this, "username : " + userName, Toast.LENGTH_SHORT).show();
        });
        /*
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInPresenter.logOut();
                finish();
            }
        });

         */

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });
        gotoSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailEditText.setText("");
                passwordEditText.setText("");
                startActivity(new Intent(LoginActivity.this , SignUpActivity.class));
            }
        });
    }

    public String getIncomingIntent(){

        Intent incomingIntent = getIntent() ;
        userName = incomingIntent.getStringExtra(SignUpActivity.userNameKey) ;
        return userName ;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        logInPresenter.googleOnActivityResult(requestCode , data);

    }

    private void googleSignIn() {
        //Intent signInIntent = googleSignInClient.getSignInIntent();
        Intent signInIntent = logInPresenter.getGoogleClient().getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void gettingIds(){
        emailEditText = findViewById(R.id.logInEmailEditText) ;
        passwordEditText = findViewById(R.id.logInPasswordEditText) ;
        logInButton = findViewById(R.id.logInButton) ;
        googleButton = findViewById(R.id.googleButton) ;
        gotoSignUpButton = findViewById(R.id.gotoSignUpButton) ;
        progressBar = findViewById(R.id.logInProgressbar) ;

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
    public void gotoHomeScreen() {

        Intent intent = new Intent(this, HomeScreen.class)  ;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
        intent.putExtra(SignUpActivity.userNameKey ,userName ) ;
        //SharedPreferences preferences = getSharedPreferences(SharedPrefrencesModel.preferenceFile , Context.MODE_PRIVATE) ;
        //emailFromPref = preferences.getString("emailKey","N/A");
        //userNameFromPref = preferences.getString("userNameKey","N/A") ;

        startActivity(intent);

    }




    // update ui func (google)
    /*
    private void updateUI(FirebaseUser user) {
        if (user != null){
            Toast.makeText(context, "google login successfully", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "google login erorrrr", Toast.LENGTH_SHORT).show();

        }
    }
     */

}