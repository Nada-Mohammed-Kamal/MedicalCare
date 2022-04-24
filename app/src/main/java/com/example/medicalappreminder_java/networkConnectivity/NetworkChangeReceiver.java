package com.example.medicalappreminder_java.networkConnectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {
String TAG = "TAG";
public static boolean isThereInternetConnection = false;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        try{
            if(isOnline(context)){
                Toast.makeText(context , "Network Connected" , Toast.LENGTH_SHORT).show();
                isThereInternetConnection = true;
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
}