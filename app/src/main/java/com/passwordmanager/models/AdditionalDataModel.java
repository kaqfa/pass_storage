package com.passwordmanager.models;

public class AdditionalDataModel {
    public int id;
    public String entryKey;
    public String entryValue;
    public boolean isPassword;

    public AdditionalDataModel() {
    }

    public AdditionalDataModel(int id, String entryKey, String entryValue, boolean isPassword) {
        this.id = id;
        this.entryKey = entryKey;
        this.entryValue = entryValue;
        this.isPassword = isPassword;
    }
    
    public AdditionalDataModel(String entryKey, String entryValue, boolean isPassword) {
        this(0, entryKey, entryKey, isPassword);
    }
    
}