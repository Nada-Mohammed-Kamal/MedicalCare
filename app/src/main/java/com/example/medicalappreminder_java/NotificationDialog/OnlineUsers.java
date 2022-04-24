package com.example.medicalappreminder_java.NotificationDialog;

import com.example.medicalappreminder_java.models.User;

import java.util.List;

public interface OnlineUsers {

    // public void setOnlineUserList(List<User> convertedUserList) ;
    void onResponse(List<User> userList);
    void onFailure(String error);
}
