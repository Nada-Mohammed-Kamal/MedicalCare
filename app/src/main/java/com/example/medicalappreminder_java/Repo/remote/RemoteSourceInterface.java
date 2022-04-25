package com.example.medicalappreminder_java.Repo.remote;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.medicalappreminder_java.Constants.OnRespondToMethod;
import com.example.medicalappreminder_java.NotificationDialog.OnlineUsers;
import com.example.medicalappreminder_java.Repo.NetworkDelegate;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public interface RemoteSourceInterface {

    public void addUserToFireStore(User user) ;

    public void addMedicineToFireStore(Medicine medicine) ;

    public void getUsersFromFireStore(OnlineUsers onlineUsers , OnRespondToMethod method) ;

    public void getMedicinesFromFireStore() ;

    public void updateUserFromFireStore(User oldUser , User newUser) ;

    public void updateMedicineFromFireStore(Medicine oldMedicine , Medicine newMedicine) ;

    public void deleteUserFromFireStore(User user) ;

    public void deleteMedicineFromFireStore(Medicine medicine) ;

//    public List<User> getUsersList() ;
    public List<Medicine> getMedicinesList();

    public LiveData<List<Medicine>> getMedicineLiveData();

    public void setMedicineLiveData(LiveData<List<Medicine>> medicineLiveData);

    public LiveData<List<User>> getUserLiveData() ;

    public void setUserLiveData(LiveData<List<User>> userLiveData);
}
