package com.example.medicalappreminder_java.roomdb;

import android.text.format.Time;

import androidx.room.TypeConverter;

import com.example.medicalappreminder_java.Constants.Status;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import kotlin.jvm.JvmStatic;

public class Converters {
    //List<User> listOfDependantEmails
    //List<Medicine> ListOfMedications
    @TypeConverter
    public static List<User> convertUsersFromStringToList(String value) {
        Type listType = new TypeToken<List<User>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String convertUsersFromListToString(List<User> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    //list<date>
    @TypeConverter
    public static List<Date> convertMedFromStringToList(String value) {
        Type listType = new TypeToken<List<Date>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String convertMedFromListToString(List<Date> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    //List<User> listOfDependantEmails
    //List<Medicine> ListOfMedications
    @TypeConverter
    public static List<Medicine> convertMedicineFromStringToList(String value) {
        Type listType = new TypeToken<List<Medicine>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String convertMedicineFromListToString(List<Medicine> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    //date
    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }

    //hashmap
    @TypeConverter
    @JvmStatic
    public static HashMap<List<Time>, Status> stringToMap(String value){
        Type listType = new TypeToken<HashMap<List<Time>, Status>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    @JvmStatic
    public static String mapToString(HashMap<List<Time>, Status> value) {
        return value == null ? "" : new Gson().toJson(value);
    }
}