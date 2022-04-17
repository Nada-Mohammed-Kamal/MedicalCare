package com.example.medicalappreminder_java.FireBaseModels.FireStore;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.medicalappreminder_java.SignUp.View.SignUpViewInterface;

import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireStoreHandler {



    Context context ;
    private FirebaseFirestore firestoreDb;
    CollectionReference usersFirestoreDb  ;

    
    // ******* change this var (signUpView) with full info view  *******
    public SignUpViewInterface signUpView ;

    public FireStoreHandler() {
        firestoreDb = FirebaseFirestore.getInstance();
    }

    public FireStoreHandler(FirebaseFirestore firestoreDb, SignUpViewInterface signUpView) {
        this.firestoreDb = firestoreDb;
        this.signUpView = signUpView;
        firestoreDb = FirebaseFirestore.getInstance();
    }

    public void addUserToFireStore(User user){

        // ***** add validation to presenter *****
        /*
        private boolean validateInputs(String name, String brand, String desc, String price, String qty) {
         if (name.isEmpty()) {
             editTextName.setError("Name required");
             editTextName.requestFocus();
             return true;
         }

         if (brand.isEmpty()) {
             editTextBrand.setError("Brand required");
             editTextBrand.requestFocus();
             return true;
         }

         if (desc.isEmpty()) {
             editTextDesc.setError("Description required");
             editTextDesc.requestFocus();
             return true;
         }

         if (price.isEmpty()) {
             editTextPrice.setError("Price required");
             editTextPrice.requestFocus();
             return true;
         }

         if (qty.isEmpty()) {
             editTextQty.setError("Quantity required");
             editTextQty.requestFocus();
             return true;
         }
         return false;
     }

        if (!validateInputs(name, brand, desc, price, qty)){call this method}
         */

        usersFirestoreDb = firestoreDb.collection("usersFirestoreDb") ;
        usersFirestoreDb.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                // ***** replace toast with func make toast from view *****
                Toast.makeText(context, "user added", Toast.LENGTH_SHORT).show();
                Log.e("fireDB", "onSuccess: added user");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "fail in adding the user", Toast.LENGTH_SHORT).show();
            }
        }) ;

    }

    public void addMedicineToFireStore(Medicine medicine){

        // ***** add validation to presenter *****


       //medicineFirestoreDb = firestoreDb.collection("medicineFirestoreDb") ;
        usersFirestoreDb.add(medicine).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                // ***** replace toast with func make toast from view *****
                Toast.makeText(context, "medicine added", Toast.LENGTH_SHORT).show();
                Log.e("fireDB", "onSuccess: medicne added" );

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "fail in adding the medicine", Toast.LENGTH_SHORT).show();
            }
        }) ;

    }
}
