package com.example.medicalappreminder_java.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medicalappreminder_java.models.User;

import java.util.List;

import io.reactivex.rxjava3.core.Single;


@Dao
public interface UserDao {

    //User
    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    @Query("SELECT * FROM User")
    Single<List<User>> getAllUserSingleList();

/*
    @Query("SELECT * FROM User WHERE email IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);
*/

    @Query("SELECT * FROM User WHERE email IN (:email)")
    List<User> loadAllByEmails(String[] email);

    @Query("SELECT * FROM User WHERE email LIKE :email")
    User findUserByEmail(String email);

/*
    @Query("SELECT * FROM User WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);
*/

    @Insert
    void insertAllUsers(User... users);

    @Insert
    void insertUser(User user);


    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

}
