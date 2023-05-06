package com.example.hoseoclub;

public class User {
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userIdToken;
    private String userId;



    public User(String userId, String userIdToken, String userName, String userPassword, String email) {
        this.userId = userId;
        this.userIdToken = userIdToken;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = email;
    }

    public String getUserId() {
        return userId;
    }
    public String getUserIdToken() {
        return userIdToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }


    public String getUserPassword() {
        return userPassword;
    }

}
