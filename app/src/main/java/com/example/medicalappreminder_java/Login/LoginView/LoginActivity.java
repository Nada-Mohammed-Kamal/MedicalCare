package com.example.medicalappreminder_java.Login.LoginView;

import static com.example.medicalappreminder_java.FireBaseModels.Authentication.AuthenticationHandler.RC_SIGN_IN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.medicalappreminder_java.Login.LoginPresenter.LogInPresenter;
import com.example.medicalappreminder_java.Login.LoginPresenter.LogInPresenterInterface;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.SignUp.View.SignUpActivity;

public class LoginActivity extends AppCompatActivity implements LogInViewInterface {

    EditText emailEditText , passwordEditText;
    Button logInButton , googleButton , logOutButton , gotoSignUpButton;
    LogInPresenterInterface logInPresenter ;
    ProgressBar progressBar ;
    String email , password ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gettingIds();
        logInPresenter = new LogInPresenter (this , this  ) ;
        logInButton.setOnClickListener(vieww -> logInPresenter.logIn());
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInPresenter.logOut();
                finish();
                //gotoHomeScreen();
            }
        });

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
        logOutButton = findViewById(R.id.logOutButton) ;
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
        /*
        Intent intent = new Intent(this, LoginTest.class)  ;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
        startActivity(intent);
         */
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