package com.example.medicalappreminder_java.Repo.local;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;

import java.util.Date;
import java.util.List;

public interface LocalSourceInterface {
//    void insertMovie(Movie movie);
//    void deleteMovie(Movie movie);
//    LiveData<List<Movie>>getAllMoviesFromDB();

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


}
