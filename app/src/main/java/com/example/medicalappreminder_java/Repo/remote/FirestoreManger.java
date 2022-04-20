package com.example.medicalappreminder_java.Repo.remote;



import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.medicalappreminder_java.Repo.NetworkDelegate;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;

import java.util.List;


public class FirestoreManger implements RemoteSourceInterface {

    public static FirestoreManger firestoreMangerInstance = null;

    public FirestoreManger() {

    }

    public static FirestoreManger getInstance(){
        if (firestoreMangerInstance == null){
            firestoreMangerInstance = new FirestoreManger();
        }
        return firestoreMangerInstance;
    }

    //fetch data mn al fire store

    @Override
    public void addUserToFireStore(User user) {

    }

    @Override
    public void addMedicineToFireStore(Medicine medicine) {

    }

    @Override
    public void getUsersFromFireStore() {

    }

    @Override
    public void getMedicinesFromFireStore() {

    }

    @Override
    public void updateUserFromFireStore(User oldUser, User newUser) {

    }

    @Override
    public void updateMedicineFromFireStore(Medicine oldMedicine, Medicine newMedicine) {

    }

    @Override
    public void deleteUserFromFireStore(User user) {

    }

    @Override
    public void deleteMedicineFromFireStore(Medicine medicine) {

    }

    @Override
    public List<User> getUsersList() {
        return null;
    }

    @Override
    public List<Medicine> getMedicinesList() {
        return null;
    }

    @Override
    public LiveData<List<Medicine>> getMedicineLiveData() {
        return null;
    }

    @Override
    public void setMedicineLiveData(LiveData<List<Medicine>> medicineLiveData) {

    }

    @Override
    public LiveData<List<User>> getUserLiveData() {
        return null;
    }

    @Override
    public void setUserLiveData(LiveData<List<User>> userLiveData) {

    }

    // ay method tanya zy add w retrive wkeda wal header bta3 al methods de lazm yab2a fal remote interface
}
