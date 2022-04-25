package com.example.medicalappreminder_java.FireBaseModels.Authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.medicalappreminder_java.Constants.OnRespondToMethod;
import com.example.medicalappreminder_java.DataStorage.SharedPrefrencesModel;
import com.example.medicalappreminder_java.Login.LoginView.LogInViewInterface;
import com.example.medicalappreminder_java.Login.LoginView.LoginActivity;
import com.example.medicalappreminder_java.NotificationDialog.OnlineUsers;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.SignUp.View.SignUpViewInterface;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationHandler implements OnlineUsers {

    public static AuthenticationHandler authenticationHandler = null;
    String emailFromPref;
    SignUpViewInterface signUpViewRef;
    RepoClass repoClass;
    LogInViewInterface logInView;
    private Context context;
    SharedPrefrencesModel sharedPrefrencesModel;
    User fireStoreUser;
    FireStoreHandler fireStoreHandler;
    LifecycleOwner lifecycleOwner;
    List<User> retrivedUsers = new ArrayList<>();

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private static final String TAG = "GoogleActivity";
    public static final int RC_SIGN_IN = 1000;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInOptions googleSignInOptions;


    public AuthenticationHandler(Context context, SignUpViewInterface signUpView) {
        this.context = context;
        this.signUpViewRef = signUpView;
        mAuth = FirebaseAuth.getInstance();
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions);

         /*
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail().build() ;
        googleSignInClient = GoogleSignIn.getClient(context,googleSignInOptions) ;
        // second method
        oneTapClient = Identity.getSignInClient(context) ;
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(context.getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build();
        */
    }

    public AuthenticationHandler(Context context, LogInViewInterface logInView, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.logInView = logInView;
        this.lifecycleOwner = lifecycleOwner;
        mAuth = FirebaseAuth.getInstance();
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions);
        sharedPrefrencesModel = SharedPrefrencesModel.getInstance(context);
    }


    /*
    public static AuthenticationHandler getInstance(Context context , SignUpViewInterface signUpView , LogInViewInterface logInView){
        if(authenticationHandler == null) {
            authenticationHandler = new AuthenticationHandler(context , signUpView , logInView);
        }
        return authenticationHandler ;
    }
     */


    public void createUserWithEmailAndPassword(String email, String password, String userName) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                signUpViewRef.setProgressbarGone();
                if (task.isSuccessful()) {
                    signUpViewRef.makeToast("User Registered successfully");
                    user = mAuth.getCurrentUser();

                    if (user != null) {
                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                .setDisplayName(userName).build();
                        user.updateProfile(profileChangeRequest);
                    }

                    fireStoreUser = new User(userName, email);
                    fireStoreUser.setFirstName(userName);
                    //fireStoreUser.setFireStoreId(user.getUid()) ;
                    String fireStoreUserUuid = fireStoreUser.getUuid();
                    SharedPrefrencesModel sharedPrefrencesModel = SharedPrefrencesModel.getInstance(context) ;
                    sharedPrefrencesModel.writeInSharedPreferences(fireStoreUserUuid);
                    fireStoreHandler = new FireStoreHandler();
                    Log.e("****", "onComplete: "+user.getUid() );
                    fireStoreHandler.addUserToFireStore(fireStoreUser );
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            signUpViewRef.makeToast("Verification is sent to your Email ");
                        }
                    });
                    signUpViewRef.gotoLoginScreen();
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        signUpViewRef.makeToast("This Email Is Already Registered");
                    } else {
                        signUpViewRef.makeToast(task.getException().getMessage());
                    }
                }
            }
        });

    }


    public void signInWithEmailAndPassword(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                logInView.setProgressbarGone();
                if (task.isSuccessful()) {
                    // go to specific screen
                    // shared preferences
                    user = mAuth.getCurrentUser();
                    //user.getDisplayName() ;
                    logInView.makeToast("Login successfully");
                    sharedPrefrencesModel.writeInSharedPreferences(email, password, user.getDisplayName());
                    //to check if the user is in the room or not to add him
                    RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
                    LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
                     repoClass = RepoClass.getInstance(remoteSourceInterface, localSourceInterface, context);

                    SharedPreferences preferences = context.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
                    emailFromPref = preferences.getString("emailKey","N/A");


                    repoClass.getUsersFromFireStore(AuthenticationHandler.this , OnRespondToMethod.skip);
                    User userByEmail = repoClass.findUserByEmail(email);
                    /*
                    if (userByEmail == null) {
                        ////////////here
                        fireStoreHandler = new FireStoreHandler();

                        LiveData<List<User>> usersLiveData = fireStoreHandler.getUserLiveData().observe(, new Observer<List<User>>() {
                            @Override
                            public void onChanged(List<User> userList) {
                                for (int i = 0; i < userList.size(); i++) {
                                    if (userList.get(i).getEmail() == email) {
                                        fireStoreUser = userList.get(i);
                                    }
                                }
                            }
                        });


                        //int size = usersLiveData.getValue().size();
                        //Toast.makeText(context, "size = " + size, Toast.LENGTH_SHORT).show();
//                        for (int i = 0; i < usersLiveData.getValue().size(); i++) {
//                            if (usersLiveData.getValue().get(i).getEmail() == email) {
//                                fireStoreUser = usersLiveData.getValue().get(i);
//                            }
//                        }
                        repoClass.insertUser(fireStoreUser);
                    }
                    */
                        logInView.gotoHomeScreen();
                } else {
                    logInView.makeToast(task.getException().getMessage());
                }
            }
        });
    }


    public void signOut() {
        mAuth.signOut();
        logInView.makeToast("You Have Logged out ");
    }


    public void signInWithGoogle(int requestCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.e(TAG, "onActivityResult: Google Sign In was successful");
                logInView.makeToast("Google Sign In was successful");
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                //GoogleSignInAccount account1 = GoogleSignIn.getLastSignedInAccount(context) ;
                Log.e(TAG, "googleOnActivityResult: " + account.getDisplayName());
                logInView.makeToast(account.getDisplayName());
                sharedPrefrencesModel.writeInSharedPreferences(account.getEmail(), account.getDisplayName());

                User user = new User(account.getDisplayName(), account.getEmail());
                RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
                LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
                RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface, localSourceInterface, context);
                repoClass.insertUser(user);
                logInView.gotoHomeScreen();
                //firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e(TAG, "Google sign in failed", e);
            }

        }
        /*
        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    if (idToken !=  null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with Firebase.
                        Log.d(TAG, "Got ID token.");
                        firebaseAuthWithGoogle(idToken);
                    }
                } catch (ApiException e) {
                    Log.e(TAG, "googleOnActivityResult: faiiiiiil");
                    Toast.makeText(context, "faaaiiiiiil", Toast.LENGTH_SHORT).show();
                    // ...
                }
                break;
        }
        */

    }

    public GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }

    @Override
    public void onResponse(List<User> userList, OnRespondToMethod method) {
        retrivedUsers = userList;
        for(User user : retrivedUsers){
            if(user.getEmail().equals(emailFromPref)){
                repoClass.listenToDataChangeInFireStore(user);
            }
        }
    }

    @Override
    public void onFailure(String error) {
        Log.e(TAG, "onFailure: Failed to fetch data from signUp in Authentication handler" );
    }

    /*
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
     */

}
