package com.passwordmanager.viewmodels;

import com.passwordmanager.daos.UserDao;
import com.passwordmanager.models.UserModel;
import com.passwordmanager.utils.SessionManager;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author apple
 */
public class LoginViewModel {

    private final UserDao userDao = new UserDao();
    public StringProperty usernameProperty = new SimpleStringProperty();
    public StringProperty passwordProperty = new SimpleStringProperty();
    public Property<String> fullnameProperty = new SimpleStringProperty();

    public String login() {
        UserModel user = userDao.login(usernameProperty.get(), passwordProperty.get());
        if (user != null) {
            SessionManager.getInstance().setSesData("user", user);
            System.out.println("Login berhasil: "+user.fullname);
            return null;
        } else {
            System.out.println("Login gagal");
            return "Username atau Password salah";
        }
    }

    public String register() {
        int idUser = userDao.register(usernameProperty.get(), passwordProperty.get(), fullnameProperty.getValue());
        UserModel user = new UserModel(idUser, usernameProperty.get(), 
                                    passwordProperty.get(), fullnameProperty.getValue());
        if (idUser > 0) {
            SessionManager.getInstance().setSesData("user", user);
            return null;
        } else {
            return "Username sudah terdaftar";
        }
    }
}
