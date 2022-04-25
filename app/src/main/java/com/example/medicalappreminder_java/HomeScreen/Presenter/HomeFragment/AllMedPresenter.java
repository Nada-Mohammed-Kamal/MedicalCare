package com.example.medicalappreminder_java.HomeScreen.Presenter.HomeFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.medicalappreminder_java.DataStorage.SharedPrefrencesModel;
import com.example.medicalappreminder_java.HomeScreen.View.HomeFragment.AllMedViewInterface;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;

import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.CustomTime;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;
import com.example.medicalappreminder_java.roomdb.UserData;

import java.util.Date;
import java.util.List;

public class AllMedPresenter implements AllMedPresenterInterface {

    private AllMedViewInterface _view;
    Context _context;
    RemoteSourceInterface remoteSourceInterface;
    LocalSourceInterface localSourceInterface;
    RepoClass repoClass;
    SharedPrefrencesModel sharedPrefrencesModel;
    // (AllMoviesViewInterface  view, Repository repo)
    //instance from data base app database
    public  AllMedPresenter(AllMedViewInterface  view,Context context){
       _context = context;
        this._view = view;
        remoteSourceInterface = new FireStoreHandler();
        localSourceInterface = new ConcreteLocalSource(_context);
        repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,_context);
        sharedPrefrencesModel = SharedPrefrencesModel.getInstance(context);
    }
    @Override
    public void getMeds(Date date) {
        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(_context);
        RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,_context);
        SharedPreferences preferences = _context.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
        String userEmail = preferences.getString("emailKey" , "user email") ;
        User currentUser = repoClass.findUserByEmail(userEmail);
        List<Medicine> listOfMedications = currentUser.getListOfMedications();
        Log.d("date", "getMeds:********************** "+date.toString());
        List<Medicine> listOfMed  = UserData.getTodayMedicineswithTimeSorted(listOfMedications,date);
        List<CustomTime> customTimes = UserData.getTodayesTimesOfDoses(listOfMed);
        _view.showData(listOfMed,customTimes);
    }

    @Override
    public User getCurrentUser() {
        SharedPreferences preferences = _context.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
        String userEmail = preferences.getString("emailKey" , "user email") ;
        User currentUser = repoClass.findUserByEmail(userEmail);
        return currentUser;
    }

    @Override
    public void updateMedicine(Medicine medicine) {
        repoClass.updateMedicine(medicine);
    }

    @Override
    public void updateUserWithNewMedData(Medicine medicine) {
        User currentUser = getCurrentUser();
        List<Medicine> listOfMedications = currentUser.getListOfMedications();
        for (Medicine oldMed:listOfMedications) {
            if(oldMed.getUuid().equals(medicine.getUuid())){
                listOfMedications.remove(oldMed);
                break;
            }
        }
        listOfMedications.add(medicine);
        currentUser.setListOfMedications(listOfMedications);
        repoClass.updateUser(currentUser);
    }

    @Override
    public User getFriend(String name) {
        String medFriendEmail = sharedPrefrencesModel.getMedFriendEmail(name);
        User currentUser = repoClass.findUserByEmail(medFriendEmail);
        return currentUser;
    }
}
