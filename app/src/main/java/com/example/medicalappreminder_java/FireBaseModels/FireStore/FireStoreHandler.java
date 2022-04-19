package com.example.medicalappreminder_java.FireBaseModels.FireStore;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.medicalappreminder_java.SignUp.View.SignUpViewInterface;

import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FireStoreHandler implements Serializable {

    Context context;
    private FirebaseFirestore fireStoreDb;
    private CollectionReference usersFirestoreDb, medicineFirestoreDb;
    private String userCollectionName = "usersFirestoreDb", medicineCollectionName = "medicineFirestoreDb";
    List<User> convertedUserList ;
    List<Medicine> convMedicineList ;

    public FireStoreHandler(Context context , List<User> userList) {
        fireStoreDb = FirebaseFirestore.getInstance();
        this.context = context ;
        this.convertedUserList = userList ;
        //convertedUserList = new ArrayList<>() ;
        convMedicineList = new ArrayList<>() ;

    }

    public FireStoreHandler(Context context ) {
        fireStoreDb = FirebaseFirestore.getInstance();
        this.context = context ;
        convertedUserList = new ArrayList<>() ;
        convMedicineList = new ArrayList<>() ;

    }

    public void addUserToFireStore(User user) {

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

        usersFirestoreDb = fireStoreDb.collection(userCollectionName);
        usersFirestoreDb.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                // ***** replace toast with func make toast from view *****
                //Toast.makeText(context, "user added", Toast.LENGTH_SHORT).show();
                Log.e("fireDB", "onSuccess: added user");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(context, "fail in adding the user", Toast.LENGTH_SHORT).show();
                Log.e("fireDB", "onFailure: failed to add the user");
            }
        });

    }

    public void addMedicineToFireStore(Medicine medicine) {

        // ***** add validation to presenter *****

        medicineFirestoreDb = fireStoreDb.collection(medicineCollectionName);
        medicineFirestoreDb.add(medicine).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                // ***** replace toast with func make toast from view *****
                //Toast.makeText(context, "medicine added", Toast.LENGTH_SHORT).show();
                Log.e("fireDB", "onSuccess: medicne added");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(context, "fail in adding the medicine", Toast.LENGTH_SHORT).show();
                Log.e("fireDB", "onFailure: faild to add the medicine");
            }
        });

    }

    public void getUsersFromFireStore() {
        fireStoreDb.collection(userCollectionName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> userDocumentSnapshotList = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : userDocumentSnapshotList) {
                        User user = documentSnapshot.toObject(User.class);
                        convertedUserList.add(user) ;
                    }
                    Toast.makeText(context, convertedUserList.get(2).getFirstName(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, ""+convertedUserList.size(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("fireDB", "onFailure: fail to get users list from FS");
            }
        });

    }

    public void getMedicinesFromFireStore() {
        fireStoreDb.collection(medicineCollectionName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> medicineDocumentSnapshotList = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : medicineDocumentSnapshotList) {
                        Medicine medicine = documentSnapshot.toObject(Medicine.class);
                        convMedicineList.add(medicine);
                    }
                    
                    Toast.makeText(context, convMedicineList.get(1).getName(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("fireDB", "onFailure: fail to get medicine list from FS");
            }
        });

    }

    public void updateMedicineFromFireStore(Medicine oldMedicine , Medicine newMedicine){
        // .document(neb3at firetore id )
        fireStoreDb.collection(medicineCollectionName).document().set(newMedicine).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "medicine updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteMedicineFromFireStore(Medicine medicine){
        fireStoreDb.collection(medicineCollectionName).document().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context, "medicine deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public List<User> getUsersList(){
        return convertedUserList ;
    }
    public List<Medicine> getMedicinesList(){
        return convMedicineList ;
    }
}

/*
 List<DocumentSnapshot> userDocumentSnapshotList = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : userDocumentSnapshotList) {
                        User user = documentSnapshot.toObject(User.class);
                        convertedUserList.add(user) ;
                    }
                    Toast.makeText(context, convertedUserList.get(2).getFirstName(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, ""+convertedUserList.size(), Toast.LENGTH_SHORT).show();
                }
 */