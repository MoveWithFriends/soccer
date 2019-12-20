package com.example.statyoursoccerteam.Data;

public class Person {

    private String FirstName;
    private String LastName;
    private String phone;
    private String BackNumber;
    private String KNVBNumber;
    private String dateOfBirth;
    private String position;
    private String gender;


    public Person(String firstName, String lastName, String phone, String backNumber, String KNVBNumber, String dateOfBirth, String position, String gender) {
        FirstName = firstName;
        LastName = lastName;
        this.phone = phone;
        BackNumber = backNumber;
        this.KNVBNumber = KNVBNumber;
        this.dateOfBirth = dateOfBirth;
        this.position = position;
        this.gender = gender;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }
}
