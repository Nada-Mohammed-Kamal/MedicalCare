package com.example.medicalappreminder_java.Repo;

import androidx.lifecycle.LiveData;

import com.example.medicalappreminder_java.Constants.OnRespondToMethod;
import com.example.medicalappreminder_java.NotificationDialog.OnlineUsers;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

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
    public Single<List<User>> getAllUserSingleList() ;


    //remote
    //put here the methods that are in the Remote source interface
    public void addUserToFireStore(User user) ;
    public void addMedicineToFireStore(Medicine medicine) ;
    public void getUsersFromFireStore(OnlineUsers onlineUsers , OnRespondToMethod method) ;
    public void getMedicinesFromFireStore() ;
    public void updateUserFromFireStore(User oldUser , User newUser) ;
    public void updateMedicineFromFireStore(Medicine oldMedicine , Medicine newMedicine) ;
    public void deleteUserFromFireStore(User user) ;
    public void deleteMedicineFromFireStore(Medicine medicine) ;
//  public List<User> getUsersList() ;
    public List<Medicine> getMedicinesList();
    public LiveData<List<Medicine>> getMedicineLiveData();
    public void setMedicineLiveData(LiveData<List<Medicine>> medicineLiveData);
    public LiveData<List<User>> getUserLiveData() ;
    public void setUserLiveData(LiveData<List<User>> userLiveData);


}
