package com.example.medicalappreminder_java.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medicalappreminder_java.models.Medicine;

import java.util.Date;
import java.util.List;

@Dao
public interface MedicineDao {
    //Medicine

    @Query("SELECT * FROM Medicine")
    List<Medicine> getAllMedicines();

/*
    @Query("SELECT * FROM User WHERE email IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);
*/

    @Query("SELECT * FROM Medicine WHERE state = 'active'")
    List<Medicine> getActiveMedicines();

    @Query("SELECT * FROM Medicine WHERE state = 'inactive'")
    List<Medicine> getInActiveMedicines();

    @Query("SELECT * FROM Medicine WHERE startDate LIKE :date")
    List<Medicine> retrieveMedicinesInACurrentDate(Date date);


/*
    @Query("SELECT * FROM User WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);
*/


    @Insert
    void insertMedicine(Medicine medicine);

    @Delete
    void deleteMedicine(Medicine medicine);

    @Update
    void updateMedicine(Medicine medicine);
}
