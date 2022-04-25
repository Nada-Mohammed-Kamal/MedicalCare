package com.example.medicalappreminder_java.models;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@IgnoreExtraProperties
public class User {
    @ColumnInfo(name = "first_name")
    String firstName;
    @ColumnInfo(name = "last_name")
    String lastName;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    String uuid;
    @ColumnInfo(name = "email")
    String email = "";
    @ColumnInfo(name = "gender")
    String gender;
    @ColumnInfo(name = "birthdate")
    Date birthdate;
    @ColumnInfo(name = "StringWIthListOfDependantEmails")
    //String stringWIthListOfDependantEmails;
    List<User> listOfDependant;
    @ColumnInfo(name = "ListOfMedications")
    List<User> listOfInvitations;

    public List<User> getListOfInvitations() {
        return listOfInvitations;
    }

    public void setListOfInvitations(List<User> listOfInvitations) {
        this.listOfInvitations = listOfInvitations;
    }

    @ColumnInfo(name = "ListOfInvitations")
    //String stringWithListOfMedicationIds;

    List<Medicine> ListOfMedications;
    @ColumnInfo(name = "fireStoreId")
    private String fireStoreId;

    public List<User> getMedFriend() {
        return medFriend;
    }

    public void setMedFriend(List<User> medFriend) {
        this.medFriend = medFriend;
    }

    @ColumnInfo(name = "medFriendId")
    List<User> medFriend;
    //String medFriendId;

    public String getFireStoreId() {
        return fireStoreId;
    }

    public void setFireStoreId(String fireStoreId) {
        this.fireStoreId = fireStoreId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }



    public User(String firstName, String email) {
        uuid = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.email = email;
        this.ListOfMedications = new ArrayList<>();
        this.listOfDependant = new ArrayList<>();

    }

    @Keep
    public User() {
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public List<User> getListOfDependant() {
        return listOfDependant;
    }

    public void setListOfDependant(List<User> listOfDependantIds) {
        this.listOfDependant = listOfDependantIds;
    }

    public List<Medicine> getListOfMedications() {
        return ListOfMedications;
    }

    public void setListOfMedications(List<Medicine> listOfMedications) {
        ListOfMedications = listOfMedications;
    }

    ///////////////////////////
    public static List<User> convertUsersFromStringToList(String value) {
        Type listType = new TypeToken<List<User>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    public static String convertUsersFromListToString(List<User> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
