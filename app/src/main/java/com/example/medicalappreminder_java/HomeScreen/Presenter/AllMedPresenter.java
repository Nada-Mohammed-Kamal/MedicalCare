package com.example.medicalappreminder_java.HomeScreen.Presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.medicalappreminder_java.HomeScreen.View.HomeFragment.AllMedViewInterface;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FirestoreManger;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;
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
        RemoteSourceInterface remoteSourceInterface = new FirestoreManger();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(_context);
        RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,_context);
        SharedPreferences preferences = _context.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
        String userEmail = preferences.getString("emailKey" , "user email") ;
        User currentUser = repoClass.findUserByEmail(userEmail);
        List<Medicine> listOfMedications = currentUser.getListOfMedications();
        _view.showData(listOfMedications);
    }
}
