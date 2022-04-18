package com.example.medicalappreminder_java.Repo.remote;



import android.util.Log;

import com.example.medicalappreminder_java.Repo.NetworkDelegate;


public class FirestoreManger implements RemoteSourceInterface {

    public static FirestoreManger firestoreMangerInstance = null;

    public FirestoreManger() {

    }

    public static FirestoreManger getInstance(){
        if (firestoreMangerInstance == null){
            firestoreMangerInstance = new FirestoreManger();
        }
        return firestoreMangerInstance;
    }

    //fetch data mn al fire store
    @Override
    public void fetchData(NetworkDelegate networkDelegate) {
        Log.e("nada", "fetchData:");
    }

    // ay method tanya zy add w retrive wkeda wal header bta3 al methods de lazm yab2a fal remote interface
}
