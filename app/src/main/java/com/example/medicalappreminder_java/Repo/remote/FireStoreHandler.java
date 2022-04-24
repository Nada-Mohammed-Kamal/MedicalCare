package com.example.medicalappreminder_java.Repo.remote;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.medicalappreminder_java.Constants.EveryHowManyDaysWilltheMedBeTaken;
import com.example.medicalappreminder_java.NotificationDialog.OnlineUsers;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.dependantInfo.PresenterInterface;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FireStoreHandler implements RemoteSourceInterface {

    Context context;
    private FirebaseFirestore fireStoreDb;
    private CollectionReference usersFirestoreDb, medicineFirestoreDb , userRef;
    private String userCollectionName = "usersFirestoreDb", medicineCollectionName = "medicineFirestoreDb";
    List<User> convertedUserList ;
    List<Medicine> convertedMedicineList;
    LiveData<List<User>> userLiveData;
    MutableLiveData<List<User>> user_mutable_live_data;
    LiveData<List<Medicine>> medicineLiveData ;
    MutableLiveData<List<Medicine>> medicine_mutable_live_data ;
    PresenterInterface presenterInterface ;
    private FirebaseFirestore firebaseFirestore;
    OnlineUsers onlineUsers ;

    public FireStoreHandler(){
        fireStoreDb = FirebaseFirestore.getInstance();

        convertedUserList = new ArrayList<>() ;
        convertedMedicineList = new ArrayList<>() ;

        user_mutable_live_data = new MutableLiveData<>() ;
        userLiveData  = user_mutable_live_data;

        medicine_mutable_live_data = new MutableLiveData<>() ;
        medicineLiveData = medicine_mutable_live_data ;
    }

    public FireStoreHandler(Context context ) {

        this.context = context ;
        fireStoreDb = FirebaseFirestore.getInstance();

        convertedUserList = new ArrayList<>() ;
        convertedMedicineList = new ArrayList<>() ;

        user_mutable_live_data = new MutableLiveData<>() ;
        userLiveData  = user_mutable_live_data;

        medicine_mutable_live_data = new MutableLiveData<>() ;
        medicineLiveData = medicine_mutable_live_data ;
    }

    @Override
    public void addUserToFireStore(User user ) {

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

        fireStoreDb.collection(userCollectionName).document(user.getUuid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.e("TAG", "onSuccess: adding user");
            }
        });

    }

    @Override
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

    @Override
    public void getUsersFromFireStore(OnlineUsers onlineUsers) {
        fireStoreDb.collection(userCollectionName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> userDocumentSnapshotList = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : userDocumentSnapshotList) {
                        User user = documentSnapshot.toObject(User.class);
                        user.setFireStoreId(documentSnapshot.getId());
                        convertedUserList.add(user) ;
                        Log.e("TAG", "onSuccess: " + user.getFireStoreId());
//                       RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
//                        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
//                        RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);
//                        User userByEmail = repoClass.findUserByEmail(user.getEmail());
//                        userByEmail.setFireStoreId(documentSnapshot.getId());
//                        repoClass.updateUser(userByEmail);

//                        convertedUserList.add(user) ;
                        // *****************************
//                        presenterInterface.setUserListFromFireStore(convertedUserList);
//                        onlineUsers.setOnlineUserList(convertedUserList);
//                        user_mutable_live_data.setValue(convertedUserList);
                    }
                    onlineUsers.onResponse(convertedUserList);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("fireDB", "onFailure: fail to get users list from FS");
                onlineUsers.onFailure(e.getMessage());
            }
        });

    }

    @Override
    public void getMedicinesFromFireStore() {
        fireStoreDb.collection(medicineCollectionName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> medicineDocumentSnapshotList = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : medicineDocumentSnapshotList) {
                        Medicine medicine = documentSnapshot.toObject(Medicine.class);
                        medicine.setFireStoreId(documentSnapshot.getId());
                        convertedMedicineList.add(medicine);
                        medicine_mutable_live_data.setValue(convertedMedicineList);
                    }
                    //Toast.makeText(context, convMedicineList.get(1).getName(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("fireDB", "onFailure: fail to get medicine list from FS");
            }
        });

    }

    @Override
    public void updateUserFromFireStore(User oldUser , User newUser){
        fireStoreDb.collection(userCollectionName).document(oldUser.getUuid()).set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //Toast.makeText(context, "user updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void updateMedicineFromFireStore(Medicine oldMedicine , Medicine newMedicine){
        fireStoreDb.collection(medicineCollectionName).document(oldMedicine.getUuid()).set(newMedicine).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //Toast.makeText(context, "medicine updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void deleteUserFromFireStore(User user){
        fireStoreDb.collection(userCollectionName).document(user.getFireStoreId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context, "user deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void deleteMedicineFromFireStore(Medicine medicine){
        fireStoreDb.collection(medicineCollectionName).document("b4db3104-0c83-4ce7-b241-15367aac6c7c"/*medicine.getUuid()*/).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //Toast.makeText(context, "medicine deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    public List<User> getUsersList(){
//        return convertedUserList ;
//    }

    @Override
    public List<Medicine> getMedicinesList(){
        return convertedMedicineList;
    }

    @Override
    public LiveData<List<Medicine>> getMedicineLiveData() {
        return medicineLiveData;
    }

    @Override
    public void setMedicineLiveData(LiveData<List<Medicine>> medicineLiveData) {
        this.medicineLiveData = medicineLiveData;
    }

    @Override
    public LiveData<List<User>> getUserLiveData() {
        return userLiveData;
    }

    @Override
    public void setUserLiveData(LiveData<List<User>> userLiveData) {
        this.userLiveData = userLiveData;
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