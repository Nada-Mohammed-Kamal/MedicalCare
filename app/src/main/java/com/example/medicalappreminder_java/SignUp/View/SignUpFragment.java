package com.example.medicalappreminder_java.SignUp.View;

import static com.example.medicalappreminder_java.SignUp.Presenter.SignUpPresenter.RC_SIGN_IN;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.SignUp.Presenter.SignUpPresenter;
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


public class SignUpFragment extends Fragment implements SignUpViewInterface {

    View view ;
    EditText emailEditText , passwordEditText;
    Button signUpButton , logInButton , googleButton , logOutButton;
    ProgressBar progressBar ;
    String email , password ;
    SignUpPresenter signUpPresenter ;

    private FirebaseAuth mAuth;

//    private static final String TAG = "GoogleActivity";
//    private static final int RC_SIGN_IN = 1000;
//    private GoogleSignInClient googleSignInClient;
//    private GoogleSignInOptions googleSignInOptions;


    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gettingIds();

        signUpPresenter = new SignUpPresenter(this , view.getContext() )  ;
        mAuth = FirebaseAuth.getInstance();
        signUpButton.setOnClickListener(vieww -> signUpPresenter.registerUser());
        logInButton.setOnClickListener(vieww -> signUpPresenter.logIn());
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpPresenter.logOut();
                //finish();
                //gotoHomeScreen();
            }
        });

//        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build() ;
//        googleSignInClient = GoogleSignIn.getClient(view.getContext(), googleSignInOptions) ;
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                Log.e(TAG, "onActivityResult: Google Sign In was successful");
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
//                gotoHomeScreen();
//                //firebaseAuthWithGoogle(account.getIdToken());
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Log.e(TAG, "Google sign in failed", e);
//            }
//        }
        //signUpPresenter.googleOnActivityResult(requestCode , data);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        return view ;
    }

    private void signIn() {
        //Intent signInIntent = googleSignInClient.getSignInIntent();
        Intent signInIntent = signUpPresenter.getClient().getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)

                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });

    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            Toast.makeText(view.getContext(), "google login successfully", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(view.getContext(), "google login erorrrr", Toast.LENGTH_SHORT).show();

        }
    }

    public void gettingIds(){
        emailEditText = view.findViewById(R.id.emailEditText) ;
        passwordEditText = view.findViewById(R.id.passwordEditText) ;
        signUpButton = view.findViewById(R.id.signUpButton) ;
        logInButton = view.findViewById(R.id.logInButton) ;
        logOutButton = view.findViewById(R.id.logOutButton) ;
        googleButton = view.findViewById(R.id.googleButton) ;
        progressBar = view.findViewById(R.id.progressbar) ;
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
        /*
        Intent intent = new Intent(this, Login.class)  ;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
        startActivity(intent);
         */
    }

    @Override
    public void gotoInfoRegistrationScreen() {
        /*
        Intent intent = new Intent(this, Login.class) ;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
        startActivity(intent);
         */
    }

}