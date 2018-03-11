package com.example.abhishek.authentication;


/**
 * Created by Abhishek on 08-09-2017.
 */

public class User {
    String name;
    String number;

    public User(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

}
