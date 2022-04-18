package com.example.medicalappreminder_java.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class User {
    @ColumnInfo(name = "first_name")
    String firstName;
    @ColumnInfo(name = "last_name")
    String lastName;
    @ColumnInfo(name = "id")
    UUID uuid;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "email")
    String email;
    @ColumnInfo(name = "gender")
    String gender;
    @ColumnInfo(name = "birthdate")
    Date birthdate;
    @ColumnInfo(name = "StringWIthListOfDependantEmails")
    //String stringWIthListOfDependantEmails;
    List<User> listOfDependantEmails;
    @ColumnInfo(name = "ListOfMedications")
    //String stringWithListOfMedicationIds;
    List<Medicine> ListOfMedications;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public User(String firstName, String lastName, UUID uuid, @NonNull String email, String gender, Date birthdate, List<User> listOfDependantEmails, List<Medicine> listOfMedications) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uuid = uuid;
        this.email = email;
        this.gender = gender;
        this.birthdate = birthdate;
        this.listOfDependantEmails = listOfDependantEmails;
        ListOfMedications = listOfMedications;
    }

    public User(String firstName, String lastName, String email, String gender, Date birthdate, List<User> listOfDependantEmails, List<Medicine> listOfMedications) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.birthdate = birthdate;
        this.listOfDependantEmails= listOfDependantEmails;
        ListOfMedications = listOfMedications;
    }

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

    public List<User> getListOfDependantEmails() {
        return listOfDependantEmails;
    }

    public void setListOfDependantEmails(List<User> listOfDependantIds) {
        this.listOfDependantEmails = listOfDependantIds;
    }

    public List<Medicine> getListOfMedications() {
        return ListOfMedications;
    }

    public void setListOfMedications(List<Medicine> listOfMedications) {
        ListOfMedications = listOfMedications;
    }
}
