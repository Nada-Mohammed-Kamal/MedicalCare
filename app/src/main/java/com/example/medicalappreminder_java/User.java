package com.example.medicalappreminder_java;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class User {
    //uuid = UUID.randomUUID();
    UUID uuid;
    String name;
    String email;
    String gender;
    Date birthdate;
    List<User> listOfDependantIds;
    List<Medicine> ListOfMedications;


    public User(UUID id, String name, String email, String gender, Date birthdate, List<User> listOfDependantIds, List<Medicine> listOfMedications) {
        this.uuid = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birthdate = birthdate;
        this.listOfDependantIds = listOfDependantIds;
        ListOfMedications = listOfMedications;
    }

    public User() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setId(UUID id) {
        this.uuid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<User> getListOfDependantIds() {
        return listOfDependantIds;
    }

    public void setListOfDependantIds(List<User> listOfDependantIds) {
        this.listOfDependantIds = listOfDependantIds;
    }

    public List<Medicine> getListOfMedications() {
        return ListOfMedications;
    }

    public void setListOfMedications(List<Medicine> listOfMedications) {
        ListOfMedications = listOfMedications;
    }
}
