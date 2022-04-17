package com.example.medicalappreminder_java.DrugReminderScreen.Presenter;

import android.content.Context;

import com.example.medicalappreminder_java.DrugReminderScreen.View.DrugReminderViewInterface;

public class DrugReminderPresenter implements DrugReminderPresenterInterface {

    Context context ;
    DrugReminderViewInterface drugReminderView ;


    public DrugReminderPresenter(Context context, DrugReminderViewInterface drugReminderView) {
        this.context = context;
        this.drugReminderView = drugReminderView;
        
    }



}
