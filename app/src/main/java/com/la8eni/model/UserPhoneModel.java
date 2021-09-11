package com.la8eni.model;

public class UserPhoneModel
{

    private String userRandomKey;
    private String userID;
    private String userName;
    private String userPhone;

    public UserPhoneModel()
    {
    }

    public UserPhoneModel(String userRandomKey, String userID, String userName, String userPhone)
    {
        this.userRandomKey = userRandomKey;
        this.userID = userID;
        this.userName = userName;
        this.userPhone = userPhone;
    }

    public String getUserRandomKey() {
        return userRandomKey;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }
}
