package com.example.leon_azraev.videostore;

/**
 * Created by Leon-Azraev on 06/01/2018.
 */

public class User {
    String FirstName;
    String LastName;
    String Password;
    String Email;
    String City;
    String Street;
    String UserName;

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getPassword() {
        return Password;
    }

    public String getEmail() {
        return Email;
    }

    public String getCity() {
        return City;
    }

    public String getStreet() {
        return Street;
    }

    public String getUserName() {
        return UserName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public User(String UserName, String Password, String Email, String FirstName, String LastName, String City, String Street)
    {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Password =Password;
        this.Email = Email;
        this.City = City;
        this.Street = Street;
        this.UserName = UserName;
    }

}

