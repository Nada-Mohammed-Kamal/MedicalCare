package com.example.medicalappreminder_java.NotificationDialog;

import com.example.medicalappreminder_java.Constants.OnRespondToMethod;
import com.example.medicalappreminder_java.models.User;

import java.util.List;

public interface OnlineUsers {

    // public void setOnlineUserList(List<User> convertedUserList) ;
    void onResponse(List<User> userList , OnRespondToMethod method);
    void onFailure(String error);
}
