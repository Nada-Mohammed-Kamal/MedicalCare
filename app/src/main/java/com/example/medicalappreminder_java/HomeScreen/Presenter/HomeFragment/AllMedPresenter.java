package com.example.medicalappreminder_java.HomeScreen.Presenter.HomeFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.medicalappreminder_java.Constants.OnRespondToMethod;
import com.example.medicalappreminder_java.Constants.Status;
import com.example.medicalappreminder_java.Constants.WorkerUtils;
import com.example.medicalappreminder_java.HomeScreen.View.HomeFragment.AllMedAdapter;
import com.example.medicalappreminder_java.HomeScreen.View.HomeFragment.AllMedViewInterface;
import com.example.medicalappreminder_java.NotificationDialog.OnlineUsers;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;

import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.CustomTime;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;
import com.example.medicalappreminder_java.networkConnectivity.NetworkChangeReceiver;
import com.example.medicalappreminder_java.roomdb.UserData;

import java.util.Date;
import java.util.List;

public class AllMedPresenter implements AllMedPresenterInterface, OnlineUsers {

    private AllMedViewInterface _view;
    Context _context;
    RemoteSourceInterface remoteSourceInterface;
    LocalSourceInterface localSourceInterface;
    RepoClass repoClass;
    String userEmail;
    Medicine medicine;
    CustomTime currentTime;
    // (AllMoviesViewInterface  view, Repository repo)
    //instance from data base app database
    public  AllMedPresenter(AllMedViewInterface  view,Context context){
        _context = context;
        this._view = view;
        remoteSourceInterface = new FireStoreHandler();
        localSourceInterface = new ConcreteLocalSource(_context);
        repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,_context);
    }
    @Override
    public void getMeds(Date date) {
        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(_context);
        RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,_context);
        SharedPreferences preferences = _context.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
        userEmail = preferences.getString("emailKey" , "user email") ;
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
    public void changeDoseState(Medicine medicine,CustomTime currentTime,Status status)
    {
        this.medicine = medicine;
        this.currentTime = currentTime;
        switch (status) {
            case Skip:
                //fireStore
                if (NetworkChangeReceiver.isThereInternetConnection == true) {
                    repoClass.getUsersFromFireStore(AllMedPresenter.this , OnRespondToMethod.skip);
                }
                break;
            case Snooze:
                WorkerUtils.addRequestAfterSnooze(medicine, currentTime);
                if (NetworkChangeReceiver.isThereInternetConnection == true) {
                    repoClass.getUsersFromFireStore(AllMedPresenter.this , OnRespondToMethod.snooze);
                }
                break;
            case Take:
                //fireStore
                if (NetworkChangeReceiver.isThereInternetConnection == true) {
                    repoClass.getUsersFromFireStore(AllMedPresenter.this , OnRespondToMethod.take);
                }
                break;
        }
    }
    @Override
    public void onResponse(List<User> userList, OnRespondToMethod method) {
        User fireStoreCurrentUser = new User();
        User oldFireStoreUser = new User();

        Medicine oldMedicine = new Medicine();
        Medicine newMedicine = new Medicine();


        Log.e("TAG", "onResponse: " + userEmail + userList.size());
        for (User fireStoreUser : userList) {
            if (fireStoreUser.getEmail() == null) {

            } else {
                if (fireStoreUser.getEmail().equals(userEmail)) {
                    oldFireStoreUser = fireStoreUser;
                    oldFireStoreUser.setUuid(fireStoreUser.getUuid());
                    List<Medicine> listOfMedications = oldFireStoreUser.getListOfMedications();
                    for (Medicine med : listOfMedications) {
                        if (med.getUuid().equals(medicine.getUuid())) {
                            oldMedicine = med;
                            oldMedicine.setUuid(medicine.getUuid());
                            newMedicine = oldMedicine;
                            if (method == OnRespondToMethod.skip) {
                                //newMedicine.setState("Inactive");
                                List<CustomTime> doseTimes = oldMedicine.getDoseTimes();
                                for(int i=0;i<doseTimes.size();i++){
                                    if(doseTimes.get(i).equals(currentTime)){
                                        doseTimes.get(i).setStatus(Status.Skip);
                                    }
                                }
                                newMedicine.setDoseTimes(doseTimes);
                            } else if(method == OnRespondToMethod.take){
                                List<CustomTime> doseTimes = oldMedicine.getDoseTimes();
                                for(int i=0;i<doseTimes.size();i++){
                                    if(doseTimes.get(i).equals(currentTime)){
                                        doseTimes.get(i).setStatus(Status.Take);
                                    }
                                }
                                newMedicine.setDoseTimes(doseTimes);

                            } else if(method == OnRespondToMethod.snooze){
                                List<CustomTime> doseTimes = oldMedicine.getDoseTimes();
                                for(int i=0;i<doseTimes.size();i++){
                                    if(doseTimes.get(i).equals(currentTime)){
                                        doseTimes.get(i).setStatus(Status.Snooze);
                                        CustomTime newCustomTime = new CustomTime(doseTimes.get(i).getHour(),doseTimes.get(i).getMinute()+10);
                                        doseTimes.add(i+1,newCustomTime);
                                    }
                                }
                                newMedicine.setDoseTimes(doseTimes);
                            }
                            newMedicine.setUuid(medicine.getUuid());
                        }
                    }

                    listOfMedications.remove(oldMedicine);
//                    if (method == OnRespondToMethod.snooze) {
                    listOfMedications.add(newMedicine);
//                    }

                    fireStoreCurrentUser = fireStoreUser;
                    fireStoreCurrentUser.setUuid(fireStoreUser.getUuid());
                    fireStoreCurrentUser.setListOfMedications(listOfMedications);
                    repoClass.updateUserFromFireStore(oldFireStoreUser , fireStoreCurrentUser);
//                    if (method == OnRespondToMethod.delete){
//                        repoClass.deleteMedicineFromFireStore(oldMedicine);
//                    }
                    //else if (method == OnRespondToMethod.suspend) {
                    repoClass.updateMedicineFromFireStore(oldMedicine, newMedicine);
                    //}
                    //repoClass.deleteMedicineFromFireStore(oldMedicine);
                    //repoClass.addMedicineToFireStore(newMedicine);
                }
            }

        }
    }

    @Override
    public void onFailure(String error) {

    }
}
