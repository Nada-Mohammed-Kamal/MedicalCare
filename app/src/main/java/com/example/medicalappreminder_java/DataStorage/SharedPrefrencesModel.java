package com.example.medicalappreminder_java.DataStorage;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefrencesModel {

    String emailFromPref , passwordFromPref  , userNameFromPref , UUIDFromPref;
    public static final String preferenceFile = "preferencesFile";
    Context context ;
    public static  SharedPrefrencesModel sharedPrefrencesModel = null ;

    private SharedPrefrencesModel(Context context){
        this.context = context ;
    }
    public static SharedPrefrencesModel getInstance(Context context){
        if(sharedPrefrencesModel == null) {
            sharedPrefrencesModel = new SharedPrefrencesModel(context) ;
        }
        return sharedPrefrencesModel ;
    }

    public void writeInSharedPreferences(String email , String password , String userName){
        SharedPreferences preferences = context.getSharedPreferences(preferenceFile , Context.MODE_PRIVATE) ;
        SharedPreferences.Editor editor = preferences.edit() ;
        editor.putString("emailKey" , email) ;
        editor.putString("passwordKey", password) ;
        editor.putString("userNameKey" , userName) ;
        editor.commit() ;
    }

    public void writeInSharedPreferences(String email , String userName){
        SharedPreferences preferences = context.getSharedPreferences(preferenceFile , Context.MODE_PRIVATE) ;
        SharedPreferences.Editor editor = preferences.edit() ;
        editor.putString("emailKey" , email) ;
        editor.putString("userNameKey" , userName) ;
        editor.commit() ;
    }

    public void writeInSharedPreferences(String UUID){
        SharedPreferences preferences = context.getSharedPreferences(preferenceFile , Context.MODE_PRIVATE) ;
        SharedPreferences.Editor editor = preferences.edit() ;
        editor.putString("UUID_Key" , UUID) ;
        editor.commit() ;
    }



    public void readFromSharedPreferences(){
        SharedPreferences preferences = context.getSharedPreferences(preferenceFile , Context.MODE_PRIVATE) ;
        emailFromPref = preferences.getString("emailKey","N/A");
        passwordFromPref = preferences.getString("passwordKey" , "N/A");
        userNameFromPref = preferences.getString("userNameKey","N/A") ;
    }

    public String readUUIDFromSharedPreferences(){
        SharedPreferences preferences = context.getSharedPreferences(preferenceFile , Context.MODE_PRIVATE) ;
       UUIDFromPref = preferences.getString("UUID_Key","N/A") ;
       return UUIDFromPref ;
    }

    public String getEmailFromPref() {
        return emailFromPref;
    }

    public String getPasswordFromPref() {
        return passwordFromPref;
    }



}
