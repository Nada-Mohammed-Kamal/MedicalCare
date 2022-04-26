package com.example.medicalappreminder_java.networkConnectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;


import com.example.medicalappreminder_java.Constants.OnRespondToMethod;
import com.example.medicalappreminder_java.NotificationDialog.OnlineUsers;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.RepoInterface;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;
import com.example.medicalappreminder_java.networkConnectivity.NetworkUtility;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;


public class NetworkChangeReceiver extends BroadcastReceiver  implements OnlineUsers {

    RepoInterface repo;
    public static boolean isThereInternetConnection = false;
    Single<List<User>> usersSingleList;
    List<User> userList = new ArrayList<>();
    List<Medicine> listOfMedications = new ArrayList<>() ;



    public NetworkChangeReceiver(Context context){
        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
        repo = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);

        repo.getUsersFromFireStore(this , OnRespondToMethod.skip);
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {

//        RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
//        LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
//        repo = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);

        int status = NetworkUtility.getConnectivityStatusString(context);
        Log.e("mando", "network");
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            if (status == NetworkUtility.NETWORK_STATUS_NOT_CONNECTED) {
                Log.e("mando", "no network");
                isThereInternetConnection =false;
            } else {
                isThereInternetConnection = true;
                sync();
                Log.e("mando", "network back");
            }
        }
    }
    public void sync(){
        Log.e("mando", "sync: " );
        new Thread(new Runnable() {
            @Override
            public void run() {

                usersSingleList = repo.getAllUserSingleList();
                usersSingleList.subscribe(new SingleObserver<List<User>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<User> users) {
                        userList = users ;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("TAG", "onError: network change reciever");
                    }
                });
                if(!userList.isEmpty()){
                    for (int i = 0 ; i < userList.size() ; i++){
                        //repo.deleteUserFromFireStore(user);
                        for (Medicine medicine : userList.get(i).getListOfMedications()){
                            repo.addMedicineToFireStore(medicine);
                        }
                        repo.addUserToFireStore(userList.get(i));
                        Log.e("*", "run: " + userList.get(i).getUuid());
                    }
                }
            }
        }).start();
    }

    @Override
    public void onResponse(List<User> userList, OnRespondToMethod method) {

        Log.e("***", "onResponse: " + userList.size());
        for(User firestoreUser : userList){
            listOfMedications = firestoreUser.getListOfMedications();
            if (listOfMedications != null) {
                for (Medicine firestoreMed : listOfMedications) {
                    //repo.deleteMedicineFromFireStore(firestoreMed);
                }
            }
            //repo.deleteUserFromFireStore(firestoreUser);
        }

        
    }

    @Override
    public void onFailure(String error) {

    }
}