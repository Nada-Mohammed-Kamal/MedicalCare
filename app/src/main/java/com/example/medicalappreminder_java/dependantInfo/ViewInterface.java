package com.example.medicalappreminder_java.dependantInfo;

import com.example.medicalappreminder_java.models.User;

import java.util.List;

public interface ViewInterface {
    void viewToastAddedDependantSuccessfully();

    void setUsers(List<User> userList);
    void responseError(String error);
}
