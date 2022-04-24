package com.example.medicalappreminder_java.networkConnectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.medicalappreminder_java.Constants.OnRespondToMethod;
import com.example.medicalappreminder_java.HomeScreen.View.HomeFragment.AllMedAdapter;
import com.example.medicalappreminder_java.NotificationDialog.OnlineUsers;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.RepoInterface;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;

import java.util.ArrayList;
import java.util.List;

public class NetworkChangeReceiver extends BroadcastReceiver implements OnlineUsers {
String TAG = "TAG";
RepoInterface repoClass;
Context context;
public static boolean isThereInternetConnection = false;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        this.context = context;
        try{
            if(isOnline(context)){
                Toast.makeText(context , "Network Connected" , Toast.LENGTH_SHORT).show();
                isThereInternetConnection = true;

                //when there is network ... drop the fireStore table and take the tables from room to fireStore
                RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
                LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
                repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);


                //fireStore
                repoClass.getUsersFromFireStore(this , OnRespondToMethod.skip);


            }else {
                Toast.makeText(context , "No Network Connection" , Toast.LENGTH_SHORT).show();
                isThereInternetConnection = false;
            }
        }catch (NullPointerException e){
            Log.i(TAG, "isOnline in Network change Reciver: produced an errrorrr -----------------");
            e.printStackTrace();
        }
    }

    public boolean isOnline(Context context){
        try{
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return (networkInfo!=null && networkInfo.isConnected());
         }catch (NullPointerException e){
            Log.i(TAG, "isOnline in Network change Reciver: produced an errrorrr -----------------");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onResponse(List<User> userList, OnRespondToMethod method) {

        for(User firestoreUser : userList){
            List<Medicine> listOfMedications = firestoreUser.getListOfMedications();
            for(Medicine firestoreMed : listOfMedications){
                repoClass.deleteMedicineFromFireStore(firestoreMed);
            }
            repoClass.deleteUserFromFireStore(firestoreUser);
        }

        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);
        List<User> allUsers = repoClass.getAllUsers();
        if(allUsers != null){
            for (User roomUser: allUsers){
                List<Medicine> listOfMedications = roomUser.getListOfMedications();
                for(Medicine firestoreMed : listOfMedications){
                    repoClass.addMedicineToFireStore(firestoreMed);
                }
                repoClass.addUserToFireStore(roomUser);
            }
        }

    }

    @Override
    public void onFailure(String error) {

    }
}