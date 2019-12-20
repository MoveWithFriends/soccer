package com.example.statyoursoccerteam.Data;

public class PersonBuilder {
    private String firstName;
    private String lastName;
    private String phone;
    private String backNumber;
    private String knvbNumber;
    private String dateOfBirth;
    private String position;
    private String gender;

    public PersonBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PersonBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public PersonBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public PersonBuilder setBackNumber(String backNumber) {
        this.backNumber = backNumber;
        return this;
    }

    public PersonBuilder setKNVBNumber(String knvbNumber) {
        this.knvbNumber = knvbNumber;
        return this;
    }

    public PersonBuilder setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public PersonBuilder setPosition(String position) {
        this.position = position;
        return this;
    }

    public PersonBuilder setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public Person createPerson() {
        return new Person(firstName, lastName, phone, backNumber, knvbNumber, dateOfBirth, position, gender);
    }
}