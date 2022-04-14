package com.example.medicalappreminder_java.SignUp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.medicalappreminder_java.Login;
import com.example.medicalappreminder_java.MainActivity;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.SignUp.Presenter.SignUpPresenter;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignUpActivity extends AppCompatActivity  {

    /*
    EditText emailEditText , passwordEditText;
    Button signUpButton , logInButton , googleButton , logOutButton;
    ProgressBar progressBar ;
    String email , password ;
    SignUpPresenter signUpPresenter ;
    
    private FirebaseAuth mAuth;

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 1000;

    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;

    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso ;
    private SignInClient oneTapClient ;
    private BeginSignInRequest signInRequest ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        gettingIds();

        signUpPresenter = new SignUpPresenter(this , this )  ;
        mAuth = FirebaseAuth.getInstance();
        signUpButton.setOnClickListener(view -> signUpPresenter.registerUser());
        logInButton.setOnClickListener(view -> signUpPresenter.logIn());
        //googleButton.setOnClickListener(view -> signUpPresenter.signUpWithGoogle());

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail()
//                .build() ;
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build() ;
        gsc = GoogleSignIn.getClient(this,gso) ;



        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });



        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpPresenter.logOut();
                finish();
                gotoHomeScreen();
            }
        });

    }

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.e(TAG, "onActivityResult: Google Sign In was successful");
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                gotoHomeScreen();
                //firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e(TAG, "Google sign in failed", e);
            }
        }

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            Toast.makeText(getApplicationContext(), "google login successfully", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "google login erorrrr", Toast.LENGTH_SHORT).show();

        }
    }

    public void gettingIds(){
        emailEditText = findViewById(R.id.emailEditText) ;
        passwordEditText = findViewById(R.id.passwordEditText) ;
        signUpButton = findViewById(R.id.signUpButton) ;
        logInButton = findViewById(R.id.logInButton) ;
        logOutButton = findViewById(R.id.logOutButton) ;
        googleButton = findViewById(R.id.googleButton) ;
        progressBar = findViewById(R.id.progressbar) ;
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
    public void setProgressbarVisible() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setProgressbarGone() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void gotoHomeScreen() {
        Intent intent = new Intent(this, Login.class)  ;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
        startActivity(intent);
    }

    @Override
    public void gotoInfoRegistrationScreen() {
        Intent intent = new Intent(this, Login.class) ;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
        startActivity(intent);
    }

*/


/*
    public void registerUser(){

        getEmail() ;
        getPassword() ;
        if (email.isEmpty()){
            emailEditText.setError("Email is required");
            emailEditText.requestFocus() ;
            return;

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Wrong Email");
            emailEditText.requestFocus() ;
            return;
        }

        if (password.isEmpty()){
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        }

        if (password.length() < 6){
            passwordEditText.setError("Minimum length of password should be 6");
            passwordEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "User Registered successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Error occured ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

 */


}