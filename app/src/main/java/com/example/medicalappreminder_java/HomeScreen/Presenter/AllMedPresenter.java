package com.example.medicalappreminder_java.HomeScreen.Presenter;

import com.example.medicalappreminder_java.HomeScreen.View.HomeFragment.AllMedViewInterface;

public class AllMedPresenter implements AllMedPresenterInterface{

    private AllMedViewInterface _view;
    // (AllMoviesViewInterface  view, Repository repo)
    //instance from data base app database
    public  AllMedPresenter(AllMedViewInterface  view){
        //this._repo = repo;
        this._view = view;
    }
    @Override
    public void getMeds() {
       // repositoryInterface.getAllMovies(this);
    }
}
