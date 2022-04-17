package com.example.medicalappreminder_java.HomeScreen.Presenter;

import android.content.Context;

import com.example.medicalappreminder_java.HomeScreen.View.HomeFragment.AllMedViewInterface;
import com.example.medicalappreminder_java.roomdb.AppDatabase;

import java.util.List;

public class AllMedPresenter implements AllMedPresenterInterface{

    private AllMedViewInterface _view;
    Context _context;
    // (AllMoviesViewInterface  view, Repository repo)
    //instance from data base app database
    public  AllMedPresenter(AllMedViewInterface  view,Context context){
       _context = context;
        this._view = view;
    }
    @Override
    public void getMeds() {
       // repositoryInterface.getAllMovies(this);
       List med = AppDatabase.getDBInstance(_context).medicineDao().getAllMedicines();
        _view.showData(med);
    }
}
