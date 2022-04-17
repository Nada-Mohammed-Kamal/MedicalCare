package com.example.medicalappreminder_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginTest extends AppCompatActivity {

    GoogleSignInOptions googleSignInOptions ;
    GoogleSignInClient googleSignInClient ;
    TextView email , pass ;
    Button signOut ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);

        email = findViewById(R.id.emailTxtv) ;
        pass = findViewById(R.id.passwordTxtv) ;
        signOut = findViewById(R.id.signOutButton) ;

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build() ;
        googleSignInClient = GoogleSignIn.getClient(this , googleSignInOptions) ;
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this) ;
        if (account != null){
            String personName = account.getDisplayName() ;
            String personEmail = account.getEmail() ;

            email.setText(personEmail);

            signOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signOut() ;
                }
            });
        }
    }

    void signOut(){
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
            }
        });
    }
}