package com.example.medicalappreminder_java.MedModification.Presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.medicalappreminder_java.Constants.WorkerUtils;
import com.example.medicalappreminder_java.MedModification.View.MedModifyActivity;
import com.example.medicalappreminder_java.MedModification.View.MedModifyActivityInterface;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;
import java.util.List;
import java.util.UUID;

public class MedModifyPresenter implements MedModifyPresenterInterface{
    public Context context;
    public MedModifyActivityInterface view;
    public Medicine medicine;

    public MedModifyPresenter(Context context, MedModifyActivityInterface view, Medicine medicine) {
        this.context = context;
        this.view = view;
        this.medicine = medicine;
    }

    @Override
    public void editMedicineInDB() {
        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);
        SharedPreferences preferences = context.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;

        repoClass.updateMedicine(medicine);

        String userEmail = preferences.getString("emailKey" , "user email") ;
        User currentUser = repoClass.findUserByEmail(userEmail);

        List<Medicine> listOfMedications = currentUser.getListOfMedications();
        for (Medicine oldMed:listOfMedications) {
            if(oldMed.getUuid().equals(medicine.getUuid())){
                listOfMedications.remove(oldMed);
                break;
            }
        }

        WorkerUtils.deleteAllRequestsFromWorkManagerByMed(UUID.fromString(medicine.getUuid()));

        Medicine newMed = view.fillMedicineWithFormData(medicine);

        WorkerUtils.addRequestsToWorkManager(newMed);

        listOfMedications.add(newMed);
        currentUser.setListOfMedications(listOfMedications);
        repoClass.updateUser(currentUser);
    }
}
