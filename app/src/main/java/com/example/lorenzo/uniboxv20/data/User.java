package com.example.lorenzo.uniboxv20.data;

import java.io.Serializable;

/**
 * Created by Lorenzo on 18/09/2014.
 */
public class User implements Serializable {

    private String name;
    private String surname;
    private String email;
    private String accessToken;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}


