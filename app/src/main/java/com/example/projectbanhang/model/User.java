package com.example.projectbanhang.model;

public class User {
    private int id;
    private String userName;
    private String email;
    private String pass;
    private String phoneNumber;
    private String role;


    public User(String userName, String email, String pass, String phoneNumber, String role) {
        this.userName = userName;
        this.email = email;
        this.pass = pass;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public User(int id, String userName, String email, String pass, String phoneNumber, String role) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.pass = pass;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
    public User() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
