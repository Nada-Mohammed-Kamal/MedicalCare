package com.example.medicalappreminder_java.Repo.local;

import android.content.Context;

import androidx.lifecycle.LiveData;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;
import com.example.medicalappreminder_java.roomdb.AppDatabase;
import com.example.medicalappreminder_java.roomdb.MedicineDao;
import com.example.medicalappreminder_java.roomdb.UserDao;

import java.util.Date;
import java.util.List;

public class ConcreteLocalSource implements LocalSourceInterface {
    private UserDao userDao;
    private MedicineDao medicineDao;

    //vars
    List<User> allUsers;
    List<Medicine> activeMedicines;
    List<Medicine> inActiveMedicines;

    private static  ConcreteLocalSource localSource = null;
    //private LiveData<List<Movie>> storedMovies;

    public ConcreteLocalSource(Context context) {
        AppDatabase dataBase = AppDatabase.getDBInstance(context.getApplicationContext());
        userDao = dataBase.userDao();
        medicineDao = dataBase.medicineDao();
        //storedMovies = dao.getAllUsers();
    }

    public static ConcreteLocalSource getInstance(Context context){
        if (localSource == null){
            localSource = new ConcreteLocalSource(context);
        }
        return localSource;
    }

    //not implemented yet
    @Override
    public List<Medicine> getAllMedicines() {
        return null;
    }

    @Override
    public List<Medicine> getActiveMedicines() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                activeMedicines = medicineDao.getActiveMedicines();
            }
        }).start();
        return activeMedicines;
    }

    @Override
    public List<Medicine> getInActiveMedicines() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                inActiveMedicines = medicineDao.getInActiveMedicines();
            }
        }).start();
        return inActiveMedicines;
    }

    @Override
    public List<Medicine> retrieveMedicinesInACurrentDate(Date date) {
        new Thread(new Runnable() {
            @Override
            public void run() {
            }
        }).start();
        return null;
    }

    @Override
    public void insertMedicine(Medicine medicine) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                medicineDao.insertMedicine(medicine);
            }
        }).start();
    }

    @Override
    public void deleteMedicine(Medicine medicine) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                 medicineDao.deleteMedicine(medicine);
            }
        }).start();
    }

    @Override
    public void updateMedicine(Medicine medicine) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                medicineDao.updateMedicine(medicine);
            }
        }).start();
    }

    @Override
    public List<User> getAllUsers() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                allUsers = userDao.getAllUsers();
            }
        }).start();
        return allUsers;
    }

    //not implemented yet
    @Override
    public List<User> loadAllByEmails(String[] email) {
        return null;
    }

    //not implemented yet
    @Override
    public User findUserByEmail(String email) {

        final User[] userByEmail = new User[1];
        userByEmail[0] = userDao.findUserByEmail(email);
        return userByEmail[0];
    }

    @Override
    public void insertAllUsers(User... users) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (User user: users) {
                    userDao.insertUser(user);
                }
            }
        }).start();
    }

    @Override
    public void insertUser(User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                userDao.insertUser(user);
            }
        }).start();
    }

    @Override
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    @Override
    public void updateUser(User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                userDao.updateUser(user);
            }
        }).start();
    }

//    @Override
//    public LiveData<List<Movie>> getAllMoviesFromDB() {
//        return storedMovies;
//    }
}
