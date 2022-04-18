package com.example.medicalappreminder_java.dependantInfo.presenter;

import android.content.Context;

import com.example.medicalappreminder_java.dependantInfo.PresenterInterface;
import com.example.medicalappreminder_java.dependantInfo.ViewInterface;
import com.example.medicalappreminder_java.models.User;
import com.example.medicalappreminder_java.roomdb.AppDatabase;

public class DepInfoPresenter implements PresenterInterface {
    Context context;
    ViewInterface viewInterface;
    public DepInfoPresenter(Context context , ViewInterface viewInterface) {
        this.context = context;
        this.viewInterface = viewInterface;
    }

    @Override
    public void addDependant(User user) {
        AppDatabase db = AppDatabase.getDBInstance(context);
        db.userDao().insertUser(user);
        viewInterface.viewToastAddedDependantSuccessfully();
    }


}
