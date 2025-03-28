package com.example.MockApi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostGetRequest {
    @JsonProperty("token")
    private String token;
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;
    private String middleName;


        // геттеры и сеттеры



    // Геттеры и сеттеры
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}