package com.example.medicalappreminder_java.dependantInfo;

import android.content.Context;

import com.example.medicalappreminder_java.models.User;

import java.util.List;

public interface PresenterInterface {

    void addDependant(User user);
    void addDependantToTheCurrentUser(User user , Context context , String userEmail);
    public void setUserListFromFireStore(List<User> convertedUserList) ;
}
