package com.passwordmanager.models;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class UserModel {
    public int id;
    public String username;
    private String password;
    public String fullname;

    public UserModel(int id, String username, String password, String fullname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
    }

    public UserModel(String username, String password, String fullname) {
        this.username = username;
        this.setPassword(password);
        this.fullname = fullname;
    }

    public static boolean checkPassword(String plainPass, String encPass) {
        String encoded = Hashing.sha256().hashString(plainPass, StandardCharsets.UTF_8).toString(); 
        return encoded.equals(encPass);
    }

    public void setPassword(String password) {
        this.password = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

    public String getPassword(){
        return this.password;
    }
}