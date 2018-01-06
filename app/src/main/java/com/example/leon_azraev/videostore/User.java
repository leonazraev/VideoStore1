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
    public User(String UserName,String Password,String Email,String FirstName,String LastName,String City,String Street)
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

