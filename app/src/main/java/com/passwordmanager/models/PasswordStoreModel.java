package com.passwordmanager.models;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.passwordmanager.utils.Encryptor;

public class PasswordStoreModel {
    public int id;
    public String name, username;
    private String password;
    public String hashkey;
    private double score;
    private int category;
    public FolderModel folder;
    public ArrayList<AdditionalDataModel> addData = new ArrayList<>();
    
    public static final int UNCATEGORIZED = 0;
    public static final int CAT_WEBAPP = 1;
    public static final int CAT_MOBILEAPP = 2;
    public static final int CAT_OTHER = 3;
    
    public static final String [] CATEGORIES = {"Belum terkategori", "Aplikasi Web", "Aplikasi Mobile", "Akun Lainnya"};
    
    public PasswordStoreModel(int id1, String name1, String username1, String password1, int category1){}
    
    public PasswordStoreModel(String name, String username, String plainPass){
        this(name, username, plainPass, UNCATEGORIZED);
    }
    
    public PasswordStoreModel(String name, String username, String plainPass, int category){
        try {
            this.hashkey = Encryptor.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PasswordStoreModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.name = name;
        this.username = username;
        
        this.setPassword(plainPass);
        this.setCategory(category);
    }        
    
    public PasswordStoreModel(String name, String username, String encPass, int category, 
            String hashKey, double score){
        this.name = name;
        this.username = username;
        this.password = encPass;
        this.category = category;
        this.hashkey = hashKey;
        this.score = score;
    }
    
    public PasswordStoreModel(int id, String name, String username, String password, 
            String hashkey, double score,int category) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.hashkey = hashkey;
        this.score = score;
        this.category = category;
    }

    public void setEncryptedPass(String encryptedPass, String hashkey){
        this.password = encryptedPass;
        this.hashkey = hashkey;
    }
    
    public void setPassword(String plainPass){
        String encryptedPass;
        try {
            encryptedPass = Encryptor.encrypt(plainPass, this.hashkey);
            this.password = encryptedPass;
            this.calculateScore(plainPass);
        } catch (Exception ex) {
            Logger.getLogger(PasswordStoreModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getEncPassword(){
        return this.password;
    }
    
    public String getPassword(){
        try {
            return Encryptor.decrypt(this.password, this.hashkey);
        } catch (Exception ex) {
            Logger.getLogger(PasswordStoreModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void setCategory(int category){
        if(category >= 0 || category <= 3){
            this.category = category;
        } else {
            this.category = 0;
        }
    }
    
    public String getCategory(){
        return CATEGORIES[this.category];
    }
    
    public int getCategoryCode(){
        return this.category;
    }
    
    private void calculateScore(String plainPass){
        double len = plainPass.length();
        if(len > 15){
            this.score = 10;
        } else {
            this.score = (len / 15) * 10;
        }
    }

    public double getScore(){
        return this.score;
    }
    
    public String repString(){
        return this.username+" - "+this.password+" - "+this.hashkey+" - "+ String.format("%,.2f", this.score);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
