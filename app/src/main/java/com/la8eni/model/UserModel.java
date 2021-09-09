package com.la8eni.model;

public class UserModel
{

    private String userRandomKey;
    private String userID;
    private String userEmail;
    private String userName;
    private String userCity;

    public UserModel()
    {
    }

    public UserModel(String userRandomKey, String userID, String userEmail, String userName, String userCity)
    {
        this.userRandomKey = userRandomKey;
        this.userID = userID;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userCity = userCity;
    }

    public String getUserRandomKey() {
        return userRandomKey;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserCity() {
        return userCity;
    }
}
