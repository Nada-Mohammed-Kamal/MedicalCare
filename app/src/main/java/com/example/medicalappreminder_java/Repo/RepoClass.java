package com.example.medicalappreminder_java.Repo;

import android.content.Context;

import androidx.lifecycle.LiveData;


import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;

import java.util.Date;
import java.util.List;

public class RepoClass implements RepoInterface {
    Context context;
    RemoteSourceInterface remoteSourceInterface;
    LocalSourceInterface localSourceInterface;
    private static RepoClass repository = null;

    public static RepoClass getInstance(RemoteSourceInterface remoteSourceInterface,LocalSourceInterface localSourceInterface, Context context){
        if (repository == null){
            repository = new RepoClass(context , remoteSourceInterface , localSourceInterface);
        }
        return repository;
    }

    public RepoClass(Context context, RemoteSourceInterface remoteSourceInterface, LocalSourceInterface localSourceInterface) {
        this.context = context;
        this.remoteSourceInterface = remoteSourceInterface;
        this.localSourceInterface = localSourceInterface;
    }

    //local
    @Override
    public List<Medicine> getAllMedicines() {
        return localSourceInterface.getAllMedicines();
    }

    @Override
    public List<Medicine> getActiveMedicines() {
        return localSourceInterface.getActiveMedicines();
    }

    @Override
    public List<Medicine> getInActiveMedicines() {
        return localSourceInterface.getInActiveMedicines();
    }

    @Override
    public List<Medicine> retrieveMedicinesInACurrentDate(Date date) {
        return localSourceInterface.retrieveMedicinesInACurrentDate(date);
    }

    @Override
    public void insertMedicine(Medicine medicine) {
        localSourceInterface.insertMedicine(medicine);
    }

    @Override
    public void deleteMedicine(Medicine medicine) {
        localSourceInterface.deleteMedicine(medicine);
    }

    @Override
    public void updateMedicine(Medicine medicine) {
        localSourceInterface.updateMedicine(medicine);
    }

    @Override
    public List<User> getAllUsers() {
        return localSourceInterface.getAllUsers();
    }

    @Override
    public List<User> loadAllByEmails(String[] email) {
        return localSourceInterface.loadAllByEmails(email);
    }

    @Override
    public User findUserByEmail(String email) {
        return localSourceInterface.findUserByEmail(email);
    }

    @Override
    public void insertAllUsers(User... users) {
        localSourceInterface.insertAllUsers(users);
    }

    @Override
    public void insertUser(User user) {
        localSourceInterface.insertUser(user);
    }

    @Override
    public void deleteUser(User user) {
        localSourceInterface.deleteUser(user);
    }

    @Override
    public void updateUser(User user) {
        localSourceInterface.updateUser(user);
    }

    //end of local part

    //remote

    //override the methods that are in the remote interface that are related to the remote networking

    //end of remote






//    @Override
//    public LiveData<List<Movie>> getAllMoviesFromDB() {
//        return localSourceInterface.getAllMoviesFromDB();
//    }
}