package com.example.leon_azraev.videostore;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Leon-Azraev on 06/01/2018.
 */

public class User implements Parcelable {
    String FirstName;
    String LastName;
    String Password;
    String Email;
    String City;
    String Street;
    String UserName;

    protected User(Parcel in) {
        FirstName = in.readString();
        LastName = in.readString();
        Password = in.readString();
        Email = in.readString();
        City = in.readString();
        Street = in.readString();
        UserName = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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
    public User()
    {
        this.FirstName = "";
        this.LastName = "";
        this.Password ="";
        this.Email = "";
        this.City = "";
        this.Street = "";
        this.UserName = "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(FirstName);
        parcel.writeString(LastName);
        parcel.writeString(Password);
        parcel.writeString(Email);
        parcel.writeString(City);
        parcel.writeString(Street);
        parcel.writeString(UserName);

    }
}

