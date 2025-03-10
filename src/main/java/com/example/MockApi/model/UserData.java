package com.example.MockApi.model;

public class UserData {
    private final String token;
    private final String password;

    public UserData(String token, String password) {
        this.token = token;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public String getPassword() {
        return password;
    }
}

