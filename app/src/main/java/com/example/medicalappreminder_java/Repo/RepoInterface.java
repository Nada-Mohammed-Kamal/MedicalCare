package com.example.medicalappreminder_java.Repo;

import androidx.lifecycle.LiveData;

import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;

import java.util.Date;
import java.util.List;

public interface RepoInterface {
//    LiveData<List<Movie>> getAllMoviesFromDB();
//
//    void getAllMoviesFromAPI(NetworkDelegate networkDelegate);
//
//    void insertMovie(Movie movie);
//    void deleteMovie(Movie movie);

    //local
    //Medicine
    List<Medicine> getAllMedicines();
    List<Medicine> getActiveMedicines();
    List<Medicine> getInActiveMedicines();
    List<Medicine> retrieveMedicinesInACurrentDate(Date date);
    void insertMedicine(Medicine medicine);
    void deleteMedicine(Medicine medicine);
    void updateMedicine(Medicine medicine);

    //User
    List<User> getAllUsers();
    List<User> loadAllByEmails(String[] email);
    User findUserByEmail(String email);
    void insertAllUsers(User... users);
    void insertUser(User user);
    void deleteUser(User user);
    void updateUser(User user);


    //remote
    //put here the methods that are in the Remote source interface


}
