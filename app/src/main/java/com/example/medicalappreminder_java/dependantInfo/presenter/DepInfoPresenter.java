package com.example.medicalappreminder_java.dependantInfo.presenter;

import android.content.Context;

import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.RepoInterface;
import com.example.medicalappreminder_java.dependantInfo.PresenterInterface;
import com.example.medicalappreminder_java.dependantInfo.ViewInterface;
import com.example.medicalappreminder_java.models.User;
import com.example.medicalappreminder_java.roomdb.AppDatabase;

public class DepInfoPresenter implements PresenterInterface {
    ViewInterface viewInterface;
    RepoInterface repoInterface;
    public DepInfoPresenter(RepoInterface repoInterface, ViewInterface viewInterface) {
        this.repoInterface =  repoInterface;
        this.viewInterface = viewInterface;
    }

    @Override
    public void addDependant(User user) {
        repoInterface.insertUser(user);
        viewInterface.viewToastAddedDependantSuccessfully();
    }


}
